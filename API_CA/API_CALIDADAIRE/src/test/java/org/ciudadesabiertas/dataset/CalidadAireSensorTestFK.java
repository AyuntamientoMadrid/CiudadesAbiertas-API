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

import static org.junit.Assert.assertFalse;
import org.ciudadesabiertas.config.WebConfig;
import org.ciudadesabiertas.dataset.controller.CalidadAireSensorController;
import org.ciudadesabiertas.utils.StartVariables;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
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
public class CalidadAireSensorTestFK {

	private static boolean activeFK = StartVariables.activeFK;

	@Autowired
	private WebApplicationContext wac;

	private MockMvc mockMvc;

	@Before
	public void setup() throws Exception {

		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();

	}

	  @Test    
	  public void test01_Add() throws Exception {
		  
		  if (activeFK == false) {
				assertFalse(activeFK);
			} else {
	  
	    	String item = "{"
	    			+"\"id\" : \"TEMPSTAT04\","
	    			+"\"isHostedBy\" : \"STAT04\","
	    			+"\"observesTitle\" : \"Elemento para tests\","
	    			+"\"observesId\" :  \"elementTests\""    			
	    			+"}";
	    	    	    	
	    	item = new String (item.getBytes(),"UTF-8");	
	    	
	    	 
	        this.mockMvc.perform(MockMvcRequestBuilders.post(CalidadAireSensorController.ADD)
	        		.contentType(MediaType.APPLICATION_JSON)
	    	        .content(item))	
	        	
	        	.andExpect(MockMvcResultMatchers.status().isCreated());
			}
	    }
	
	@Test
	public void test02_Add_WRONG_SENSOR_409() throws Exception {

		if (activeFK == false) {
			assertFalse(activeFK);
		} else {

			String item = "{" + "\"id\" : \"TEMPSTAT04\"," + "\"isHostedBy\" : \"STATKO\","
					+ "\"observesTitle\" : \"Elemento para tests\"," + "\"observesId\" :  \"elementTests\"" + "}";

			item = new String(item.getBytes(), "UTF-8");

			this.mockMvc
					.perform(MockMvcRequestBuilders.post(CalidadAireSensorController.ADD)
							.contentType(MediaType.APPLICATION_JSON).content(item))

					.andExpect(MockMvcResultMatchers.status().isConflict());

		}
	}

	@Test
	public void test03_Update_WRONG_SENSOR_409() throws Exception {

		if (activeFK == false) {
			assertFalse(activeFK);
		} else {

			String item = "{" + "\"id\" : \"TEMPSTAT04\"," + "\"isHostedBy\" : \"STATKO\","
					+ "\"observesTitle\" : \"Elemento para tests 2\"," + "\"observesId\" : \"elementTests\"" + "}";

			String agendaUPDATE = new String(item.getBytes(), "UTF-8");

			this.mockMvc
					.perform(
							MockMvcRequestBuilders
									.put(CalidadAireSensorController.CONTEXTO + "/estacion/" + "STAT04" + "/sensor/"
											+ "elementTests")
									.contentType(MediaType.APPLICATION_JSON).content(agendaUPDATE))

					.andExpect(MockMvcResultMatchers.status().isConflict());

		}
	}
	
	
	@Test    
    public void test99_Delete() throws Exception {
    	
		if (activeFK == false) {
			assertFalse(activeFK);
		} else {    	    	
			this.mockMvc.perform(MockMvcRequestBuilders.delete(CalidadAireSensorController.CONTEXTO+"/estacion/"+"STAT04"+"/sensor/"+"elementTests"+".json")
        		.contentType(MediaType.APPLICATION_JSON))            
	        		.andExpect(MockMvcResultMatchers.status().isOk());
		}
    }

}