package org.soen490.application;

import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.soen490.application.dispatcher.Dispatcher;

public class FrontController extends HttpServlet {
	/**
	 * READ IMPORTANT
	 * 
	 * Requests should have the format:
	 * 		Upload: whatever-url.com/upload?latitude=X&longitude=Y&speed=Z
	 * 		Download: whatever-url.com/download?mid=X
	 * 		Get from area (have to find better name for command): whatever-url.com/ping?latitude=X&longitude=Y&speed=Z
	 */
	
	private static final long serialVersionUID = 1L;

	private static final String	DISPATCHER_PATH = "org.soen490.application.dispatcher.";
	
	public static void prepareDbRegistry() {
		
	}

	protected void processRequest(HttpServletRequest request, HttpServletResponse response, String method) throws ServletException, IOException, InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		Dispatcher fc = getFrontCommand(request, method);
		fc.init(request, response);
		fc.execute();
	}

	private Dispatcher getFrontCommand(HttpServletRequest request, String method) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		String command = (String) request.getAttribute("command");
		String fqCommand = DISPATCHER_PATH + method + "." + command;
		try {
			return (Dispatcher) Class.forName(fqCommand).newInstance();
		} catch (Exception e) {
			fqCommand = DISPATCHER_PATH + "Error";
			// Should never throw an instantiation error
			return (Dispatcher) Class.forName(fqCommand).newInstance();
		}
	}
	
	protected void processMethod(HttpServletRequest request, HttpServletResponse response, String method) throws ServletException, IOException {
		boolean isOK = preProcessRequest(request, response);
		String command = (String) (request.getAttribute("command"));
		
		if (isOK || command.equals("Login")) {	
			try {
				processRequest(request, response, method);
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				postProcessRequest(request, response);
			}
		} else {
			throw new ServletException("No command found.");
			//Dispatcher fc = new Login();
			//fc.init(request, response);
			//fc.execute();
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processMethod(request, response, "post");
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processMethod(request, response, "get");
	}

	protected boolean preProcessRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO PERFORM AUTHENTICATION
		return true;
	}

	protected void postProcessRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO CLOSE DB CONNECTION IF NEDED
	}
}
