

package com.example.currFair.api.service;

import java.util.List;

import javax.annotation.security.PermitAll;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.spi.validation.ValidateRequest;

import com.example.currFair.api.model.TradeMessage;



@Path("/messages")
@ValidateRequest
public interface MessageService
{

    @PermitAll
    @POST
    @Consumes("application/json")
    @Produces("application/json")
    @Path("/consume/")
    public Response consumeMessages(List<TradeMessage> tradeMessages) throws WebApplicationException;
    
    @PermitAll
    @GET
    @Produces("application/json")
    @Path("/list/")
    public Response listMessages() throws WebApplicationException;

    @PermitAll
    @GET
    @Produces("application/json")
    @Path("/{searchParam}/listMessagesByUserId/")
    public Response listMessagesByUserId(@PathParam("searchParam") String searchParam) throws WebApplicationException;
    
    @PermitAll
    @GET
    @Produces("application/json")
    @Path("/messagesLocations/")
    public Response listMessagesLocations() throws WebApplicationException;
    
	
}
