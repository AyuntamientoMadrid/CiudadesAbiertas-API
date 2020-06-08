package org.ciudadesAbiertas.madrid.test;


import static org.junit.Assert.assertEquals;

import java.util.concurrent.TimeUnit;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.ciudadesAbiertas.madrid.utils.constants.Constants;
import org.ciudadesAbiertas.madrid.utils.constants.LiteralConstants;
import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;



@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class LoginTest {
	
	
	private static final String BUTTON_IN_ID = "buttonIn";
	private static final String PASSWORD_INPUT_NAME = "password";
	private static final String USERNAME_INPUT_NAME = "username";

	private WebDriver driver;
	
	private static final Logger log = Logger.getLogger(LoginTest.class);
	
	
	
	
	public static void loginSteps(WebDriver driver) {
		
		driver.get(TestConstants.LOGIN_URL);
		
		WebElement username=driver.findElement(By.name(USERNAME_INPUT_NAME));		
		username.clear();
		username.sendKeys(TestConstants.USER_OK);
		
		WebElement password=driver.findElement(By.name(PASSWORD_INPUT_NAME));		
		password.clear();
		password.sendKeys(TestConstants.PASSWORD_OK);
		
		WebElement button=driver.findElement(By.id(BUTTON_IN_ID));		
		button.click();
	}
	
	
	@Before
    public void setup() throws Exception 
    {   
		BasicConfigurator.configure();
		Logger.getRootLogger().setLevel(Level.INFO);
		
		log.info("setup");
		
		driver=TestFunctions.configureDriver(TestConstants.DRIVER_SELECTED);
		
		driver.manage().window().maximize();
		driver.get(TestConstants.LOGIN_URL);
		
		TestFunctions.setTimeOut(driver);
		
		driver.get(TestConstants.LOGIN_URL);
    }
	
	
	@Test
	public void test_01_Page()
	{	
		log.info("test_01_Page");
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		
		String title=LiteralConstants.TITLE_HEAD_LOGIN+LiteralConstants.TITLE_HEAD_SUFFIX;
		
		assertEquals(title,driver.getTitle());
	}
	
		
	@Test
	public void test_02_In()
	{	
		log.info("test_02_In");
		
		loginSteps(driver);
		
		String title=LiteralConstants.TITLE_HEAD_HOME+LiteralConstants.TITLE_HEAD_SUFFIX;
		assertEquals(title,driver.getTitle());
	}
	
	@Test
	public void test_03_Wrong_Credentials()
	{	
		log.info("test_03_Wrong_Credentials");
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		
		WebElement username=driver.findElement(By.name(USERNAME_INPUT_NAME));		
		username.clear();
		username.sendKeys(TestConstants.USER_OK);
		
		WebElement password=driver.findElement(By.name(PASSWORD_INPUT_NAME));		
		password.clear();
		password.sendKeys("BAD_PASSWORD");
		
		WebElement button=driver.findElement(By.id(BUTTON_IN_ID));		
		button.click();
		
		TestFunctions.checkErrorOnTitle(driver);		
		
		log.info(driver.getCurrentUrl());		
		
		assertEquals(driver.getCurrentUrl(),TestConstants.LOGIN_URL+"?"+Constants.PARAM_ERROR);
	}


	
	
	@Test
	public void test_04_EmptyFields()
	{	
		log.info("test_04_EmptyFields");
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		
		WebElement username=driver.findElement(By.name(USERNAME_INPUT_NAME));		
		username.clear();		
		
		WebElement password=driver.findElement(By.name(PASSWORD_INPUT_NAME));		
		password.clear();
		
		
		WebElement button=driver.findElement(By.id(BUTTON_IN_ID));		
		button.click();
		
		assertEquals(driver.getCurrentUrl(),TestConstants.LOGIN_URL);
	}

	@After
    public void tearDown() throws Exception 
    {   
		driver.quit();        
    }

}
