package org.ciudadesAbiertas.madrid.test;

import static org.junit.Assert.assertEquals;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.ciudadesAbiertas.madrid.utils.constants.LiteralConstants;
import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class QueryTest {

    public static final String EDITADA = "-editada";

    public static final String PRUEBA_SELENIUM = "prueba";

    private WebDriver driver;

    private static final Logger log = Logger.getLogger(QueryTest.class);

    public static final String QUERY_URL = TestConstants.HOST + TestConstants.CONTEXT + "/query";

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
    public void test_01_AddRecord() {
	log.info("test_01_AddRecord");

	LoginTest.loginSteps(driver);

	driver.get(QUERY_URL);

	TestFunctions.checkErrorOnTitle(driver);

	driver.get(QUERY_URL + "/add");

	TestFunctions.checkErrorOnTitle(driver);

	WebElement textoBox = driver.findElement(By.name("code"));
	textoBox.clear();
	textoBox.sendKeys(PRUEBA_SELENIUM);

	WebElement textoLargoBox = driver.findElement(By.name("texto"));
	textoLargoBox.clear();
	textoLargoBox.sendKeys("SELECT GETDATE() current_date_time");
	//textoLargoBox.sendKeys("SELECT NOW() ahora FROM DUAL");
	
	WebElement summaryBox = driver.findElement(By.name("summary"));
	summaryBox.clear();
	summaryBox.sendKeys("Descripción tiempo");

	WebElement pathBox = driver.findElement(By.name("path"));
	pathBox.clear();
	pathBox.sendKeys("D:\\temp\\time");
	
	Select modeSelect = new Select(driver.findElement(By.name("mode")));
	modeSelect.selectByValue("manual");
	
	Select minuteSelect = new Select(driver.findElement(By.name("minute")));
	minuteSelect.deselectAll();
	minuteSelect.selectByValue("00");
	minuteSelect.selectByValue("05");
	
	Select hourSelect = new Select(driver.findElement(By.name("hour")));
	hourSelect.deselectAll();
	hourSelect.selectByValue("00");
	hourSelect.selectByValue("02");	
	
	Select dayMSelect = new Select(driver.findElement(By.name("dayM")));
	dayMSelect.deselectAll();
	dayMSelect.selectByValue("01");
	dayMSelect.selectByValue("15");	
	
	Select dayWSelect = new Select(driver.findElement(By.name("dayW")));
	dayWSelect.deselectAll();
	dayWSelect.selectByValue("*");
	
	Select monthSelect = new Select(driver.findElement(By.name("month")));
	monthSelect.deselectAll();
	monthSelect.selectByValue("*");
	
	WebElement zipCheck = driver.findElement(By.name("zip"));
	zipCheck.click();
	
	WebElement itemsBox = driver.findElement(By.name("items"));
	itemsBox.clear();
	itemsBox.sendKeys("1000");
	
	WebElement overwriteCheck = driver.findElement(By.name("overwrite"));
	overwriteCheck.click();	
	
	WebElement boton = driver.findElement(By.id("botonGuardar"));
	boton.click();

	TestFunctions.checkErrorOnTitle(driver);

	WebElement advice = driver.findElement(By.id("addedAdviceTitle"));
	log.info(advice.getText());

	assertEquals(advice.getText(), LiteralConstants.TEXT_ADDED);

    }

    
  
    
    @Test
    public void test_02_EditRecord() {
	log.info("test_02_EditRecord");

	LoginTest.loginSteps(driver);

	driver.get(QUERY_URL);

	TestFunctions.checkErrorOnTitle(driver);

	WebElement justAddedLink = driver.findElement(By.linkText(PRUEBA_SELENIUM));
	log.info(justAddedLink.getText());
	justAddedLink.click();

	TestFunctions.checkErrorOnTitle(driver);

	WebElement editLink = driver.findElement(By.id("enlaceEditar"));
	editLink.click();

	TestFunctions.checkErrorOnTitle(driver);

	WebElement textoBox = driver.findElement(By.name("code"));
	textoBox.clear();
	textoBox.sendKeys(PRUEBA_SELENIUM + EDITADA);

	WebElement textoLargoBox = driver.findElement(By.name("texto"));
	textoLargoBox.clear();
	textoLargoBox.sendKeys("SELECT GETDATE() as fecha_actual");
	//textoLargoBox.sendKeys("SELECT NOW() ahora FROM DUAL");

	WebElement summaryBox = driver.findElement(By.name("summary"));
	summaryBox.clear();
	summaryBox.sendKeys("Descripción tiempo editado");

	WebElement pathBox = driver.findElement(By.name("path"));
	pathBox.clear();
	pathBox.sendKeys("D:\\temp\\time_modificado");
	
	Select modeSelect = new Select(driver.findElement(By.name("mode")));
	modeSelect.selectByValue("auto");
	
	Select minuteSelect = new Select(driver.findElement(By.name("minute")));
	minuteSelect.deselectAll();
	minuteSelect.selectByValue("30");
	
	Select hourSelect = new Select(driver.findElement(By.name("hour")));
	hourSelect.deselectAll();
	hourSelect.selectByValue("00");
	
	
	Select dayMSelect = new Select(driver.findElement(By.name("dayM")));
	dayMSelect.deselectAll();
	dayMSelect.selectByValue("*");	
	
	Select dayWSelect = new Select(driver.findElement(By.name("dayW")));
	dayWSelect.deselectAll();
	dayWSelect.selectByValue("1");
	dayWSelect.selectByValue("4");
	
	Select monthSelect = new Select(driver.findElement(By.name("month")));
	monthSelect.deselectAll();
	monthSelect.selectByValue("01");
	monthSelect.selectByValue("06");
	monthSelect.selectByValue("12");
	
	WebElement zipCheck = driver.findElement(By.name("zip"));
	zipCheck.click();
	
	WebElement itemsBox = driver.findElement(By.name("items"));
	itemsBox.clear();
	itemsBox.sendKeys("10");
	
	WebElement overwriteCheck = driver.findElement(By.name("overwrite"));
	overwriteCheck.click();	

	WebElement boton = driver.findElement(By.id("botonGuardar"));
	boton.click();

	TestFunctions.checkErrorOnTitle(driver);

	WebElement advice = driver.findElement(By.id("updatedAdviceTitle"));
	log.info(advice.getText());

	assertEquals(advice.getText(), LiteralConstants.TEXT_UPDATED);

    }

    
    @Test
    public void test_03_ListPageAndRunProcess() {
	log.info("test_03_ListPageAndRunProcess");

	LoginTest.loginSteps(driver);

	String title = LiteralConstants.TITLE_HEAD_CONSULTAS_SQL + LiteralConstants.TITLE_HEAD_SUFFIX;

	driver.get(QUERY_URL);

	log.info(title);
	log.info(driver.getTitle());

	TestFunctions.checkErrorOnTitle(driver);
	
	WebElement linkRun = driver.findElement(By.id("linkRun"+PRUEBA_SELENIUM + EDITADA));
	linkRun.click();
	
	TestFunctions.checkErrorOnTitle(driver);

	assertEquals(title, driver.getTitle());
    }
    
    
  

    @Test
    public void test_04_FirstRecord() {
	log.info("test_04_FirstRecord");

	LoginTest.loginSteps(driver);

	driver.get(QUERY_URL);

	TestFunctions.checkErrorOnTitle(driver);

	WebElement firstLink = driver.findElement(By.cssSelector("#title_element_1 > a:nth-child(1)"));

	String titleInList = firstLink.getText();

	log.info(titleInList);

	firstLink.click();

	TestFunctions.checkErrorOnTitle(driver);

	WebElement titulo = driver.findElement(By.id("parrafoTitulo"));

	log.info(titulo.getText());

	assertEquals(titleInList, titulo.getText());
    }

    
    @Test
    public void test_05_DeleteRecord() {
	log.info("test_05_DeleteRecord");

	LoginTest.loginSteps(driver);

	driver.get(QUERY_URL);

	TestFunctions.checkErrorOnTitle(driver);

	WebElement justAddedLink = driver.findElement(By.linkText(PRUEBA_SELENIUM + EDITADA));
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
    
    

    @After
    public void tearDown() throws Exception {
	driver.quit();
    }

}
