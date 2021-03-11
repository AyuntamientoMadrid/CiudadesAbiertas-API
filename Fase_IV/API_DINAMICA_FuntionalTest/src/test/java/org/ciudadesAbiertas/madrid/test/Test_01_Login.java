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

import java.util.concurrent.TimeUnit;
import org.apache.log4j.Logger;
import org.ciudadesAbiertas.madrid.utils.constants.Constants;
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
public class Test_01_Login {

    private static final String BUTTON_IN_ID = "buttonIn";
    private static final String PASSWORD_INPUT_NAME = "password";
    private static final String USERNAME_INPUT_NAME = "username";

    private static WebDriver driver;

    private static final Logger log = Logger.getLogger(Test_01_Login.class);

    public static void loginSteps(WebDriver driver) {
      
    driver.get(TestConstants.LOGIN_OUT);
  	
  	driver.manage().timeouts().implicitlyWait(TestConstants.TIMEOUT, TimeUnit.SECONDS);

	driver.get(TestConstants.LOGIN_URL);

	WebElement username = driver.findElement(By.name(USERNAME_INPUT_NAME));
	username.clear();
	username.sendKeys(TestConstants.USER_OK);

	WebElement password = driver.findElement(By.name(PASSWORD_INPUT_NAME));
	password.clear();
	password.sendKeys(TestConstants.PASSWORD_OK);

	WebElement button = driver.findElement(By.id(BUTTON_IN_ID));
	button.click();
    }

    @BeforeClass
    public static void setup() throws Exception {
      
      TestFunctions.loadConfiguration();

  		log.info("setup");

  		driver = TestFunctions.configureDriver(TestConstants.DRIVER_SELECTED);

  		driver.manage().window().maximize();

  		TestFunctions.setTimeOut(driver);
    }

    

    @Test
    public void test_01_Page() {
	log.info("test_01_Page");
	
	driver.get(TestConstants.LOGIN_URL);
	
	TestFunctions.await(driver);

	String title = LiteralConstants.TITLE_HEAD_LOGIN + LiteralConstants.TITLE_HEAD_SUFFIX;

	assertEquals(title, driver.getTitle());
    }

    @Test
    public void test_02_In() {
	log.info("test_02_In");

	loginSteps(driver);
	
	TestFunctions.await(driver);

	String title = LiteralConstants.TITLE_HEAD_HOME + LiteralConstants.TITLE_HEAD_SUFFIX;
	assertEquals(title, driver.getTitle());
    }

    @Test
    public void test_03_Wrong_Credentials() {
	log.info("test_03_Wrong_Credentials");
	
	driver.get(TestConstants.LOGIN_OUT);
	
	TestFunctions.await(driver);
	
	driver.get(TestConstants.LOGIN_URL);
	
	TestFunctions.await(driver);

	WebElement username = driver.findElement(By.name(USERNAME_INPUT_NAME));
	username.clear();
	username.sendKeys(TestConstants.USER_OK);

	WebElement password = driver.findElement(By.name(PASSWORD_INPUT_NAME));
	password.clear();
	password.sendKeys("BAD_PASSWORD");

	WebElement button = driver.findElement(By.id(BUTTON_IN_ID));
	button.click();

	TestFunctions.checkErrorOnTitle(driver);

	log.info(driver.getCurrentUrl());

	assertEquals(driver.getCurrentUrl(), TestConstants.LOGIN_URL + "?" + Constants.PARAM_ERROR);
    }

    @Test
    public void test_04_EmptyFields() {
	log.info("test_04_EmptyFields");
	
	driver.get(TestConstants.LOGIN_OUT);
	
	TestFunctions.await(driver);
	
	driver.get(TestConstants.LOGIN_URL);
	
	TestFunctions.await(driver);

	WebElement username = driver.findElement(By.name(USERNAME_INPUT_NAME));
	username.clear();

	WebElement password = driver.findElement(By.name(PASSWORD_INPUT_NAME));
	password.clear();

	WebElement button = driver.findElement(By.id(BUTTON_IN_ID));
	button.click();

	assertEquals(driver.getCurrentUrl(), TestConstants.LOGIN_URL);
    }

	

    @AfterClass
    public static void tearDown() throws Exception {
	driver.quit();
    }

}
