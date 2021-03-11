package org.ciudadesAbiertas.madrid.model.dynamic;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "semantic_rel_prefix")
public class SemanticRelPrefix implements java.io.Serializable {

    private static final long serialVersionUID = -3813147453090308909L;

    private String id;
    private String query;
    private int prefix;

    public SemanticRelPrefix() {
    }

    public SemanticRelPrefix( String query, int prefix) {
	
	this.query = query;
	this.prefix = prefix;
    }

    @Id

    @Column(name = "id", unique = true, nullable = false, length = 50)
    public String getId() {
	return this.id;
    }

    public void setId(String id) {
	this.id = id;
    }

    @Column(name = "query", nullable = false, length = 50)
    public String getQuery() {
	return this.query;
    }

    public void setQuery(String query) {
	this.query = query;
    }

    @Column(name = "prefix", nullable = false, length = 50)
    public int getPrefix() {
	return this.prefix;
    }

    public void setPrefix(int prefix) {
	this.prefix = prefix;
    }

}
