package com.redhat.demo.kudos.rest;

import com.redhat.demo.kudos.entity.Kudos;
import com.redhat.demo.kudos.service.KudosService;
import io.quarkus.oidc.IdToken;
import io.quarkus.security.Authenticated;
import org.eclipse.microprofile.jwt.JsonWebToken;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/api/kudos")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Authenticated // TODO add admin role features
public class KudosResource {

    @Inject
    KudosService kudosService;

    @Inject
    JsonWebToken accessToken;

    @GET
    public List<Kudos> fetchKudos() {
        var user = accessToken.getClaim("preferred_username").toString();
        return kudosService.listKudos(user);
    }

    @POST
    public Kudos postKudos(Kudos kudos) {
        // Validation
        if (kudos.getUserFrom() == null || kudos.getUserTo() == null) {
            throw new BadRequestException("Both userFrom and userTo must be provided");
        }
        return kudosService.createKudos(
                kudos.getUserFrom(),
                kudos.getUserTo(),
                kudos.getDescription()
        );
    }

    @GET
    @Path("/{id}")
    public Kudos fetchKudosById(@PathParam("id") Long id) {
        var user = accessToken.getClaim("preferred_username").toString();
        var kudosFound = this.fetchKudos().stream()
                .filter(kudos -> kudos.getId().equals(id)).findAny();
        return kudosFound.orElseThrow(() -> new NotFoundException("Kudos record with id='" + id + "' was not found"));
    }

    @PUT
    @Path("/{id}")
    public Kudos updateKudos(@PathParam("id") Long id, Kudos kudos) {
        throw new RuntimeException("Not implemented yet");
    }

    @DELETE
    @Path("/{id}")
    public void deleteKudos(@PathParam("id") Long id) {
        // Silly check that the requested record actually exists.
        // If it doesn't, it throws NotFoundException.
        this.fetchKudosById(id);
        kudosService.deleteKudos(id);
    }

}