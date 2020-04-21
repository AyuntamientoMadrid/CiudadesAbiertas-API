/**
 * Copyright 2019 Ayuntamiento de A Coruña, Ayuntamiento de Madrid, Ayuntamiento de Santiago de Compostela, Ayuntamiento de Zaragoza, Entidad Pública Empresarial Red.es
 * 
 * This file is part of the Open Cities API, developed within the "Ciudades Abiertas" project (https://ciudadesabiertas.es/).
 * 
 * Licensed under the EUPL, Version 1.2 or – as soon they will be approved by the European Commission - subsequent versions of the EUPL (the "Licence");
 * You may not use this work except in compliance with the Licence.
 * You may obtain a copy of the Licence at:
 * 
 * https://joinup.ec.europa.eu/software/page/eupl
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the Licence is distributed on an "AS IS" basis,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the Licence for the specific language governing permissions and limitations under the Licence.
 */

package org.ciudadesabiertas.dataset.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.ciudadesAbiertas.rdfGeneratorZ.anotations.Context;
import org.ciudadesAbiertas.rdfGeneratorZ.anotations.PathId;
import org.ciudadesAbiertas.rdfGeneratorZ.anotations.Rdf;
import org.ciudadesAbiertas.rdfGeneratorZ.anotations.RdfTripleExtenal;
import org.ciudadesabiertas.model.GeoModel;
import org.ciudadesabiertas.model.RDFModel;
import org.ciudadesabiertas.utils.Constants;
import org.ciudadesabiertas.utils.Util;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;
import com.opencsv.bean.CsvDate;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


/**
 * @author Juan Carlos Ballesteros (Localidata)
 * @author Carlos Martínez de la Casa (Localidata)
 * @author Hugo Lafuente (Localidata)
 * @author Oscar Corcho (UPM, Localidata)
 *
 */
@Entity
@ApiModel
@Table(name = "bici_punto_paso")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonPropertyOrder(alphabetic=false)
@JsonIgnoreProperties({Constants.IKEY})
@JacksonXmlRootElement(localName = Constants.RECORD)
@Rdf(contexto = Context.ESBICI, propiedad = "PuntoDePaso")
@PathId(value="/bicicleta-publica/punto-paso")
public class BicicletaPublicaPuntoPaso  implements java.io.Serializable, GeoModel, RDFModel {
	
	@JsonIgnore
	private static final long serialVersionUID = -1504640833269124191L;	
	
	@ApiModelProperty(hidden = true)
	@JsonIgnore
	private String ikey;	
	
	@CsvBindByPosition(position=1)
	@CsvBindByName(column=Constants.IDENTIFICADOR, format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.DCT, propiedad = Constants.IDENTIFIER)
	@RdfTripleExtenal(sujetoInicioURI="/bicicleta-publica/trayecto/", sujetoFinURI="trayectoId", propiedadURI=Context.ESBICI_URI+"tieneAsociadoPuntoPaso", objetoInicioURI="/bicicleta-publica/punto-paso/", objetoFinURI="id")
	private String id;
	
	@CsvBindByPosition(position=2)
	@CsvBindByName(column="fechaPaso")
	@CsvDate(Constants.DATE_FORMAT)
	@Rdf(contexto = Context.ESBICI, propiedad = "fechaPaso",typeURI=Context.XSD_URI+"dateTime")
	private Date fechaPaso;
	
	@CsvBindByPosition(position=3)
	@CsvBindByName(column="trayectoId", format=Constants.STRING_FORMAT)	
	private String trayectoId;
	
	@CsvBindByPosition(position=4)
	@CsvBindByName(column="orden", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.ESBICI, propiedad = "orden")	
	private String orden;
	
	@CsvBindByPosition(position=5)
	@CsvBindByName(column="xETRS89")	
	@Rdf(contexto = Context.GEOCORE, propiedad = "xETRS89", typeURI=Context.XSD_URI+"double")
	private BigDecimal x;
	
	
	@CsvBindByPosition(position=6)
	@CsvBindByName(column="yETRS89")
	@Rdf(contexto = Context.GEOCORE, propiedad = "yETRS89", typeURI=Context.XSD_URI+"double")
	private BigDecimal y;
	
	@Transient
	@ApiModelProperty(hidden = true)
	@CsvBindByPosition(position=5)
	@CsvBindByName(column="latitud")
	@Rdf(contexto = Context.GEO, propiedad = "lat", typeURI=Context.XSD_URI+"double")	
	private BigDecimal latitud;
	
	@Transient
	@ApiModelProperty(hidden = true)
	@CsvBindByPosition(position=6)
	@CsvBindByName(column="longitud")
	@Rdf(contexto = Context.GEO, propiedad = "long", typeURI=Context.XSD_URI+"double")
	private BigDecimal longitud;
	
	private Double distance;

	public BicicletaPublicaPuntoPaso()
	{
	}

