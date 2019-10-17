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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.ciudadesAbiertas.rdfGeneratorZ.MultiURI;
import org.ciudadesAbiertas.rdfGeneratorZ.anotations.Context;
import org.ciudadesAbiertas.rdfGeneratorZ.anotations.IsUri;
import org.ciudadesAbiertas.rdfGeneratorZ.anotations.PathId;
import org.ciudadesAbiertas.rdfGeneratorZ.anotations.Rdf;
import org.ciudadesAbiertas.rdfGeneratorZ.anotations.RdfBlankNode;
import org.ciudadesAbiertas.rdfGeneratorZ.anotations.RdfExternalURI;
import org.ciudadesabiertas.model.GeoModel;
import org.ciudadesabiertas.model.RDFModel;
import org.ciudadesabiertas.utils.Constants;
import org.ciudadesabiertas.utils.EnumTipoEquipamiento;
import org.ciudadesabiertas.utils.Util;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author Juan Carlos Ballesteros (Localidata)
 * @author Carlos Martínez de la Casa (Localidata)
 * @author Oscar Corcho (UPM, Localidata)
 *
 */
@Entity
@ApiModel
@Table(name = "equipamiento")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonPropertyOrder(alphabetic=false)
@JsonIgnoreProperties({Constants.IKEY})
@JacksonXmlRootElement(localName = Constants.RECORD)
@Rdf(contexto = Context.ESEQUIP, propiedad = "Equipamiento")
@PathId(value="/equipamiento")
public class Equipamiento implements java.io.Serializable, GeoModel, RDFModel, MultiURI {


	@JsonIgnore
	private static final long serialVersionUID = -1138933398801266235L;

	@JsonIgnore
	private String ikey;
	
	@CsvBindByPosition(position=1)
	@CsvBindByName(column=Constants.IDENTIFICADOR, format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.DCT, propiedad = Constants.IDENTIFIER)
	private String id;
	
	@CsvBindByPosition(position=2)
	@CsvBindByName(column="title", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.DCT, propiedad = "title")
	private String title;
	
	
	
	@CsvBindByPosition(position=3)
	@CsvBindByName(column="tipoEquipamiento", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.ESEQUIP, propiedad = "tipo-equipamiento")	
	private String tipoEquipamiento;
	
	@CsvBindByPosition(position=4)
	@CsvBindByName(column="municipioId", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.DCT, propiedad = "identifier")
	@RdfBlankNode(tipo=Context.ESADM_URI+"Municipio", propiedad=Context.ESADM_URI+"municipio", nodoId="municipio")	
	private String municipioId;
	
	@CsvBindByPosition(position=5)
	@CsvBindByName(column="municipioTitle", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.DCT, propiedad = "title")
	@RdfBlankNode(tipo=Context.ESADM_URI+"Municipio", propiedad=Context.ESADM_URI+"municipio", nodoId="municipio")
	private String municipioTitle;
	
	@CsvBindByPosition(position=6)
	@CsvBindByName(column="provincia", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.ESADM, propiedad = "provincia")
	@RdfExternalURI(inicioURI="http://datos.gob.es/recurso/sector-publico/territorio/Provincia/", finURI="provincia", capitalize=true, urifyLevel=1)
	private String provincia;
	
	@CsvBindByPosition(position=7)
	@CsvBindByName(column="autonomia", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.ESADM, propiedad = "autonomia")		
	@RdfExternalURI(inicioURI="http://datos.gob.es/recurso/sector-publico/territorio/Autonomia/", finURI="autonomia",capitalize=true, urifyLevel=1)
	private String autonomia;
	
	@CsvBindByPosition(position=8)
	@CsvBindByName(column="pais", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.ESADM, propiedad = "pais")	
	@RdfExternalURI(inicioURI="http://datos.gob.es/recurso/sector-publico/territorio/Pais/", finURI="pais", capitalize=true, urifyLevel=1)
	private String pais;
	
	@CsvBindByPosition(position=9)
	@CsvBindByName(column="streetAddress", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.SCHEMA, propiedad = "streetAddress")
	@RdfBlankNode(tipo=Context.SCHEMA_URI+"PostalAddress", propiedad=Context.SCHEMA_URI+"address", nodoId="address")	
	private String streetAddress;
	
