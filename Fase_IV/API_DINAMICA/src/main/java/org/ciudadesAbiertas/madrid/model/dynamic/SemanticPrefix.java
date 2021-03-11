package org.ciudadesAbiertas.madrid.model.dynamic;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "semantic_prefix")
public class SemanticPrefix implements java.io.Serializable {

	private static final long serialVersionUID = -8592933210313219197L;

	private int id;
	private String code;
	private String url;

	public SemanticPrefix() {
	}

	@Id

	@Column(name = "id", unique = true, nullable = false, length = 20)
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Column(name = "code", unique = true, nullable = false, length = 50)
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Column(name = "url", nullable = false, length = 200)
	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		result = prime * result + id;
		result = prime * result + ((url == null) ? 0 : url.hashCode());
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
		SemanticPrefix other = (SemanticPrefix) obj;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
			return false;
		if (id != other.id)
			return false;
		if (url == null) {
			if (other.url != null)
				return false;
		} else if (!url.equals(other.url))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "SemanticPrefix [id=" + id + ", code=" + code + ", url=" + url + "]";
	}

}