	public BicicletaPublicaPuntoPaso(BicicletaPublicaPuntoPaso copia)
	{		
		this.ikey = copia.ikey;
		this.id = copia.id;
		this.fechaPaso = copia.fechaPaso;
		this.trayectoId = copia.trayectoId;
		this.orden = copia.orden;
		this.x = copia.x;
		this.y = copia.y;
	}

	
	public BicicletaPublicaPuntoPaso(BicicletaPublicaPuntoPaso copia, List<String> attributesToSet)
	{
		if (attributesToSet.contains(Constants.IKEY)) {
			this.ikey = copia.ikey;
		}
		if (attributesToSet.contains(Constants.IDENTIFICADOR)) {
			this.id = copia.id;
		}
		if (attributesToSet.contains("fechaPaso")) {
			this.fechaPaso = copia.fechaPaso;		
		}
		if (attributesToSet.contains("trayectoId")) {
			this.trayectoId = copia.trayectoId;
		}
		if (attributesToSet.contains("orden")) {
			this.orden = copia.orden;
		}
		if (attributesToSet.contains("x")) {
			this.x = copia.x;
		}
		if (attributesToSet.contains("y")) {
			this.y = copia.y;
		}
		
		
	}


	@Id
	@Column(name = "ikey", unique = true, nullable = false, length = 50)
	public String getIkey()
	{
		return this.ikey;
	}

	public void setIkey(String ikey)
	{
		this.ikey = ikey;
	}

	@Column(name = "id", nullable = false, length = 50)
	public String getId()
	{
		return this.id;
	}

	public void setId(String id)
	{
		this.id = id;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fecha_paso", length = 19)
	public Date getFechaPaso() {
		return this.fechaPaso;
	}

	public void setFechaPaso(Date fechaPaso) {
		this.fechaPaso = fechaPaso;
	}

	@Column(name = "trayecto_id", length = 50)
	public String getTrayectoId() {
		return this.trayectoId;
	}

	public void setTrayectoId(String trayectoId) {
		this.trayectoId = trayectoId;
	}
	
	@Column(name = "orden", length = 200)
	public String getOrden() {
		return this.orden;
	}

	public void setOrden(String orden) {
		this.orden = orden;
	}
	
	@JsonProperty("xETRS89")
	@Column(name = "x_etrs89", precision = 13, scale = 5)
	public BigDecimal getX()
	{
		return x;
	}
	
	public void setX(BigDecimal x)
	{
		this.x = x;
	}
	
	@JsonProperty("yETRS89")
	@Column(name = "y_etrs89", precision = 13, scale = 5)
	public BigDecimal getY()
	{
		return y;
	}
	
	public void setY(BigDecimal y)
	{
		this.y = y;
	}
	
	@Transient
	public BigDecimal getLatitud()
	{
		return this.latitud;
	}

	public void setLatitud(BigDecimal latitud)
	{
		this.latitud = latitud;
	}

	@Transient
	public BigDecimal getLongitud()
	{
		return this.longitud;
	}

	public void setLongitud(BigDecimal longitud)
	{
		this.longitud = longitud;
	}
	
	@Transient
	public void setDistance(Double distance) {
		this.distance = distance;
	}
	
	@Transient
	public Double getDistance() {
		return this.distance;
	}

	@Override
	public String toString() {
		return "BicicletaPublicaPuntoPaso [ikey=" + ikey + ", id=" + id + ", fechaPaso=" + fechaPaso + ", trayectoId="
				+ trayectoId + ", orden=" + orden + ", x=" + x + ", y=" + y + ", latitud=" + latitud + ", longitud="
				+ longitud + ", distance=" + distance + "]";
	}

	public Map<String,String> prefixes()
	{
		Map<String,String> prefixes=new HashMap<String,String>();				
		prefixes.put(Context.XSD, Context.XSD_URI);
		prefixes.put(Context.DCT, Context.DCT_URI);			
		prefixes.put(Context.ESBICI, Context.ESBICI_URI);
		prefixes.put(Context.GEO, Context.GEO_URI);
		prefixes.put(Context.GEOCORE, Context.GEOCORE_URI);
		prefixes.put(Context.ESEQUIP, Context.ESEQUIP_URI);
		
		return prefixes;
	}


	@SuppressWarnings("unchecked")
	@Override
	public <T> T cloneModel(T copia, List<String> listado) 
	{
		return (T) cloneClass((BicicletaPublicaPuntoPaso) copia, listado);
	}
	
	public BicicletaPublicaPuntoPaso cloneClass(BicicletaPublicaPuntoPaso copia, List<String> attributesToSet) {

		BicicletaPublicaPuntoPaso obj = new BicicletaPublicaPuntoPaso(copia,attributesToSet);		

		return obj;

	}

	@Override
	public List<String> validate()
	{
		List<String> result= new ArrayList<String>();
		
		//Validamos campos Obligatorios ver si es posible obtener esta información mediante anotaciones del modelo
		if (!Util.validValue(this.getId())) {
			result.add("Id is not valid [Id:"+this.getId()+"]");
		}		
								
		if (!Util.validValue(this.getFechaPaso())) {
			result.add("Title is not valid [Title:"+this.getFechaPaso()+"]");
		}
		
		if (!Util.validValue(this.getOrden())) {
			result.add("FechaAlta is not valid [FechaAlta:" + this.getOrden() + "]");
		}
		
		return result;
	}
	


	
	




}
