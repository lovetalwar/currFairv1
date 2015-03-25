package com.example.currFair.api.model;

import java.util.ArrayList;
import java.util.List;

public class UserAccount {
	
	public static List<UserAccount> userAccounts = new ArrayList<UserAccount>();
	
	private String userId;
	private String firstName;
	private String lastName;
	private String email;
	private String phoneNo;
	public UserAccount(){
		UserAccount userAccount = new UserAccount();
		userAccount.setUserId("Test User");
		userAccount.setFirstName("Test");
		userAccount.setLastName("User");
		userAccount.setEmail("testUser@currFair.com");
		userAccount.setPhoneNo("123456");
		userAccounts.add(userAccount);
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhoneNo() {
		return phoneNo;
	}
	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}
	
	public static  UserAccount  getList(String search)
	{
		for (UserAccount userAccount : userAccounts) {
			if(userAccount.getUserId().equals(search))
				return userAccount;
		}
		return null;
	}

}
