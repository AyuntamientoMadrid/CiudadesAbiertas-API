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
 * SemanticRelPrefix generated by hbm2java
 */
@Entity
@Table(name = "semantic_rel_prefix", catalog = "apiDinamica")
public class SemanticRelPrefix implements java.io.Serializable {

private String id;
private Query query;
private SemanticPrefix semanticPrefix;

public SemanticRelPrefix() {
}

public SemanticRelPrefix(String id, Query query, SemanticPrefix semanticPrefix) {
  this.id = id;
  this.query = query;
  this.semanticPrefix = semanticPrefix;
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

@ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name = "prefix", nullable = false)
public SemanticPrefix getSemanticPrefix() {
  return this.semanticPrefix;
}

public void setSemanticPrefix(SemanticPrefix semanticPrefix) {
  this.semanticPrefix = semanticPrefix;
}

}
