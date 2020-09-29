package com.localidata.htools;
// Generated 1 jul. 2020 14:16:07 by Hibernate Tools 4.3.5.Final

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Estadistica generated by hbm2java
 */
@Entity
@Table(name = "estadistica", catalog = "apiDinamica")
public class Estadistica implements java.io.Serializable {

    private String id;
    private String url;
    private Date fechaPeticion;
    private String origen;
    private String userAgent;

    public Estadistica() {
    }

    public Estadistica(String id, String url, Date fechaPeticion, String origen, String userAgent) {
	this.id = id;
	this.url = url;
	this.fechaPeticion = fechaPeticion;
	this.origen = origen;
	this.userAgent = userAgent;
    }

    @Id

    @Column(name = "id", unique = true, nullable = false, length = 50)
    public String getId() {
	return this.id;
    }

    public void setId(String id) {
	this.id = id;
    }

    @Column(name = "url", nullable = false, length = 2000)
    public String getUrl() {
	return this.url;
    }

    public void setUrl(String url) {
	this.url = url;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fecha_peticion", nullable = false, length = 19)
    public Date getFechaPeticion() {
	return this.fechaPeticion;
    }

    public void setFechaPeticion(Date fechaPeticion) {
	this.fechaPeticion = fechaPeticion;
    }

    @Column(name = "origen", nullable = false, length = 20)
    public String getOrigen() {
	return this.origen;
    }

    public void setOrigen(String origen) {
	this.origen = origen;
    }

    @Column(name = "user_agent", nullable = false, length = 200)
    public String getUserAgent() {
	return this.userAgent;
    }

    public void setUserAgent(String userAgent) {
	this.userAgent = userAgent;
    }

}