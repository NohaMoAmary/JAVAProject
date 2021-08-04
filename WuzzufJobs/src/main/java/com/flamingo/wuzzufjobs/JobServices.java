/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flamingo.wuzzufjobs;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import smile.data.DataFrame;
import tech.tablesaw.api.Table;

@Path ("/jobService")
public class JobServices {
    jobDAO jDAO = new jobDAOImp () ;
    @GET 
    @Produces(MediaType.TEXT_PLAIN)
    public String read()
    {
    jDAO.readCSV();
    return "welcom ";
    }
    ////////////////////////////////////////////////////////////////////////////
    @GET
    @Path ("/jobs")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Job> viewJobList()
    {
     jDAO.readCSV();
     return jDAO.getJobList();
    }
    
    ////////////////////////////////////////////////////////////////////////////
    @GET
    @Path ("/cleanData")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Job> cleardata()
    {
     jDAO.readCSV();
     return jDAO.cleanData();
    }
    ////////////////////////////////////////////////////////////////////////////
    @GET
    @Path ("count")
    @Produces(MediaType.APPLICATION_JSON)
    public Response count_comp()
    {
     Gson gson = new Gson();
     Map<String, Integer> jcount = jDAO.countJobsCompany();
     String jsonString = gson.toJson(jcount);
     return Response.status(Response.Status.OK).entity(jsonString).build();
     
    }
     ////////////////////////////////////////////////////////////////////////////
    @GET
    @Path ("/plotcount")
    @Produces(MediaType.TEXT_PLAIN)
    public String plotcount()
    {
     return jDAO.plotData();
    }
    ////////////////////////////////////////////////////////////////////////////
     ////////////////////////////////////////////////////////////////////////////
    @GET
    @Path ("/plottop10")
    @Produces(MediaType.TEXT_PLAIN)
    public String plotTop()
    {
     return jDAO.plotLastComData();
    }
    ////////////////////////////////////////////////////////////////////////////
    @GET
    @Path ("poptitle")
    @Produces(MediaType.APPLICATION_JSON)
    public Response popTitle()
    {
     Gson gson = new Gson();
     Map<String, Integer> jcount = jDAO.popularJobTitles();
     String jsonString = gson.toJson(jcount);
     return Response.status(Response.Status.OK).entity(jsonString).build();
     
    }
     ////////////////////////////////////////////////////////////////////////////
    @GET
    @Path ("/plotpoptitle")
    @Produces(MediaType.TEXT_PLAIN)
    public String plotTitle()
    {
     return jDAO.plotPopTitle();
    }
    ////////////////////////////////////////////////////////////////////////////
    @GET
    @Path ("poparea")
    @Produces(MediaType.APPLICATION_JSON)
    public Response popArea()
    {
     Gson gson = new Gson();
     Map<String, Integer> jcount = jDAO.MostPopArea();
     String jsonString = gson.toJson(jcount);
     return Response.status(Response.Status.OK).entity(jsonString).build();
     
    }
     ////////////////////////////////////////////////////////////////////////////
    @GET
    @Path ("/plotarea")
    @Produces(MediaType.TEXT_PLAIN)
    public String plotPopArea()
    {
     return jDAO.plotMPArea();
    }
    ////////////////////////////////////////////////////////////////////////////
    @GET
    @Path ("skills")
    @Produces(MediaType.APPLICATION_JSON)
    public Response count_skills()
    {
     Gson gson = new Gson();
     Map<String, Integer> skills = jDAO.Skills();
     String jsonString = gson.toJson(skills);
     return Response.status(Response.Status.OK).entity(jsonString).build();
     
    }
    ////////////////////////////////////////////////////////////////////////////
    
}