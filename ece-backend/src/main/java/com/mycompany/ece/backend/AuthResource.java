package com.mycompany.ece.backend;

import io.smallrye.jwt.build.Jwt;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.Set;

@Path("/auth")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AuthResource {

    @POST
    @Path("/login")
    public Response login(UsuarioLogin usuario) {
        String username = usuario.username == null ? "" : usuario.username.trim().toLowerCase();
        String password = usuario.password == null ? "" : usuario.password;

        if (esUsuario(username, password, "ivan.ochoa@ece.mx", "Ivan#1997", "paciente", "1234")) {
            return Response.ok(crearTokenPaciente(
                    username,
                    "Ivan Alejandro Ochoa Vega",
                    "OOVI970830HSRCGV01",
                    "Paciente"
            )).build();
        }

        if (esUsuario(username, password, "mariana.ruiz@ece.mx", "Tutor#2012", "tutor", "1234")) {
            return Response.ok(crearTokenPaciente(
                    username,
                    "Mariana Ruiz Lopez",
                    "RULM850901MSONPR09",
                    "Tutor"
            )).build();
        }

        if (esUsuario(username, password, "valeria.ramirez@ece.mx", "Valeria#2012", "valeria", "1234")) {
            return Response.ok(crearTokenPaciente(
                    username,
                    "Valeria Ramirez Ruiz",
                    "NINA120304MSONRR05",
                    "Menor"
            )).build();
        }

        if (esUsuario(username, password, "pedro.lara@ece.mx", "Pedro#1990", "pedro", "1234")) {
            return Response.ok(crearTokenPaciente(
                    username,
                    "Pedro Lara Beltran",
                    "LABP900808HDFRRL02",
                    "Paciente"
            )).build();
        }

        if (esUsuario(username, password, "sofia.martinez@salud.gob.mx", "Sofia#928374", "medico", "1234")) {
            return Response.ok(crearTokenDoctor(
                    username,
                    "Dra. Sofia Martinez",
                    "CED-928374",
                    "Medicina general",
                    "Hospital General de Hermosillo"
            )).build();
        }

        if (esUsuario(username, password, "andres.lopez@salud.gob.mx", "Andres#774410", "doctor", "1234")) {
            return Response.ok(crearTokenDoctor(
                    username,
                    "Dr. Andres Lopez",
                    "CED-774410",
                    "Pediatria",
                    "Clinica Infantil del Norte"
            )).build();
        }

        if (esUsuario(username, password, "elena.torres@salud.gob.mx", "Elena#118902", "admin", "1234")) {
            return Response.ok(crearTokenDoctor(
                    username,
                    "Dra. Elena Torres",
                    "CED-118902",
                    "Cardiologia",
                    "Hospital Privado Reforma"
            )).build();
        }

        return Response.status(Response.Status.UNAUTHORIZED)
                .entity("Credenciales invalidas")
                .build();
    }

    private boolean esUsuario(
            String username,
            String password,
            String correo,
            String contrasena,
            String alias,
            String contrasenaAlias) {
        return (correo.equals(username) && contrasena.equals(password))
                || (alias.equals(username) && contrasenaAlias.equals(password));
    }

    private String crearTokenPaciente(String username, String nombre, String curpPaciente, String actorRol) {
        return Jwt.issuer("ece-backend")
                .upn(username)
                .groups(Set.of("User", "Patient"))
                .claim("role", "Patient")
                .claim("name", nombre)
                .claim("curp", curpPaciente)
                .claim("actorRole", actorRol)
                .expiresIn(3600)
                .sign();
    }

    private String crearTokenDoctor(
            String username,
            String nombre,
            String cedula,
            String especialidad,
            String institucion) {
        return Jwt.issuer("ece-backend")
                .upn(username)
                .groups(Set.of("User", "Doctor"))
                .claim("role", "Doctor")
                .claim("name", nombre)
                .claim("cedulaProfesional", cedula)
                .claim("especialidad", especialidad)
                .claim("institucion", institucion)
                .expiresIn(3600)
                .sign();
    }
}