	@CsvBindByPosition(position=10)
	@CsvBindByName(column="postalCode", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.SCHEMA, propiedad = "postalCode")	
	@RdfBlankNode(tipo=Context.SCHEMA_URI+"PostalAddress", propiedad=Context.SCHEMA_URI+"address", nodoId="address")	
	private String postalCode;
	
	@CsvBindByPosition(position=11)
	@CsvBindByName(column="barrio", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.ESADM, propiedad = "barrio")		
	private String barrio;
	
	@CsvBindByPosition(position=12)
	@CsvBindByName(column="distrito", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.ESADM, propiedad = "distrito")
	private String distrito;
	
	@CsvBindByPosition(position=13)
	@CsvBindByName(column="xETRS89")
	@Rdf(contexto = Context.GEOCORE, propiedad = "xETRS89",typeURI=Context.XSD_URI+"double")
	private BigDecimal x;	
	
	@CsvBindByPosition(position=14)
	@CsvBindByName(column="yETRS89")
	@Rdf(contexto = Context.GEOCORE, propiedad = "yETRS89",typeURI=Context.XSD_URI+"double")
	private BigDecimal y;
	
	@Transient
	@ApiModelProperty(hidden = true)
	@CsvBindByPosition(position=15)
	@CsvBindByName(column="latitud")
	@Rdf(contexto = Context.GEO, propiedad = "lat", typeURI=Context.XSD_URI+"double")
	private BigDecimal latitud;
	
	@Transient
	@ApiModelProperty(hidden = true)
	@CsvBindByPosition(position=16)
	@CsvBindByName(column="longitud")
	@Rdf(contexto = Context.GEO, propiedad = "long", typeURI=Context.XSD_URI+"double")
	private BigDecimal longitud;
	
	@CsvBindByPosition(position=17)
	@CsvBindByName(column="email", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.SCHEMA, propiedad = "email")
	private String email;
	
	@CsvBindByPosition(position=18)
	@CsvBindByName(column="telephone", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.SCHEMA, propiedad = "telephone")
	private String telephone;
	
	@CsvBindByPosition(position=19)
	@CsvBindByName(column="url", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.SCHEMA, propiedad = "url")
	@IsUri
	private String url;
	
	@CsvBindByPosition(position=20)
	@CsvBindByName(column="titularidad", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.ESEQUIP, propiedad = "titularidad")
	private String titularidad;
	
	@CsvBindByPosition(position=21)	
	@CsvBindByName(column="openingHours", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.SCHEMA, propiedad = "openingHours")
	private String openingHours;


	@CsvBindByPosition(position=22)	
	@CsvBindByName(column="description", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.SCHEMA, propiedad = "description")
	private String description;
	
	
	private Double distance;
	
	
	public Equipamiento() {
	}


	public Equipamiento(Equipamiento eq) {
		this.ikey = eq.ikey;
		this.id = eq.id;
		this.title = eq.title;
		this.description = eq.description;
		this.tipoEquipamiento = eq.tipoEquipamiento;
		this.municipioId = eq.municipioId;
		this.municipioTitle = eq.municipioTitle;
		this.provincia = eq.provincia;
		this.autonomia = eq.autonomia;
		this.pais = eq.pais;
		this.streetAddress = eq.streetAddress;
		this.postalCode = eq.postalCode;
		this.barrio = eq.barrio;
		this.distrito = eq.distrito;
		this.x = eq.x;
		this.y = eq.y;
		this.email = eq.email;
		this.telephone = eq.telephone;
		this.url = eq.url;
		this.titularidad = eq.titularidad;
		this.openingHours = eq.openingHours;
	}

	@Id

	@Column(name = Constants.IKEY, unique = true, nullable = false, length = 50)
	public String getIkey() {
		return this.ikey;
	}

	public void setIkey(String ikey) {
		this.ikey = ikey;
	}

