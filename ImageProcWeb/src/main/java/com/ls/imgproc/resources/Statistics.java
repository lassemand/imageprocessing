package com.ls.imgproc.resources;

import com.ls.imgproc.service.StatisticsService;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.io.IOException;

/**
 * Created by Lasse on 21/07/2016.
 */
@Path("/statistics")
public class Statistics {

    StatisticsService statisticsService = StatisticsService.uniqueInstance;

    @Path("save")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String saveStatistics(String input) throws IOException {
        new JSONValidator();
        statisticsDao.insertNewStatistiscsTest(statisticsDao.convertStringToBSON(input));
        return "{}";
    }
}
