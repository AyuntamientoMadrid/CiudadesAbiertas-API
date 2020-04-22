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

package org.ciudadesabiertas.contratos;

import static org.junit.Assert.assertTrue;

import org.ciudadesabiertas.config.WebConfig;
import org.ciudadesabiertas.dataset.controller.LotController;
import org.ciudadesabiertas.utils.TestUtils;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

/**
 * @author Juan Carlos Ballesteros (Localidata)
 * @author Carlos Martínez de la Casa (Localidata)
 * @author Oscar Corcho (UPM, Localidata)
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { WebConfig.class })
@WebAppConfiguration
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class LotDataRSQLTest
{

	@Autowired
	private WebApplicationContext wac;

	JSONParser parser = new JSONParser();

	private MockMvc mockMvc;

	private String listURL=LotController.LIST;
	
	private final String paramField="q";		
	

	@Before
	public void setup() throws Exception
	{
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
	}

	
	@Test
	public void test_Busqueda_Id() throws Exception
	{		
		String value = "id==LT1";
		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);
		assertTrue(records.size() == 1);
	}
	
	@Test
	public void test_Busqueda_Title() throws Exception
	{		
		String value = "title==Material*";
		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);
		assertTrue(records.size() == 7);
	}
	
	
	@Test
	public void test_Busqueda_description() throws Exception
	{		
		String value = "description==Material*1";
		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);
		assertTrue(records.size() == 1);
	}
	
	
	@Test
	public void test_Busqueda_hasSupplier() throws Exception
	{		
		String value = "hasSupplier==AW1";
		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);
		assertTrue(records.size() == 1);
	}
	
	@Test
	public void test_Busqueda_tenderId() throws Exception
	{		
		String value = "tenderId==TN1";
		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);
		assertTrue(records.size() == 7);
	}
	
	@Test
	public void test_Busqueda_valueAmount() throws Exception
	{		
		String value = "valueAmount==26260";
		JSONArray records = TestUtils.extractRecords(listURL, paramField, value, mockMvc);
		assertTrue(records.size() == 1);
	}
	
}
