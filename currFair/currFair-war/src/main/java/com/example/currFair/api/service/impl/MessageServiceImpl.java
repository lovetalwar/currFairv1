

package com.example.currFair.api.service.impl;

import java.util.List;
import java.util.Map;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import com.example.currFair.api.manager.MessageManager;
import com.example.currFair.api.model.MessageLocation;
import com.example.currFair.api.model.TradeMessage;
import com.example.currFair.api.service.MessageService;
import com.example.currFair.api.util.ApiTransaction;
import com.google.inject.Inject;

/**
 * REST implementation of Message Service(I)
 * 
 */
public class MessageServiceImpl  extends AbstractServiceImpl implements MessageService
{
    @Inject
    private MessageManager messageManager;

   
    public Response consumeMessages(List<TradeMessage> tradeMessages) throws WebApplicationException
    {

        ApiTransaction transaction = transactionProvider.get().init("consumeMessages");
        
       
        Map<String, List<TradeMessage>> map = null;
		try {
			map = messageManager.processMessages(tradeMessages);
		} catch (Exception e) {
			e.printStackTrace();
		}
      
        ResponseBuilder builder = Response.ok(map);
        return transaction.complete(builder.build());

    }
    
    
    
    public Response listMessages() throws WebApplicationException
    {

        ApiTransaction transaction = transactionProvider.get().init("listMessages");
        
       
        Map<String, List<TradeMessage>> map= messageManager.listMessages();
      
        ResponseBuilder builder = Response.ok(map);
        return transaction.complete(builder.build());

    }
    
    
    public Response listMessagesByUserId(String userId) throws WebApplicationException
    {

        ApiTransaction transaction = transactionProvider.get().init("listMessagesByUserId");
        
       
        List<TradeMessage> map= messageManager.listMessagesByUserId(userId);
      
        ResponseBuilder builder = Response.ok(map);
        return transaction.complete(builder.build());

    }
    
    public Response listMessagesLocations() throws WebApplicationException
    {

        ApiTransaction transaction = transactionProvider.get().init("listMessages");
        
       
        List<MessageLocation> messageLocations = messageManager.listMessagesLocations();
      
        ResponseBuilder builder = Response.ok(messageLocations);
        return transaction.complete(builder.build());

    }
}
