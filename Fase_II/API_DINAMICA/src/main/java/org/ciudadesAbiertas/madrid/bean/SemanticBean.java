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
    
    
}
