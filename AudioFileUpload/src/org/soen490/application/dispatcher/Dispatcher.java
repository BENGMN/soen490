package org.soen490.application.dispatcher;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class Dispatcher {
	protected HttpServletResponse response;
	protected HttpServletRequest request;
	
	public abstract void execute() throws ServletException, IOException;
	
	public void init(HttpServletRequest request, HttpServletResponse response) {
		this.request = request;
		this.response = response;
	}
}
