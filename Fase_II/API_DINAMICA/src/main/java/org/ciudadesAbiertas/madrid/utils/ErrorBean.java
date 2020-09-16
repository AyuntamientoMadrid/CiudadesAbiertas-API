package org.ciudadesAbiertas.madrid.utils;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JacksonXmlRootElement(localName = "ErrorInfo")
public class ErrorBean implements Serializable{

	private static final long serialVersionUID = 5505746182743191429L;
	
	private String name;
	private String description;
	
	
	public ErrorBean() {
		super();
		this.name = "";
		this.description = "";
	}
	
	
	public ErrorBean(String name, String description) {
		super();
		this.name = name;
		this.description = description;
	}
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	
	
	
}
