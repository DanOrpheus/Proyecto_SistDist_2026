package com.mycompany.ece.backend;

import io.smallrye.jwt.build.Jwt;
import jakarta.ws.rs.*;
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

        if ("admin".equals(usuario.username)
                && "1234".equals(usuario.password)) {

            String token = Jwt.issuer("ece-backend")
                    .upn(usuario.username)
                    .groups(Set.of("User"))
                    .expiresIn(3600)
                    .sign();

            return Response.ok(token).build();
        }

        return Response.status(Response.Status.UNAUTHORIZED)
                .entity("Credenciales inválidas")
                .build();
    }
}