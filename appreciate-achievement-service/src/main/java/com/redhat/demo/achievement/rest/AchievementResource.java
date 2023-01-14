package com.redhat.demo.achievement.rest;

import com.redhat.demo.achievement.entity.Achievement;
import com.redhat.demo.achievement.service.AchievementService;
import io.quarkus.security.Authenticated;
import org.eclipse.microprofile.jwt.JsonWebToken;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/api/achievement")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Authenticated
public class AchievementResource {

    @Inject
    AchievementService achievementService;

    @Inject
    JsonWebToken accessToken;

    private String _username() {
        return accessToken.getClaim("preferred_username").toString();
    }

    @GET
    public List<Achievement> fetchAchievement() {
        return achievementService.listAchievements(_username());
    }

    @Path("/{id}")
    public Achievement fetchAchievementById(@PathParam("id") Long id) {
        var achievementFound = fetchAchievement().stream()
                .filter(achievement -> achievement.getId().equals(id))
                .findAny();
        return achievementFound.orElseThrow(() -> new NotFoundException("Achievement record with id='" + id + "' was not found"));
    }

}