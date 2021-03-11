
package org.ciudadesAbiertas.madrid.apiDinamica;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import org.apache.commons.io.FileUtils;
import org.ciudadesAbiertas.madrid.config.WebConfig;
import org.ciudadesAbiertas.madrid.controller.dynamic.DynamicController;
import org.ciudadesAbiertas.madrid.controller.form.SemanticController;
import org.ciudadesAbiertas.madrid.model.dynamic.ParamD;
import org.ciudadesAbiertas.madrid.model.dynamic.QueryD;
import org.ciudadesAbiertas.madrid.model.dynamic.SemanticField;
import org.ciudadesAbiertas.madrid.model.dynamic.SemanticPrefix;
import org.ciudadesAbiertas.madrid.model.dynamic.SemanticRelPrefix;
import org.ciudadesAbiertas.madrid.model.dynamic.SemanticRml;
import org.ciudadesAbiertas.madrid.service.dynamic.ParamService;
import org.ciudadesAbiertas.madrid.service.dynamic.PrefixRelService;
import org.ciudadesAbiertas.madrid.service.dynamic.PrefixService;
import org.ciudadesAbiertas.madrid.service.dynamic.QueryService;
import org.ciudadesAbiertas.madrid.service.dynamic.SemanticFieldService;
import org.ciudadesAbiertas.madrid.service.dynamic.SemanticRmlService;
import org.ciudadesAbiertas.madrid.utils.RMLMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
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
    private ParamService paramService;

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

	QueryD record = queryService.recordCode("subvencion");

	Assert.assertTrue(record.getCode().equals("subvencion"));
    }

    @Test
    public void test04_SubvencionCleanPrefix() throws Exception {

	List<SemanticRelPrefix> prefixes = prefixRelService.getPrefixRelFromQuery("subvencion");

	for (SemanticRelPrefix rel : prefixes) {

	    prefixRelService.delete(rel);
	}

	prefixes = prefixRelService.getPrefixRelFromQuery("subvencion");

	Assert.assertTrue(prefixes.size() == 0);
    }

    @Test
    public void test05_SubvencionFieldsCleanFields() throws Exception {

	List<SemanticField> fields = semanticFieldService.getFieldsFromQuery("subvencion","id");

	for (SemanticField f : fields) {

	    semanticFieldService.delete(f);
	}

	fields = semanticFieldService.getFieldsFromQuery("subvencion","id");

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

	Map<Integer, SemanticPrefix> prefixMap = prefixService.queryMap();
	List<SemanticPrefix> prefixesList = new ArrayList<SemanticPrefix>();

	String prefixes = "1;2;3;4;5;6;7;8;9";	
	
	String[] splittedPrefixes = prefixes.split(";");
	for (String tempPrefix : splittedPrefixes) {
	    SemanticRelPrefix rel = new SemanticRelPrefix(query, Integer.parseInt(tempPrefix));
	    prefixRelService.add(rel);

	    prefixesList.add(prefixMap.get(Integer.parseInt(tempPrefix)));
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
    
    @Test
    public void test08_ParamSubvencion() throws Exception {

      List<ParamD> list = paramService.list("subvencion-groupby");

      Assert.assertTrue(list.size()>0);
    }
    
    
    @Test
    public void test09_AddAndRemove3Queries() throws Exception {
      
      int pre = queryService.listRowCount();
      
      QueryD query=new QueryD();
      query.setCode("testCode01");
      query.setSummary("consulta de prueba");
      query.setDatabase("default");
      query.setTexto("Select * from subvencion");
      query.setTags("Etiqueta 1");
           
      queryService.add(query, null);
      
      query=new QueryD();
      query.setCode("testCode02");
      query.setSummary("consulta de prueba");
      query.setDatabase("default");
      query.setTexto("Select * from subvencion");
      query.setTags("Etiqueta 2");
      
      queryService.add(query, null);
      
      query=new QueryD();
      query.setCode("testCode03");
      query.setSummary("consulta de prueba");
      query.setDatabase("default");
      query.setTexto("Select * from subvencion");
      query.setTags("Etiqueta 2");
      
      queryService.add(query, null);
      
      int post = queryService.listRowCount();
      
      QueryD temp=queryService.recordCode("testCode01");      
      queryService.delete(temp, null);
      temp=queryService.recordCode("testCode02");      
      queryService.delete(temp, null);
      temp=queryService.recordCode("testCode03");      
      queryService.delete(temp, null);
      
      int last = queryService.listRowCount();

      Assert.assertTrue ((pre < post)  && (pre==last));
    }

}
