package org.ciudadesAbiertas.madrid.model.dynamic;



import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name = "param")
public class ParamD implements java.io.Serializable {

	private static final long serialVersionUID = 2013030178052867033L;
	
	private int id;
	private QueryD query;
	private String name;
	private String type;
	private String description;
	private String example;

	public ParamD() {
	    super();
	}

	public ParamD(ParamD copia) {
	    super();
	    this.id = copia.id;
	    this.query = copia.query;
	    this.name = copia.name;
	    this.type = copia.type;
	    this.description = copia.description;
	    this.example = copia.example;
	}



	@Id
	@Column(name = "id", unique = true, nullable = false)
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(
	        name = "query_code",
	        referencedColumnName = "code", nullable = false
	    )
	public QueryD getQuery() {
		return this.query;
	}

	public void setQuery(QueryD query) {
		this.query = query;
	}

	@Column(name = "name", nullable = false, length = 20)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "type", nullable = false, length = 10)
	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Column(name = "description", length = 100)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "example", length = 500)
	public String getExample() {
		return this.example;
	}

	public void setExample(String example) {
		this.example = example;
	}

	@Override
	public String toString() {
		return name;
	}

	
	
}
