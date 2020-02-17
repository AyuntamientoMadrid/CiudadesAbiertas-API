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
import org.ciudadesAbiertas.rdfGeneratorZ.anotations.Context;
import org.ciudadesAbiertas.rdfGeneratorZ.anotations.PathId;
import org.ciudadesAbiertas.rdfGeneratorZ.anotations.Rdf;
import org.ciudadesAbiertas.rdfGeneratorZ.anotations.RdfBlankNode;
import org.ciudadesAbiertas.rdfGeneratorZ.anotations.RdfExternalURI;
import org.ciudadesabiertas.model.RDFModel;
import org.ciudadesabiertas.utils.Constants;
import org.ciudadesabiertas.utils.Util;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * ContratosLot generated by hbm2java
 */

@Entity
@ApiModel
@Table(name = "contratos_lot")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonPropertyOrder(alphabetic = false)
@JsonIgnoreProperties({ Constants.IKEY })
@JacksonXmlRootElement(localName = Constants.RECORD)
@Rdf(contexto = Context.OCDS, propiedad = "Lot")
@PathId(value = "/contract/lot")
public class Lot implements java.io.Serializable,  RDFModel {
	
	@JsonIgnore
	private static final long serialVersionUID = -2266907000514652778L;
	
	@ApiModelProperty(hidden = true)
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
	@CsvBindByName(column="description", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.DCT, propiedad = "description")
	private String description;
	
	@CsvBindByPosition(position=4)
	@CsvBindByName(column="hasSupplier", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.OCDS, propiedad = "hasSupplier")
	@RdfExternalURI(inicioURI="/contratos/award/", finURI="hasSupplier", urifyLevel=2)	
	private String hasSupplier;
	
	@CsvBindByPosition(position=5)
	@CsvBindByName(column="tenderId", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.OCDS, propiedad = "tenderId")
	@RdfExternalURI(inicioURI="/contratos/tender/", finURI="tenderId", urifyLevel=2)	
	private String tenderId;
		
	@CsvBindByPosition(position=6)
	@CsvBindByName(column="valueAmount", format=Constants.STRING_FORMAT)
	@Rdf(contexto = Context.OCDS, propiedad = "valueAmount",typeURI=Context.XSD_URI+"double")
	@RdfBlankNode(tipo=Context.OCDS_URI+"Value", propiedad=Context.OCDS_URI+"hasLotValue", nodoId="amount")
	private BigDecimal valueAmount;
	

	public Lot() {
	}


	public Lot(Lot copia) {
		super();
		this.ikey = copia.ikey;
		this.hasSupplier = copia.hasSupplier;
		this.tenderId = copia.tenderId;
		this.id = copia.id;
		this.title = copia.title;
		this.description = copia.description;
		this.valueAmount = copia.valueAmount;
	}

	public Lot(Lot copia, List<String> attributesToSet)
	{
		if (attributesToSet.contains(Constants.IKEY)) {
			this.ikey = copia.ikey;
		}
		if (attributesToSet.contains(Constants.IDENTIFICADOR)) {
			this.id = copia.id;
		}
		if (attributesToSet.contains("hasSupplier")) {
			this.hasSupplier = copia.hasSupplier;
		}
		if (attributesToSet.contains("title")) {
			this.title = copia.title;		
		}
		if (attributesToSet.contains("description")) {
			this.description = copia.description;		
		}
		if (attributesToSet.contains("tenderId")) {
			this.tenderId = copia.tenderId;		
		}		
		if (attributesToSet.contains("valueAmount")) {
			this.valueAmount = copia.valueAmount;		
		}
		
	}





	@Id

	@Column(name = "ikey", unique = true, nullable = false, length = 50)
	public String getIkey() {
		return this.ikey;
	}

	public void setIkey(String ikey) {
		this.ikey = ikey;
	}

		
	@Column(name = "id", unique = true, nullable = false, length = 50)
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "title", nullable = false, length = 400)
	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Column(name = "description", nullable = false, length = 4000)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "value_amount", precision = 12)
	public BigDecimal getValueAmount() {
		return this.valueAmount;
	}

	public void setValueAmount(BigDecimal valueAmount) {
		this.valueAmount = valueAmount;
	}


	@Column(name = "has_supplier", length = 50)
	public String getHasSupplier() {
		return hasSupplier;
	}


	public void setHasSupplier(String hasSupplier) {
		this.hasSupplier = hasSupplier;
	}

	@Column(name = "tender_id",  length = 50)
	public String getTenderId() {
		return tenderId;
	}


	public void setTenderId(String tenderId) {
		this.tenderId = tenderId;
	}

	public Map<String,String> prefixes()
	{
		Map<String,String> prefixes=new HashMap<String,String>();
		prefixes.put(Context.DCT, Context.DCT_URI);	
		prefixes.put(Context.SCHEMA, Context.SCHEMA_URI);
		prefixes.put(Context.DUL, Context.DUL_URI);
		prefixes.put(Context.OCDS, Context.OCDS_URI);
		prefixes.put(Context.XSD, Context.XSD_URI);
		
		return prefixes;
	}
	
	

	@SuppressWarnings("unchecked")
	@Override
	public <T> T cloneModel(T copia, List<String> listado) 
	{
		return (T) cloneClass((Lot) copia, listado);
	}
	
	public Lot cloneClass(Lot copia, List<String> attributesToSet) {

		Lot obj = new Lot(copia,attributesToSet);		

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
				
								
		if (!Util.validValue(this.getTitle())) {
			result.add("Title is not valid [Title:"+this.getTitle()+"]");
		}
		
		
		
		
		return result;
	}


	@Override
	public String toString() {
		return "Lot [ikey=" + ikey + ", id=" + id + ", title=" + title + ", description=" + description
				+ ", hasSupplier=" + hasSupplier + ", tenderId=" + tenderId + ", valueAmount=" + valueAmount + "]";
	}


	
	
}
