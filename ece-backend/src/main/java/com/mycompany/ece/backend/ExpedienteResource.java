package com.mycompany.ece.backend;

import jakarta.annotation.security.RolesAllowed;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/api/expedientes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ExpedienteResource {

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
}