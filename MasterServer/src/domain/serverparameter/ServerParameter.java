package domain.serverparameter;

/**
 * Class that contains attributes for server parameters.
 * This is not exactly a domain entity, but should have it's own stack of Mappers to persistence. 
 * A factory is not required for this class, because these will be added by the application administrator. 
 * @author Soto
 *
 */
public class ServerParameter {
	/**
	 * Unique ID of parameter
	 */
	private String paramName;
	
	/**
	 * Description of the parameter
	 */
	private String description;
	
	/**
	 * Value of the parameter. It is a string to support most values. Must be parsed to whatever is needed
	 */
	private String value;

	/**
	 * The only constructor that should be used.
	 * @param paramName
	 * @param description
	 * @param value
	 */
	public ServerParameter(String paramName, String description, String value) {
		this.paramName = paramName;
		this.description = description;
		this.value = value;
	}
	
	/*
	 * Getters and setters
	 */
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getParamName() {
		return paramName;
	}
	
	public boolean equals(Object o) {
		if (o == null)
			return false;
		if (!(o instanceof ServerParameter))
			return false;
		if (!((ServerParameter)o).paramName.equals(this.paramName))
			return false;
		if (!((ServerParameter)o).description.equals(this.description))
			return false;
		if (!((ServerParameter)o).value.equals(this.value))
			return false;
		return true;
	}
}
