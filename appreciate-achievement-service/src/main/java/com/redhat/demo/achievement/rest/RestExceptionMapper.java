package com.redhat.demo.achievement.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.UUID;

@Provider
public class RestExceptionMapper implements ExceptionMapper<Exception> {

    private static final Logger log = LoggerFactory.getLogger(RestExceptionMapper.class);

    @Override
    public Response toResponse(Exception e) {
        UUID errorUUID = UUID.randomUUID();
        log.error("Error [" + errorUUID + "]", e);
        Response.Status status;
        switch (e.getClass().getSimpleName()) {
            case "NotFoundException":
                status = Response.Status.NOT_FOUND;
                break;
            case "BadRequestException":
                status = Response.Status.BAD_REQUEST;
                break;
            default:
                status = Response.Status.INTERNAL_SERVER_ERROR;
                break;
        }
        return Response.status(status).entity(new ErrorResponse(errorUUID.toString(), e.getClass().getSimpleName(), e.getMessage())).build();
    }
}
