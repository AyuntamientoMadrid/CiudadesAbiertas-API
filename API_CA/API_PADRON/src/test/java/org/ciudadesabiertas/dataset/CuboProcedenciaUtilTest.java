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

package org.ciudadesabiertas.dataset;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.List;

import org.ciudadesabiertas.dataset.model.CuboProcedencia;
import org.ciudadesabiertas.utils.TestUtils;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CuboProcedenciaUtilTest
{

	private static final String[] fieldsToIngore = { "ikey", "dataset","dsd" };
	
	private static final String testJSON = " {\n" + 
			"      \"id\": \"obs1\",\n" + 
			"      \"paisId\": \"123\",\n" + 
			"      \"paisTitle\": \"España\",\n" + 
			"      \"autonomiaId\": \"343\",\n" + 
			"      \"autonomiaTitle\": \"Comunidad de Madrid\",\n" + 
			"      \"municipioId\": \"28006\",\n" + 
			"      \"municipioTitle\": \"Alcobendas\",\n" + 
			"      \"distritoId\": \"2800601\",\n" + 
			"      \"distritoTitle\": \"Distrito 1\",\n" + 
			"      \"barrioId\": \"28006011\",\n" + 
			"      \"barrioTitle\": \"Barrio 1\",\n" + 
			"      \"seccionCensalId\": \"2800601020\",\n" + 
			"      \"seccionCensalTitle\": \"Sección Censal 20\",\n" + 
			"      \"refPeriod\": \"2016\",\n" + 
			"      \"edadGruposQuinquenales\": \"00-a-04\",\n" + 
			"      \"tipoNivelEstudio\": \"00\",\n" + 
			"      \"municipioProcedencia\": \"15030\",\n" + 
			"      \"provinciaProcedencia\": \"a-coruna\",\n" + 
			"      \"numeroPersonas\": 19076\n" + 
			"    }";

	@Test
	public void constructorCopia() throws JsonParseException, JsonMappingException, IOException, IllegalArgumentException, IllegalAccessException
	{
		ObjectMapper mapper = new ObjectMapper();
		// JSON file to Java object
		CuboProcedencia mappedObj = mapper.readValue(testJSON, CuboProcedencia.class);
		CuboProcedencia item = new CuboProcedencia(mappedObj);
		boolean allFields = TestUtils.checkAllAttributes(item, fieldsToIngore);
		assertTrue(allFields);
	}

	@Test
	public void constructorCampos() throws JsonParseException, JsonMappingException, IOException, IllegalArgumentException, IllegalAccessException
	{

		ObjectMapper mapper = new ObjectMapper();

		// JSON file to Java object
		CuboProcedencia mappedObj = mapper.readValue(testJSON, CuboProcedencia.class);

		List<String> listaCampos = TestUtils.extractFields(mappedObj);

		CuboProcedencia item = new CuboProcedencia(mappedObj, listaCampos);
		boolean allFields = TestUtils.checkAllAttributes(item, fieldsToIngore);
		assertTrue(allFields);
	}

}
