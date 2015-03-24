

package com.example.currFair.api.util;

import com.example.currFair.api.model.UserAccount;
import com.google.inject.Inject;
import com.google.inject.servlet.RequestScoped;

/**
 * Small object to manage the user authentication process 
 */
@RequestScoped
public class UserContextHolder
{
   private UserAccount userAccount;
 
   @Inject
   public UserContextHolder(UserAccount userAccount)
   {
       super();
       this.userAccount = userAccount;
      
   }

   public UserAccount getUserAccount()
   {
       return userAccount;
   }
   
  
}
