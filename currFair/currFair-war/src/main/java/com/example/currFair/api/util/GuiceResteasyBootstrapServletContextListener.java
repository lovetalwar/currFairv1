

package com.example.currFair.api.util;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.jboss.resteasy.plugins.server.servlet.ResteasyBootstrap;
import org.jboss.resteasy.spi.Registry;
import org.jboss.resteasy.spi.ResteasyProviderFactory;

import com.google.inject.Injector;
import com.google.inject.Module;
import com.google.inject.Stage;


/**
 * IMPORTANT! This class was extracted from the library
 * resteasy-guice-2.3.1-GA.jar in order to modifiy it and make the Injector
 * instance available in the ServletContext. If the library is updated and this
 * class was modified by the resteasy team, we will have to review this code to
 * be sure that everything is working properly.
 *
 */
public class GuiceResteasyBootstrapServletContextListener extends ResteasyBootstrap implements ServletContextListener
{
	
	
    private Injector injector = null;

    protected Injector getInjector() { return injector; }

   public void contextInitialized(final ServletContextEvent event)
   {
      super.contextInitialized(event);
      final ServletContext context = event.getServletContext();
      final Registry registry = (Registry) context.getAttribute(Registry.class.getName());
      final ResteasyProviderFactory providerFactory = (ResteasyProviderFactory) context.getAttribute(ResteasyProviderFactory.class.getName());
      final ModuleProcessor processor = new ModuleProcessor(registry, providerFactory);
      final Stage stage = getStage(context);
      final List<Module> modules = getModules(context);
      
      if (stage == null)
      {
         injector = processor.process(modules);
      }
      else
      {
    	  injector = processor.process(stage, modules);
      }
      
      context.setAttribute(Injector.class.getName(), injector);
      
   }

    private Stage getStage(ServletContext context)
    {
        final String stageAsString = context.getInitParameter("resteasy.guice.stage");
    

        if (stageAsString == null)
            return null;
        
        try
        {
            return Stage.valueOf(stageAsString.trim());
        }
        catch (IllegalArgumentException e)
        {
            throw new RuntimeException( "Injector stage is not defined properly. " + stageAsString + " is wrong value." + " Possible values are PRODUCTION, DEVELOPMENT, TOOL.");
        }
    }

    @SuppressWarnings("rawtypes") private List<Module> getModules(final ServletContext context)
    {
        final List<Module> result = new ArrayList<Module>();
        final String modulesString = context.getInitParameter("resteasy.guice.modules");
        if (modulesString != null)
        {
            final String[] moduleStrings = modulesString.trim().split(",");
            for (final String moduleString : moduleStrings)
            {
                try
                {
					
                    final Class clazz = Thread.currentThread().getContextClassLoader() .loadClass(moduleString.trim());
                    final Module module = (Module) clazz.newInstance();
                    result.add(module);
                }
                catch (ClassNotFoundException e)
                {
                    throw new RuntimeException(e);
                }
                catch (IllegalAccessException e)
                {
                    throw new RuntimeException(e);
                }
                catch (InstantiationException e)
                {
                    throw new RuntimeException(e);
                }
            }

        }
        return result;
    }

    public void contextDestroyed(final ServletContextEvent event) { }
}
