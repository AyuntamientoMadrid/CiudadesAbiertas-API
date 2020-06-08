package org.ciudadesAbiertas.madrid.model;


import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Entity
@Table(name = "entidad_base")
public class EntidadBase implements java.io.Serializable {

	private static final long serialVersionUID = 2381479936764101230L;
	
	private String id;
	private String texto;
	private String textoLargo;
	private Date fecha;
	private int numeroEntero;
	private BigDecimal numeroDecimal;

	public EntidadBase() {
		this.id = "";
		this.texto = "";
		this.textoLargo = "";
		this.fecha = null;
		this.numeroEntero = 0;
		this.numeroDecimal = BigDecimal.valueOf(0);
	}

	public EntidadBase(String id, String texto, String textoLargo, Date fecha, int numeroEntero,
			BigDecimal numeroDecimal) {
		this.id = id;
		this.texto = texto;
		this.textoLargo = textoLargo;
		this.fecha = fecha;
		this.numeroEntero = numeroEntero;
		this.numeroDecimal = numeroDecimal;
	}
	
	
	public EntidadBase(EntidadBase copia) {
		this.id = copia.id;
		this.texto = copia.texto;
		this.textoLargo =  copia.textoLargo;
		this.fecha =  copia.fecha;
		this.numeroEntero =  copia.numeroEntero;
		this.numeroDecimal =  copia.numeroDecimal;
	}

	@Id

	@Column(name = "id", unique = true, nullable = false, length = 50)
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "texto", nullable = false, length = 100)
	public String getTexto() {
		return this.texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

	@Column(name = "texto_largo", nullable = true, length = 4000)
	public String getTextoLargo() {
		return this.textoLargo;
	}

	public void setTextoLargo(String textoLargo) {
		this.textoLargo = textoLargo;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fecha", nullable = true, length = 19)
	public Date getFecha() {
		return this.fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	@Column(name = "numero_entero", nullable = true)
	public int getNumeroEntero() {
		return this.numeroEntero;
	}

	public void setNumeroEntero(int numeroEntero) {
		this.numeroEntero = numeroEntero;
	}

	@Column(name = "numero_decimal", nullable = true, precision = 12)
	public BigDecimal getNumeroDecimal() {
		return this.numeroDecimal;
	}

	public void setNumeroDecimal(BigDecimal numeroDecimal) {
		this.numeroDecimal = numeroDecimal;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((fecha == null) ? 0 : fecha.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((numeroDecimal == null) ? 0 : numeroDecimal.hashCode());
		result = prime * result + numeroEntero;
		result = prime * result + ((texto == null) ? 0 : texto.hashCode());
		result = prime * result + ((textoLargo == null) ? 0 : textoLargo.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EntidadBase other = (EntidadBase) obj;
		if (fecha == null) {
			if (other.fecha != null)
				return false;
		} else if (!fecha.equals(other.fecha))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (numeroDecimal == null) {
			if (other.numeroDecimal != null)
				return false;
		} else if (!numeroDecimal.equals(other.numeroDecimal))
			return false;
		if (numeroEntero != other.numeroEntero)
			return false;
		if (texto == null) {
			if (other.texto != null)
				return false;
		} else if (!texto.equals(other.texto))
			return false;
		if (textoLargo == null) {
			if (other.textoLargo != null)
				return false;
		} else if (!textoLargo.equals(other.textoLargo))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "EntidadBase [id=" + id + ", texto=" + texto + ", textoLargo=" + textoLargo + ", fecha=" + fecha
				+ ", numeroEntero=" + numeroEntero + ", numeroDecimal=" + numeroDecimal + "]";
	}
	
	

	
	
}
