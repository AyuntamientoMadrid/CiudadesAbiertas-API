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

import static org.junit.Assert.assertTrue;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

/**
 * @author Juan Carlos Ballesteros (Localidata)
 * @author Carlos Martínez de la Casa (Localidata)
 * @author Oscar Corcho (UPM, Localidata)
 *
 */
public class TestFunctions {

private static final Logger log = Logger.getLogger(TestFunctions.class);

public static void setTimeOut(WebDriver driver) {

  driver.manage().timeouts().implicitlyWait(TestConstants.TIMEOUT, TimeUnit.SECONDS);
}

public static void checkErrorOnTitle(WebDriver driver) {
  if (driver.getTitle().toLowerCase().contains("error")) {
	log.error("Error detectado en head -> title: " + driver.getTitle());
	assertTrue(false);
  }
}

public static WebDriver configureDriver(String name) {
  WebDriver driver = null;
  if (name.toLowerCase().contains("chrome")) {
	System.setProperty("webdriver.chrome.driver", "./src/test/resources/chromedriver/chromedriver.exe");
	driver = new ChromeDriver();
	return driver;
  }
  if (name.toLowerCase().contains("firefox")) {
	System.setProperty("webdriver.gecko.driver", "./src/test/resources/firefoxdriver/geckodriver.exe");
	driver = new FirefoxDriver();

	// FirefoxOptions options = new FirefoxOptions();
	// Esta linea se utiliza para que no levante firefox (evitar entorno grafico)
	// options.addArguments("--headless");
	// driver = new FirefoxDriver(options);

  }
  return driver;
}

public static WebDriver configureDriver() {
  return configureDriver("chrome");
}

public static void loadConfiguration() throws IOException, FileNotFoundException {
  Properties p = new Properties();
  p.load(new FileReader("src/test/resources/config.properties"));

  String navegador = (String) p.get("navegador");
  TestConstants.DRIVER_SELECTED = navegador;

  String host = (String) p.get("host");
  TestConstants.HOST = host;

  String contexto = (String) p.get("contexto");
  TestConstants.CONTEXT = contexto;

  String timeout = (String) p.get("timeout");
  TestConstants.TIMEOUT = Integer.parseInt(timeout);

  String user = (String) p.get("user");
  TestConstants.USER_OK = user;

  String password = (String) p.get("password");
  TestConstants.PASSWORD_OK = password;
}

public static void await(WebDriver theDriver) {
  theDriver.manage().timeouts().implicitlyWait(TestConstants.TIMEOUT, TimeUnit.SECONDS);
}

public static void sleep(int miliseconds)
{
  try {
	Thread.sleep(miliseconds);
  } catch (InterruptedException e) {
	log.error("Error in sleep",e);
  } 
}

}
