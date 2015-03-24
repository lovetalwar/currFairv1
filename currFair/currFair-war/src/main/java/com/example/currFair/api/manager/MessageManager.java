

package com.example.currFair.api.manager;

import java.util.List;
import java.util.Map;

import com.example.currFair.api.model.MessageLocation;
import com.example.currFair.api.model.TradeMessage;


public interface MessageManager
{

    public  Map<String, List<TradeMessage>> processMessages(List<TradeMessage> tradeMessages) throws Exception;
    
    
    public  Map<String, List<TradeMessage>> listMessages();
    
    public  List<TradeMessage> listMessagesByUserId(String userId);
    
    public  List<MessageLocation> listMessagesLocations();
    
}
