
package com.example.currFair.api.inject;

import com.google.inject.Binder;
import com.google.inject.Module;


public class MainGuiceModule implements Module
{

    @Override public void configure(Binder binder)
    {
        
        binder.install(new ApiGuiceModule());
       
    }
}
