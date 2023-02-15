package com.redhat.demo.achievement.service;

import com.redhat.demo.common.Kudos;
import io.quarkus.oidc.client.filter.OidcClientFilter;
import org.eclipse.microprofile.faulttolerance.CircuitBreaker;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import java.util.List;

@Path("/api/admin/kudos")
@RegisterRestClient(configKey = "kudos-api")
@OidcClientFilter
public interface KudosService {

    @GET
    List<Kudos> listKudos(@QueryParam("user") String user);

}
