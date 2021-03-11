package com.localidata.htools;
// Generated 19 ene. 2021 10:56:41 by Hibernate Tools 4.3.5.Final

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * SemanticRml generated by hbm2java
 */
@Entity
@Table(name = "semantic_rml", catalog = "apiDinamica")
public class SemanticRml implements java.io.Serializable {

private String id;
private Query query;
private String rml;

public SemanticRml() {
}

public SemanticRml(String id, Query query, String rml) {
  this.id = id;
  this.query = query;
  this.rml = rml;
}

@Id

@Column(name = "id", unique = true, nullable = false, length = 20)
public String getId() {
  return this.id;
}

public void setId(String id) {
  this.id = id;
}

@ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name = "query", nullable = false)
public Query getQuery() {
  return this.query;
}

public void setQuery(Query query) {
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
