package com.mycompany.ece.backend;

import io.quarkus.mongodb.panache.PanacheMongoEntity;
import io.quarkus.mongodb.panache.common.MongoEntity;
import java.util.List;

@MongoEntity(collection = "expedientes")
public class Expediente extends PanacheMongoEntity {

    public String curpPaciente;
    public String nombreCompleto;
    public Integer edad;
    public String fechaNacimiento;
    public String sexo;
    public String domicilio;
    public String tipoSangre;
    public List<String> alergias;
    public String notasClinicas;
    public Tutor tutor;
    public MedicoAutorizado medicoAutorizado;
    public Biometria biometriaPaciente;
    public List<ConsultaClinica> consultas;
    public List<ArchivoClinico> imagenes;
    public List<ArchivoClinico> documentosPdf;
    public List<AccesoExpediente> historialAccesos;

    public static Expediente findByCurp(String curp) {
        return find("curpPaciente", curp).firstResult();
    }

    public static class Tutor {
        public String nombreCompleto;
        public String parentesco;
        public String curp;
        public String telefono;
    }

    public static class MedicoAutorizado {
        public String nombreCompleto;
        public String cedulaProfesional;
        public String especialidad;
        public String institucion;
        public String accesoDesde;
        public String accesoHasta;
        public String motivoAcceso;
        public String estadoAcceso;
        public String fechaSolicitud;
        public String fechaAutorizacion;
    }

    public static class Biometria {
        public String tipo;
        public String estado;
        public String fechaValidacion;
    }

    public static class ConsultaClinica {
        public String fecha;
        public String medico;
        public String cedulaProfesional;
        public String diagnostico;
        public String tratamiento;
        public String notas;
    }

    public static class ArchivoClinico {
        public String tipo;
        public String nombre;
        public String fecha;
        public String descripcion;
        public String urlDemo;
        public String mimeType;
        public String dataUrl;
        public Long tamanoBytes;
    }

    public static class AccesoExpediente {
        public String fechaHora;
        public String actor;
        public String rol;
        public String accion;
        public String metodoAutorizacion;
        public String resultado;
    }
}
