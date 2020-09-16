package org.ciudadesAbiertas.madrid.model.dynamic;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "semantic_rml")
public class SemanticRml implements java.io.Serializable {

    private static final long serialVersionUID = -2633168958785496667L;

    private String id;
    private String query;
    private String rml;

    public SemanticRml() {
    }

    public SemanticRml(String id, String query, String rml) {
	this.id = id;
	this.query = query;
	this.rml = rml;
    }

    @Id

    @Column(name = "id", unique = true, nullable = false, length = 50)
    public String getId() {
	return this.id;
    }

    public void setId(String id) {
	this.id = id;
    }

    @Column(name = "query", nullable = false)
    public String getQuery() {
	return this.query;
    }

    public void setQuery(String query) {
	this.query = query;
    }

    @Column(name = "rml", nullable = false)
    public String getRml() {
	return this.rml;
    }

    public void setRml(String rml) {
	this.rml = rml;
    }

}
