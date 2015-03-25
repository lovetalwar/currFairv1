
package com.example.currFair.api.manager.impl;

import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.example.currFair.api.dao.MessageDAO;
import com.example.currFair.api.manager.MessageManager;
import com.example.currFair.api.model.MessageLocation;
import com.example.currFair.api.model.TradeMessage;
import com.google.inject.Inject;
import com.google.inject.name.Named;


public class MessageManagerImpl extends AbstractManagerImpl implements MessageManager
{

	@Inject
	MessageDAO messageDAO;
	
	@Inject @Named("geocoding.api.key")
	String key;
	
	@Inject @Named("geocoding.api.url")
	String GEOCODER_REQUEST_PREFIX_FOR_XML;
	
    public synchronized Map<String, List<TradeMessage>> processMessages(List<TradeMessage> tradeMessages) throws Exception
    {
    	messageDAO.saveMessages(tradeMessages);
    	messageDAO.saveMessagesLocations(getGeocoding(tradeMessages));
    	return createReport();
    }
    
    private Map<String, List<TradeMessage>> createReport()
    {
    	List<TradeMessage> fullList = messageDAO.listMessages();
    	Map<String, List<TradeMessage>>  map = new java.util.HashMap<String, List<TradeMessage>>();
    	List<TradeMessage> newList;
    	for (TradeMessage tradeMessage : fullList)
    	{
    		String key =tradeMessage.getCurrencyFrom()+"-"+tradeMessage.getCurrencyTo();
    		if(map.get(key) == null)
    		{
    			 newList = new ArrayList<TradeMessage>();
    			newList.add(tradeMessage);
    			map.put(key, newList);
    		}else{
    			newList =map.get(key);
    			newList.add(tradeMessage);
    		}
		}
    	return map;
    }
    
    public  Map<String, List<TradeMessage>> listMessages(){
    	 return createReport();
    	
    }
   
    public  List<TradeMessage> listMessagesByUserId(String userId)
    {
    	List<TradeMessage> tradeMessages = new ArrayList<TradeMessage>();
    	List<TradeMessage> fullList = messageDAO.listMessages();
    	for (TradeMessage tradeMessage : fullList) {
			if(tradeMessage.getUserId().equalsIgnoreCase(userId))
				tradeMessages.add(tradeMessage);
		}
    	return tradeMessages;
    }
    
    public  List<MessageLocation> listMessagesLocations(){
    	return messageDAO.listMessagesLocations(); 
   	
   }
    public List<MessageLocation> getGeocoding(List<TradeMessage> tradeMessages) throws Exception 
	{
		List<MessageLocation> messageLocations = new ArrayList<MessageLocation>();
		try {
				for (TradeMessage tradeMessage : tradeMessages) 
				{
					String address = tradeMessage.getOriginatingCountry();
					Document geocoderResultDocument = getGeoCoding(address, key);
					XPath xpath = XPathFactory.newInstance().newXPath();
	
					// extract the result
					NodeList resultNodeList = null;
	
					resultNodeList = (NodeList) xpath.evaluate( "/GeocodeResponse/*", geocoderResultDocument, XPathConstants.NODESET);
	
					String status = "";
					String message = "";
	
					for (int i = 0; i < resultNodeList.getLength(); ++i) 
					{
						Node node = resultNodeList.item(i);
						if ("status".equals(node.getNodeName()))
							status = node.getTextContent();
						if ("message".equals(node.getNodeName()))
							message = node.getTextContent();
					}
	
					if (status.equalsIgnoreCase("OVER_QUERY_LIMIT")) 
					{
						Thread.sleep(1000);
						geocoderResultDocument = getGeoCoding(address, key);
						resultNodeList = (NodeList) xpath.evaluate( "/GeocodeResponse/*", geocoderResultDocument, XPathConstants.NODESET);
						for (int i = 0; i < resultNodeList.getLength(); ++i) {
							Node node = resultNodeList.item(i);
							if ("status".equals(node.getNodeName()))
								status = node.getTextContent();
							if ("message".equals(node.getNodeName()))
								message = node.getTextContent();
						}
					}
					
	
					System.out.println(status + "----" + message);
					resultNodeList = (NodeList) xpath.evaluate( "/GeocodeResponse/result[1]/geometry/location/*", geocoderResultDocument, XPathConstants.NODESET);
					Double lat = Double.NaN;
					Double lng = Double.NaN;
					for (int i = 0; i < resultNodeList.getLength(); ++i) {
						Node node = resultNodeList.item(i);
						if ("lat".equals(node.getNodeName()))
							lat = Double.parseDouble(node.getTextContent());
						if ("lng".equals(node.getNodeName()))
							lng = Double.parseDouble(node.getTextContent());
					}
					MessageLocation messageLocation = new MessageLocation();
					messageLocation.setLat(lat);
					messageLocation.setLng(lng);
					messageLocations.add(messageLocation);
					

			}
				return messageLocations;	
		} catch (XPathExpressionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
    
    public Document getGeoCoding(String address,String key)
	{
		Document geocoderResultDocument = null;
		
		try{
			  URL url = new URL(GEOCODER_REQUEST_PREFIX_FOR_XML + "?address=" + URLEncoder.encode(address, "UTF-8") + "&sensor=false&key="+key);
	          HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	
	          
	          try {
	            
	            conn.connect();
	            InputSource geocoderResultInputSource = new InputSource(conn.getInputStream());
	
	            // read result and parse into XML Document
	            geocoderResultDocument = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(geocoderResultInputSource);
	          } finally {
	            conn.disconnect();
	          }
         
		}catch (Exception e)
		{
			
			e.printStackTrace();
			
		}
		
		return geocoderResultDocument;
	}
    
}
