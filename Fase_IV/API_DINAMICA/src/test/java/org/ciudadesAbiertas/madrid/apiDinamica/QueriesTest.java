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

package org.ciudadesAbiertas.madrid.apiDinamica;

import static org.junit.Assert.assertTrue;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.ciudadesAbiertas.madrid.config.WebConfig;
import org.ciudadesAbiertas.madrid.model.dynamic.ParamD;
import org.ciudadesAbiertas.madrid.model.dynamic.QueryD;
import org.ciudadesAbiertas.madrid.service.dynamic.ParamService;
import org.ciudadesAbiertas.madrid.service.dynamic.QueryService;
import org.ciudadesAbiertas.madrid.utils.constants.Constants;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
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
public class QueriesTest {

@Autowired
private WebApplicationContext wac;

private MockMvc mockMvc;

@Before
public void setup() throws Exception {
  this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
}

private static final Logger log = LoggerFactory.getLogger(QueriesTest.class);



@Autowired
private QueryService queryService;

@Autowired
private ParamService paramService;

@Test
public void test01_AddQueryToCSV() throws Exception {

  int pre = queryService.listRowCount();

  QueryD query = new QueryD();
  query.setCode("testCode01");
  query.setSummary("consulta de prueba");
  query.setDatabase("default");
  query.setTexto("Select * from subvencion");
  query.setTags("Etiqueta 1");

  queryService.add(query, null);

  int post = queryService.listRowCount();

  assertTrue(post > pre);

}



@Test
public void test02_basic() throws Exception {

  String URL = "/API/query/testCode01.json";

  MockHttpServletResponse response = this.mockMvc.perform(MockMvcRequestBuilders.get(URL)).andReturn().getResponse();
 
  boolean testCondition = checkStatus(response); 

  assertTrue(testCondition);
}



@Test
public void test02_basic_pagination() throws Exception {

  String URL = "/API/query/testCode01.json?pageSize=100&page=3";

  MockHttpServletResponse response = this.mockMvc.perform(MockMvcRequestBuilders.get(URL)).andReturn().getResponse();
 
  boolean testCondition = checkStatus(response); 

  assertTrue(testCondition);
}


public boolean checkStatus(MockHttpServletResponse response) throws UnsupportedEncodingException {
	if (response == null) {
		return false;
	}
	return (response.getStatus() == 200);
}

@Test
public void test99_RemoveQuery() throws Exception {

  int pre = queryService.listRowCount();

  queryService.delete(queryService.recordCode("testCode01"), null);

  int post = queryService.listRowCount();

  assertTrue(post < pre);

}



}
