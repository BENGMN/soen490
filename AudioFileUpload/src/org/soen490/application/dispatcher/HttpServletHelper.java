package org.soen490.application.dispatcher;

import javax.servlet.http.HttpServletRequest;

import org.soen490.domain.helper.Helper;

public class HttpServletHelper implements Helper {
	
	private HttpServletRequest request;

	public HttpServletHelper(HttpServletRequest request) {
		this.request = request;
	}

	@Override
	public Integer getInt(String paramName) throws NumberFormatException {
		return Integer.parseInt(getString(paramName));
	}

	@Override
	public Long getLong(String paramName) throws NumberFormatException {
		return Long.parseLong(getString(paramName));
	}

	@Override
	public String getString(String paramName) {
		return request.getParameter(paramName);
	}

	@Override
	public Boolean getBoolean(String paramName) {
		return Boolean.parseBoolean(getString(paramName));
	}

	@Override
	public Float getFloat(String paramName) {
		return Float.parseFloat(getString(paramName));
	}

	@Override
	public Double getDouble(String paramName) {
		return Double.parseDouble(getString(paramName));
	}

	@Override
	public void setRequestAttribute(String key, Object value) {
		request.setAttribute(key, value);
	}

	@Override
	public Object getRequestAttribute(String key) {
		return request.getAttribute(key);
	}

	@Override
	public void setSessionAttribute(String key, Object value) {
		request.getSession(true).setAttribute(key, value);
	}

	@Override
	public Object getSessionAttribute(String key) {
		return request.getSession(true).getAttribute(key);
	}

	@Override
	public Object getApplicationAttribute(String key) {
		return request.getSession(true).getServletContext().getAttribute(key);
	}
	
	@Override
	public void setApplicationAttribute(String key, Object value) {
		request.getSession(true).getServletContext().setAttribute(key, value);
	}
	
	@Override
	public String getSessionId() {
		return request.getSession(true).getId();
	}
	
	@Override
	public Object getAttribute(String key) {
		Object value;
		if ((value = getRequestAttribute(key)) != null) 
			return value;
		if ((value = getSessionAttribute(key)) != null) 
			return value;
		if ((value = getApplicationAttribute(key)) != null)
			return value;
		else return null;
		
	}

	
	
	
}
