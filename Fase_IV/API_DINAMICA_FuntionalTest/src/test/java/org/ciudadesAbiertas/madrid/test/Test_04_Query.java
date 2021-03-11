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

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.ciudadesAbiertas.madrid.utils.constants.LiteralConstants;
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
public class Test_04_Query {

private static final String EDITADA = "-editada";

public static final String PRUEBA_SELENIUM = "prueba-selenium";

private static WebDriver driver;

private static final Logger log = Logger.getLogger(Test_04_Query.class);

public static final String QUERY_URL = TestConstants.HOST + TestConstants.CONTEXT + "/query";

@BeforeClass
public static void setup() throws Exception {
  BasicConfigurator.configure();
  Logger.getRootLogger().setLevel(Level.INFO);

  TestFunctions.loadConfiguration();

  log.info("setup");

  driver = TestFunctions.configureDriver(TestConstants.DRIVER_SELECTED);

  driver.manage().window().maximize();

  TestFunctions.setTimeOut(driver);

}

@Test
public void test_01_AddRecord() {
  log.info("test_01_AddRecord");

  Test_01_Login.loginSteps(driver);

  driver.get(QUERY_URL);
  
  TestFunctions.await(driver);

  TestFunctions.checkErrorOnTitle(driver);

  driver.get(QUERY_URL + "/add");
  
  TestFunctions.await(driver);

  TestFunctions.checkErrorOnTitle(driver);

  WebElement textoBox = driver.findElement(By.name("code"));
  textoBox.clear();
  textoBox.sendKeys(PRUEBA_SELENIUM);

  WebElement textoLargoBox = driver.findElement(By.name("texto"));
  textoLargoBox.clear();
  textoLargoBox.sendKeys("Select * from query");

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
public void test_02_EditRecord() {
  log.info("test_02_EditRecord");

  Test_01_Login.loginSteps(driver);

  driver.get(QUERY_URL);
  
  TestFunctions.await(driver);

  TestFunctions.checkErrorOnTitle(driver);

  WebElement justAddedLink = driver.findElement(By.linkText(PRUEBA_SELENIUM));
  log.info(justAddedLink.getText());
  justAddedLink.click();

  TestFunctions.checkErrorOnTitle(driver);

  WebElement editLink = driver.findElement(By.id("enlaceEditar"));
  editLink.click();

  TestFunctions.await(driver);
  
  TestFunctions.checkErrorOnTitle(driver);

  WebElement textoBox = driver.findElement(By.name("code"));
  textoBox.clear();
  textoBox.sendKeys(PRUEBA_SELENIUM + EDITADA);

  WebElement textoLargoBox = driver.findElement(By.name("texto"));
  textoLargoBox.clear();
  textoLargoBox.sendKeys("Select texto from query");

  WebElement summaryBox = driver.findElement(By.name("summary"));
  summaryBox.clear();
  summaryBox.sendKeys("Yo soy Groot Editado. Yo soy Groot Editado. Yo soy Groot Editado. Yo soy Groot Editado.");

  WebElement etiquetasBox = driver.findElement(By.name("tags"));
  etiquetasBox.clear();
  etiquetasBox.sendKeys("Etiqueta2");

  TestFunctions.checkErrorOnTitle(driver);

  WebElement boton = driver.findElement(By.id("botonGuardar"));
  boton.click();
  
  TestFunctions.await(driver);

  TestFunctions.checkErrorOnTitle(driver);

  WebElement advice = driver.findElement(By.id("updatedAdviceTitle"));
  log.info(advice.getText());

  assertEquals(advice.getText(), LiteralConstants.TEXT_UPDATED);

}

@Test
public void test_03_ListPage() {
  log.info("test_03_ListPage");

  Test_01_Login.loginSteps(driver);

  String title = LiteralConstants.TITLE_HEAD_CONSULTAS_SQL + LiteralConstants.TITLE_HEAD_SUFFIX;

  driver.get(QUERY_URL);
  
  TestFunctions.await(driver);

  log.info(title);
  log.info(driver.getTitle());

  TestFunctions.checkErrorOnTitle(driver);

  assertEquals(title, driver.getTitle());
}

@Test
public void test_04_FirstRecord() {
  log.info("test_04_FirstRecord");

  Test_01_Login.loginSteps(driver);

  driver.get(QUERY_URL);
  
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
public void test_05_DeleteRecord() {
  log.info("test_05_DeleteRecord");

  Test_01_Login.loginSteps(driver);

  driver.get(QUERY_URL);
  
  TestFunctions.await(driver);

  TestFunctions.checkErrorOnTitle(driver);

  WebElement justAddedLink = driver.findElement(By.linkText(PRUEBA_SELENIUM + EDITADA));
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
