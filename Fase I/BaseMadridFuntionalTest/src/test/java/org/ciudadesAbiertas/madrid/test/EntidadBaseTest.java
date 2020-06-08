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
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;



@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class EntidadBaseTest {
		
	

	private static final String EDITADA = " Editada";

	private static final String PRUEBA_SELENIUM = "Prueba selenium";

	private WebDriver driver;
	
	private static final Logger log = Logger.getLogger(EntidadBaseTest.class);
	
	public static final String ENTIDAD_BASE_URL=TestConstants.HOST+TestConstants.CONTEXT+"/entidadBase";
	
	@Before
    public void setup() throws Exception 
    {   
		BasicConfigurator.configure();
		Logger.getRootLogger().setLevel(Level.INFO);
		
		log.info("setup");
		
		driver=TestFunctions.configureDriver(TestConstants.DRIVER_SELECTED);
		
		driver.manage().window().maximize();
		
		TestFunctions.setTimeOut(driver);
		
    }
	
		
	

	
	@Test
	public void test_01_AddRecord()
	{	
		log.info("test_03_AddRecord");
		
		LoginTest.loginSteps(driver);
			
		driver.get(ENTIDAD_BASE_URL);
		
		TestFunctions.checkErrorOnTitle(driver);
		
		driver.get(ENTIDAD_BASE_URL+"/add");
		
		TestFunctions.checkErrorOnTitle(driver);
		
		WebElement textoBox=driver.findElement(By.name("texto"));	
		textoBox.clear();
		textoBox.sendKeys(PRUEBA_SELENIUM);
		
		WebElement textoLargoBox=driver.findElement(By.name("textoLargo"));	
		textoLargoBox.clear();
		textoLargoBox.sendKeys("Yo soy Groot. Yo soy Groot. Yo soy Groot. Yo soy Groot. Yo soy Groot. Yo soy Groot. ");
		
		WebElement fechaBox=driver.findElement(By.name("fecha"));	
		fechaBox.clear();
		fechaBox.sendKeys("24/01/2020");
		
		WebElement numeroBox=driver.findElement(By.name("numeroEntero"));	
		numeroBox.clear();
		numeroBox.sendKeys("1634");
		
		WebElement decimalBox=driver.findElement(By.name("numeroDecimal"));	
		decimalBox.clear();
		decimalBox.sendKeys("23.56");
		
		WebElement boton=driver.findElement(By.id("botonGuardar"));
		boton.click();
		
		TestFunctions.checkErrorOnTitle(driver);
		
		WebElement advice=driver.findElement(By.id("addedAdviceTitle"));		
		log.info(advice.getText());
				
		assertEquals(advice.getText(),LiteralConstants.TEXT_ADDED);
			
	}

	
	@Test
	public void test_02_EditRecord()
	{	
		log.info("test_04_EditRecord");
		
		LoginTest.loginSteps(driver);
			
		driver.get(ENTIDAD_BASE_URL);
		
		TestFunctions.checkErrorOnTitle(driver);
		
		WebElement justAddedLink=driver.findElement(By.linkText(PRUEBA_SELENIUM));
		log.info(justAddedLink.getText());
		justAddedLink.click();
		
		TestFunctions.checkErrorOnTitle(driver);
		
		WebElement editLink=driver.findElement(By.id("enlaceEditar"));
		editLink.click();
		
		TestFunctions.checkErrorOnTitle(driver);
		
		WebElement textoBox=driver.findElement(By.name("texto"));	
		textoBox.clear();
		textoBox.sendKeys(PRUEBA_SELENIUM+EDITADA);		
		
		TestFunctions.checkErrorOnTitle(driver);
		
		WebElement boton=driver.findElement(By.id("botonGuardar"));
		boton.click();
		
		TestFunctions.checkErrorOnTitle(driver);
		
		WebElement advice=driver.findElement(By.id("updatedAdviceTitle"));		
		log.info(advice.getText());
				
		assertEquals(advice.getText(),LiteralConstants.TEXT_UPDATED);
			
	}
	
	
	@Test
	public void test_03_ListPage()
	{	
		log.info("test_01_ListPage");
		
		LoginTest.loginSteps(driver);
		
		String title=LiteralConstants.TITLE_HEAD_ENTIDAD_BASE+LiteralConstants.TITLE_HEAD_SUFFIX;
		
		driver.get(ENTIDAD_BASE_URL);
	
		log.info(title);
		log.info(driver.getTitle());
		
		TestFunctions.checkErrorOnTitle(driver);
				
		assertEquals(title,driver.getTitle());
	}

	@Test
	public void test_04_FirstRecord()
	{	
		log.info("test_02_FirstRecord");
		
		LoginTest.loginSteps(driver);
			
		driver.get(ENTIDAD_BASE_URL);
		
		TestFunctions.checkErrorOnTitle(driver);
		
		WebElement firstLink=driver.findElement(By.cssSelector("#title_element_1 > a:nth-child(1)"));	
		
		String titleInList=firstLink.getText();
		
		log.info(titleInList);
	
		firstLink.click();		
		
		TestFunctions.checkErrorOnTitle(driver);
		
		WebElement titulo=driver.findElement(By.id("parrafoTitulo"));
		
		log.info(titulo.getText());
				
		assertEquals(titleInList,titulo.getText());
	}
	
	
	@Test
	public void test_05_DeleteRecord()
	{	
		log.info("test_05_DeleteRecord");
		
		LoginTest.loginSteps(driver);
			
		driver.get(ENTIDAD_BASE_URL);
		
		TestFunctions.checkErrorOnTitle(driver);
		
		WebElement justAddedLink=driver.findElement(By.linkText(PRUEBA_SELENIUM+EDITADA));
		log.info(justAddedLink.getText());
		justAddedLink.click();
		
		TestFunctions.checkErrorOnTitle(driver);
		
		WebElement deleteLink=driver.findElement(By.id("enlaceBorrar"));
		deleteLink.click();
		
		TestFunctions.checkErrorOnTitle(driver);
		
		WebElement boton=driver.findElement(By.id("botonBorrar"));
		boton.click();
		
		TestFunctions.checkErrorOnTitle(driver);
		
		WebElement advice=driver.findElement(By.id("deletedAdviceTitle"));		
		log.info(advice.getText());
				
		assertEquals(advice.getText(),LiteralConstants.TEXT_DELETED);
			
	}
	
	

	@After
    public void tearDown() throws Exception 
    {   
		driver.quit();        
    }

}
