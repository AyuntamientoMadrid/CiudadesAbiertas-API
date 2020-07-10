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

package org.ciudadesabiertas.presupuesto;

import static org.junit.Assert.assertTrue;

import org.ciudadesabiertas.config.WebConfig;
import org.ciudadesabiertas.dataset.controller.PresupuestoGastoController;
import org.ciudadesabiertas.utils.TestUtils;
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



@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { WebConfig.class })
@WebAppConfiguration
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PresupuestoGastoDataRSQLTest
{
	
	@Autowired
	private WebApplicationContext wac;

	private String listURL=PresupuestoGastoController.LIST;

	private MockMvc mockMvc;

	@Before
	public void setup() throws Exception
	{
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
	}

	@Test
	public void test_Busqueda_RSQL_id() throws Exception
	{

		String paramField = "q";

		String value = "id=='PREGASTO02'";

		long total = TestUtils.extractTotal(listURL, paramField, value, mockMvc);
		
		assertTrue(total == 1);

	}
	
	@Test
	public void test_Busqueda_RSQL_creditoPresupuestarioInicial() throws Exception
	{

		String paramField = "q";

		String value = "creditoPresupuestarioInicial==65000";

		long total = TestUtils.extractTotal(listURL, paramField, value, mockMvc);
		
		assertTrue(total == 1);

	}
	
	
	@Test
	public void test_Busqueda_RSQL_clasificacionOrganica() throws Exception
	{

		String paramField = "q";

		String value = "clasificacionOrganica=='002'";

		long total = TestUtils.extractTotal(listURL, paramField, value, mockMvc);
		
		assertTrue(total ==2 );

	}

	
	
	
	@Test
	public void test_Busqueda_RSQL_clasificacionPrograma() throws Exception
	{

		String value = "clasificacionPrograma=='23270'";

		String paramField = "q";

		long total = TestUtils.extractTotal(listURL, paramField, value, mockMvc);

		assertTrue(total == 2);
	}
	
	
	@Test
	public void test_Busqueda_RSQL_clasificacionEconomicaGasto() throws Exception
	{		
		String value = "clasificacionEconomicaGasto==22606";
		String paramField = "q";

		long total = TestUtils.extractTotal(listURL, paramField, value, mockMvc);

		assertTrue(total == 1);
	}
	
	
	@Test
	public void test_Busqueda_RSQL_presupuesto() throws Exception
	{		
		String value = "presupuesto=='PRESUP01'";
		String paramField = "q";

		long total = TestUtils.extractTotal(listURL, paramField, value, mockMvc);

		assertTrue(total == 2);
	}

}
