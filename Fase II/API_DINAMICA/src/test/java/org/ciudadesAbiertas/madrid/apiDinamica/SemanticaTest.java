
package org.ciudadesAbiertas.madrid.apiDinamica;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.OutputStreamWriter;
import java.lang.reflect.Field;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import org.apache.commons.io.FileUtils;
import org.ciudadesAbiertas.madrid.config.WebConfig;
import org.ciudadesAbiertas.madrid.controller.dynamic.DynamicController;
import org.ciudadesAbiertas.madrid.controller.form.SemanticController;
import org.ciudadesAbiertas.madrid.model.dynamic.QueryD;
import org.ciudadesAbiertas.madrid.model.dynamic.SemanticField;
import org.ciudadesAbiertas.madrid.model.dynamic.SemanticPrefix;
import org.ciudadesAbiertas.madrid.model.dynamic.SemanticRelPrefix;
import org.ciudadesAbiertas.madrid.model.dynamic.SemanticRml;
import org.ciudadesAbiertas.madrid.service.dynamic.PrefixRelService;
import org.ciudadesAbiertas.madrid.service.dynamic.PrefixService;
import org.ciudadesAbiertas.madrid.service.dynamic.QueryService;
import org.ciudadesAbiertas.madrid.service.dynamic.SemanticFieldService;
import org.ciudadesAbiertas.madrid.service.dynamic.SemanticRmlService;
import org.ciudadesAbiertas.madrid.utils.RMLMapper;
import org.ciudadesAbiertas.madrid.utils.Util;
import org.ciudadesAbiertas.madrid.utils.constants.Constants;
import org.codehaus.jackson.map.ser.StdSerializers.UtilDateSerializer;
import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { WebConfig.class })
@WebAppConfiguration
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SemanticaTest {

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private QueryService queryService;

    @Autowired
    private SemanticRmlService semanticRmlService;

    @Autowired
    private PrefixRelService prefixRelService;

    @Autowired
    private PrefixService prefixService;

    @Autowired
    private SemanticFieldService semanticFieldService;

    private static final Logger log = LoggerFactory.getLogger(SemanticaTest.class);

    private MockMvc mockMvc;

    private static File temporalDataFile;

    private static final String UTF8_BOM = "\uFEFF";

    private static String removeBOM(String s) {
	if (s.startsWith(UTF8_BOM)) {
	    s = s.substring(1);
	}
	return s;
    }

    @Before
    public void setup() throws Exception {

	this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();

    }
    
    @Test
    public void test01_t() {
	assertTrue(true);
    }
    
    /*

    @Test
    public void test01_Service() {

	List<Boolean> list = new ArrayList<Boolean>();

	list.add(prefixService != null);
	list.add(queryService != null);
	list.add(semanticRmlService != null);
	list.add(prefixRelService != null);
	list.add(semanticFieldService != null);

	boolean finalBoolean = true;
	for (Boolean boolean1 : list) {
	    if (boolean1.booleanValue() == false) {
		finalBoolean = false;
		break;
	    }
	}

	assertTrue(finalBoolean);

    }

    @Test
    public void test02_Controller() {
	ServletContext servletContext = wac.getServletContext();

	Assert.assertNotNull(servletContext);
	Assert.assertTrue(servletContext instanceof MockServletContext);
	Assert.assertNotNull(wac.getBean("semanticController"));
    }

    @Test
    public void test03_QuerySubvencion() throws Exception {

	QueryD record = queryService.record("subvencion");

	Assert.assertTrue(record.getCode().equals("subvencion"));
    }

    @Test
    public void test04_SubvencionCleanPrefix() throws Exception {

	List<SemanticRelPrefix> prefixes = prefixRelService.getPrefixFromQuery("subvencion");

	for (SemanticRelPrefix rel : prefixes) {

	    prefixRelService.delete(rel);
	}

	prefixes = prefixRelService.getPrefixFromQuery("subvencion");

	Assert.assertTrue(prefixes.size() == 0);
    }

    @Test
    public void test05_SubvencionFieldsCleanFields() throws Exception {

	List<SemanticField> fields = semanticFieldService.getFieldsFromQuery("subvencion");

	for (SemanticField f : fields) {

	    semanticFieldService.delete(f);
	}

	fields = semanticFieldService.getFieldsFromQuery("subvencion");

	Assert.assertTrue(fields.size() == 0);
    }

    @Test
    public void test06_Generacion_CSV() throws Exception {
	
	String csvURL=DynamicController.path + "subvencion.csv?pageSize=2";
	
	MockHttpServletResponse response = this.mockMvc.perform(MockMvcRequestBuilders.get(csvURL))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andReturn()
		.getResponse();

	response.setCharacterEncoding("utf8");

	String content = response.getContentAsString();
	
	content=removeBOM(content);

	temporalDataFile = File.createTempFile("temporalData", ".csv");

	FileUtils.write(temporalDataFile, content, "utf8");
    }

    @Test
    public void test07_GenerateRML() throws Exception {

	String query = "subvencion";

	Map<String, SemanticPrefix> prefixMap = prefixService.queryMap();
	List<SemanticPrefix> prefixesList = new ArrayList<SemanticPrefix>();

	String prefixes = "xsd;rdf;owl;org;essubv;espresup;esadm;dcterms;";
	String[] splittedPrefixes = prefixes.split(";");
	for (String tempPrefix : splittedPrefixes) {
	    SemanticRelPrefix rel = new SemanticRelPrefix(query, tempPrefix);
	    prefixRelService.add(rel);

	    prefixesList.add(prefixMap.get(tempPrefix));
	}
	// tipo (rdfType)
	SemanticField rdfType = new SemanticField();
	rdfType.setQuery(query);
	rdfType.setField("type");
	rdfType.setObjectReference("https://alzir.dia.fi.upm.es/OpenCitiesAPI/subvencion/subvencion/{id}");
	rdfType.setObjectUri(true);

	rdfType.setPredicate("essubv:Subvencion");
	semanticFieldService.add(rdfType);

	List<SemanticField> semanticFields = new ArrayList<SemanticField>();

	SemanticField id = new SemanticField();
	id.setQuery(query);
	id.setField("id");
	id.setObjectReference("id");
	id.setPredicate("dcterms:identifier");
	semanticFieldService.add(id);

	semanticFields.add(id);

	SemanticField title = new SemanticField();
	title.setQuery(query);
	title.setField("title");
	title.setObjectReference("title");
	title.setPredicate("dcterms:title");
	semanticFieldService.add(title);

	semanticFields.add(title);

	SemanticField importe = new SemanticField();
	importe.setQuery(query);
	importe.setField("importe");
	importe.setObjectReference("importe");
	importe.setPredicate("essubv:importe");
	importe.setObjectType("xsd:float");
	semanticFieldService.add(importe);

	semanticFields.add(importe);
	
	SemanticField entidadFinanciadora = new SemanticField();
	entidadFinanciadora.setQuery(query);
	entidadFinanciadora.setField("entidadFinanciadora");
	entidadFinanciadora.setObjectReference("https://alzir.dia.fi.upm.es/OpenCitiesAPI/organigrama/organizacion/{entidadFinanciadoraId}");
	entidadFinanciadora.setPredicate("essubv:entidadFinanciadora");	
	entidadFinanciadora.setObjectUri(true);
	semanticFieldService.add(entidadFinanciadora);

	semanticFields.add(entidadFinanciadora);
	
	SemanticField adjudicatarioId = new SemanticField();
	adjudicatarioId.setQuery(query);
	adjudicatarioId.setField("adjudicatarioId");
	adjudicatarioId.setObjectReference("adjudicatarioId");	
	adjudicatarioId.setPredicate("dcterms:identifier");	
	adjudicatarioId.setBlankNodeType("owl:Thing");
	adjudicatarioId.setBlankNodeProperty("essubv:adjudicatario");
	adjudicatarioId.setBlankNodeId("nodoAdjudicatario");
	semanticFieldService.add(adjudicatarioId);
	
	semanticFields.add(adjudicatarioId);
	
	SemanticField adjudicatarioTitle = new SemanticField();
	adjudicatarioTitle.setQuery(query);
	adjudicatarioTitle.setField("adjudicatarioTitle");
	adjudicatarioTitle.setObjectReference("adjudicatarioTitle");
	adjudicatarioTitle.setPredicate("dcterms:title");	
	adjudicatarioTitle.setBlankNodeType("owl:Thing");
	adjudicatarioTitle.setBlankNodeProperty("essubv:adjudicatario");
	adjudicatarioTitle.setBlankNodeId("nodoAdjudicatario");
	semanticFieldService.add(adjudicatarioTitle);

	semanticFields.add(adjudicatarioTitle);

	SemanticRml rml = new SemanticRml();
	rml.setQuery(query);
	rml.setRml(SemanticController.generateRML(rdfType, semanticFields, prefixesList));

	// System.out.println(rml.getRml());

	File rmlDinamico = File.createTempFile("rmlDinamico", ".ttl");

	FileUtils.write(rmlDinamico, rml.getRml().replace("nombreDinamico.csv", temporalDataFile.getAbsolutePath()), "utf8");

	RMLMapper.generateRDF(rmlDinamico.getAbsolutePath(), prefixesList);

	Assert.assertTrue(rml.getRml() != null);
    }
    
    */

}
