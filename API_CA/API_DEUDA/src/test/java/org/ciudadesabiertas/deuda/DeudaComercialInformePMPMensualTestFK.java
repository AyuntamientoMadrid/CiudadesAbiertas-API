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

package org.ciudadesabiertas.deuda;

import static org.junit.Assert.assertFalse;

import org.ciudadesabiertas.config.WebConfig;
import org.ciudadesabiertas.dataset.controller.DeudaComercialInformePMPMensualController;
import org.ciudadesabiertas.utils.StartVariables;
import org.ciudadesabiertas.utils.Util;
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
public class DeudaComercialInformePMPMensualTestFK {
	
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

	    	String id ="XXX-XXX-PMP-MENSUAL-01";
	    	String item = "{\r\n"
	    			+ "      \"id\": \""+id+"\",\r\n"
	    			+ "      \"periodoMedioPagoMensual\": 18.72,\n"
	    			+ "      \"ratioOperacionesPagadas\": 18.51,\n"
	    			+ "      \"importeOperacionesPagada\": 0,\n"
	    			+ "      \"ratioOperacionesPendientesPago\": 24.23,\n"
	    			+ "      \"importeOperacionesPendientesPago\": 0,\n"
	    			+ "      \"entidad\": \"02-50-297-AO-005\",\n"
	    			+ "      \"periodo\": \"2020-06\" \n"
	    			+ "    }";
	    	    	    	

			item = new String(item.getBytes(), "UTF-8");

			this.mockMvc.perform(MockMvcRequestBuilders.post(DeudaComercialInformePMPMensualController.ADD)
					.contentType(MediaType.APPLICATION_JSON).content(item))
					.andExpect(MockMvcResultMatchers.status().is2xxSuccessful());

		}
	}

	@Test
	public void test01_Add_ERROR_FK() throws Exception {

		if (activeFK == false) {
			assertFalse(activeFK);
		} else {

			String id ="XXX-XXX-PMP-MENSUAL-02";
	    	String item = "{\r\n"
	    			+ "      \"id\": \""+id+"\",\r\n"
	    			+ "      \"periodoMedioPagoMensual\": 18.72,\n"
	    			+ "      \"ratioOperacionesPagadas\": 18.51,\n"
	    			+ "      \"importeOperacionesPagada\": 0,\n"
	    			+ "      \"ratioOperacionesPendientesPago\": 24.23,\n"
	    			+ "      \"importeOperacionesPendientesPago\": 0,\n"
	    			//Modificamos los valores
	    			+ "      \"entidad\": \"02-50-297-AO-005-KO\",\n"
	    			+ "      \"periodo\": \"2020-06-KO\" \n"
	    			+ "    }";

			item = new String(item.getBytes(), "UTF-8");

			this.mockMvc.perform(MockMvcRequestBuilders.post(DeudaComercialInformePMPMensualController.ADD)
					.contentType(MediaType.APPLICATION_JSON).content(item))
					.andExpect(MockMvcResultMatchers.status().isConflict());

		}
	}

	@Test
	public void test07_Update() throws Exception {

		if (activeFK == false) {
			assertFalse(activeFK);
		} else {

			String id ="XXX-XXX-PMP-MENSUAL-01";
	    	String item = "{\r\n"
	    			+ "      \"id\": \""+id+"\",\r\n"
	    			+ "      \"periodoMedioPagoMensual\": 18.72,\n"
	    			+ "      \"ratioOperacionesPagadas\": 18.51,\n"
	    			+ "      \"importeOperacionesPagada\": 0,\n"
	    			+ "      \"ratioOperacionesPendientesPago\": 24.23,\n"
	    			+ "      \"importeOperacionesPendientesPago\": 0,\n"
	    			//Modificamos los valores
	    			+ "      \"entidad\": \"12-28-079-AP-001\",\n"
	    			+ "      \"periodo\": \"2020-07\"\n "
	    			+ "    }";

			String itemUPDATE = new String(item.getBytes(), "UTF-8");

			id = Util.encodeURL(id);

			this.mockMvc
					.perform(MockMvcRequestBuilders.put(DeudaComercialInformePMPMensualController.ADD + "/" + id)
							.contentType(MediaType.APPLICATION_JSON).content(itemUPDATE))

					.andExpect(MockMvcResultMatchers.status().isOk());

		}
	}

	@Test
	public void test07_Update_ERROR_FK() throws Exception {

		if (activeFK == false) {
			assertFalse(activeFK);
		} else {

			String id ="XXX-XXX-PMP-MENSUAL-01";
	    	String item = "{\r\n"
	    			+ "      \"id\": \""+id+"\",\r\n"
	    			+ "      \"periodoMedioPagoMensual\": 18.72,\n"
	    			+ "      \"ratioOperacionesPagadas\": 18.51,\n"
	    			+ "      \"importeOperacionesPagada\": 0,\n"
	    			+ "      \"ratioOperacionesPendientesPago\": 24.23,\n"
	    			+ "      \"importeOperacionesPendientesPago\": 0,\n"
	    			//Modificamos los valores
	    			+ "      \"entidad\": \"02-50-297-AO-005-KO\",\n"
	    			+ "      \"periodo\": \"2020-06-KO\" \n"
	    			+ "    }";

			String itemUPDATE = new String(item.getBytes(), "UTF-8");

			id = Util.encodeURL(id);

			this.mockMvc
					.perform(MockMvcRequestBuilders.put(DeudaComercialInformePMPMensualController.ADD + "/" + id)
							.contentType(MediaType.APPLICATION_JSON).content(itemUPDATE))

					.andExpect(MockMvcResultMatchers.status().isConflict());

		}
	}

	
	

	@Test
	public void test99_Delete() throws Exception {
		if (activeFK == false) {
			assertFalse(activeFK);
		} else {

			String id ="XXX-XXX-PMP-MENSUAL-01";

			id = Util.encodeURL(id);

			this.mockMvc
					.perform(MockMvcRequestBuilders.delete(DeudaComercialInformePMPMensualController.ADD + "/" + id)
							.contentType(MediaType.APPLICATION_JSON))

					.andExpect(MockMvcResultMatchers.status().isOk());

		}

	}
           
    
}
