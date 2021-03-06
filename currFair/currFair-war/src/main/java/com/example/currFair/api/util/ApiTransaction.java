
package com.example.currFair.api.util;

import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;

import com.example.currFair.api.model.UserAccount;
import com.google.inject.Inject;
import com.google.inject.servlet.RequestScoped;


@RequestScoped
public class ApiTransaction {
	static final Logger logger = Logger.getLogger(ApiTransaction.class);

	private UserAccount userAccount;
	private Long requestId; // Request ID
	private Long start; // Time of start of transaction
	private String name; // Name of transaction

	@Inject
	public ApiTransaction(UserAccount userAccount) {
		this.userAccount = userAccount;
	}

	public ApiTransaction() {}

	public ApiTransaction init(String name) {
		return begin(name, null);
	}

	public ApiTransaction init(String name, String description, Object... objects) {
		return begin(name, description, objects);
	}

	public ApiTransaction begin(String name, String description, Object... objects) {
		this.start = System.currentTimeMillis();
		return this;
	}

	public Response complete(Response response) {
		return this.complete(response, null);
	}

	public Response complete(Response response, String description, Object... objects) {
		
		return response;
	}

	
}
