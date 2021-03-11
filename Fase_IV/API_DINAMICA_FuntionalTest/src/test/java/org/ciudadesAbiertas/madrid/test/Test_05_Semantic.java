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

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.ciudadesAbiertas.madrid.utils.constants.LiteralConstants;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

/**
 * @author Juan Carlos Ballesteros (Localidata)
 * @author Carlos Martínez de la Casa (Localidata)
 * @author Oscar Corcho (UPM, Localidata)
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class Test_05_Semantic {

private static final String CODIGO_ESADM = "2";

// CODIGO SELECT
private static String CODIGO_CONSULTA = Test_04_Query.PRUEBA_SELENIUM;

private static String CODIGO_PREFIX = "4";

private static String CODIGO_DCTERMS = "1";

private static String CODIGO_FIN_URL = "Subvencion";

// COMBO QUERY
private static String COMBO_QUERY = "query";

private static String COMBO_PREFIX = "prefixAvailable";

private static String COMBO_FIN_URI = "fieldObjectURI";

private static String COMBO_URL_TIPO = "prefixType";



// ELEMENTOS

private static final String ELEMENT_ID = "id";

private static final String ELEMENT_TYPEURI = "typeURI";



// BOTONES

private static final String BOTON_GUARDAR = "botonGuardar";

private static final String BOTON_BORRAR = "botonBorrar";

// Avisos

private static final String ADVICE_TITLE_ADD = "addedAdviceTitle";

private static final String ADVICE_TITLE_UPDATE = "updatedAdviceTitle";

private static final String ADVICE_TITLE_DELETE = "deletedAdviceTitle";

// Enlace Editar

private static final String ENLACE_EDITAR = "enlaceEditar";

private static final String ENLACE_BORRAR = "enlaceBorrar";

private static WebDriver driver;

private static final Logger log = Logger.getLogger(Test_05_Semantic.class);

public static final String DEF_SEMANTIC_URL = TestConstants.HOST + TestConstants.CONTEXT + "/semanticDef";

@BeforeClass
public static void setup() throws Exception {

  TestFunctions.loadConfiguration();

  log.info("setup");

  driver = TestFunctions.configureDriver(TestConstants.DRIVER_SELECTED);

  driver.manage().window().maximize();

  TestFunctions.setTimeOut(driver);

}

@Test
public void test_01_AddQueryRecord() {
  log.info("test_01_AddQueryRecord");

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
  
  String query="SELECT\r\n" 
		  + "        id,\r\n" 
		  + "        title,\r\n" 
		  + "        area_id as areaId,\r\n" 
		  + "        area_title as areaTitle,\r\n"	  
		  + "        municipio_id as municipioId,\r\n" 
		  + "        municipio_title as municipioTitle,\r\n" 
		  + "        adjudicatario_id as adjudicatarioId,\r\n"	  
		  + "        adjudicatario_title as adjudicatarioTitle,\r\n" 
		  + "        entidad_financiadora_id as entidadFinanciadoraId,\r\n" 
		  + "        entidad_financiadora_title as entidadFinanciadoraTitle,\r\n"	  
		  + "        importe,\r\n" 
		  + "        fecha_adjudicacion as fechaAdjudicacion,\r\n" 
		  + "        linea_financiacion as lineaFinanciacion,\r\n" 
		  + "        bases_reguladoras as basesReguladoras,\r\n"	  
		  + "        tipo_instrumento as tipoInstrumento,\r\n" 
		  + "        aplicacion_presupuestaria as aplicacionPresupuestaria"  
		  + "    FROM\r\n" 
		  + "        subvencion";
  
  textoLargoBox.sendKeys(query);

  WebElement summaryBox = driver.findElement(By.name("summary"));
  summaryBox.clear();
  summaryBox.sendKeys("Yo soy Groot. Yo soy Groot. Yo soy Groot. Yo soy Groot. Yo soy Groot.");

  WebElement etiquetasBox = driver.findElement(By.name("tags"));
  etiquetasBox.clear();
  etiquetasBox.sendKeys("Etiqueta1");

  WebElement boton = driver.findElement(By.id("botonGuardar"));
  boton.click();
  
  TestFunctions.await(driver);

  TestFunctions.checkErrorOnTitle(driver);

  WebElement advice = driver.findElement(By.id("addedAdviceTitle"));
  log.info(advice.getText());

  assertEquals(advice.getText(), LiteralConstants.TEXT_ADDED);

}

@Test
public void test_02_AddRecord() {
  log.info("test_02_AddRecord");

  Test_01_Login.loginSteps(driver);

  driver.get(DEF_SEMANTIC_URL);
  
  TestFunctions.await(driver);

  TestFunctions.checkErrorOnTitle(driver);

  driver.get(DEF_SEMANTIC_URL + "/add");
  
  TestFunctions.await(driver);

  TestFunctions.checkErrorOnTitle(driver);

  // COMBO QUERY
  Select selectQuery = new Select(driver.findElement(By.id(COMBO_QUERY)));

  selectQuery.selectByValue(CODIGO_CONSULTA);

  // COMBO PREFIX
  Select selectPrefix = new Select(driver.findElement(By.id(COMBO_PREFIX)));

  List<String> prefixes=new ArrayList<String>();
  prefixes.add("1");
  prefixes.add("2");
  prefixes.add("3");
  prefixes.add("4");
  prefixes.add("5");
  prefixes.add("6");
  prefixes.add("7");
  prefixes.add("8");
  prefixes.add("9");
 
  for (String prefix:prefixes)
  {
	  selectPrefix.selectByValue(prefix);
	  if (driver instanceof JavascriptExecutor) {
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("testPruebas('" + prefix + "')");
	  } else {
		throw new IllegalStateException("This driver does not support JavaScript!");
	  }
	  TestFunctions.sleep(100);
  }
  
  // COMBO FIN URI
  Select selectFinURi = new Select(driver.findElement(By.id(COMBO_FIN_URI)));

  selectFinURi.selectByValue(ELEMENT_ID);

  // COMBO PREDICADO (URL TIPO)
  // Es necesario que previamente se halla selecionado el prefijo essubv -->
  // CODIGO_PREFIX

  Select selectUrlTipo = new Select(driver.findElement(By.id(COMBO_URL_TIPO)));

  selectUrlTipo.selectByValue(CODIGO_PREFIX);

  WebElement textoBox = driver.findElement(By.name(ELEMENT_TYPEURI));
  textoBox.clear();
  textoBox.sendKeys(CODIGO_FIN_URL);
  
  //Propiedades
  {  
    //Campo identifier
    String comboPrefijoCampo="prefixTypeCampoAdded_id";
    
    Select select = new Select(driver.findElement(By.id(comboPrefijoCampo)));
  
    select.selectByValue(CODIGO_DCTERMS);
  
    if (driver instanceof JavascriptExecutor) {
  	JavascriptExecutor jse = (JavascriptExecutor) driver;
  	jse.executeScript("testPruebas('" + CODIGO_DCTERMS + "')");
    } else {
  	throw new IllegalStateException("This driver does not support JavaScript!");
    }
    
    String finURICampo="urlCampoAdded_id";
    WebElement textoCampoFinURI = driver.findElement(By.name(finURICampo));
    textoCampoFinURI.clear();
    textoCampoFinURI.sendKeys("identifier");
    
    //Campo title
    comboPrefijoCampo="prefixTypeCampoAdded_title";
    select = new Select(driver.findElement(By.id(comboPrefijoCampo)));
    select.selectByValue(CODIGO_DCTERMS);
  
    if (driver instanceof JavascriptExecutor) {
  	JavascriptExecutor jse = (JavascriptExecutor) driver;
  	jse.executeScript("testPruebas('" + CODIGO_DCTERMS + "')");
    } else {
  	throw new IllegalStateException("This driver does not support JavaScript!");
    }
    
    finURICampo="urlCampoAdded_title";
    textoCampoFinURI = driver.findElement(By.name(finURICampo));
    textoCampoFinURI.clear();
    textoCampoFinURI.sendKeys("title");
    
    
    //Campo areaId
    comboPrefijoCampo="prefixTypeCampoAdded_areaId";
    select = new Select(driver.findElement(By.id(comboPrefijoCampo)));
    select.selectByValue(CODIGO_ESADM);
  
    if (driver instanceof JavascriptExecutor) {
  	JavascriptExecutor jse = (JavascriptExecutor) driver;
  	jse.executeScript("testPruebas('"+CODIGO_ESADM+"')");
    } else {
  	throw new IllegalStateException("This driver does not support JavaScript!");
    }
    
    finURICampo="urlCampoAdded_areaId";
    textoCampoFinURI = driver.findElement(By.name(finURICampo));
    textoCampoFinURI.clear();
    textoCampoFinURI.sendKeys("area");
    
    WebElement RadioType = driver.findElement(By.id("objectTypeUrlCampoAdded_areaId"));
    RadioType.click();
    
    WebElement areaURL=driver.findElement(By.id("objectValueURLCampoAdded_areaId"));
    areaURL.clear();
    areaURL.sendKeys("https://alzir.dia.fi.upm.es/OpenCitiesAPI/organigrama/organizacion/{areaId}");
    
  
  }

  WebElement boton = driver.findElement(By.id(BOTON_GUARDAR));
  boton.click();
  
  TestFunctions.await(driver);

  TestFunctions.checkErrorOnTitle(driver);

  WebElement advice = driver.findElement(By.id(ADVICE_TITLE_ADD));
  log.info(advice.getText());

  assertEquals(advice.getText(), LiteralConstants.TEXT_ADDED);

}

@Test
public void test_03_EditRecord() {
  log.info("test_03_EditRecord");

  Test_01_Login.loginSteps(driver);

  driver.get(DEF_SEMANTIC_URL);
  
  TestFunctions.await(driver);

  TestFunctions.checkErrorOnTitle(driver);

  WebElement justAddedLink = driver.findElement(By.linkText(CODIGO_CONSULTA));
  log.info(justAddedLink.getText());
  justAddedLink.click();
  
  TestFunctions.await(driver);

  TestFunctions.checkErrorOnTitle(driver);

  WebElement editLink = driver.findElement(By.id(ENLACE_EDITAR));
  editLink.click();
  
  TestFunctions.await(driver);

  TestFunctions.checkErrorOnTitle(driver);

  // MODIFICACIONES
  //Propiedades
  {  
    //Campo identifier
    String comboPrefijoCampo="prefixTypeCampoAdded_id";
    
    Select select = new Select(driver.findElement(By.id(comboPrefijoCampo)));
  
    select.selectByValue("dcterms");
  
    if (driver instanceof JavascriptExecutor) {
  	JavascriptExecutor jse = (JavascriptExecutor) driver;
  	jse.executeScript("testPruebas('dcterms')");
    } else {
  	throw new IllegalStateException("This driver does not support JavaScript!");
    }
    
    String finURICampo="urlCampoAdded_id";
    WebElement textoCampoFinURI = driver.findElement(By.name(finURICampo));
    textoCampoFinURI.clear();
    textoCampoFinURI.sendKeys("myIdentifier");
    
    //Campo title
    comboPrefijoCampo="prefixTypeCampoAdded_title";
    select = new Select(driver.findElement(By.id(comboPrefijoCampo)));
    select.selectByValue("esadm");
  
    if (driver instanceof JavascriptExecutor) {
  	JavascriptExecutor jse = (JavascriptExecutor) driver;
  	jse.executeScript("testPruebas('esadm')");
    } else {
  	throw new IllegalStateException("This driver does not support JavaScript!");
    }
    
    finURICampo="urlCampoAdded_title";
    textoCampoFinURI = driver.findElement(By.name(finURICampo));
    textoCampoFinURI.clear();
    textoCampoFinURI.sendKeys("myTitle");
    
    
    
    
    
    
  
  }

  TestFunctions.checkErrorOnTitle(driver);

  WebElement boton = driver.findElement(By.id(BOTON_GUARDAR));
  boton.click();

  TestFunctions.await(driver);
  
  TestFunctions.checkErrorOnTitle(driver);

  WebElement advice = driver.findElement(By.id(ADVICE_TITLE_UPDATE));
  log.info(advice.getText());

  assertEquals(advice.getText(), LiteralConstants.TEXT_UPDATED);

}

@Test
public void test_04_ListPage() {
  log.info("test_04_ListPage");

  Test_01_Login.loginSteps(driver);

  String title = LiteralConstants.TITLE_HEAD_PREFIX_DEF + LiteralConstants.TITLE_HEAD_SUFFIX;

  driver.get(DEF_SEMANTIC_URL);
  
  TestFunctions.await(driver);

  log.info(title);
  log.info(driver.getTitle());

  TestFunctions.checkErrorOnTitle(driver);

  assertEquals(title, driver.getTitle());
}

@Test
public void test_05_FirstRecord() {
  log.info("test_05_FirstRecord");

  Test_01_Login.loginSteps(driver);

  driver.get(DEF_SEMANTIC_URL);
  
  TestFunctions.await(driver);

  TestFunctions.checkErrorOnTitle(driver);

  WebElement firstLink = driver.findElement(By.cssSelector("#title_element_1 > a:nth-child(1)"));

  String titleInList = firstLink.getText();

  log.info(titleInList);

  firstLink.click();
  
  TestFunctions.await(driver);

  TestFunctions.checkErrorOnTitle(driver);

  WebElement titulo = driver.findElement(By.id("parrafoTitulo"));

  log.info(titulo.getText());

  assertEquals(titleInList, titulo.getText());
}

@Test
public void test_06_DeleteRecord() {
  log.info("test_06_DeleteRecord");

  Test_01_Login.loginSteps(driver);

  driver.get(DEF_SEMANTIC_URL);
  
  TestFunctions.await(driver);

  TestFunctions.checkErrorOnTitle(driver);

  WebElement justAddedLink = driver.findElement(By.linkText(CODIGO_CONSULTA));
  log.info(justAddedLink.getText());
  justAddedLink.click();

  TestFunctions.checkErrorOnTitle(driver);

  WebElement deleteLink = driver.findElement(By.id(ENLACE_BORRAR));
  deleteLink.click();
  
  TestFunctions.await(driver);

  TestFunctions.checkErrorOnTitle(driver);

  WebElement boton = driver.findElement(By.id(BOTON_BORRAR));
  boton.click();
  
  TestFunctions.await(driver);

  TestFunctions.checkErrorOnTitle(driver);

  WebElement advice = driver.findElement(By.id(ADVICE_TITLE_DELETE));
  log.info(advice.getText());

  assertEquals(advice.getText(), LiteralConstants.TEXT_DELETED);

}

@Test
public void test_07_DeleteQueryRecord() {
  log.info("test_07_DeleteQueryRecord");

  Test_01_Login.loginSteps(driver);

  driver.get(Test_04_Query.QUERY_URL);

  TestFunctions.checkErrorOnTitle(driver);

  WebElement justAddedLink = driver.findElement(By.linkText(Test_04_Query.PRUEBA_SELENIUM));
  log.info(justAddedLink.getText());
  justAddedLink.click();

  TestFunctions.checkErrorOnTitle(driver);

  WebElement deleteLink = driver.findElement(By.id("enlaceBorrar"));
  deleteLink.click();

  TestFunctions.checkErrorOnTitle(driver);

  WebElement boton = driver.findElement(By.id("botonBorrar"));
  boton.click();

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
