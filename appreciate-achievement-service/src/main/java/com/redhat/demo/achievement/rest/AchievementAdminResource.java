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

@Path("/api/admin/achievement")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@RolesAllowed("app-admin")
public class AchievementAdminResource {

    @Inject
    AchievementService achievementService;

    @Inject
    JsonWebToken accessToken;

    @GET
    public List<Achievement> fetchAllAchievements() {
        return achievementService.listAllAchievements();
    }

    @GET
    @Path("/{id}")
    public Achievement fetchAchievementById(@PathParam("id") Long id) {
        var achievementFound = fetchAllAchievements().stream()
                .filter(achievement -> achievement.getId().equals(id))
                .findAny();
        return achievementFound.orElseThrow(() -> new NotFoundException("Achievement record with id='" + id + "' was not found"));
    }

    @POST
    public Achievement postAchievement(Achievement achievement) {
        // Validation
        if (achievement.getOwner() == null || achievement.getType() == null) {
            throw new BadRequestException("Both owner and type must be provided");
        }
        return achievementService.grantAchievement(achievement.getOwner(), achievement.getType());
    }

    @PUT
    public List<Achievement> refreshAchievements(@QueryParam("user") String user) {
        return achievementService.refreshAchievements(user);
    }

    @DELETE
    @Path("/{id}")
    public void deleteAchievement(@PathParam("id") Long id) {
        fetchAchievementById(id);
        achievementService.deleteAchievement(id);
    }

}