	@Column(name = Constants.IDENTIFICADOR, length = 50)
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "title", length = 400)
	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Column(name = "description", length = 65535)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "tipo_equipamiento", length = 100)
	public String getTipoEquipamiento() {
		return this.tipoEquipamiento;
	}

	public void setTipoEquipamiento(String tipoEquipamiento) {
		this.tipoEquipamiento = tipoEquipamiento;
	}

	@Column(name = "municipio_id", length = 10)
	public String getMunicipioId() {
		return this.municipioId;
	}

	public void setMunicipioId(String municipioId) {
		this.municipioId = municipioId;
	}

	@Column(name = "municipio_title", length = 200)
	public String getMunicipioTitle() {
		return this.municipioTitle;
	}

	public void setMunicipioTitle(String municipioTitle) {
		this.municipioTitle = municipioTitle;
	}

	@Column(name = "provincia", length = 200)
	public String getProvincia() {
		return this.provincia;
	}

	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}

	@Column(name = "autonomia", length = 200)
	public String getAutonomia() {
		return this.autonomia;
	}

	public void setAutonomia(String autonomia) {
		this.autonomia = autonomia;
	}

	@Column(name = "pais", length = 200)
	public String getPais() {
		return this.pais;
	}

	public void setPais(String pais) {
		this.pais = pais;
	}

	@Column(name = "street_address", length = 200)
	public String getStreetAddress() {
		return this.streetAddress;
	}

	public void setStreetAddress(String streetAddress) {
		this.streetAddress = streetAddress;
	}

	@Column(name = "postal_code", length = 10)
	public String getPostalCode() {
		return this.postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	@Column(name = "barrio", length = 200)
	public String getBarrio() {
		return this.barrio;
	}

	public void setBarrio(String barrio) {
		this.barrio = barrio;
	}

	@Column(name = "distrito", length = 200)
	public String getDistrito() {
		return this.distrito;
	}

	public void setDistrito(String distrito) {
		this.distrito = distrito;
	}	

	@Column(name = "email", length = 200)
	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "telephone", length = 200)
	public String getTelephone() {
		return this.telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	@Column(name = "url", length = 400)
	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Column(name = "titularidad", length = 400)
	public String getTitularidad() {
		return this.titularidad;
	}

	public void setTitularidad(String titularidad) {
		this.titularidad = titularidad;
	}
	
	@Column(name = "opening_hours", length = 400)
	public String getOpeningHours()
	{
		return this.openingHours;
	}

	public void setOpeningHours(String openingHours)
	{
		this.openingHours = openingHours;
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
	public BigDecimal getLatitud() {
		return this.latitud;
	}

	@Transient
	public void setLatitud(BigDecimal latitud) {
		this.latitud = latitud;
	}

	@Transient
	public BigDecimal getLongitud() {
		return this.longitud;
	}

	@Transient
	public void setLongitud(BigDecimal longitud) {
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

	//Constructor copia con lista de attributos a settear
	public Equipamiento(Equipamiento copia, List<String> attributesToSet)
	{
		if (attributesToSet.contains(Constants.IKEY)) {
			this.ikey = copia.ikey;
		}
		if (attributesToSet.contains(Constants.IDENTIFICADOR)) {
			this.id = copia.id;
		}
		if (attributesToSet.contains("title")) {
			this.title = copia.title;		
		}
		if (attributesToSet.contains("description")) {
			this.description = copia.description;	
		}
		if (attributesToSet.contains("tipoEquipamiento")) {
			this.tipoEquipamiento = copia.tipoEquipamiento;	
		}
		if (attributesToSet.contains("municipioId")) {
			this.municipioId = copia.municipioId;
		}
		if (attributesToSet.contains("municipioTitle")) {
			this.municipioTitle = copia.municipioTitle;
		}
		if (attributesToSet.contains("provincia")) {
			this.provincia = copia.provincia;
		}
		if (attributesToSet.contains("autonomia")) {
			this.autonomia = copia.autonomia;	
		}
		if (attributesToSet.contains("pais")) {
			this.pais = copia.pais;
		}
		if (attributesToSet.contains("streetAddress")) {
			this.streetAddress = copia.streetAddress;
		}
		if (attributesToSet.contains("postalCode")) {
			this.postalCode = copia.postalCode;
		}
		if (attributesToSet.contains("barrio")) {
			this.barrio = copia.barrio;
		}
		if (attributesToSet.contains("distrito")) {
			this.distrito = copia.distrito;	
		}
		if (attributesToSet.contains("xETRS89")) {
			this.x = copia.x;
		}
		if (attributesToSet.contains("yETRS89")) {
			this.y = copia.y;
		}
		if (attributesToSet.contains("email")) {
			this.email = copia.email;	
		}
		if (attributesToSet.contains("telephone")) {
			this.telephone = copia.telephone;
		}
		if (attributesToSet.contains("url")) {
			this.url = copia.url;
		}
		if (attributesToSet.contains("titularidad")) {
			this.titularidad = copia.titularidad;			
		}
		
		if (attributesToSet.contains("openingHours")) {
			this.openingHours = copia.openingHours;			
		}
	}

	@Override
	public String toString() {
		return "Equipamiento [ikey=" + ikey + ", id=" + id + ", title=" + title + ", description=" + description
				+ ", tipoEquipamiento=" + tipoEquipamiento + ", municipioId=" + municipioId + ", municipioTitle=" + municipioTitle
				+ ", provincia=" + provincia + ", autonomia=" + autonomia + ", pais="
				+ pais + ", streetAddress=" + streetAddress + ", postalCode=" + postalCode + ", barrio="
				+ barrio + ", distrito=" + distrito + ", x=" + x + ", y="
				+ y + ", email=" + email + ", telephone=" + telephone + ", url=" + url + ", titularidad="
				+ titularidad + ", openingHours=" + openingHours+"]";
	}
		
	public Map<String,String> prefixes()
	{
		Map<String,String> prefixes=new HashMap<String,String>();				
		prefixes.put(Context.XSD, Context.XSD_URI);
		prefixes.put(Context.DCT, Context.DCT_URI);		
		prefixes.put(Context.ESADM, Context.ESADM_URI);
		prefixes.put(Context.SCHEMA, Context.SCHEMA_URI);		
		prefixes.put(Context.GEO, Context.GEO_URI);	
		prefixes.put(Context.GEOCORE, Context.GEOCORE_URI);
		prefixes.put(Context.ESEQUIP, Context.ESEQUIP_URI);
		
		
		return prefixes;
	}
	
	
	
	public String obtainURLPathFromType()
	{
		String path="";		
		for (EnumTipoEquipamiento tipo : EnumTipoEquipamiento.values()) {
			String comp1=Util.prepareStringToCompare(getTipoEquipamiento());
			String comp2=Util.prepareStringToCompare(tipo.getTipo());
			if (comp1.equals(comp2))
			{
				path=tipo.getPath();
				break;
			}         
        } 
		return path;
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public <T> T cloneModel(T copia, List<String> listado) {

		return (T) cloneClass((Equipamiento) copia, listado);
	}

// Constructor copia con lista de attributos a settear
	public Equipamiento cloneClass(Equipamiento copia, List<String> attributesToSet) {

		Equipamiento obj = new Equipamiento(copia,attributesToSet);		

		return obj;

	}
	

	public  List<String> validate() {
		List<String> result= new ArrayList<String>();
		
		//Validamos campos Obligatorios ver si es posible obtener esta información mediante anotaciones del modelo
		if (!Util.validValue(this.getId())) {
			result.add("Id is not valid [Id:"+this.getId()+"]");
		}		
								
		if (!Util.validValue(this.getTitle())) {
			result.add("Nombre is not valid [Nombre:"+this.getTitle()+"]");
		}
		
		if (!Util.validValue(this.getTipoEquipamiento())) {
			result.add("TipoNombre is not valid [TipoNombre:"+this.getTipoEquipamiento()+"]");
		}
				
		if (!Util.validValue(this.getX())) {
			result.add("X (ETRS89) is not valid [x:"+this.getX()+"]");
		}
		
		if (!Util.validValue(this.getY())) {
			result.add("Y (ETRS89) is not valid [y:"+this.getY()+"]");
		}
		
		
		return result;
	}
	

}
