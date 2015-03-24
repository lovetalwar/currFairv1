
package com.example.currFair.api.manager.impl;

import com.example.currFair.api.util.ApiTransaction;
import com.example.currFair.api.util.UserContextHolder;
import com.google.inject.Inject;
import com.google.inject.name.Named;

/**
 * Inject the Context Holder in every manager.
 *
 */
public abstract class AbstractManagerImpl
{

    @Inject @Named("userContextHolder")
    protected com.google.inject.Provider<UserContextHolder> contextProvider;
    
    @Inject @Named("transaction")
    protected com.google.inject.Provider<ApiTransaction> transactionProvider;
    
  
}
