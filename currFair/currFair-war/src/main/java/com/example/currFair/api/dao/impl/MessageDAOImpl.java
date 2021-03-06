
package com.example.currFair.api.dao.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.example.currFair.api.dao.MessageDAO;
import com.example.currFair.api.model.MessageLocation;
import com.example.currFair.api.model.TradeMessage;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Query;



public class MessageDAOImpl extends AbstractDAO implements MessageDAO
{
	
    public void  saveMessages(List<TradeMessage> tradeMessages)
    {
    	
    	for (TradeMessage tradeMessage : tradeMessages) {
    			    
    	    Entity messageEntity = new Entity("TradeMessage");
    	    messageEntity.setProperty("userId", tradeMessage.getUserId());
    	    messageEntity.setProperty("amountBuy", tradeMessage.getAmountBuy());
    	    messageEntity.setProperty("amountSell", tradeMessage.getAmountSell());
    	    messageEntity.setProperty("currencyFrom", tradeMessage.getCurrencyFrom());
    	    messageEntity.setProperty("currencyTo", tradeMessage.getCurrencyTo());
    	    messageEntity.setProperty("originatingCountry", tradeMessage.getOriginatingCountry());
    	    messageEntity.setProperty("rate", tradeMessage.getRate());
    	    messageEntity.setProperty("timePlaced", tradeMessage.getTimePlaced());
    	    messageEntity.setProperty("crtDate", Calendar.getInstance().getTime());
    	    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    	    datastore.put(messageEntity);
    	   }
    	
    }
    
	
    
    public List<TradeMessage> listMessages()
    {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();	    
	    Query query = new Query("TradeMessage").addSort("crtDate", Query.SortDirection.DESCENDING);
	    List entities = datastore.prepare(query).asList(FetchOptions.Builder.withLimit( 100 ));
	    List<TradeMessage> tradeMessages = new ArrayList<TradeMessage>();
	    if( entities != null && entities.size() > 0 ) {
	    	for( Object ob	: entities ) {
	    		Entity entity = (Entity) ob;
	    		TradeMessage tradeMessage = new TradeMessage();
	    		tradeMessage.setAmountBuy((Double)entity.getProperty("amountBuy"));
	    		tradeMessage.setAmountSell((Double)entity.getProperty("amountSell"));
	    		tradeMessage.setCurrencyFrom((String)entity.getProperty("currencyFrom"));
	    		tradeMessage.setCurrencyTo((String)entity.getProperty("currencyTo"));
	    		tradeMessage.setUserId((String)entity.getProperty("userId"));
	    		tradeMessage.setOriginatingCountry((String)entity.getProperty("originatingCountry"));
	    		tradeMessage.setTimePlaced((String)entity.getProperty("timePlaced"));
	    		tradeMessage.setRate((Double)entity.getProperty("rate"));
	    		tradeMessages.add(tradeMessage);
	    	}
	    }
	    return tradeMessages;
    }
    
    public List<MessageLocation> listMessagesLocations()
    {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();	    
	    Query query = new Query("MessageLocation");
	    List entities = datastore.prepare(query).asList(FetchOptions.Builder.withDefaults());
	    List<MessageLocation> messageLocations = new ArrayList<MessageLocation>();
	    if( entities != null && entities.size() > 0 ) {
	    	for( Object ob	: entities ) {
	    		Entity entity = (Entity) ob;
	    		MessageLocation messageLocation = new MessageLocation();
	    		messageLocation.setLat((Double)entity.getProperty("lat"));
	    		messageLocation.setLng((Double)entity.getProperty("lng"));
	    		
	    		messageLocations.add(messageLocation);
	    	}
	    }
	    return messageLocations;
    }
   
    public void  saveMessagesLocations(List<MessageLocation> messageLocations)
    {
    	for (MessageLocation messageLocation : messageLocations) {	    
    	    Entity locationEntity = new Entity("MessageLocation");
    	    locationEntity.setProperty("lat", messageLocation.getLat());
    	    locationEntity.setProperty("lng", messageLocation.getLng());
    	   
    	    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    	    datastore.put(locationEntity);
		}
    	
    }
}
