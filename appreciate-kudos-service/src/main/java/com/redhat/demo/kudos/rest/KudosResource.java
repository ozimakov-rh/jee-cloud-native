package com.redhat.demo.kudos.rest;

import com.redhat.demo.kudos.entity.Kudos;
import com.redhat.demo.kudos.service.KudosService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/api/kudos")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class KudosResource {

    @Inject
    KudosService kudosService;

    @GET
    public List<Kudos> fetchKudos() {
        return kudosService.listAllKudos();
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
    public Kudos fetchKudos(@PathParam("id") Long id) {
        var kudosFound = kudosService.listAllKudos().stream()
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
        this.fetchKudos(id);
        kudosService.deleteKudos(id);
    }

}