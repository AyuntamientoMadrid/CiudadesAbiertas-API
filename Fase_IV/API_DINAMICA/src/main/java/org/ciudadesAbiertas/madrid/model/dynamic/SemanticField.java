package org.ciudadesAbiertas.madrid.model.dynamic;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "semantic_field")
public class SemanticField implements java.io.Serializable {
 
    private static final long serialVersionUID = 2025223763071861622L;
    
    private String id;
    private String query;
    private String field;
    private String predicate;
    private String objectReference;
    private String objectReferenceType;
    private String objectType;
    private boolean objectUri;
    private String blankNodeType;
    private String blankNodeId;
    private String blankNodeProperty;

    public SemanticField() {
    }

    public SemanticField(String id, String query, String field, String predicate, String objectReference, String objectReferenceType, String objectType, boolean objectUri, String blankNodeType, String blankNodeId) {
	this.id = id;
	this.query = query;
	this.field = field;
	this.predicate = predicate;
	this.objectReference = objectReference;
	this.objectReferenceType = objectReferenceType;
	this.objectType = objectType;
	this.objectUri = objectUri;
	this.blankNodeType = blankNodeType;
	this.blankNodeId = blankNodeId;
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

    @Column(name = "field", nullable = false, length = 100)
    public String getField() {
	return this.field;
    }

    public void setField(String field) {
	this.field = field;
    }

    @Column(name = "predicate", nullable = false, length = 200)
    public String getPredicate() {
	return this.predicate;
    }

    public void setPredicate(String predicate) {
	this.predicate = predicate;
    }

    @Column(name = "object_reference", nullable = false, length = 200)
    public String getObjectReference() {
	return this.objectReference;
    }

    public void setObjectReference(String objectReference) {
	this.objectReference = objectReference;
    }

    @Column(name = "object_type", nullable = true, length = 50)
    public String getObjectType() {
	return this.objectType;
    }

    public void setObjectType(String objectType) {
	this.objectType = objectType;
    }

    @Column(name = "object_uri", nullable = false)
    public boolean isObjectUri() {
	return this.objectUri;
    }

    public void setObjectUri(boolean objectUri) {
	this.objectUri = objectUri;
    }

    @Column(name = "blank_node_type", nullable = true, length = 50)
    public String getBlankNodeType() {
        return blankNodeType;
    }

    public void setBlankNodeType(String blankNodeType) {
        this.blankNodeType = blankNodeType;
    }

    @Column(name = "blank_node_id", nullable = true, length = 100)
    public String getBlankNodeId() {
        return blankNodeId;
    }

    public void setBlankNodeId(String blankNodeId) {
        this.blankNodeId = blankNodeId;
    }
    
    
    
    @Column(name = "object_reference_type", nullable = true, length = 20)
    public String getObjectReferenceType() {
        return objectReferenceType;
    }

    public void setObjectReferenceType(String objectReferenceType) {
        this.objectReferenceType = objectReferenceType;
    }

    @Column(name = "blank_property_id", nullable = true, length = 100)
    public String getBlankNodeProperty() {
        return blankNodeProperty;
    }

    public void setBlankNodeProperty(String blankNodeProperty) {
        this.blankNodeProperty = blankNodeProperty;
    }

    @Override
    public String toString() {
	return field;
    }
    
    

}
