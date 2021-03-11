package org.ciudadesAbiertas.madrid.controller.form;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.ciudadesAbiertas.madrid.bean.DataType;
import org.ciudadesAbiertas.madrid.bean.QueryBean;
import org.ciudadesAbiertas.madrid.bean.SemanticBean;
import org.ciudadesAbiertas.madrid.controller.Commons;
import org.ciudadesAbiertas.madrid.controller.dynamic.DynamicController;
import org.ciudadesAbiertas.madrid.model.dynamic.ParamD;
import org.ciudadesAbiertas.madrid.model.dynamic.QueryD;
import org.ciudadesAbiertas.madrid.model.dynamic.SemanticField;
import org.ciudadesAbiertas.madrid.model.dynamic.SemanticPrefix;
import org.ciudadesAbiertas.madrid.model.dynamic.SemanticRelPrefix;
import org.ciudadesAbiertas.madrid.model.dynamic.SemanticRml;
import org.ciudadesAbiertas.madrid.service.dynamic.DynamicService;
import org.ciudadesAbiertas.madrid.service.dynamic.ParamService;
import org.ciudadesAbiertas.madrid.service.dynamic.PrefixRelService;
import org.ciudadesAbiertas.madrid.service.dynamic.PrefixService;
import org.ciudadesAbiertas.madrid.service.dynamic.QueryService;
import org.ciudadesAbiertas.madrid.service.dynamic.SemanticFieldService;
import org.ciudadesAbiertas.madrid.service.dynamic.SemanticRmlService;
import org.ciudadesAbiertas.madrid.utils.StartVariables;
import org.ciudadesAbiertas.madrid.utils.Util;
import org.ciudadesAbiertas.madrid.utils.constants.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class SemanticController {

private static final String RDF_TYPE = "type";

private static final String ADD_OPERATION = "ADD";
private static final String UPDATE_OPERATION = "UPDATE";

private static final Logger log = LoggerFactory.getLogger(SemanticController.class);

@Autowired
protected Environment env;

@Autowired
private QueryService queryService;

@Autowired
private SemanticRmlService semanticRmlService;

@Autowired
private PrefixService prefixService;

@Autowired
private SemanticFieldService semanticFieldService;

@Autowired
private PrefixRelService prefixRelService;

@Autowired
private ParamService paramService;

@Autowired
private DynamicService dynamicDataBaseService;

private static final String OBJECT_URI = StartVariables.uriBase + StartVariables.context + DynamicController.path;

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

		Map<String, List<SemanticField>> mapQueryFields = semanticFieldService.getMapQueryFields();

		if (!queryList.isEmpty() && !rmlList.isEmpty()) {
			for (SemanticRml rml : rmlList) {

				SemanticBean bean = new SemanticBean();
				bean.setRml(rml);
				bean.setFields(semanticFieldService.getFieldsFromQuery(rml.getQuery(), "id"));

				SemanticField typeField = null;
				List<SemanticField> list = mapQueryFields.get(rml.getQuery());
				for (SemanticField semanticField : list) {
					if (semanticField.getField().equals((RDF_TYPE))) {
					  
					  	if (semanticField.getPredicate().contains(":"))
					  	{
					  	  String[] split = semanticField.getPredicate().split(":");
					  	  bean.setPrefixTypeURL(split[0]);
					  	  bean.setTypeURL(split[1]);
					  	}
					  	else
					  	{
					  	 bean.setTypeURL(semanticField.getPredicate());
					  	}

						typeField = semanticField;

						break;
					}
				}
				if (typeField != null) {
					list.remove(typeField);
				}

				bean.setFields(list);
				
				listBean.add(bean);

			}
		}

		model.addObject("list", listBean);
		 
		Map<String, String> exampleParams = new HashMap<String, String>();
		 
		for (SemanticBean queryD : listBean) {

		  // Genero los parametros por defecto para testear las queries si es necesario
		  List<ParamD> params = paramService.list(queryD.getRml().getQuery());
		  String param = "";
		  for (ParamD actualParam : params) {
			if (Util.validValue(actualParam.getExample())) {
			  param += actualParam.getName() + "=" + actualParam.getExample() + "&";
			}
		  }
		  if (param.length() > 0) {
			param = StringUtils.chop(param);
		  }
		  if (Util.validValue(param)) {
			exampleParams.put(queryD.getRml().getQuery(), param);
		  }

		}
		
		model.addObject("exampleParams", exampleParams);
		
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
	xsdDataTypes.add(new DataType("xsd:long", "xsd:long", "Ej. \"12233720368547758\"^^xsd:long"));
	xsdDataTypes.add(new DataType("xsd:decimal", "xsd:decimal", "Ej. \"1.3\"^^xsd:decimal"));
	xsdDataTypes.add(new DataType("xsd:double", "xsd:double", "Ej. \"40.54806866\"^^xsd:double"));
	xsdDataTypes.add(new DataType("xsd:float", "xsd:float", "Ej. \" 1.0e6\"^^xsd:float"));
	xsdDataTypes.add(new DataType("xsd:boolean", "xsd:boolean", "Ej. \"true\"^^boolean"));
	xsdDataTypes.add(new DataType("xsd:date", "xsd:date", "Ej. \"2018-10-26\"^^xsd:date"));
	xsdDataTypes.add(new DataType("xsd:time", "xsd:time", "Ej. \"23:59:59\"^^xsd:time"));
	xsdDataTypes.add(new DataType("xsd:dateTime", "xsd:dateTime", "Ej. \"2016-04-25T12:30:00\"^^xsd:dateTime"));

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
			
		model.addObject("objectURI",OBJECT_URI);		
		
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
		SemanticBean bean = generateRecordBean(id);

		// System.out.println(generateRML(typeURI, fields, prefixes));

		model.addObject("object", bean);

		Commons.auditoria(MODULO, OPERACION_FICHA, estadoOperacion);
		return model;
	} catch (Exception e) {
		log.error("Error reading object", e);
		estadoOperacion = Constants.AUDITORIA_ESTADO_ERROR;
	}

	Commons.auditoria(MODULO, OPERACION_FICHA, estadoOperacion);

	return new ModelAndView("redirect:" + LIST + "?" + Constants.PARAM_ERROR);

}

