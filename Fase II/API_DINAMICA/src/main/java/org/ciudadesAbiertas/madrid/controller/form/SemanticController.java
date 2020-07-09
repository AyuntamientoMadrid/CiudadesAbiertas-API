package org.ciudadesAbiertas.madrid.controller.form;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.ciudadesAbiertas.madrid.bean.DataType;
import org.ciudadesAbiertas.madrid.bean.QueryBean;
import org.ciudadesAbiertas.madrid.bean.SemanticBean;
import org.ciudadesAbiertas.madrid.config.multipe.MultipleDataSource;
import org.ciudadesAbiertas.madrid.controller.Commons;
import org.ciudadesAbiertas.madrid.model.dynamic.ParamD;
import org.ciudadesAbiertas.madrid.model.dynamic.QueryD;
import org.ciudadesAbiertas.madrid.model.dynamic.SemanticField;
import org.ciudadesAbiertas.madrid.model.dynamic.SemanticPrefix;
import org.ciudadesAbiertas.madrid.model.dynamic.SemanticRelPrefix;
import org.ciudadesAbiertas.madrid.model.dynamic.SemanticRml;
import org.ciudadesAbiertas.madrid.model.dynamic.SwaggerDefinition;
import org.ciudadesAbiertas.madrid.service.dynamic.DynamicService;
import org.ciudadesAbiertas.madrid.service.dynamic.ParamService;
import org.ciudadesAbiertas.madrid.service.dynamic.PrefixRelService;
import org.ciudadesAbiertas.madrid.service.dynamic.PrefixService;
import org.ciudadesAbiertas.madrid.service.dynamic.QueryService;
import org.ciudadesAbiertas.madrid.service.dynamic.SemanticFieldService;
import org.ciudadesAbiertas.madrid.service.dynamic.SemanticRmlService;
import org.ciudadesAbiertas.madrid.service.dynamic.SwaggerDefinitionService;
import org.ciudadesAbiertas.madrid.utils.ErrorBean;
import org.ciudadesAbiertas.madrid.utils.StartVariables;
import org.ciudadesAbiertas.madrid.utils.Util;
import org.ciudadesAbiertas.madrid.utils.constants.Constants;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomCollectionEditor;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class SemanticController {

    private static final String FIELD_TYPE_CAMPO_ADDED = "fieldTypeCampoAdded_";

    private static final String URL_CAMPO_ADDED = "urlCampoAdded_";

    private static final String PREFIX_TYPE_CAMPO_ADDED = "prefixTypeCampoAdded_";

    private static final String FIELD_NAME_CAMPO_ADDED = "fieldNameCampoAdded_";

    private static final Logger log = LoggerFactory.getLogger(SemanticController.class);

    @Autowired
    protected Environment env;

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

    private static final String PATH = "/semanticDef";
    private static final String PARAM_ID = "{id}";

    public static final String LIST = PATH;
    public static final String RECORD = PATH + "/" + PARAM_ID;
    public static final String ADD = PATH + "/add";
    public static final String SAVE = PATH + "/save";
    public static final String EDIT = PATH + "/edit/" + PARAM_ID;
    public static final String DELETE = PATH + "/delete/" + PARAM_ID;
    public static final String UPDATE = PATH + "/update/" + PARAM_ID;

    public static final String MODULO = "Definciones Semánticas";
    public static final String OPERACION_PREALTA = "PRE-ALTA";
    public static final String OPERACION_ALTA = "ALTA";
    public static final String OPERACION_LISTADO = "Listado";
    public static final String OPERACION_FICHA = "Ficha";
    public static final String OPERACION_PREBORRADO = "PRE-BORRADO";
    public static final String OPERACION_BORRADO = "BORRADO";

    public static final String OPERACION_PREEDICION = "PRE-EDICION";
    public static final String OPERACION_EDICION = "EDICION";

    @RequestMapping(value = LIST, method = RequestMethod.GET)
    public ModelAndView list(HttpServletRequest request, @RequestParam(value = Constants.PAGE, defaultValue = "1", required = false) String page, @RequestParam(value = Constants.PARAM_ERROR, required = false) String error,
	    @RequestParam(value = Constants.PARAM_DELETED, required = false) String deleted, @RequestParam(value = Constants.PARAM_UPDATED, required = false) String updated,
	    @RequestParam(value = Constants.PARAM_ADDED, required = false) String added) {

	log.info(LIST);

	String estadoOperacion = Constants.AUDITORIA_ESTADO_OK;

	ModelAndView model = new ModelAndView();
	model.setViewName("semantic/list");

	Commons.controllerCommon(model);

	if ((deleted != null)) {
	    model.addObject("deleted", "true");
	}

	if ((updated != null)) {
	    model.addObject("updated", "true");
	}

	if ((added != null)) {
	    model.addObject("added", "true");
	}

	if ((error != null)) {
	    model.addObject("error", "true");
	}

	try {
	    int firstResult = 1;
	    int pageNumber = 1;
	    try {
		pageNumber = Integer.parseInt(page);
		pageNumber--;
	    } catch (Exception e) {
		log.error("Wrong page number", e);
	    }
	    if (pageNumber >= 0) {
		firstResult = pageNumber * StartVariables.defaultPageSize;
	    } else {
		firstResult = 0;
	    }

	    int total = semanticRmlService.listRowCount();

	    List<SemanticRml> rmlList = semanticRmlService.list(firstResult, StartVariables.defaultPageSize);

	    Map<String, QueryD> queryList = queryService.queryMap();

	    List<SemanticBean> listBean = new ArrayList<SemanticBean>();

	    if (!queryList.isEmpty() && !rmlList.isEmpty()) {
		for (SemanticRml rml : rmlList) {

		    SemanticBean bean = new SemanticBean();
		    bean.setRml(rml);

		    listBean.add(bean);

		}
	    }

	    model.addObject("list", listBean);

	    Map<String, String> pMCalculation = Util.pageMetadataCalculation(request, total, StartVariables.defaultPageSize, env.getProperty(Constants.URI_BASE), env.getProperty(Constants.STR_CONTEXTO));

	    if (pMCalculation.get("actual").equals("1") == false) {
		model.addObject("first", pMCalculation.get(Constants.FIRST));
	    }
	    if (pMCalculation.get("actual").equals(pMCalculation.get("total")) == false) {
		model.addObject("last", pMCalculation.get(Constants.LAST));
	    }
	    model.addObject("next", pMCalculation.get(Constants.NEXT));
	    model.addObject("prev", pMCalculation.get(Constants.PREV));
	    model.addObject("actual", pMCalculation.get(Constants.PREV));
	    model.addObject("total", pMCalculation.get("total"));
	    model.addObject("actual", pMCalculation.get("actual"));

	} catch (Exception e) {
	    log.error("Error reading list", e);
	    model.addObject("list", new ArrayList<QueryD>());
	    model.addObject(Constants.ERROR_OBJ, "error reading list");
	    estadoOperacion = Constants.AUDITORIA_ESTADO_ERROR;
	}

	Commons.auditoria(MODULO, OPERACION_LISTADO, estadoOperacion);

	return model;
    }

    private List<DataType> xsdDatatypes() {
	List<DataType> xsdDataTypes = new ArrayList<DataType>();
	xsdDataTypes.add(new DataType("String", "string", ""));
	xsdDataTypes.add(new DataType("xsd:integer", "xsd:integer", "Ej. \"1\"^^xsd:integer"));
	xsdDataTypes.add(new DataType("xsd:decimal", "xsd:decimal", "Ej. \"1.3\"^^xsd:decimal"));
	xsdDataTypes.add(new DataType("xsd:double", "xsd:double", "Ej. \" 1.0e6\"^^xsd:double"));
	xsdDataTypes.add(new DataType("xsd:boolean", "xsd:boolean", "Ej. \"true\"^^boolean"));
	return xsdDataTypes;
    }

    @RequestMapping(value = ADD, method = RequestMethod.GET)
    public ModelAndView add(HttpServletRequest request) {

	String estadoOperacion = Constants.AUDITORIA_ESTADO_OK;

	log.info(ADD);

	ModelAndView model = new ModelAndView();
	model.setViewName("semantic/add");

	try {

	    List<SemanticRml> rmlList = semanticRmlService.list(-1, -1);

	    Map<String, QueryD> queryMap = queryService.queryMap();

	    // Borramos las queries que ya se están utilizando
	    for (SemanticRml semanticRml : rmlList) {
		queryMap.remove(semanticRml.getQuery());
	    }

	    model.addObject("availableQueries", queryMap);

	    List<SemanticPrefix> prefixList = prefixService.list(-1, -1);

	    model.addObject("prefixList", prefixList);

	    model.addObject("xsdDataTypes", xsdDatatypes());

	    Commons.controllerCommon(model);

	} catch (Exception e) {
	    log.error("Error adding object", e);
	    estadoOperacion = Constants.AUDITORIA_ESTADO_ERROR;
	}

	Commons.auditoria(MODULO, OPERACION_PREALTA, estadoOperacion);

	return model;

    }

    @RequestMapping(value = RECORD, method = RequestMethod.GET)
    public ModelAndView record(@PathVariable String id) {

	log.info(RECORD);

	String estadoOperacion = Constants.AUDITORIA_ESTADO_OK;

	ModelAndView model = new ModelAndView();
	model.setViewName("semantic/record");

	Commons.controllerCommon(model);

	try {
	    QueryD entidad = queryService.record(id);
	    model.addObject("object", entidad);
	    setDatabaseSelected(model, entidad);

	    Commons.auditoria(MODULO, OPERACION_FICHA, estadoOperacion);
	    return model;
	} catch (Exception e) {
	    log.error("Error reading object", e);
	    estadoOperacion = Constants.AUDITORIA_ESTADO_ERROR;
	}

	Commons.auditoria(MODULO, OPERACION_FICHA, estadoOperacion);

	return new ModelAndView("redirect:" + LIST + "?" + Constants.PARAM_ERROR);

    }

    @RequestMapping(value = SAVE, method = RequestMethod.POST)
    public ModelAndView save(HttpServletRequest request, @RequestParam Map<String, String> allParams) {

	log.info(SAVE);

	String estadoOperacion = Constants.AUDITORIA_ESTADO_OK;

	String query = allParams.get("query");

	String prefixes = allParams.get("prefixSelectedHidden");

	String prefixType = allParams.get("prefixType");

	String urlType = allParams.get("typeURI");

	try {

	    Map<String, SemanticPrefix> prefixMap = prefixService.queryMap();
	    List<SemanticPrefix> prefixesList = new ArrayList<SemanticPrefix>();

	    // tratamiento de prefijos
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

	    String prefix = "";
	    if (Util.validValue(prefixType)) {
		prefix = prefixType + ":" + urlType;
	    } else {
		prefix = urlType;
	    }
	    rdfType.setPredicate(prefix);
	    semanticFieldService.add(rdfType);

	    Map<String, String> mapFields = StartVariables.modelsForDynamicQuerys.get(query);

	    List<SemanticField> semanticFields = new ArrayList<SemanticField>();

	    for (String key : mapFields.keySet()) {
		String fieldName = allParams.get(FIELD_NAME_CAMPO_ADDED + key);
		String fieldPrefix = allParams.get(PREFIX_TYPE_CAMPO_ADDED + key);
		String fieldURL = allParams.get(URL_CAMPO_ADDED + key);
		String fieldType = allParams.get(FIELD_TYPE_CAMPO_ADDED + key);

		if (Util.validValue(fieldURL)) {
		    SemanticField temporalType = new SemanticField();
		    temporalType.setQuery(query);
		    temporalType.setField(fieldName);
		    temporalType.setObjectReference(fieldURL);

		    // temporalType.setObjectUri(true);

		    prefix = "";
		    if (Util.validValue(fieldPrefix)) {
			prefix = fieldPrefix + ":" + fieldType;
		    } else {
			prefix = fieldType;
		    }
		    temporalType.setPredicate(prefix);
		    semanticFieldService.add(temporalType);

		    semanticFields.add(temporalType);

		}
	    }

	    SemanticRml rml = new SemanticRml();
	    rml.setQuery(query);
	    rml.setRml(generateRML(rdfType, semanticFields, prefixesList));
	    semanticRmlService.add(rml);

	    /*
	     * fieldNameCampoAdded_id prefixTypeCampoAdded_id urlCampoAdded_id
	     * fieldTypeCampoAdded_id
	     */

	    Commons.auditoria(MODULO, OPERACION_ALTA, estadoOperacion);
	    return new ModelAndView("redirect:" + LIST + "?" + Constants.PARAM_ADDED);

	} catch (Exception e) {
	    log.error("Error saving object", e);
	    estadoOperacion = Constants.AUDITORIA_ESTADO_ERROR;
	}

	Commons.auditoria(MODULO, OPERACION_ALTA, estadoOperacion);
	return new ModelAndView("redirect:" + LIST + "?" + Constants.PARAM_ERROR);

    }

    public static String generateRML(SemanticField rdfType, List<SemanticField> semanticFields, List<SemanticPrefix> prefixes) {

	List<String> rmlLines = new ArrayList<String>();

	rmlLines.add("@prefix rr: <http://www.w3.org/ns/r2rml#> .");
	rmlLines.add("@prefix ex: <http://example.com/> .");
	rmlLines.add("@prefix rml: <http://semweb.mmlab.be/ns/rml#> .");
	rmlLines.add("@prefix ql: <http://semweb.mmlab.be/ns/ql#> .");
	rmlLines.add("@base <http://example.com/base/> .");

	for (SemanticPrefix prefix : prefixes) {
	    rmlLines.add("@prefix " + prefix.getId() + ":   <" + prefix.getUrl() + "> .");
	}

	rmlLines.add("");

	rmlLines.add("<#TriplesMap1> a rr:TriplesMap;");

	rmlLines.add("");

	rmlLines.add("rml:logicalSource [");
	rmlLines.add("  rml:source \"nombreDinamico.csv\";");
	rmlLines.add("  rml:referenceFormulation ql:CSV");
	rmlLines.add("  ];");

	rmlLines.add("");

	if (rdfType != null) {
	    rmlLines.add("rr:subjectMap [");
	    rmlLines.add(" rr:template \"" + rdfType.getObjectReference() + "\";");
	    if (Util.validValue(rdfType.getPredicate())) {
		rmlLines.add("rr:class " + rdfType.getPredicate());
	    }
	    rmlLines.add("];");

	    rmlLines.add("");
	}

	
	List<SemanticField> blankNodeList=new ArrayList<SemanticField>();
	List<String> blankNodeIds=new ArrayList<String>();
	
	Map<String,String> relationTreeNodeId=new HashMap<String,String>();
	Map<String,String> relationTypeNodeId=new HashMap<String,String>();
	
	for (SemanticField field : semanticFields) {

	    if (Util.validValue(field.getBlankNodeId()) == false) {
		if (field.isObjectUri()) {
		    rmlLines.add("rr:predicateObjectMap [");
		    rmlLines.add("rr:predicate " + field.getPredicate() + " ;");
		    rmlLines.add("rr:objectMap [ rr:template \"" + field.getObjectReference() + "\" ]");
		    rmlLines.add(" ];");
		} else {
		    rmlLines.add("rr:predicateObjectMap [");
		    rmlLines.add("rr:predicate " + field.getPredicate() + " ;");
		    String objectType = "";
		    if (Util.validValue(field.getObjectType())) {
			objectType = "; rr:datatype " + field.getObjectType() + ";";
		    }
		    rmlLines.add("rr:objectMap [ rml:reference \"" + field.getObjectReference() + "\"" + objectType + " ]");
		    rmlLines.add(" ];");
		}
	    }else {
		blankNodeList.add(field);
		if (blankNodeIds.contains(field.getBlankNodeId())==false)
		{
		    blankNodeIds.add(field.getBlankNodeId());
		    
		    
		    rmlLines.add("rr:predicateObjectMap [");
		    rmlLines.add("   rr:predicate "+field.getBlankNodeProperty()+";");		   
		    
		    String treeName="#TriplesMap"+(blankNodeIds.size()+1);
		    
		    rmlLines.add("   rr:objectMap [");
		    rmlLines.add("    rr:parentTriplesMap <"+treeName+">;");
		    rmlLines.add("    rr:joinCondition [");
		    rmlLines.add("      rr:child \"id\";");
		    rmlLines.add("      rr:parent \"id\";");
		    rmlLines.add("      ];");
		    rmlLines.add("    ]");
		    rmlLines.add("   ];");
		    
		    relationTreeNodeId.put(field.getBlankNodeId(), treeName);
		    relationTypeNodeId.put(field.getBlankNodeId(), field.getBlankNodeType());
		}
	    }		
	}
	
	

	replaceLastCharForPoint(rmlLines);
	
	
	//Arboles para nodos en blanco
	if (blankNodeIds.size()>0)
	{
	    rmlLines.add("");
	    rmlLines.add("");
	    //Por cada identificador de un nodo en blanco tengo que:
	    //	a) Añadir la definicion del treemap y su sujeto
	    //  b) Buscar los campos que se corresponden con este nodo
	    //	c) Escribir su definición dentro del nodo	
	    //	d) Acabar en . en lugar de ;
	    for (String nodeId : blankNodeIds) {
				
		//a
		rmlLines.add("<"+relationTreeNodeId.get(nodeId)+"> a rr:TriplesMap;");
		rmlLines.add("  rml:logicalSource [");
		rmlLines.add("      rml:source \"nombreDinamico.csv\";");
		rmlLines.add("    rml:referenceFormulation ql:CSV");
		rmlLines.add(" ];");
		
		rmlLines.add("");
		rmlLines.add("rr:subjectMap [");
		rmlLines.add("  rr:termType rr:BlankNode;");
		if (Util.validValue(relationTypeNodeId.get(nodeId)))
		{
		    rmlLines.add("  rr:class "+ relationTypeNodeId.get(nodeId));
		}		
		rmlLines.add(" ];");
		
		//b
		List<SemanticField> fieldsForThisNodeId=new ArrayList<SemanticField>();
		for (SemanticField field : blankNodeList) {
		    if (field.getBlankNodeId().contentEquals(nodeId))
		    {
			fieldsForThisNodeId.add(field);
		    }
		}
		for (SemanticField field : fieldsForThisNodeId) {
		    rmlLines.add("");
		    if (field.isObjectUri()) {
			    rmlLines.add("rr:predicateObjectMap [");
			    rmlLines.add("rr:predicate " + field.getPredicate() + " ;");
			    rmlLines.add("rr:objectMap [ rr:template \"" + field.getObjectReference() + "\" ]");
			    rmlLines.add(" ];");
			} else {
			    rmlLines.add("rr:predicateObjectMap [");
			    rmlLines.add("rr:predicate " + field.getPredicate() + " ;");
			    String objectType = "";
			    if (Util.validValue(field.getObjectType())) {
				objectType = "; rr:datatype " + field.getObjectType() + ";";
			    }
			    rmlLines.add("rr:objectMap [ rml:reference \"" + field.getObjectReference() + "\"" + objectType + " ]");
			    rmlLines.add(" ];");
			}
		}
		
		
		//d
		replaceLastCharForPoint(rmlLines);
	    }
	    
	    
	}
	

	String rml = "";
	for (String l : rmlLines) {
	    rml += l + System.getProperty("line.separator");
	}

	return rml;
    }

    private static void replaceLastCharForPoint(List<String> rmlLines) {
	String lastLine = rmlLines.get(rmlLines.size() - 1);
	if (lastLine.endsWith(";")) {
	    lastLine = lastLine.substring(0, lastLine.lastIndexOf(";"));
	    lastLine += ".";
	    rmlLines.set(rmlLines.size() - 1, lastLine);
	}
    }

    private List<ParamD> extractParamsFromRequest(HttpServletRequest request, Enumeration<String> parameterNames) {
	List<ParamD> queryParameters = new ArrayList<ParamD>();
	String code = "";
	String description = "";
	String type = "";
	String example = "";
	while (parameterNames.hasMoreElements()) {

	    String paramName = parameterNames.nextElement();

	    if (paramName.contains("code")) {
		String[] paramValues = request.getParameterValues(paramName);

		code = paramValues[0];
	    }

	    if (paramName.contains("description")) {
		String[] paramValues = request.getParameterValues(paramName);

		description = paramValues[0];
	    }

	    if (paramName.contains("type")) {
		String[] paramValues = request.getParameterValues(paramName);
		type = paramValues[0];
	    }

	    if (paramName.contains("example")) {
		String[] paramValues = request.getParameterValues(paramName);

		example = paramValues[0];

		if ((code != null) && (!code.equals(""))) {
		    ParamD param = new ParamD();
		    param.setName(code);
		    param.setDescription(description);
		    param.setExample(example);
		    param.setType(type);

		    queryParameters.add(param);
		}

		code = "";
		description = "";
		type = "";
		example = "";
	    }
	}
	return queryParameters;
    }

    @RequestMapping(value = EDIT, method = RequestMethod.GET)
    public ModelAndView edit(@PathVariable String id) {

	log.info(EDIT);

	String estadoOperacion = Constants.AUDITORIA_ESTADO_OK;

	ModelAndView model = new ModelAndView();
	model.setViewName("semantic/edit");

	Commons.controllerCommon(model);

	try {
	    QueryD entidad = queryService.record(id);
	    model.addObject("object", entidad);
	    setDatabaseSelected(model, entidad);

	    model.addObject("definitionSelected", entidad.getDefinition());
	    Commons.auditoria(MODULO, OPERACION_PREEDICION, estadoOperacion);
	    return model;
	} catch (Exception e) {
	    log.error("Error updating object", e);
	    estadoOperacion = Constants.AUDITORIA_ESTADO_ERROR;
	}

	Commons.auditoria(MODULO, OPERACION_PREEDICION, estadoOperacion);
	return new ModelAndView("redirect:" + LIST + "?" + Constants.PARAM_ERROR);

    }

    private void setDatabaseSelected(ModelAndView model, QueryD entidad) {
	if (entidad.getDatabase().equals(Constants.DEFAULT_DATABASE)) {
	    model.addObject("databaseSelected", Constants.BASE_DE_DATOS_POR_DEFECTO);
	} else {
	    model.addObject("databaseSelected", entidad.getDatabase());
	}
    }

    @RequestMapping(value = UPDATE, method = RequestMethod.POST)
    public ModelAndView update(HttpServletRequest request, @PathVariable String id, @ModelAttribute("queryForm") QueryD queryForm, @RequestParam String originalCode) {

	log.info(UPDATE);

	String estadoOperacion = Constants.AUDITORIA_ESTADO_OK;

	try {

	    QueryD oldQuery = queryService.record(id);

	    queryForm.setTexto(Util.formatSQL(queryForm.getTexto()));

	    if (queryForm.getDefinition() != null && queryForm.getDefinition().equals("-1")) {
		queryForm.setDefinition("");
	    }

	    String code = queryForm.getCode().toLowerCase();
	    code = StringUtils.replace(code, " ", "");
	    queryForm.setCode(code);

	    queryService.update(queryForm, oldQuery, null, null);

	    // Actualizo el modelo
	    Commons.auditoria(MODULO, OPERACION_EDICION, estadoOperacion);
	    return new ModelAndView("redirect:" + LIST + "?" + Constants.PARAM_UPDATED);

	} catch (Exception e) {
	    log.error("Error updating object", e);
	    estadoOperacion = Constants.AUDITORIA_ESTADO_ERROR;
	}

	Commons.auditoria(MODULO, OPERACION_EDICION, estadoOperacion);
	return new ModelAndView("redirect:" + LIST + "?" + Constants.PARAM_ERROR);

    }

    @RequestMapping(value = DELETE, method = RequestMethod.GET)
    public ModelAndView preDelete(@PathVariable String id) {

	log.info(DELETE + " - get");

	String estadoOperacion = Constants.AUDITORIA_ESTADO_OK;

	ModelAndView model = new ModelAndView();
	model.setViewName("semantic/delete");

	Commons.controllerCommon(model);

	try {
	    QueryD entidad = queryService.record(id);
	    model.addObject("object", entidad);
	    setDatabaseSelected(model, entidad);

	    Commons.auditoria(MODULO, OPERACION_PREBORRADO, estadoOperacion);
	    return model;
	} catch (Exception e) {
	    log.error("Error searching object", e);
	    estadoOperacion = Constants.AUDITORIA_ESTADO_ERROR;
	}

	Commons.auditoria(MODULO, OPERACION_PREBORRADO, estadoOperacion);
	return new ModelAndView("redirect:" + LIST + "?" + Constants.PARAM_ERROR);

    }

    @RequestMapping(value = DELETE, method = RequestMethod.POST)
    public ModelAndView delete(@PathVariable String id) {

	log.info(DELETE + " - post");

	String estadoOperacion = Constants.AUDITORIA_ESTADO_OK;

	try {

	    QueryD recordToDelete = queryService.record(id);

	    queryService.delete(recordToDelete, null);
	    Commons.auditoria(MODULO, OPERACION_BORRADO, estadoOperacion);
	    return new ModelAndView("redirect:" + LIST + "?" + Constants.PARAM_DELETED);
	} catch (Exception e) {
	    log.error("Error deleting object", e);
	    estadoOperacion = Constants.AUDITORIA_ESTADO_ERROR;
	}

	Commons.auditoria(MODULO, OPERACION_BORRADO, estadoOperacion);
	return new ModelAndView("redirect:" + LIST + "?" + Constants.PARAM_ERROR);

    }

}