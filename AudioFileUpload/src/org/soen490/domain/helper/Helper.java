package org.soen490.domain.helper;

public interface Helper {
	public abstract Integer getInt(String paramName) throws NumberFormatException;

	public abstract Long getLong(String paramName) throws NumberFormatException;

	public abstract String getString(String paramName);

	public abstract Boolean getBoolean(String paramName);
	
	public abstract Float getFloat(String paramName);
	
	public abstract Double getDouble(String paramName);
		
	public abstract void setRequestAttribute(String key, Object value);

	public abstract Object getRequestAttribute(String key);	

	public abstract void setSessionAttribute(String key, Object value);

	public abstract Object getSessionAttribute(String key);
	
	public abstract void setApplicationAttribute(String key, Object value);
	
	public abstract Object getApplicationAttribute(String key);
	
	public abstract String getSessionId();
	
	/**
	 * Gets attributes in the following order:
	 * 	  -request
	 *    -session
	 *    -application
	 * @return null iff all the above returned null 
	 */
	public abstract Object getAttribute(String key);
}
