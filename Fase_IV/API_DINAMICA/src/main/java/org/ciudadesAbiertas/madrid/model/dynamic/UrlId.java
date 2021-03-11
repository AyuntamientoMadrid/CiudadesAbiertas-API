package org.ciudadesAbiertas.madrid.model.dynamic;
// Generated 22-jun-2017 10:58:01 by Hibernate Tools 5.2.1.Final

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * UrlId generated by hbm2java
 */
@Embeddable
public class UrlId implements java.io.Serializable {

	private String code;
	private int type;

	public UrlId() {
	}

	public UrlId(String code, int type) {
		this.code = code;
		this.type = type;
	}

	@Column(name = "code", nullable = false, length = 20)
	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Column(name = "type", nullable = false)
	public int getType() {
		return this.type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof UrlId))
			return false;
		UrlId castOther = (UrlId) other;

		return ((this.getCode() == castOther.getCode()) || (this.getCode() != null && castOther.getCode() != null && this.getCode().equals(castOther.getCode()))) && (this.getType() == castOther.getType());
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (getCode() == null ? 0 : this.getCode().hashCode());
		result = 37 * result + this.getType();
		return result;
	}

}
