package org.ciudadesAbiertas.madrid.model.dynamic;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "semantic_prefix")
public class SemanticPrefix implements java.io.Serializable {

    private static final long serialVersionUID = -8592933210313219197L;
    
    private String id;
    private String url;
   
    public SemanticPrefix() {
    }

    public SemanticPrefix(String id, String url) {
	this.id = id;
	this.url = url;
    }

  

    @Id

    @Column(name = "id", unique = true, nullable = false, length = 50)
    public String getId() {
	return this.id;
    }

    public void setId(String id) {
	this.id = id;
    }

    @Column(name = "url", nullable = false, length = 200)
    public String getUrl() {
	return this.url;
    }

    public void setUrl(String url) {
	this.url = url;
    }

   

}
