package com.redhat.demo.kudos.rest;

import com.redhat.demo.common.Kudos;
import com.redhat.demo.kudos.service.KudosService;
import io.quarkus.security.Authenticated;
import org.eclipse.microprofile.jwt.JsonWebToken;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/api/kudos")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Authenticated
public class KudosResource {

    @Inject
    KudosService kudosService;

    @Inject
    JsonWebToken accessToken;

    private String _username() {
        return accessToken.getClaim("preferred_username").toString();
    }

    @GET
    public List<Kudos> fetchKudos() {
        return kudosService.listKudos(_username());
    }

    @POST
    public Kudos postKudos(Kudos kudos) {
        if (kudos.getUserFrom() == null || kudos.getUserTo() == null) {
            throw new BadRequestException("Both userFrom and userTo must be provided");
        }
        if (!kudos.getUserFrom().equalsIgnoreCase(_username())) {
            throw new BadRequestException("Wrong user in the userFrom field");
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
        var kudosFound = this.fetchKudos().stream()
                .filter(kudos -> kudos.getId().equals(id)).findAny();
        return kudosFound.orElseThrow(() -> new NotFoundException("Kudos record with id='" + id + "' was not found"));
    }

    @PUT
    @Path("/{id}")
    public Kudos updateKudos(@PathParam("id") Long id, Kudos kudos) {
        throw new NotSupportedException("Not implemented yet");
    }

    @DELETE
    @Path("/{id}")
    public void deleteKudos(@PathParam("id") Long id) {
        Kudos kudos = this.fetchKudosById(id);
        if (kudos.getUserFrom().equalsIgnoreCase(_username())) {
            kudosService.deleteKudos(id);
        } else {
            throw new BadRequestException("User can delete a kudos record only being its author");
        }
    }

}