package org.ciudadesAbiertas.madrid.bean;

import java.util.ArrayList;
import java.util.List;

import org.ciudadesAbiertas.madrid.model.dynamic.SemanticField;
import org.ciudadesAbiertas.madrid.model.dynamic.SemanticPrefix;
import org.ciudadesAbiertas.madrid.model.dynamic.SemanticRml;

public class SemanticBean {
    
    private SemanticRml rml;
    private List<SemanticPrefix> prefixes=new ArrayList<SemanticPrefix>();
    private List<SemanticField> fields=new ArrayList<SemanticField>();
    private String typeURL;
    private String prefixTypeURL;
    private String subjectURI;
    private String selectedFieldForURI;
    
    
    public SemanticRml getRml() {
        return rml;
    }
    public void setRml(SemanticRml rml) {
        this.rml = rml;
    }
    public List<SemanticPrefix> getPrefixes() {
        return prefixes;
    }
    public void setPrefixes(List<SemanticPrefix> prefixes) {
        this.prefixes = prefixes;
    }
    public List<SemanticField> getFields() {
        return fields;
    }
    public void setFields(List<SemanticField> fields) {
        this.fields = fields;
    }
    public String getTypeURL() {
        return typeURL;
    }
    public void setTypeURL(String typeURL) {
        this.typeURL = typeURL;
    }
    public String getPrefixTypeURL() {
        return prefixTypeURL;
    }
    public void setPrefixTypeURL(String prefixTypeURL) {
        this.prefixTypeURL = prefixTypeURL;
    }
	public String getSubjectURI() {
	  return subjectURI;
	}
	public void setSubjectURI(String subjectURI) {
	  this.subjectURI = subjectURI;
	}
	public String getSelectedFieldForURI() {
	  return selectedFieldForURI;
	}
	public void setSelectedFieldForURI(String selectedFieldForURI) {
	  this.selectedFieldForURI = selectedFieldForURI;
	}
     
    
}
