package org.ciudadesAbiertas.madrid.model.dynamic;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "query")
public class QueryD implements java.io.Serializable {
	
	private static final long serialVersionUID = 3885360849498032483L;
	
	private String code;
	private String texto;
	private String database;
	private String summary;

	public QueryD() {
	    super();
	}

	

	public QueryD(String code,  String database, String summary, String texto) {
	    super();
	    this.code = code;
	    this.texto = texto;
	    this.database = database;
	    this.summary = summary;
	}

	public QueryD(QueryD copy) {
	    super();
	    this.code = copy.code;
	    this.texto = copy.texto;
	    this.database = copy.database;
	    this.summary = copy.summary;
	}



	@Id
	@Column(name = "code", unique = true, nullable = false, length = 20)
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


	@Override
	public String toString() {
		return "QueryD [code=" + code + ", texto=" + texto + ", database=" + database + ", summary=" + summary + "]";
	}

	
	
}
