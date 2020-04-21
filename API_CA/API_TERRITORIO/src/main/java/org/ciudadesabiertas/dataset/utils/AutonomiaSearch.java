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

package org.ciudadesabiertas.dataset.utils;

import java.io.Serializable;

import org.ciudadesabiertas.dataset.model.Autonomia;
import org.ciudadesabiertas.utils.DatasetSearch;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author Juan Carlos Ballesteros (Localidata)
 * @author Carlos Martínez de la Casa (Localidata)
 * @author Oscar Corcho (UPM, Localidata)
 *
 */
public class AutonomiaSearch implements  Serializable, DatasetSearch<Autonomia> {

	@JsonIgnore
	private static final long serialVersionUID = -7461261144397876766L;
	
	@ApiModelProperty(value = "Identificador de la autonomía. Ejemplo: 13")
	private String id;
	
	@ApiModelProperty(value = "Nombre de la autonomía. Ejemplo: Comunidad de Madrid")
	private String title;
	
	@ApiModelProperty(value = "Nombre de la autonomía urificado para URLs. Ejemplo: Comunidad-Madrid")
	private String identifier;
	
	@ApiModelProperty(value = "País al que pertenece un fenómeno geográfico o una entidad administrativa. Ejemplo: España")
	private String pais;
	
	

	public AutonomiaSearch() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public String getPais() {
		return pais;
	}

	public void setPais(String pais) {
		this.pais = pais;
	}	
	
		
	
	
	
}
