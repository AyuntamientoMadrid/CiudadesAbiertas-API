package org.ciudadesAbiertas.madrid.model.dynamic;
// Generated 22-jun-2017 10:58:01 by Hibernate Tools 5.2.1.Final

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Url generated by hbm2java
 */
@Entity
@Table(name = "url")
public class UrlD implements java.io.Serializable {

	private UrlId id;
	private UrlGroup urlGroup;

	public UrlD() {
	}

	public UrlD(UrlId id, UrlGroup urlGroup) {
		this.id = id;
		this.urlGroup = urlGroup;
	}

	@EmbeddedId

	@AttributeOverrides({ @AttributeOverride(name = "code", column = @Column(name = "code", nullable = false, length = 20)), @AttributeOverride(name = "type", column = @Column(name = "type", nullable = false)) })
	public UrlId getId() {
		return this.id;
	}

	public void setId(UrlId id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "urlgroup", nullable = false)
	public UrlGroup getUrlGroup() {
		return this.urlGroup;
	}

	public void setUrlGroup(UrlGroup urlGroup) {
		this.urlGroup = urlGroup;
	}

}