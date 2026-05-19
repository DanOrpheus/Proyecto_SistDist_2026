package com.mycompany.ece.backend;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.time.LocalDateTime;
import java.util.ArrayList;
import org.eclipse.microprofile.jwt.JsonWebToken;

@Path("/api/expedientes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ExpedienteResource {

    @Inject
    JsonWebToken jwt;

    @GET
    @RolesAllowed("User")
    public Response listarExpedientes() {
        return Response.ok(Expediente.listAll()).build();
    }

    @GET
    @Path("/{curp}")
    @RolesAllowed("User")
    public Response consultarExpediente(@PathParam("curp") String curp) {

        Expediente expediente = Expediente.findByCurp(curp);

        if (expediente == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Expediente no encontrado para el CURP: " + curp)
                    .build();
        }

        return Response.ok(expediente).build();
    }

    @POST
    @RolesAllowed("User")
    public Response guardarExpediente(Expediente nuevoExpediente) {

        nuevoExpediente.persist();

        return Response.status(Response.Status.CREATED)
                .entity(nuevoExpediente)
                .build();
    }

    @POST
    @Path("/{curp}/solicitar-acceso")
    @RolesAllowed("Doctor")
    public Response solicitarAcceso(@PathParam("curp") String curp, SolicitudAcceso solicitud) {
        Expediente expediente = Expediente.findByCurp(curp);
        SolicitudAcceso datosSolicitud = solicitud == null ? new SolicitudAcceso() : solicitud;

        if (expediente == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Expediente no encontrado para el CURP: " + curp)
                    .build();
        }

        if (expediente.medicoAutorizado == null) {
            expediente.medicoAutorizado = new Expediente.MedicoAutorizado();
        }

        expediente.medicoAutorizado.nombreCompleto = valor(datosSolicitud.nombreMedico, "Dra. Sofia Martinez");
        expediente.medicoAutorizado.cedulaProfesional = valor(datosSolicitud.cedulaProfesional, "CED-928374");
        expediente.medicoAutorizado.especialidad = valor(datosSolicitud.especialidad, "Medicina general");
        expediente.medicoAutorizado.institucion = valor(datosSolicitud.institucion, "Hospital General de Hermosillo");
        expediente.medicoAutorizado.motivoAcceso = valor(datosSolicitud.motivoAcceso, "Cita medica programada");
        expediente.medicoAutorizado.estadoAcceso = "Pendiente";
        expediente.medicoAutorizado.fechaSolicitud = LocalDateTime.now().toString();
        expediente.medicoAutorizado.fechaAutorizacion = null;

        agregarHistorial(
                expediente,
                expediente.medicoAutorizado.nombreCompleto,
                "Medico",
                "Solicito acceso al expediente",
                "Cedula profesional",
                "Pendiente");

        expediente.update();
        return Response.ok(expediente).build();
    }

    @GET
    @Path("/tutores/{curpTutor}/menores")
    @RolesAllowed("Patient")
    public Response listarMenoresBajoTutela(@PathParam("curpTutor") String curpTutor) {
        return Response.ok(Expediente.list("tutor.curp", curpTutor)).build();
    }

    @POST
    @Path("/{curp}/notas")
    @RolesAllowed("Doctor")
    public Response agregarNotaClinica(@PathParam("curp") String curp, NotaClinicaRequest nota) {
        Expediente expediente = Expediente.findByCurp(curp);

        if (expediente == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Expediente no encontrado para el CURP: " + curp)
                    .build();
        }

        if (nota == null || nota.diagnostico == null || nota.diagnostico.isBlank()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("El diagnostico es obligatorio para agregar una nota clinica.")
                    .build();
        }

        if (expediente.consultas == null) {
            expediente.consultas = new ArrayList<>();
        }

        Expediente.ConsultaClinica consulta = new Expediente.ConsultaClinica();
        consulta.fecha = LocalDateTime.now().toLocalDate().toString();
        consulta.medico = valor(nota.medico, "Dra. Sofia Martinez");
        consulta.cedulaProfesional = valor(nota.cedulaProfesional, "CED-928374");
        consulta.diagnostico = nota.diagnostico;
        consulta.tratamiento = valor(nota.tratamiento, "Seguimiento clinico indicado");
        consulta.notas = valor(nota.notas, "Nota agregada durante consulta medica autorizada.");
        expediente.consultas.add(0, consulta);

        agregarHistorial(
                expediente,
                consulta.medico,
                "Medico",
                "Agrego nota clinica",
                "Sesion medica autorizada",
                "Registrado");

        expediente.update();
        return Response.ok(expediente).build();
    }

    @POST
    @Path("/{curp}/archivos")
    @RolesAllowed("Doctor")
    public Response agregarArchivoClinico(@PathParam("curp") String curp, ArchivoClinicoRequest archivo) {
        Expediente expediente = Expediente.findByCurp(curp);

        if (expediente == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Expediente no encontrado para el CURP: " + curp)
                    .build();
        }

        if (archivo == null || archivo.dataUrl == null || archivo.dataUrl.isBlank()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("El archivo es obligatorio.")
                    .build();
        }

        if (archivo.mimeType == null
                || (!archivo.mimeType.startsWith("image/") && !"application/pdf".equals(archivo.mimeType))) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Solo se permiten imagenes y documentos PDF.")
                    .build();
        }

        Expediente.ArchivoClinico nuevoArchivo = new Expediente.ArchivoClinico();
        nuevoArchivo.tipo = valor(archivo.tipo, archivo.mimeType.startsWith("image/") ? "Imagenologia" : "Laboratorio");
        nuevoArchivo.nombre = valor(archivo.nombre, archivo.mimeType.startsWith("image/") ? "Imagen clinica" : "Documento PDF");
        nuevoArchivo.fecha = LocalDateTime.now().toLocalDate().toString();
        nuevoArchivo.descripcion = valor(archivo.descripcion, "Archivo clinico cargado al expediente.");
        nuevoArchivo.urlDemo = nuevoArchivo.nombre;
        nuevoArchivo.mimeType = archivo.mimeType;
        nuevoArchivo.dataUrl = archivo.dataUrl;
        nuevoArchivo.tamanoBytes = archivo.tamanoBytes;

        if (archivo.mimeType.startsWith("image/")) {
            if (expediente.imagenes == null) {
                expediente.imagenes = new ArrayList<>();
            }
            expediente.imagenes.add(0, nuevoArchivo);
        } else {
            if (expediente.documentosPdf == null) {
                expediente.documentosPdf = new ArrayList<>();
            }
            expediente.documentosPdf.add(0, nuevoArchivo);
        }

        agregarHistorial(
                expediente,
                nombreActor("Personal medico"),
                "Medico",
                archivo.mimeType.startsWith("image/") ? "Cargo imagen clinica" : "Cargo documento PDF",
                "Sesion medica autorizada",
                "Registrado");

        expediente.update();
        return Response.ok(expediente).build();
    }

    @POST
    @Path("/{curp}/autorizar-acceso")
    @RolesAllowed("Patient")
    public Response autorizarAcceso(@PathParam("curp") String curp) {
        Expediente expediente = Expediente.findByCurp(curp);

        if (expediente == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Expediente no encontrado para el CURP: " + curp)
                    .build();
        }

        if (expediente.medicoAutorizado == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("No hay solicitud de acceso para autorizar.")
                    .build();
        }

        if ("Menor".equals(rolActor("")) && expediente.tutor != null) {
            return Response.status(Response.Status.FORBIDDEN)
                    .entity("Los menores de edad requieren autorizacion del tutor legal.")
                    .build();
        }

        expediente.medicoAutorizado.estadoAcceso = "Autorizado";
        expediente.medicoAutorizado.fechaAutorizacion = LocalDateTime.now().toString();
        expediente.medicoAutorizado.accesoDesde = LocalDateTime.now().toString();
        expediente.medicoAutorizado.accesoHasta = LocalDateTime.now().plusHours(12).toString();

        agregarHistorial(
                expediente,
                nombreActor(expediente.nombreCompleto),
                expediente.tutor == null ? "Paciente" : rolActor("Tutor"),
                "Autorizo acceso remoto",
                "Huella dactilar",
                "Autorizado");

        expediente.update();
        return Response.ok(expediente).build();
    }

    @POST
    @Path("/{curp}/revocar-acceso")
    @RolesAllowed("Patient")
    public Response revocarAcceso(@PathParam("curp") String curp) {
        Expediente expediente = Expediente.findByCurp(curp);

        if (expediente == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Expediente no encontrado para el CURP: " + curp)
                    .build();
        }

        if (expediente.medicoAutorizado == null) {
            expediente.medicoAutorizado = new Expediente.MedicoAutorizado();
        }

        if ("Menor".equals(rolActor("")) && expediente.tutor != null) {
            return Response.status(Response.Status.FORBIDDEN)
                    .entity("Los menores de edad requieren autorizacion del tutor legal.")
                    .build();
        }

        expediente.medicoAutorizado.estadoAcceso = "Revocado";
        expediente.medicoAutorizado.accesoHasta = LocalDateTime.now().toString();

        agregarHistorial(
                expediente,
                nombreActor(expediente.nombreCompleto),
                expediente.tutor == null ? "Paciente" : rolActor("Tutor"),
                "Revoco acceso al expediente",
                "Huella dactilar",
                "Revocado");

        expediente.update();
        return Response.ok(expediente).build();
    }

    private static void agregarHistorial(
            Expediente expediente,
            String actor,
            String rol,
            String accion,
            String metodo,
            String resultado) {
        if (expediente.historialAccesos == null) {
            expediente.historialAccesos = new ArrayList<>();
        }

        Expediente.AccesoExpediente acceso = new Expediente.AccesoExpediente();
        acceso.fechaHora = LocalDateTime.now().toString();
        acceso.actor = actor;
        acceso.rol = rol;
        acceso.accion = accion;
        acceso.metodoAutorizacion = metodo;
        acceso.resultado = resultado;
        expediente.historialAccesos.add(0, acceso);
    }

    private static String valor(String valor, String respaldo) {
        return valor == null || valor.isBlank() ? respaldo : valor;
    }

    private String nombreActor(String respaldo) {
        String nombre = jwt == null ? null : jwt.getClaim("name");
        return valor(nombre, respaldo);
    }

    private String rolActor(String respaldo) {
        String rol = jwt == null ? null : jwt.getClaim("actorRole");
        return valor(rol, respaldo);
    }

    public static class SolicitudAcceso {
        public String nombreMedico;
        public String cedulaProfesional;
        public String especialidad;
        public String institucion;
        public String motivoAcceso;
    }

    public static class NotaClinicaRequest {
        public String medico;
        public String cedulaProfesional;
        public String diagnostico;
        public String tratamiento;
        public String notas;
    }

    public static class ArchivoClinicoRequest {
        public String tipo;
        public String nombre;
        public String descripcion;
        public String mimeType;
        public String dataUrl;
        public Long tamanoBytes;
    }
}
