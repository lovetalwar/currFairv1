package com.example.currFair.api.dao.impl;

import com.example.currFair.api.util.ApiTransaction;
import com.example.currFair.api.util.UserContextHolder;
import com.google.inject.Inject;
import com.google.inject.name.Named;

/**
 * Abstract class for DAOs to manage the user context holder.
 * 
 * @author Juan Pablo Francisconi (jpfrancisconi@affsys.com)
 */
public abstract class AbstractDAO
{
    @Inject @Named("userContextHolder")
    protected com.google.inject.Provider<UserContextHolder> contextProvider;
    
    @Inject @Named("transaction")
    protected com.google.inject.Provider<ApiTransaction> transactionProvider;
    
   
}
