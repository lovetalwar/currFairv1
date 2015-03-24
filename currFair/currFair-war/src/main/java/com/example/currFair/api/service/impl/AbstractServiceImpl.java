
package com.example.currFair.api.service.impl;

import com.example.currFair.api.util.ApiTransaction;
import com.google.inject.Inject;
import com.google.inject.name.Named;



public abstract class AbstractServiceImpl
{
    @Inject @Named("transaction")
    protected com.google.inject.Provider<ApiTransaction> transactionProvider;
    
   
}
