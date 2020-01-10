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

import java.util.List;

import javax.servlet.ServletContext;

import org.ciudadesabiertas.config.WebConfig;
import org.ciudadesabiertas.dataset.controller.CuboIndicadoresController;
import org.ciudadesabiertas.dataset.controller.CuboNacionalidadController;
import org.ciudadesabiertas.dataset.model.CuboNacionalidad;
import org.ciudadesabiertas.dataset.utils.CuboPadronConstants;
import org.ciudadesabiertas.exception.DAOException;
import org.ciudadesabiertas.service.DatasetService;
import org.ciudadesabiertas.utils.TestUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;



@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { WebConfig.class })
@WebAppConfiguration
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CuboNacionalidadTest {
	
	@Autowired
	private WebApplicationContext wac;

	@Autowired
	private DatasetService<CuboNacionalidad> dsService;	
    

    
    private MockMvc mockMvc;
    @Before
    public void setup() throws Exception {

    	
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
       
    }

    @Test
    public void test01_Service() throws DAOException {
	
        final List<CuboNacionalidad> items = dsService.basicQuery(CuboPadronConstants.KEY,CuboNacionalidad.class);
        
        assertTrue( items.size()>0 );


    }
    
    
    @Test
    public void test02_Controller() {
        ServletContext servletContext = wac.getServletContext();
         
        Assert.assertNotNull(servletContext);
        Assert.assertTrue(servletContext instanceof MockServletContext);
        Assert.assertNotNull(wac.getBean("cuboNacionalidadController"));
    }
    
    
    
    @Test    
    public void test03_List() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get(CuboNacionalidadController.LIST+".json")).andExpect(MockMvcResultMatchers.status().isOk());
    }
    
    
    @Test    
    public void test04_Query1Dimension() throws Exception {
    	
    	String dimensionP="dimension=refPeriod";
    	String groupP="group=AVG";
    	String measureP="measure=numeroPersonas";
    	
    	String params="?"+dimensionP+"&"+groupP+"&"+measureP;
    	
        this.mockMvc.perform(MockMvcRequestBuilders.get(CuboNacionalidadController.QUERY+".json"+params)).andExpect(MockMvcResultMatchers.status().isOk());
    }
    
    
    @Test    
    public void test05_Query2Dimensions() throws Exception {
    	
    	String dimensionP="dimension=refPeriod,edadGruposQuinquenales";
    	String groupP="group=AVG";
    	String measureP="measure=numeroPersonas";
    	
    	String params="?"+dimensionP+"&"+groupP+"&"+measureP;
    	
        this.mockMvc.perform(MockMvcRequestBuilders.get(CuboNacionalidadController.QUERY+".json"+params)).andExpect(MockMvcResultMatchers.status().isOk());
    }
    
    
    @Test    
    public void test05_Query2DimensionsWhere() throws Exception {
    	
    	String dimensionP="dimension=refPeriod,edadGruposQuinquenales";
    	String groupP="group=AVG";
    	String measureP="measure=numeroPersonas";
    	String whereP="where=numeroPersonas>50000";
    	
    	String params="?"+dimensionP+"&"+groupP+"&"+measureP+"&"+whereP;
    	
        this.mockMvc.perform(MockMvcRequestBuilders.get(CuboNacionalidadController.QUERY+".json"+params)).andExpect(MockMvcResultMatchers.status().isOk());
    }
   
    

       
    
    @Test    
    public void test10_Record() throws Exception {
    	String id ="obs1";
    	    	
        this.mockMvc.perform(MockMvcRequestBuilders.get(CuboNacionalidadController.LIST+"/"+id+".json")
        		.contentType(MediaType.APPLICATION_JSON))	
            
	        .andExpect(MockMvcResultMatchers.status().isOk());
    }
    
    @Test    
    public void test11_Record_HEAD() throws Exception {
    	String id ="obs1";
    	
        this.mockMvc.perform(MockMvcRequestBuilders.head(CuboNacionalidadController.LIST+"/"+id+".json")
        		.contentType(MediaType.APPLICATION_JSON))
	        .andExpect(MockMvcResultMatchers.status().isOk());
    }
    
    @Test    
    public void test12_Record_NO_OK_404() throws Exception {
    	String id ="obs1_ko";
    	    	
        this.mockMvc.perform(MockMvcRequestBuilders.get(CuboNacionalidadController.LIST+"/"+id+".json")
        		.contentType(MediaType.APPLICATION_JSON))	
            
	        .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
    
   
    
    @Test    
    public void test15_List_HEAD() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.head(CuboNacionalidadController.LIST+".json")).andExpect(MockMvcResultMatchers.status().isOk());
    }
    
    @Test    
    public void test16_List_RDF() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.head(CuboNacionalidadController.LIST+".rdf")).andExpect(MockMvcResultMatchers.status().isOk());
    }
    
    @Test    
    public void test16_List_HEAD_303() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.head(CuboNacionalidadController.LIST)).andExpect(MockMvcResultMatchers.status().is(303));
    }
    
    @Test    
    public void test17_List_303() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get(CuboNacionalidadController.LIST)).andExpect(MockMvcResultMatchers.status().is(303));
    }

    
   
    
    @Test    
    public void test21_List_Sort_200() throws Exception {
    	String sort="?sort=-id,numeroPersonas";
        this.mockMvc.perform(MockMvcRequestBuilders.get(CuboNacionalidadController.LIST+".json"+sort)).andExpect(MockMvcResultMatchers.status().is(200));
    }
    
    @Test    
    public void test22_List_Sort_400() throws Exception {
    	String sort="?sort=-id,erroneo";
        this.mockMvc.perform(MockMvcRequestBuilders.get(CuboNacionalidadController.LIST+".json"+sort)).andExpect(MockMvcResultMatchers.status().is(400));
    }
    
    
    @Test
    public void test25_List_Formatos_200() throws Exception {    	    	
    	boolean checkAllFormats=TestUtils.checkFormatURIs(CuboNacionalidadController.LIST, mockMvc);
    	assertTrue(checkAllFormats);    	    	
    }
    
    
    @Test
    public void test26_List_RDF_200() throws Exception {    	
    	String theURI = TestUtils.checkRDFURI(this.mockMvc,CuboNacionalidadController.LIST);        
        this.mockMvc.perform(MockMvcRequestBuilders.get(theURI)).andExpect(MockMvcResultMatchers.status().is(200));    	    	
    }
    
    @Test
    public void test27_Record_Formatos_200() throws Exception {    	    	
    	boolean checkAllFormats=TestUtils.checkFormatURIs(CuboNacionalidadController.LIST+"/"+"obs1", mockMvc);
    	assertTrue(checkAllFormats);    	    	
    }
    
}