private SemanticBean generateRecordBean(String id) {
	SemanticRml rml = semanticRmlService.recordByQuery(id);
	List<SemanticField> fields = semanticFieldService.getFieldsFromQuery(rml.getQuery(), "id");
	List<SemanticPrefix> prefixes = prefixService.getPrefixInQuery(rml.getQuery());
	SemanticField typeURI = null;

	for (SemanticField semanticField : fields) {
		if (semanticField.getField().equals((RDF_TYPE))) {
			typeURI = semanticField;
			break;
		}
	}

	SemanticBean bean = new SemanticBean();
	bean.setRml(rml);
	bean.setFields(fields);
	bean.setPrefixes(prefixes);
	bean.setSubjectURI(typeURI.getObjectReference());
	
	String selectedField=bean.getSubjectURI().substring(bean.getSubjectURI().indexOf("{")+1,bean.getSubjectURI().indexOf("}"));	
	bean.setSelectedFieldForURI(selectedField);

	if (typeURI != null) {
		fields.remove(typeURI);

		if (typeURI.getPredicate().contains(":")) {
			String[] split = typeURI.getPredicate().split(":");
			bean.setPrefixTypeURL(split[0]);
			bean.setTypeURL(split[1]);
		} else {
			bean.setTypeURL(typeURI.getPredicate());
		}
	}
	return bean;
}

