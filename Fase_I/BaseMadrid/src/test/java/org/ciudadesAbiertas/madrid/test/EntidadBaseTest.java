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

package org.ciudadesAbiertas.madrid.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletContext;

import org.ciudadesAbiertas.madrid.config.WebConfig;
import org.ciudadesAbiertas.madrid.model.EntidadBase;
import org.ciudadesAbiertas.madrid.service.EntidadBaseService;
import org.ciudadesAbiertas.madrid.utils.TestUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
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
public class EntidadBaseTest {
	
	

	private static final Logger log = LoggerFactory.getLogger(EntidadBaseTest.class);
	
	@Autowired
	private WebApplicationContext wac;

	@Autowired
	private EntidadBaseService service;
    
    private EntidadBase bean;
    
    @Before
    public void setup() throws Exception 
    {           
        bean=new EntidadBase();
        bean.setFecha(new Date());
        bean.setId("Test00000023");
        bean.setNumeroDecimal(BigDecimal.valueOf(21111112.44));
        bean.setNumeroEntero(2131443222);
        bean.setTexto("texto de test");
        bean.setTextoLargo("texto largo de test");
    }

    @Test
    public void test01_Service()  
    {
        
        assertNotNull(service);
    }
    
    
    @Test
    public void test02_Controller() {
        ServletContext servletContext = wac.getServletContext();
         
        Assert.assertNotNull(servletContext);
        Assert.assertTrue(servletContext instanceof MockServletContext);
        Assert.assertNotNull(wac.getBean("entidadBaseController"));
    }

    
    
    @Test    
    public void test03_Add() throws Exception 
    {	
		int countA=service.listRowCount();		
		service.add(bean);		
		int countB=service.listRowCount();		
		assertTrue(countA<countB);
    }
    
    @Test    
    public void test04_List() throws Exception {
    
    	 final List<EntidadBase> items = service.list(0, 10);
         assertTrue( items.size()>0 );
    }
    
        
    @Test    
    public void test05_Add_KO() throws Exception {
    	
    	bean.setTexto("");
    	int countA=service.listRowCount();
    	bean.setTexto(null);
    	try
    	{
    		service.add(bean);
    	}
    	catch (Exception e)
    	{
    		log.error(TestUtils.EXCEPCION_CONTROLADA+e);
    	}
		int countB=service.listRowCount();		
		assertTrue(countA==countB);
    }
     
    @Test    
    public void test06_Update() throws Exception {    	
    	
    	String id=bean.getId();
    	bean.setTexto("Texto Actualizado");
    	service.update(id, bean);    	
    	EntidadBase record = service.record(id);    	
    	assertTrue(record.getTexto().equals(bean.getTexto()));
    }
    
    
    
    @Test    
    public void test08_Update_Wrong_Update() throws Exception {
    	String id=bean.getId();
    	bean.setTexto(null);    	
    	try
    	{
    		service.update(id, bean);
    	}
    	catch (Exception e)
    	{
    		log.error(TestUtils.EXCEPCION_CONTROLADA+e);
    	}
    	EntidadBase item = service.record(id);
    	  	
    	assertFalse(item.getTexto().equals(bean.getTexto()));
    }
    
    @Test    
    public void test09_Update_Wrong_Id() throws Exception {    	
    	Exception exception=null;
    	try
    	{
    		service.update(TestUtils.NO_EXISTE, bean);
    	}
    	catch (Exception e)
    	{
    		exception=e;
    		log.error(TestUtils.EXCEPCION_CONTROLADA+e);
    	}    	
    	finally
    	{
    		assertNotNull(exception);
    	}
    }
    
   
   
    
    @Test    
    public void test10_Record() throws Exception {
    	
    	EntidadBase record = service.record(bean.getId());
    	assertNotNull(record);
    	
    }
    
   
    
    @Test    
    public void test12_Record_No_Existe() throws Exception {
    	
    	EntidadBase record = service.record(TestUtils.NO_EXISTE);
    	assertNull(record);
    }
    
    
    
    @Test    
    public void test13_Delete() throws Exception {
    	
    	int countA=service.listRowCount();		
		service.delete(bean.getId());		
		int countB=service.listRowCount();		
		assertTrue(countA>countB);
    }
    
    
    
    @Test    
    public void test14_Delete_No_Existe() throws Exception {
    
    	Exception ex=null;
    	try
    	{
    		service.delete(TestUtils.NO_EXISTE);
    	}
    	catch (Exception e)
    	{
    		log.error(TestUtils.EXCEPCION_CONTROLADA+e);
    		ex=e;
    	}
    	finally
    	{
    		assertNotNull(ex);
    	}
    	
    	
    }
    
    
    
}
