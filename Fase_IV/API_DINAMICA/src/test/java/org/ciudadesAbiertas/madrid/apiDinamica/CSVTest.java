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
public class CSVTest {

@Autowired
private WebApplicationContext wac;

private MockMvc mockMvc;

@Before
public void setup() throws Exception {
  this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
}

private static final Logger log = LoggerFactory.getLogger(CSVTest.class);



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
public void test02_CheckCSV01() throws Exception {

  String URL = "/API/query/testCode01.csv";

  MockHttpServletResponse response = this.mockMvc.perform(MockMvcRequestBuilders.get(URL)).andReturn().getResponse();
  
  String separador=",";
  
  boolean testCondition = checkCSV(response, separador); 

  assertTrue(testCondition);
}

@Test
public void test03_CheckCSV02() throws Exception {
  
  String separador=";";
  
  String URL = "/API/query/testCode01.csv?"+Constants.PARAM_CSV_SEPARATOR+"="+separador;
  
  MockHttpServletResponse response = this.mockMvc.perform(MockMvcRequestBuilders.get(URL)).andReturn().getResponse();
  
  boolean testCondition = checkCSV(response, separador); 

  assertTrue(testCondition);
}

@Test
public void test04_CheckCSV03() throws Exception {
   
  String separador="*";
  
  String URL = "/API/query/testCode01.csv?"+Constants.PARAM_CSV_SEPARATOR+"="+separador;
  
  MockHttpServletResponse response = this.mockMvc.perform(MockMvcRequestBuilders.get(URL)).andReturn().getResponse();
  
  separador="\t";
  
  boolean testCondition = checkCSV(response, separador); 

  assertTrue(testCondition);
  

}

public boolean checkCSV(MockHttpServletResponse response, String separador) throws UnsupportedEncodingException {
  boolean testCondition=false;
  if (response.getStatus() == 200) {
	String contentAsString = response.getContentAsString();
	if (contentAsString!=null)
	{
	  //borramos el bom
	  contentAsString = contentAsString.substring(3);
	  String[] lines = contentAsString.split("\n");
	  log.info(lines.length+" lines");
	  if (lines.length>2)
	  {
		String dataLine=lines[2];
		String[] columns = dataLine.split(separador);
		if (columns.length>0) {
		  testCondition=true;
		}
	  }
	}
	
  }
  return testCondition;
}


@Test
public void test05_AddDistinctQueryToCSV() throws Exception {

  int pre = queryService.listRowCount();

  QueryD query = new QueryD();
  query.setCode("testCode02");
  query.setSummary("consulta de prueba");
  query.setDatabase("default");
  query.setTexto(" select\n"
  	+ "        distinct field \n"
  	+ "    from\n"
  	+ "        (  SELECT\n"
  	+ "            id        ,\n"
  	+ "            title        ,\n"
  	+ "            area_id as areaId        ,\n"
  	+ "            area_title as areaTitle        ,\n"
  	+ "            municipio_id as municipioId        ,\n"
  	+ "            municipio_title as municipioTitle        ,\n"
  	+ "            adjudicatario_id as adjudicatarioId        ,\n"
  	+ "            adjudicatario_title as adjudicatarioTitle        ,\n"
  	+ "            entidad_financiadora_id as entidadFinanciadoraId        ,\n"
  	+ "            entidad_financiadora_title as entidadFinanciadoraTitle        ,\n"
  	+ "            importe        ,\n"
  	+ "            fecha_adjudicacion as fechaAdjudicacion        ,\n"
  	+ "            linea_financiacion as lineaFinanciacion        ,\n"
  	+ "            bases_reguladoras as basesReguladoras        ,\n"
  	+ "            tipo_instrumento as tipoInstrumento        ,\n"
  	+ "            aplicacion_presupuestaria as aplicacionPresupuestaria     \n"
  	+ "        FROM\n"
  	+ "            subvencion) as consultaDistinct");
  query.setTags("Etiqueta 1");

  ParamD parametro=new ParamD();
  parametro.setDescription("campo para realizar el distinct");
  parametro.setName("field");
  parametro.setType("text");
  parametro.setExample("id");

  
  List<ParamD> paramList=new ArrayList<ParamD>();
  
  paramList.add(parametro);
  
  queryService.add(query, paramList);

  int post = queryService.listRowCount();

  assertTrue(post > pre);

}


@Test
public void test06_CheckDistinctCSV() throws Exception {
   
  String separador=",";
  
  String URL = "/API/query/testCode02.csv?field=id&"+Constants.PARAM_CSV_SEPARATOR+"="+separador;
  
  MockHttpServletResponse response = this.mockMvc.perform(MockMvcRequestBuilders.get(URL)).andReturn().getResponse();
  
  boolean testCondition = checkCSV(response, separador); 

  assertTrue(testCondition);
  

}

@Test
public void test98_RemoveQuery() throws Exception {

  int pre = queryService.listRowCount();

  queryService.delete(queryService.recordCode("testCode01"), null);

  int post = queryService.listRowCount();

  assertTrue(post < pre);

}

@Test
public void test99_RemoveDistinctQuery() throws Exception {

  int pre = queryService.listRowCount();
 
  QueryD recordCode = queryService.recordCode("testCode02");
  
  List<ParamD> paramList = paramService.list("testCode02");

  queryService.delete(recordCode, paramList );

  int post = queryService.listRowCount();

  assertTrue(post < pre);

}

}
