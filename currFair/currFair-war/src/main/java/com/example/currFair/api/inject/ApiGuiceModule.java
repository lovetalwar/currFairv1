
package com.example.currFair.api.inject;

import java.util.Properties;

import org.jboss.resteasy.plugins.interceptors.SecurityInterceptor;

import com.example.currFair.api.dao.MessageDAO;
import com.example.currFair.api.dao.impl.MessageDAOImpl;
import com.example.currFair.api.manager.MessageManager;
import com.example.currFair.api.manager.impl.MessageManagerImpl;
import com.example.currFair.api.service.MessageService;
import com.example.currFair.api.service.impl.MessageServiceImpl;
import com.example.currFair.api.util.ApiTransaction;
import com.example.currFair.api.util.PropertiesUtil;
import com.example.currFair.api.util.UserContextHolder;
import com.google.inject.AbstractModule;
import com.google.inject.Key;
import com.google.inject.Singleton;
import com.google.inject.name.Names;
import com.google.inject.servlet.RequestScoped;
import com.google.inject.servlet.SessionScoped;


public class ApiGuiceModule extends AbstractModule
{

    private Properties properties;

    public ApiGuiceModule()
    {
        loadProperties();
    }

    public Properties loadProperties()
    {
        properties = PropertiesUtil.getInstance().loadProperties("/api.properties");
        String path = System.getProperty("api.properties.path");
        if (path != null && this.properties != null)
        {
            properties.putAll(PropertiesUtil.getInstance().loadProperties(path));
        }
        return properties;
    }

    @Override
    public void configure()
    {
        
        Names.bindProperties(binder(), properties);
        
        initContext();

        initProviders();

        initMisc();

        initManagers();
        
        initDaos();

        initServices();
        
        
    }
    
    private void initContext()
    {
        bindScope(RequestScoped.class, com.google.inject.servlet.ServletScopes.REQUEST);
        bindScope(SessionScoped.class, com.google.inject.servlet.ServletScopes.SESSION);
        bind(Key.get(UserContextHolder.class, Names.named("userContextHolder"))).to(UserContextHolder.class).in(RequestScoped.class);
        bind(Key.get(ApiTransaction.class, Names.named("transaction"))).to(ApiTransaction.class).in(RequestScoped.class);
      
    }

    private void initManagers()
    {
        bind(MessageManager.class).to(MessageManagerImpl.class).in(Singleton.class);
       
    }
    
    private void initDaos()
    {
        bind(MessageDAO.class).to(MessageDAOImpl.class).in(Singleton.class);
       
    }

    private void initServices()
    {
        bind(MessageService.class).to(MessageServiceImpl.class).in(Singleton.class);
       
    }

    private void initMisc()
    {
       
    }

    
    private void initProviders()
    {
      
        bind(SecurityInterceptor.class).in(Singleton.class);
     
    }
    
  
}