@RequestMapping(value = SAVE, method = RequestMethod.POST)
public ModelAndView save(HttpServletRequest request, @RequestParam Map<String, String> allParams) {

	log.info(SAVE);

	String estadoOperacion = Constants.AUDITORIA_ESTADO_OK;

	String query = allParams.get("query");

	String prefixes = allParams.get("prefixSelectedHidden");

	String prefixType = allParams.get("prefixType");

	String urlType = allParams.get("typeURI");
	
	String fieldObjectURI=allParams.get("fieldObjectURI");

	try {

		Map<Integer, SemanticPrefix> prefixMap = prefixService.queryMap();
		List<SemanticPrefix> prefixesList = new ArrayList<SemanticPrefix>();
		List<SemanticRelPrefix> relPrefixesList = new ArrayList<SemanticRelPrefix>();

		// tratamiento de prefijos
		String[] splittedPrefixes = prefixes.split(";");
		for (String tempPrefix : splittedPrefixes) {
			SemanticRelPrefix rel = new SemanticRelPrefix(query, Integer.parseInt(tempPrefix) );
			relPrefixesList.add(rel);

			prefixesList.add(prefixMap.get(Integer.parseInt(tempPrefix)));
		}
		// tipo (rdfType)
		SemanticField rdfType = new SemanticField();
		rdfType.setQuery(query);
		rdfType.setField(RDF_TYPE);
		String objectURI = OBJECT_URI + query + "/{"+fieldObjectURI+"}";
		rdfType.setObjectReference(objectURI);
		rdfType.setObjectUri(true);

		String temporalURI = "";
		String prefixCode ="";
		
		if (prefixMap.containsKey(Integer.parseInt(prefixType)))
		{
			prefixCode=prefixMap.get(Integer.parseInt(prefixType)).getCode();
		}
		
		if (Util.validValue(prefixCode)) {
			temporalURI = prefixCode + ":" + urlType;
		} else {
			temporalURI = urlType;
		}
		rdfType.setPredicate(temporalURI);
		
		
		// semanticFieldService.add(rdfType);

		Map<String, String> mapFields = StartVariables.modelsForDynamicQuerys.get(query);
		List<String> stringFields=new ArrayList<String>( mapFields.keySet());
		
		List<SemanticField> semanticFields = readSemanticFields(ADD_OPERATION, query, stringFields, allParams, prefixMap );	

		SemanticRml rml = new SemanticRml();
		rml.setQuery(query);
		rml.setRml(generateRML(rdfType, semanticFields, prefixesList));
		// semanticRmlService.add(rml);

		semanticRmlService.addComplex(rml, rdfType, semanticFields, relPrefixesList);

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
		rmlLines.add("@prefix " + prefix.getCode() + ":   <" + prefix.getUrl() + "> .");
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
	}
  	rmlLines.add("");	
	
	List<SemanticField> blankNodeList = new ArrayList<SemanticField>();
	List<String> blankNodeIds = new ArrayList<String>();

	Map<String, String> relationTreeNodeId = new HashMap<String, String>();
	Map<String, String> relationTypeNodeId = new HashMap<String, String>();

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
				if (Util.validValue(field.getObjectReferenceType())) {
					objectType = "; rr:datatype " + field.getObjectReferenceType() + ";";
				}
				rmlLines.add("rr:objectMap [ rml:reference \"" + field.getObjectReference() + "\"" + objectType + " ]");
				rmlLines.add(" ];");
			}
		} else {
			blankNodeList.add(field);
			if (blankNodeIds.contains(field.getBlankNodeId()) == false) {
				blankNodeIds.add(field.getBlankNodeId());

				rmlLines.add("rr:predicateObjectMap [");
				rmlLines.add("   rr:predicate " + field.getBlankNodeProperty() + ";");

				String treeName = "#TriplesMap" + (blankNodeIds.size() + 1);

				rmlLines.add("   rr:objectMap [");
				rmlLines.add("    rr:parentTriplesMap <" + treeName + ">;");
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

	// Arboles para nodos en blanco
	if (blankNodeIds.size() > 0) {
		rmlLines.add("");
		rmlLines.add("");
		// Por cada identificador de un nodo en blanco tengo que:
		// a) Añadir la definicion del treemap y su sujeto
		// b) Buscar los campos que se corresponden con este nodo
		// c) Escribir su definición dentro del nodo
		// d) Acabar en . en lugar de ;
		for (String nodeId : blankNodeIds) {

			// a
			rmlLines.add("<" + relationTreeNodeId.get(nodeId) + "> a rr:TriplesMap;");
			rmlLines.add("  rml:logicalSource [");
			rmlLines.add("      rml:source \"nombreDinamico.csv\";");
			rmlLines.add("    rml:referenceFormulation ql:CSV");
			rmlLines.add(" ];");

			rmlLines.add("");
			rmlLines.add("rr:subjectMap [");
			rmlLines.add("  rr:termType rr:BlankNode;");
			if (Util.validValue(relationTypeNodeId.get(nodeId))) {
				rmlLines.add("  rr:class " + relationTypeNodeId.get(nodeId));
			}
			rmlLines.add(" ];");

			// b
			List<SemanticField> fieldsForThisNodeId = new ArrayList<SemanticField>();
			for (SemanticField field : blankNodeList) {
				if (field.getBlankNodeId().contentEquals(nodeId)) {
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
					if (Util.validValue(field.getObjectReferenceType())) {
						objectType = "; rr:datatype " + field.getObjectReferenceType() + ";";
					}
					rmlLines.add("rr:objectMap [ rml:reference \"" + field.getObjectReference() + "\"" + objectType + " ]");
					rmlLines.add(" ];");
				}
			}

			// d
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
  
  	for (int i=rmlLines.size()-1;i>0;i--)
  	{ 	
    	String lastLine = rmlLines.get(i);
    	if (!lastLine.contentEquals(""))
    	{
        	if (lastLine.endsWith(";")) {
        		lastLine = lastLine.substring(0, lastLine.lastIndexOf(";"));
        		lastLine += ".";
        		rmlLines.set(rmlLines.size() - 1, lastLine);
        		break;
        	}
    	}
  	}
  
	
}



@RequestMapping(value = EDIT, method = RequestMethod.GET)
public ModelAndView edit(@PathVariable String id) {

	log.info(EDIT);

	String estadoOperacion = Constants.AUDITORIA_ESTADO_OK;

	ModelAndView model = new ModelAndView();
	model.setViewName("semantic/edit");

	Commons.controllerCommon(model);

	try {
		SemanticBean bean = generateRecordBean(id);
		model.addObject("object", bean);

		List<SemanticRml> rmlList = semanticRmlService.list(-1, -1);

		Map<String, QueryD> queryMap = queryService.queryMap();

		// Borramos las queries que ya se están utilizando
		// pero la nuestra la dejamos
		for (SemanticRml semanticRml : rmlList) {
			if (semanticRml.getQuery().equals(id) == false) {
				queryMap.remove(semanticRml.getQuery());
			}
		}

		model.addObject("availableQueries", queryMap);

		// borramos de los prefijos disponibles los que estamos utilizando
		List<SemanticPrefix> prefixList = prefixService.list(-1, -1);
		prefixList.removeAll(bean.getPrefixes());

		model.addObject("prefixList", prefixList);

		model.addObject("xsdDataTypes", xsdDatatypes());

		/*
		 * necesito sacar todos los campos del modelo de esta query que están sin
		 * rellenar para pintarlos vacios
		 */

		Map<String, String> map= StartVariables.modelsForDynamicQuerys.get(id);
		
		if (map == null) {
			QueryD query = queryService.recordCode(id);

			if (query != null) {
				List<ParamD> queryParams = paramService.list(query.getCode());
					
				try {
					map = dynamicDataBaseService.typesQuery(query, queryParams);
					
					StartVariables.modelsForDynamicQuerys.put(id, map);

				} catch (Exception e) {
					log.error("Error extracting model from query", e);
				}
			}
		}
		
		model.addObject("allFields",map.keySet());
		
		Map<String, String> ourMap = new LinkedHashMap<String,String>(map);
		
		for(SemanticField f:bean.getFields())
		{
			ourMap.remove(f.getField());
		}
		
		List<String> emptyFields = new ArrayList<String>(ourMap.keySet());
		model.addObject("emptyFields",emptyFields);
		
		model.addObject("objectURI",OBJECT_URI);
		
		model.addObject("subjectURI",bean.getSubjectURI());

		Commons.auditoria(MODULO, OPERACION_PREEDICION, estadoOperacion);
		return model;
	} catch (Exception e) {
		log.error("Error updating object", e);
		estadoOperacion = Constants.AUDITORIA_ESTADO_ERROR;
	}

	Commons.auditoria(MODULO, OPERACION_PREEDICION, estadoOperacion);
	return new ModelAndView("redirect:" + LIST + "?" + Constants.PARAM_ERROR);

}

@RequestMapping(value = UPDATE, method = RequestMethod.POST)
public ModelAndView update(HttpServletRequest request, @RequestParam Map<String, String> allParams) {

	log.info(UPDATE);
	
	String query = allParams.get("query");
	
	String estadoOperacion = Constants.AUDITORIA_ESTADO_OK;

	String prefixes = allParams.get("prefixSelectedHidden");

	String prefixType = allParams.get("prefixType");

	String urlType = allParams.get("typeURI");
	
	String fieldObjectURI=allParams.get("fieldObjectURI");

	try {

		//Recogemos los datos del formulario (nuevos)
		
		Map<Integer, SemanticPrefix> prefixMap = prefixService.queryMap();
		List<SemanticPrefix> prefixesList = new ArrayList<SemanticPrefix>();
		List<SemanticRelPrefix> relPrefixesList = new ArrayList<SemanticRelPrefix>();

		// tratamiento de prefijos
		String[] splittedPrefixes = prefixes.split(";");
		for (String tempPrefix : splittedPrefixes) {
			SemanticRelPrefix rel = new SemanticRelPrefix(query, Integer.parseInt(tempPrefix));
			relPrefixesList.add(rel);

			prefixesList.add(prefixMap.get(Integer.parseInt(tempPrefix)));
		}
		// tipo (rdfType)
		SemanticField rdfType = new SemanticField();
		rdfType.setQuery(query);
		rdfType.setField(RDF_TYPE);
		String objectURI = OBJECT_URI + query + "/{"+fieldObjectURI+"}";
		rdfType.setObjectReference(objectURI);		
		rdfType.setObjectUri(true);

		String temporalURI = "";
		if (Util.validValue(prefixType)&&(prefixType.contentEquals("-1")==false)) {
			temporalURI = prefixType + ":" + urlType;
		} else {
			temporalURI = urlType;
		}
		rdfType.setPredicate(temporalURI);
		// semanticFieldService.add(rdfType);

		Map<String, String> mapFields = StartVariables.modelsForDynamicQuerys.get(query);
		
		List<String> stringFields=new ArrayList<String>( mapFields.keySet());

		List<SemanticField> semanticFields = readSemanticFields(UPDATE_OPERATION, query, stringFields, allParams, prefixMap);	

		SemanticRml rml = new SemanticRml();
		rml.setQuery(query);
		rml.setRml(generateRML(rdfType, semanticFields, prefixesList));
			
		
		//Recogemos los datos de bbdd (antiguos)

		SemanticRml oldRmlObj = semanticRmlService.recordByQuery(query);

		List<SemanticField> oldFieldsFromQuery = semanticFieldService.getFieldsFromQuery(query, "id");
		SemanticField oldType = null;
		for (SemanticField semanticField : oldFieldsFromQuery) {
			if (semanticField.getField().equals((RDF_TYPE))) {
				oldType = semanticField;
				break;
			}
		}
		if (oldType != null) {
			oldFieldsFromQuery.remove(oldType);
		}

		List<SemanticRelPrefix> oldPrefixFromQuery = prefixRelService.getPrefixRelFromQuery(query);

		semanticRmlService.updateComplex(rml, rdfType, semanticFields, relPrefixesList, oldRmlObj, oldType, oldFieldsFromQuery, oldPrefixFromQuery);
		
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
		SemanticBean bean = generateRecordBean(id);
		model.addObject("object", bean);

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

		SemanticRml rmlObj = semanticRmlService.recordByQuery(id);

		List<SemanticField> fieldsFromQuery = semanticFieldService.getFieldsFromQuery(id, "id");
		SemanticField type = null;
		for (SemanticField semanticField : fieldsFromQuery) {
			if (semanticField.getField().equals((RDF_TYPE))) {
				type = semanticField;
				break;
			}
		}
		if (type != null) {
			fieldsFromQuery.remove(type);
		}

		List<SemanticRelPrefix> prefixFromQuery = prefixRelService.getPrefixRelFromQuery(id);

		semanticRmlService.removeComplex(rmlObj, type, fieldsFromQuery, prefixFromQuery);

		Commons.auditoria(MODULO, OPERACION_BORRADO, estadoOperacion);
		return new ModelAndView("redirect:" + LIST + "?" + Constants.PARAM_DELETED);
	} catch (Exception e) {
		log.error("Error deleting object", e);
		estadoOperacion = Constants.AUDITORIA_ESTADO_ERROR;
	}

	Commons.auditoria(MODULO, OPERACION_BORRADO, estadoOperacion);
	return new ModelAndView("redirect:" + LIST + "?" + Constants.PARAM_ERROR);

}


private List<SemanticField> readSemanticFields(String type, String query, List<String> stringFields, Map<String, String> allParams, Map<Integer, SemanticPrefix> prefixMap) {
	List<SemanticField> semanticFields = new ArrayList<SemanticField>();

	for (String key : stringFields) {

		String fieldName = allParams.get("fieldNameCampoAdded_" + key);
		String predicatePrefix = allParams.get("prefixTypeCampoAdded_" + key);
		String predicateURL = allParams.get("urlCampoAdded_" + key);
		String objectType = allParams.get("objectTypeCampoAdded_" + key);
		String objectValue = allParams.get("objectValueCampoAdded_" + key);

		String objectValueURL = allParams.get("objectValueURLCampoAdded_" + key);

		// tipo de campo si es simple
		String simpleType = allParams.get("fieldTypeCampoAdded_" + key);
		// información del nodo si es nodo en blanco
		String blankNodeId = allParams.get("nodeIdCampoAdded_" + key);
		String blankNodeRdfTypePrefix = allParams.get("prefixNodePredicateTypeCampoAdded_" + key);
		String blankNodeRdfTypeURL = allParams.get("urlNodePredicateTypeCampoAdded_" + key);
		String blankNodePredicatePrefix = allParams.get("prefixNodePredicateCampoAdded_" + key);
		String blankNodePredicateURL = allParams.get("urlNodePredicateCampoAdded_" + key);

		if (Util.validValue(predicateURL)) {
			SemanticField temporalType = new SemanticField();
			temporalType.setQuery(query);
			temporalType.setField(fieldName);
			temporalType.setObjectReference(objectValue);
			temporalType.setObjectType(objectType);

			String temporalURI = "";
			String prefixCode = "";
			if (type.equals(ADD_OPERATION))
			{
				if (prefixMap.containsKey((Integer.parseInt(predicatePrefix))))
				{
					prefixCode=prefixMap.get(Integer.parseInt(predicatePrefix)).getCode();
				}	
			}else if (type.equals(UPDATE_OPERATION))
			{
				if (Util.isNumeric(predicatePrefix))
				{
					SemanticPrefix semanticPrefix = prefixMap.get(Integer.parseInt(predicatePrefix));
					if (semanticPrefix!=null)
					{
						prefixCode=semanticPrefix.getCode();
					}else {
						log.error("Prefijo no encontrado: "+predicatePrefix);
					}
				}else {
					prefixCode=predicatePrefix;
				}
				
			}
			if (Util.validValue(prefixCode)) {
				temporalURI = prefixCode + ":" + predicateURL;
			} else {
				temporalURI = predicateURL;
			}
			temporalType.setPredicate(temporalURI);

			if (objectType.equals("url")) {
				temporalType.setObjectUri(true);
				temporalType.setObjectReference(objectValueURL);
			} else if (objectType.equals("simple")) {
				temporalType.setObjectUri(false);

				if (Util.validValue(simpleType) && (simpleType.equals("string") == false)) {
					temporalType.setObjectReferenceType(simpleType);
				}

			} else if (objectType.equals("blank")) {
				temporalType.setObjectUri(false);

				if (Util.validValue(simpleType) && (simpleType.equals("string") == false)) {
					temporalType.setObjectReferenceType(simpleType);
				}

				temporalType.setBlankNodeId(blankNodeId);

				temporalURI = "";
				if (Util.validValue(blankNodeRdfTypePrefix)) {
					temporalURI = blankNodeRdfTypePrefix + ":" + blankNodeRdfTypeURL;
				} else {
					temporalURI = blankNodeRdfTypeURL;
				}
				temporalType.setBlankNodeType(temporalURI);

				temporalURI = "";
				if (Util.validValue(blankNodePredicatePrefix)) {
					temporalURI = blankNodePredicatePrefix + ":" + blankNodePredicateURL;
				} else {
					temporalURI = blankNodePredicateURL;
				}
				temporalType.setBlankNodeProperty(temporalURI);
			}

			semanticFields.add(temporalType);

		}
	}

	return semanticFields;

}

}