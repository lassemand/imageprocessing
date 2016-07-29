package com.ls.imgproc.resources;

import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import com.ls.imgproc.service.StatisticsService;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;

/**
 * Created by Lasse on 21/07/2016.
 */
@Path("/statistics")
public class Statistics {

    StatisticsService statisticsService = StatisticsService.uniqueInstance;
    JSONValidator validator = new JSONValidator();

    @Path("save")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response saveStatistics(@Context HttpServletRequest req, String input) throws IOException {
        String prefix = "http://" + req.getServerName() + ":" + req.getServerPort();
        try {
            if(validator.doValidate(prefix, input, JSONValidator.ValidationType.STATISTICS)){
                statisticsService.insertStatistics(statisticsService.convertStringToBSON(input));
                return Response.ok().build();
            }
        } catch (ProcessingException e) {
            e.printStackTrace();
        }
        return Response.serverError().entity("Invalid input").build();
    }

    @Path("retrieve")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveStatistics() throws IOException {
        String statistics = statisticsService.getStatistics();
        return Response.ok().entity(statistics).build();
    }



}
