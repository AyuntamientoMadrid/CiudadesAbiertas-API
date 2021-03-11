package org.ciudadesAbiertas.madrid.model.dynamic;


import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "query")
public class QueryD implements java.io.Serializable {
	
	private static final long serialVersionUID = 3885360849498032483L;
	
	private int id;
	private String code;
	private String texto;
	private String database;
	private String summary;
	private String tags;
	private Set<ParamD> params = new HashSet<ParamD>(0);
	private String definition;

	public QueryD() {
	    super();
	}
	
	public QueryD(QueryD copia) {
	    super();
	    this.id = copia.id;
	    this.code = copia.code;
	    this.texto =  copia.texto;
	    this.database =  copia.database;
	    this.summary =  copia.summary;
	    this.tags =  copia.tags;
	    this.params =  copia.params;
	    this.definition =  copia.definition;
	}
	
	@Id
	@Column(name = "id", unique = true, nullable = false)
	public int getId() {
	  return this.id;
	}

	public void setId(int id) {
	  this.id = id;
	}

	@Column(name = "code", unique = true, nullable = false, length = 50)
	public String getCode() {
	  return this.code;
	}

	public void setCode(String code) {
	  this.code = code;
	}

	@Column(name = "texto", nullable = false, length = 4000)
	public String getTexto() {
		return this.texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}
	
	@Column(name = "database_con", nullable = false, length = 20)
	public String  getDatabase() {
		return this.database;
	}

	public void setDatabase(String database) {
		this.database = database;
	}
	
	
	@Column(name = "summary", nullable = true, length = 100)
	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	@Column(name = "tags", nullable = false)
	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "query")
	public Set<ParamD> getParams() {
		return this.params;
	}

	public void setParams(Set<ParamD> params) {
		this.params = params;
	}
	
	@Column(name = "definition", nullable = true, length = 100)
	public String getDefinition() {
		return this.definition;
	}

	public void setDefinition(String definition) {
		this.definition = definition;
	}

	@Override
	public String toString() {
		return "QueryD [code=" + code + ", texto=" + texto + ", database=" + database + ", summary=" + summary
				+ ", tags=" + tags + ", params=" + params + ", definition=" + definition + "]";
	}

	
	
}
