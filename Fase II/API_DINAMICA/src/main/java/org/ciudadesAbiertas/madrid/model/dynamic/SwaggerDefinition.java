package org.ciudadesAbiertas.madrid.model.dynamic;



import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "swagger_definition")
public class SwaggerDefinition implements java.io.Serializable {

	
	private static final long serialVersionUID = 1L;
	
	private String code;	
	private String description;
	private String text;
	

	public SwaggerDefinition() {
	    super();
	}
	
	public SwaggerDefinition(String code, String description, String text) {
	    super();
	    this.code = code;
	    this.description = description;
	    this.text = text;
	}

	public SwaggerDefinition(SwaggerDefinition copia) {
	    super();
	    this.code = copia.code;
	    this.description = copia.description;
	    this.text = copia.text;
	}


	@Id
	@Column(name = "code", unique = true, nullable = false,length=100)
	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Column(name = "description", nullable = false, length=200)
	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "text", nullable = false, length=4000)
	public String getText() {
		return text;
	}


	public void setText(String text) {
		this.text = text;
	}

	@Override
	public String toString() {
	    return "SwaggerDefinition [code=" + code + ", description=" + description + ", text=" + text + "]";
	}

	
	
}
