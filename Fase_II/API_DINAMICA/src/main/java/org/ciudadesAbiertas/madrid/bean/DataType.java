package org.ciudadesAbiertas.madrid.bean;

import java.io.Serializable;

public class DataType implements Serializable {

    private static final long serialVersionUID = -5754180830084967392L;
    
    private String name;
    private String example;
    private String value;
    
    public DataType() {
	super();
	
    }
    
    public DataType(String name,  String value, String example) {
	super();
	this.name = name;
	this.example = example;
	this.value = value;
    }
    
    
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getExample() {
        return example;
    }
    public void setExample(String example) {
        this.example = example;
    }
    public String getValue() {
        return value;
    }
    public void setValue(String value) {
        this.value = value;
    }
    
    
}
