/**
 * Copyright 2021 Ayuntamiento de A Coruña, Ayuntamiento de Madrid, Ayuntamiento de Santiago de Compostela, Ayuntamiento de Zaragoza, Entidad Pública Empresarial Red.es
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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.apache.log4j.Logger;
import org.ciudadesAbiertas.madrid.utils.constants.LiteralConstants;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * @author Juan Carlos Ballesteros (Localidata)
 * @author Carlos Martínez de la Casa (Localidata)
 * @author Oscar Corcho (UPM, Localidata)
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class Test_06_Template_Swagger {

private static WebDriver driver;

private static final Logger log = Logger.getLogger(Test_06_Template_Swagger.class);

public static final String DEF_SEMANTIC_URL = TestConstants.HOST + TestConstants.CONTEXT + "/semanticDef";

private JSONParser parser = new JSONParser();

@BeforeClass
public static void setup() throws Exception {

  TestFunctions.loadConfiguration();

  log.info("setup");

  driver = TestFunctions.configureDriver(TestConstants.DRIVER_SELECTED);

  driver.manage().window().maximize();

  TestFunctions.setTimeOut(driver);

}

@Test
public void test_01_AddQueryXY() {
  log.info("test_01_AddQueryXY");

  Test_01_Login.loginSteps(driver);

  driver.get(Test_04_Query.QUERY_URL);

  TestFunctions.await(driver);

  TestFunctions.checkErrorOnTitle(driver);

  driver.get(Test_04_Query.QUERY_URL + "/add");

  TestFunctions.await(driver);

  TestFunctions.checkErrorOnTitle(driver);

  WebElement textoBox = driver.findElement(By.name("code"));
  textoBox.clear();
  textoBox.sendKeys(Test_04_Query.PRUEBA_SELENIUM);

  WebElement textoLargoBox = driver.findElement(By.name("texto"));
  textoLargoBox.clear();
  textoLargoBox.sendKeys("SELECT\r\n" + "        equipamiento.id ,\r\n" + "        equipamiento.title ,\r\n" + "        equipamiento.description ,\r\n" + "        equipamiento.tipo_equipamiento as tipoEquipamiento ,\r\n"
	  + "        equipamiento.municipio_id as municipioId ,\r\n" + "        equipamiento.municipio_title as municipioTitle ,\r\n" + "        equipamiento.provincia_id as provinciaId ,\r\n"
	  + "        equipamiento.autonomia_id as autonomiaId ,\r\n" + "        equipamiento.pais_id as paisId ,\r\n" + "        equipamiento.street_address as streetAddress ,\r\n" + "        equipamiento.postal_code as postalCode ,\r\n"
	  + "        equipamiento.barrio_id as barrioId ,\r\n" + "        equipamiento.distrito_id as distritoId ,\r\n" + "        equipamiento.email ,\r\n" + "        equipamiento.telephone ,\r\n" + "        equipamiento.url ,\r\n"
	  + "        equipamiento.opening_hours as openingHours ,\r\n" + "        equipamiento.x_etrs89 AS xETRS89 ,\r\n" + "        equipamiento.y_etrs89 AS yETRS89 \r\n" + "    FROM\r\n" + "        equipamiento \r\n" + "    WHERE\r\n"
	  + "        equipamiento.tipo_equipamiento LIKE 'equipamiento municipal'");

  WebElement summaryBox = driver.findElement(By.name("summary"));
  summaryBox.clear();
  summaryBox.sendKeys("Equipamiento XY prueba");

  WebElement etiquetasBox = driver.findElement(By.name("tags"));
  etiquetasBox.clear();
  etiquetasBox.sendKeys("Equipamiento XY prueba");

  WebElement boton = driver.findElement(By.id("botonGuardar"));
  boton.click();

  TestFunctions.await(driver);

  TestFunctions.checkErrorOnTitle(driver);

  WebElement advice = driver.findElement(By.id("addedAdviceTitle"));
  log.info(advice.getText());

  assertEquals(advice.getText(), LiteralConstants.TEXT_ADDED);

}

@Test
public void test_02_Swagger_Check() {
  log.info("test_02_Swagger_Check");

  driver.get(TestConstants.SWAGGER_URL + "#/Equipamiento%20XY%20prueba/listprueba-selenium");

  TestFunctions.await(driver);

  WebElement findElement = driver.findElement(By.id("operations-Equipamiento_XY_prueba-listprueba-selenium"));
  List<WebElement> buttonList = findElement.findElements(By.className("btn"));
  WebElement buttonTryOut=buttonList.get(0);
  buttonTryOut.click(); 

  findElement = driver.findElement(By.id("operations-Equipamiento_XY_prueba-listprueba-selenium"));
  buttonList = findElement.findElements(By.className("execute"));
  WebElement buttonExecute=buttonList.get(0);
  buttonExecute.click(); 
  log.info("send petition query"); 

  // Espero 10 segundos para la respuesta
  TestFunctions.sleep(10000);

  log.info("read response");
  findElement = driver.findElement(By.className("highlight-code"));
  List<WebElement> preList = findElement.findElements(By.className("microlight"));
  WebElement pre = preList.get(0);
  String JSONResponse = pre.getText();

  JSONObject parsed = new JSONObject();
  try {
	parsed = (JSONObject) parser.parse(JSONResponse);
  } catch (ParseException e) {
	log.error("Error parsing data", e);
	assertTrue(false);
	return;
  }

  try {
	JSONArray records = (JSONArray) parsed.get("records");
	JSONObject element = (JSONObject) records.get(0);
	
	log.info(element.toJSONString());
	
	if (element.get("xETRS89") != null && element.get("yETRS89") != null) {
	  Double x = (Double) element.get("xETRS89");
	  Double y = (Double) element.get("yETRS89");
	  
	  Double lat = (Double) element.get("latitud");
	  Double lon = (Double) element.get("longitud");
	  
	  assertTrue(x > 0 && y > 0 && Math.abs(lon) > 0 && Math.abs(lat) > 0);
	} else {
	  assertTrue(false);
	}
  } catch (Exception e) {
	log.error("Error reading coordinates", e);
	assertTrue(false);
  }

}


@Test
public void test_03_DeleteQueryXY() {
  log.info("test_03_DeleteQueryXY");

  Test_01_Login.loginSteps(driver);

  driver.get(Test_04_Query.QUERY_URL);
  
  TestFunctions.await(driver);

  TestFunctions.checkErrorOnTitle(driver);

  WebElement justAddedLink = driver.findElement(By.linkText(Test_04_Query.PRUEBA_SELENIUM));
  log.info(justAddedLink.getText());
  justAddedLink.click();
  
  TestFunctions.await(driver);

  TestFunctions.checkErrorOnTitle(driver);

  WebElement deleteLink = driver.findElement(By.id("enlaceBorrar"));
  deleteLink.click();

  TestFunctions.checkErrorOnTitle(driver);

  WebElement boton = driver.findElement(By.id("botonBorrar"));
  boton.click();
  
  TestFunctions.await(driver);

  TestFunctions.checkErrorOnTitle(driver);

  WebElement advice = driver.findElement(By.id("deletedAdviceTitle"));
  log.info(advice.getText());

  assertEquals(advice.getText(), LiteralConstants.TEXT_DELETED);

}


@Test
public void test_04_AddQueryGeographicDistance() {
  log.info("test_04_AddQueryGeographicDistance");

  Test_01_Login.loginSteps(driver);

  driver.get(Test_04_Query.QUERY_URL);

  TestFunctions.await(driver);

  TestFunctions.checkErrorOnTitle(driver);

  driver.get(Test_04_Query.QUERY_URL + "/add?typeQuery=geoquery");

  TestFunctions.await(driver);

  TestFunctions.checkErrorOnTitle(driver);

  WebElement textoBox = driver.findElement(By.name("code"));
  textoBox.clear();
  textoBox.sendKeys(Test_04_Query.PRUEBA_SELENIUM);

  WebElement textoLargoBox = driver.findElement(By.name("texto"));
  textoLargoBox.clear();
  textoLargoBox.sendKeys("select * from (\r\n"
  	+ "  select \r\n"
  	+ "  id, \r\n"
  	+ "  title, \r\n"
  	+ "  municipio_id as municipioId, \r\n"
  	+ "  x_etrs89 as xETRS89, \r\n"
  	+ "  y_etrs89 as yETRS89, \r\n"
  	+ "  SQRT((x_etrs89-xEtrs89P)*(x_etrs89-xEtrs89P)+(y_etrs89-yEtrs89P)*(y_etrs89-yEtrs89P)) as distance \r\n"
  	+ "  from equipamiento \r\n"
  	+ ") as consultaGeo \r\n"
  	+ "where \r\n"
  	+ "	distance<=metersP \r\n"
  	+ "order by \r\n"
  	+ "	distance asc");

  WebElement summaryBox = driver.findElement(By.name("summary"));
  summaryBox.clear();
  summaryBox.sendKeys("Equipamiento Geo prueba");

  WebElement etiquetasBox = driver.findElement(By.name("tags"));
  etiquetasBox.clear();
  etiquetasBox.sendKeys("Equipamiento Geo prueba");
  
  WebElement example0 = driver.findElement(By.name("example0"));
  example0.clear();
  example0.sendKeys("445672.8734");
  
  WebElement example1 = driver.findElement(By.name("example1"));
  example1.clear();
  example1.sendKeys("4488726.88198");
  

  WebElement boton = driver.findElement(By.id("botonGuardar"));
  boton.click();

  TestFunctions.await(driver);

  TestFunctions.checkErrorOnTitle(driver);

  WebElement advice = driver.findElement(By.id("addedAdviceTitle"));
  log.info(advice.getText());

  assertEquals(advice.getText(), LiteralConstants.TEXT_ADDED);

}

@Test
public void test_05_Swagger_Check() {
  log.info("test_05_Swagger_Check");

  driver.get(TestConstants.SWAGGER_URL + "#/Equipamiento%20Geo%20prueba/listprueba-selenium");

  TestFunctions.await(driver);

  WebElement findElement = driver.findElement(By.id("operations-Equipamiento_Geo_prueba-listprueba-selenium"));
  List<WebElement> buttonList = findElement.findElements(By.className("btn"));
  WebElement buttonTryOut=buttonList.get(0);
  buttonTryOut.click(); 

  findElement = driver.findElement(By.id("operations-Equipamiento_Geo_prueba-listprueba-selenium"));
  buttonList = findElement.findElements(By.className("execute"));
  WebElement buttonExecute=buttonList.get(0);
  buttonExecute.click(); 
  log.info("send petition query"); 

  // Espero 5 segundos para la respuesta
  TestFunctions.sleep(5000);

  log.info("read response");
  findElement = driver.findElement(By.className("highlight-code"));
  List<WebElement> preList = findElement.findElements(By.className("microlight"));
  WebElement pre = preList.get(0);
  String JSONResponse = pre.getText();

  JSONObject parsed = new JSONObject();
  try {
	parsed = (JSONObject) parser.parse(JSONResponse);
  } catch (ParseException e) {
	log.error("Error parsing data", e);
	assertTrue(false);
	return;
  }

  try {
	JSONArray records = (JSONArray) parsed.get("records");
	JSONObject element = (JSONObject) records.get(0);
	
	log.info(element.toJSONString());
	if (element.get("xETRS89") != null && element.get("yETRS89") != null) {
	  Double x = (Double) element.get("xETRS89");
	  Double y = (Double) element.get("yETRS89");

	  Double lat = (Double) element.get("latitud");
	  Double lon = (Double) element.get("longitud");
	  
	  assertTrue(x > 0 && y > 0 && Math.abs(lon) > 0 && Math.abs(lat) > 0);
	} else {
	  assertTrue(false);
	}
  } catch (Exception e) {
	log.error("Error reading coordinates", e);
	assertTrue(false);
  }

}


@Test
public void test_06_DeleteQueryGeo() {
  log.info("test_06_DeleteQueryGeo");

  Test_01_Login.loginSteps(driver);

  driver.get(Test_04_Query.QUERY_URL);
  
  TestFunctions.await(driver);

  TestFunctions.checkErrorOnTitle(driver);

  WebElement justAddedLink = driver.findElement(By.linkText(Test_04_Query.PRUEBA_SELENIUM));
  log.info(justAddedLink.getText());
  justAddedLink.click();
  
  TestFunctions.await(driver);

  TestFunctions.checkErrorOnTitle(driver);

  WebElement deleteLink = driver.findElement(By.id("enlaceBorrar"));
  deleteLink.click();

  TestFunctions.checkErrorOnTitle(driver);

  WebElement boton = driver.findElement(By.id("botonBorrar"));
  boton.click();
  
  TestFunctions.await(driver);

  TestFunctions.checkErrorOnTitle(driver);

  WebElement advice = driver.findElement(By.id("deletedAdviceTitle"));
  log.info(advice.getText());

  assertEquals(advice.getText(), LiteralConstants.TEXT_DELETED);

}


@AfterClass
public static void tearDown() throws Exception {
  driver.quit();
}

}
