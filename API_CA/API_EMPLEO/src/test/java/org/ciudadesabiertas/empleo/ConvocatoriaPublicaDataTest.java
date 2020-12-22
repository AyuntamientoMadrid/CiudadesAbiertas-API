/**
 * 
 */
package org.ciudadesabiertas.empleo;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Collection;
import org.ciudadesabiertas.config.WebConfig;
import org.ciudadesabiertas.dataset.controller.ConvocatoriaEmpleoPublicoController;
import org.ciudadesabiertas.utils.TestUtils;
import org.json.simple.JSONArray;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestContextManager;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

/**
 * @author Juan Carlos Ballesteros (Localidata)
 * @author Carlos Martínez de la Casa (Localidata)
 * @author Hugo Lafuente Matesanz (Localidata)
 * @author Oscar Corcho (UPM, Localidata)
 *
 */
@RunWith(Parameterized.class)
@ContextConfiguration(classes = { WebConfig.class })
@WebAppConfiguration
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ConvocatoriaPublicaDataTest {

private static final Logger log = LoggerFactory.getLogger(ConvocatoriaPublicaDataTest.class);


@Autowired
private WebApplicationContext wac;

private MockMvc mockMvc;

private String listURL = ConvocatoriaEmpleoPublicoController.LIST;

// Manually config for spring to use Parameterised
private TestContextManager testContextManager;

@Parameter(value = 0)
public String paramName;

@Parameter(value = 1)
public String value;

@Parameter(value = 2)
public Integer expected;

// Posibles valores que tomaran los parámetros anteriores
@Parameters(name = "{index}: test {0}")
public static Collection<Object[]> data() {
  Collection<Object[]> params = new ArrayList<>();
  params.add(new Object[] { "id", "convocatoria001", 1 });
  params.add(new Object[] { "title", "Convocatoria Informática Noviembre 2020", 1 });
  params.add(new Object[] { "description", "Convocatoria para cubrir 5 plazas para el despartamento de Informática", 1 });
  params.add(new Object[] { "datePublished", "2020-11-02T14:00:00", 1 });
  params.add(new Object[] { "fechaAprobacion", "2020-10-28T10:00:00", 1 });
  params.add(new Object[] { "fechaResolucion", "2020-10-20T14:00:00", 1 });
  params.add(new Object[] { "estadoPlazo", "true", 2 });
  params.add(new Object[] { "plazos", "Hasta el 03/12/2021", 2 });
  params.add(new Object[] { "numeroPlazasConvocadas", "5", 1 });
  params.add(new Object[] { "listaEsperaEx", "false", 1 });
  params.add(new Object[] { "observaciones", "observaciones", 1 });
  params.add(new Object[] { "disposiciones", "disposiciones", 1 });
  params.add(new Object[] { "requisitos", "Ingenieros Tecnicos en Informatica, Ingeniero superiores en Informatica, Grado en Informática", 1 });
  params.add(new Object[] { "bases", "Las bases cuentan....", 1 });
  params.add(new Object[] { "basesUrl", "http://www.aytoMadrid.org/basesConvocatoria/informatica/2020", 1 });
  params.add(new Object[] { "formularioInscripcion", "http://www.aytoMadrid.org/basesConvocatoria/informatica/2020/formulario", 1 });
  params.add(new Object[] { "pruebas", "Se debe codificar un aplicacion WEB básica en menos de 1 hora utilizando JAVA y el ID Eclipse", 1 });
  params.add(new Object[] { "grupoProfesional", "A1", 2 });
  params.add(new Object[] { "empleadoPublico", "funcionario", 2 });
  params.add(new Object[] { "cuerpo", "administracion-general", 2 });
  params.add(new Object[] { "modalidad", "oposicion", 2 }); 

  return params;
}

@Before
public void setup() throws Exception {
  this.testContextManager = new TestContextManager(getClass());
  this.testContextManager.prepareTestInstance(this);
  this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
}



@Test
public void test_evaluador_DATA() throws Exception {
  
	JSONArray records = TestUtils.extractRecords(listURL, paramName, value, mockMvc);
	try
	{
	assertTrue(records.size() == expected);
	}
	catch (AssertionError e)
	{
	  log.error("Assertion error");
	  log.error("  Param: "+paramName);
	  log.error("  Value: "+value);
	  log.error("  Expected: "+expected);	  

	  throw new AssertionError("Incorrect value on Param "+paramName+": "+records.size(), new Throwable("Expected: "+expected));
	}
}

}
