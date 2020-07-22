package org.ciudadesAbiertas.madrid.test;

import static org.junit.Assert.assertTrue;

import java.util.Date;
import java.util.List;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.ciudadesAbiertas.madrid.controller.form.TaskController;
import org.ciudadesAbiertas.madrid.model.dynamic.QueryConfD;
import org.ciudadesAbiertas.madrid.model.dynamic.TaskD;
import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TaskTest {

    private static final String AGENDAMUNICIPAL = "agendamunicipal";

    private WebDriver driver;

    private static final Logger log = Logger.getLogger(TaskTest.class);

    public static final String QUERY_URL = TestConstants.HOST + TestConstants.CONTEXT + "/task";

    @Before
    public void setup() throws Exception {
	BasicConfigurator.configure();
	Logger.getRootLogger().setLevel(Level.INFO);

	TestFunctions.loadConfiguration();

	log.info("setup");

	driver = TestFunctions.configureDriver(TestConstants.DRIVER_SELECTED);

	driver.manage().window().maximize();

	TestFunctions.setTimeOut(driver);

    }

    @Test
    public void test_01_List() {
	log.info("test_01_AddRecord");

	LoginTest.loginSteps(driver);

	driver.get(QUERY_URL);

	TestFunctions.checkErrorOnTitle(driver);	

	List<WebElement> rows = driver.findElements(By.cssSelector("[class='table-striped'] tr"));
	
	assertTrue(rows.size()>0);

    }
    
    
    @Test
    public void test_02_Search_Query() {
	log.info("test_02_Search");

	LoginTest.loginSteps(driver);

	driver.get(QUERY_URL);

	TestFunctions.checkErrorOnTitle(driver);	
	
	Select modeSelect = new Select(driver.findElement(By.name("query")));
	modeSelect.selectByValue(AGENDAMUNICIPAL);

	WebElement boton = driver.findElement(By.id("botonBuscar"));
	boton.click();

	TestFunctions.checkErrorOnTitle(driver);
	
	List<WebElement> rows = driver.findElements(By.cssSelector("[class='table-striped'] tr"));
	
	assertTrue(rows.size()>0);	

    }
    
    @Test
    public void test_03_Search_Status() {
	log.info("test_03_Search_Status");

	LoginTest.loginSteps(driver);

	driver.get(QUERY_URL);

	TestFunctions.checkErrorOnTitle(driver);	
	
	Select modeSelect = new Select(driver.findElement(By.name("status")));
	modeSelect.selectByValue(TaskD.FINALIZADA);

	WebElement boton = driver.findElement(By.id("botonBuscar"));
	boton.click();

	TestFunctions.checkErrorOnTitle(driver);
	
	List<WebElement> rows = driver.findElements(By.cssSelector("[class='table-striped'] tr"));
	
	assertTrue(rows.size()>0);	

    }
    
    @Test
    public void test_04_Search_Mode() {
	log.info("test_04_Search_Mode");

	LoginTest.loginSteps(driver);

	driver.get(QUERY_URL);

	TestFunctions.checkErrorOnTitle(driver);	
	
	Select modeSelect = new Select(driver.findElement(By.name("mode")));
	modeSelect.selectByValue(QueryConfD.MODE_MANUAL);

	WebElement boton = driver.findElement(By.id("botonBuscar"));
	boton.click();

	TestFunctions.checkErrorOnTitle(driver);
	
	List<WebElement> rows = driver.findElements(By.cssSelector("[class='table-striped'] tr"));
	
	assertTrue(rows.size()>0);	

    }
   
  
    @Test
    public void test_05_Search_Fecha_Desde() {
	log.info("test_05_Search_Fecha_Desde");

	LoginTest.loginSteps(driver);

	driver.get(QUERY_URL);

	TestFunctions.checkErrorOnTitle(driver);	
	
	WebElement textoLargoBox = driver.findElement(By.name("start"));
	textoLargoBox.clear();
	textoLargoBox.sendKeys(TaskController.dateFormFormatter.format(new Date()));

	WebElement boton = driver.findElement(By.id("botonBuscar"));
	boton.click();

	TestFunctions.checkErrorOnTitle(driver);
	
	List<WebElement> rows = driver.findElements(By.cssSelector("[class='table-striped'] tr"));
	
	assertTrue(rows.size()>0);	

    }
    
    @Test
    public void test_06_Search_Fecha_Hasta() {
	log.info("test_06_Search_Fecha_Hasta");

	LoginTest.loginSteps(driver);

	driver.get(QUERY_URL);

	TestFunctions.checkErrorOnTitle(driver);	
	
	WebElement textoLargoBox = driver.findElement(By.name("finish"));
	textoLargoBox.clear();
	textoLargoBox.sendKeys(TaskController.dateFormFormatter.format(new Date()));

	WebElement boton = driver.findElement(By.id("botonBuscar"));
	boton.click();

	TestFunctions.checkErrorOnTitle(driver);
	
	List<WebElement> rows = driver.findElements(By.cssSelector("[class='table-striped'] tr"));
	
	assertTrue(rows.size()>0);	

    }
    
    @Test
    public void test_07_Search_Fecha_Desde_Hasta() {
	log.info("test_07_Search_Fecha_Desde_Hasta");

	LoginTest.loginSteps(driver);

	driver.get(QUERY_URL);

	TestFunctions.checkErrorOnTitle(driver);	
	
	WebElement textoLargoBox = driver.findElement(By.name("start"));
	textoLargoBox.clear();
	textoLargoBox.sendKeys(TaskController.dateFormFormatter.format(new Date()));
	
	textoLargoBox = driver.findElement(By.name("finish"));
	textoLargoBox.clear();
	textoLargoBox.sendKeys(TaskController.dateFormFormatter.format(new Date()));

	WebElement boton = driver.findElement(By.id("botonBuscar"));
	boton.click();

	TestFunctions.checkErrorOnTitle(driver);
	
	List<WebElement> rows = driver.findElements(By.cssSelector("[class='table-striped'] tr"));
	
	assertTrue(rows.size()>0);	

    }
    
    
    @Test
    public void test_08_Search_Fecha_Desde_Hasta_Hora() {
	log.info("test_08_Search_Fecha_Desde_Hasta_Hora");

	LoginTest.loginSteps(driver);

	driver.get(QUERY_URL);

	TestFunctions.checkErrorOnTitle(driver);	
	
	WebElement textoLargoBox = driver.findElement(By.name("start"));
	textoLargoBox.clear();
	textoLargoBox.sendKeys(TaskController.dateFormFormatter.format(new Date()));
	
	textoLargoBox = driver.findElement(By.name("finish"));
	textoLargoBox.clear();
	textoLargoBox.sendKeys(TaskController.dateFormFormatter.format(new Date()));

	textoLargoBox = driver.findElement(By.name("timeStart"));
	textoLargoBox.clear();
	textoLargoBox.sendKeys("00:01");
	
	textoLargoBox = driver.findElement(By.name("timeFinish"));
	textoLargoBox.clear();
	textoLargoBox.sendKeys("23:30");
	
	
	WebElement boton = driver.findElement(By.id("botonBuscar"));
	boton.click();

	TestFunctions.checkErrorOnTitle(driver);
	
	List<WebElement> rows = driver.findElements(By.cssSelector("[class='table-striped'] tr"));
	
	assertTrue(rows.size()>0);	

    }
   

    @Test
    public void test_09_Search_Fecha_Desde_Hasta_KO() {
	log.info("test_09_Search_Fecha_Desde_Hasta_KO");

	LoginTest.loginSteps(driver);

	driver.get(QUERY_URL);

	TestFunctions.checkErrorOnTitle(driver);	
	
	WebElement textoLargoBox = driver.findElement(By.name("start"));
	textoLargoBox.clear();
	textoLargoBox.sendKeys("01/06/2010");
	
	textoLargoBox = driver.findElement(By.name("finish"));
	textoLargoBox.clear();
	textoLargoBox.sendKeys("01/12/2010");

	WebElement boton = driver.findElement(By.id("botonBuscar"));
	boton.click();

	TestFunctions.checkErrorOnTitle(driver);
	
	List<WebElement> rows = driver.findElements(By.cssSelector("[class='table-striped'] tr"));
	
	assertTrue(rows.size()==0);	

    }
    
    
    @After
    public void tearDown() throws Exception {
	driver.quit();
    }

}
