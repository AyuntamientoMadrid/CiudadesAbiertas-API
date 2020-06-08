package org.ciudadesAbiertas.madrid.test;

import static org.junit.Assert.assertTrue;

import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class TestFunctions 
{
	private static final Logger log = Logger.getLogger(TestFunctions.class);	
	
	public static void setTimeOut(WebDriver driver)
	{
		log.info("Setting time out");
		driver.manage().timeouts().implicitlyWait(TestConstants.TIMEOUT, TimeUnit.SECONDS);
	}
	
	public static void checkErrorOnTitle(WebDriver driver) {		
		if (driver.getTitle().toLowerCase().contains("error"))
		{
			log.error("Error detectado en head -> title: "+driver.getTitle());
			assertTrue(false);			
		}		
	}
	
	public static WebDriver configureDriver(String name)
	{
		WebDriver driver=null;
		if (name.toLowerCase().contains("chrome"))
		{
			System.setProperty("webdriver.chrome.driver","./src/test/resources/chromedriver/chromedriver.exe");
			driver= new ChromeDriver();
			return driver;
		}
		if (name.toLowerCase().contains("firefox"))
		{
			System.setProperty("webdriver.gecko.driver", "./src/test/resources/firefoxdriver/geckodriver.exe");
			driver = new FirefoxDriver(); 
		}
		return driver;
	}
	
	public static WebDriver configureDriver()
	{
		return configureDriver("chrome");
	}
}
