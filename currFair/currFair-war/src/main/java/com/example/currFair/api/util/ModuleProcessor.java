

package com.example.currFair.api.util;

import java.lang.reflect.Type;
import java.util.logging.Logger;

import javax.ws.rs.ext.Provider;

import org.jboss.resteasy.plugins.guice.GuiceResourceFactory;
import org.jboss.resteasy.spi.Registry;
import org.jboss.resteasy.spi.ResourceFactory;
import org.jboss.resteasy.spi.ResteasyProviderFactory;
import org.jboss.resteasy.util.GetRestful;

import com.google.inject.Binding;
import com.google.inject.Guice;
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
 * 
 */
public class ModuleProcessor
{
    private static final Logger logger = Logger.getLogger(ModuleProcessor.class.getName());

    private final Registry registry;
    private final ResteasyProviderFactory providerFactory;

    public ModuleProcessor(final Registry registry, final ResteasyProviderFactory providerFactory)
    {
        this.registry = registry;
        this.providerFactory = providerFactory;
    }

    public void process(final Module... modules)
    {
        final Injector injector = Guice.createInjector(modules);
        processInjector(injector);
    }

    public Injector process(final Stage stage, final Module... modules)
    {
        final Injector injector = Guice.createInjector(stage, modules);
        processInjector(injector);
        return injector;
    }

    public Injector process(final Iterable<Module> modules)
    {
        final Injector injector = Guice.createInjector(modules);
        processInjector(injector);
        return injector;
    }

    public Injector process(final Stage stage, final Iterable<Module> modules)
    {
        final Injector injector = Guice.createInjector(stage, modules);
        processInjector(injector);
        return injector;
    }

    @SuppressWarnings("rawtypes") private void processInjector(final Injector injector)
    {
        for (final Binding<?> binding : injector.getBindings().values())
        {
            final Type type = binding.getKey().getTypeLiteral().getType();
            if (type instanceof Class)
            {
                final Class<?> beanClass = (Class) type;
                if (GetRestful.isRootResource(beanClass))
                {
                    final ResourceFactory resourceFactory = new GuiceResourceFactory(binding.getProvider(), beanClass);
                    logger.info(String.format("Registering factory for %s", beanClass.getName()));
                    registry.addResourceFactory(resourceFactory);
                }
                if (beanClass.isAnnotationPresent(Provider.class))
                {
                    logger.info(String.format("Registering provider instance for %s", beanClass.getName()));
                    providerFactory.registerProviderInstance(binding .getProvider().get());
                }
            }
        }
    }
}
