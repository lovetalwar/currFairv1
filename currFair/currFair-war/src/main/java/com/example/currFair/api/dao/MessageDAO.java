

package com.example.currFair.api.dao;

import java.util.List;

import com.example.currFair.api.model.MessageLocation;
import com.example.currFair.api.model.TradeMessage;


public interface MessageDAO
{

    void  saveMessages(List<TradeMessage> tradeMessages);
    
    List<TradeMessage> listMessages();
    
    List<MessageLocation> listMessagesLocations();
    
    void  saveMessagesLocations(List<MessageLocation> messageLocations);
    
}
