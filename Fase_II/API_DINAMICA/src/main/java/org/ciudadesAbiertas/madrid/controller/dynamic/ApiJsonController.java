package org.ciudadesAbiertas.madrid.controller.dynamic;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.ciudadesAbiertas.madrid.model.dynamic.ParamD;
import org.ciudadesAbiertas.madrid.model.dynamic.QueryD;
import org.ciudadesAbiertas.madrid.model.dynamic.SwaggerDefinition;
import org.ciudadesAbiertas.madrid.service.dynamic.DynamicService;
import org.ciudadesAbiertas.madrid.service.dynamic.ParamService;
import org.ciudadesAbiertas.madrid.service.dynamic.QueryService;
import org.ciudadesAbiertas.madrid.service.dynamic.SwaggerDefinitionService;
import org.ciudadesAbiertas.madrid.utils.StartVariables;
import org.ciudadesAbiertas.madrid.utils.Util;
import org.ciudadesAbiertas.madrid.utils.constants.Constants;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiJsonController {

    public static final String path = "/API/api.json";

    private static final Logger log = LoggerFactory.getLogger(ApiJsonController.class);

    @Autowired
    private SwaggerDefinitionService swaggerDefinitionService;

    @Autowired
    private QueryService queryService;

    @Autowired
    private ParamService paramService;

    @Autowired
    private DynamicService dynamicDataBaseService;

    @Autowired
    private Environment env;

    JSONParser parser = new JSONParser();

    @SuppressWarnings("unchecked")
    @RequestMapping(path)
    public @ResponseBody String all() {

	String unionPoint = "\"paths\": {";

	String originalJSON = "";
	try {
	    Resource resource = new ClassPathResource("api.json");
	    File file = resource.getFile();
	    originalJSON = FileUtils.readFileToString(file, "utf8");
	} catch (IOException e1) {
	    log.error("Original json not accesible", e1);
	}

	JSONObject paths = new JSONObject();

	JSONObject definitions = new JSONObject();

	JSONObject definitiveDefinitions = new JSONObject();

	List<QueryD> dynamicQueries = queryService.list(0, 0);

	List<SwaggerDefinition> definitionsFromBBDD = swaggerDefinitionService.list(0, 0);

	Map<String, String> mapDefinitionsFromBBDD = new HashMap<String, String>();

	for (SwaggerDefinition swaggerDefinition : definitionsFromBBDD) {
	    mapDefinitionsFromBBDD.put(swaggerDefinition.getCode(), swaggerDefinition.getText());
	}

	int dynamicApiCalls = dynamicQueries.size();

	if (dynamicApiCalls == 0)
	    return originalJSON;

	for (QueryD query : dynamicQueries) {

	    List<ParamD> queryParams = paramService.list(query.getCode());

	    Map<String, String> modeloQuery = new HashMap<String, String>();
	    Map<String, String> modeloDescriptionQuery = new HashMap<String, String>();
	    if (mapDefinitionsFromBBDD.get(query.getDefinition()) != null) {
		JSONObject definition = Util.stringToJSONObject(mapDefinitionsFromBBDD.get(query.getDefinition()));

		JSONObject defMap = (JSONObject) definition.get("properties");
		Map<String, String> translatedDefMap = new HashMap<String, String>();
		if (defMap != null) {
		    Set<String> keySet = defMap.keySet();
		    for (String s : keySet) {
			Map<String, String> tempMap = (Map<String, String>) defMap.get(s);
			translatedDefMap.put(s, convertJavaTypesToSwaggerTypes(tempMap.get("type")));
			modeloDescriptionQuery.put(s, tempMap.get("description"));
		    }
		    modeloQuery = translatedDefMap;
		}
	    } else {
		modeloQuery = StartVariables.modelsForDynamicQuerys.get(query.getCode());
	    }

	    if (modeloQuery == null || modeloQuery.size() == 0) {
		try {
		    modeloQuery = dynamicDataBaseService.typesQuery(query, queryParams);
		} catch (Exception e) {
		    log.error("Error extracting model from query", e);
		}
		StartVariables.modelsForDynamicQuerys.put(query.getCode(), modeloQuery);
	    }

	    JSONObject queryJ = toJSONObject(query, queryParams, modeloQuery, modeloDescriptionQuery);
	    paths.put("/API/query/" + query.getCode(), queryJ);

	}

	log.info("End of JSON");

	String pathsJSON = paths.toJSONString().trim();
	pathsJSON = pathsJSON.substring(1, pathsJSON.length() - 1);
	String finalJSON = originalJSON.replace(unionPoint, unionPoint + pathsJSON + ",");
	finalJSON = finalJSON.replace("\\/", "/");

	// Now we add the definitions

	JSONObject originalDefinitions = null;
	JSONObject result = null;
	try {
	    originalDefinitions = (JSONObject) parser.parse(finalJSON);
	    originalDefinitions = (JSONObject) originalDefinitions.get("definitions");
	    result = (JSONObject) originalDefinitions.get("Result");
	} catch (ParseException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}

	if (originalDefinitions != null) {
	    definitions = originalDefinitions;

	    JSONObject resultC = null;
	    for (SwaggerDefinition swagerDef : definitionsFromBBDD) {

		JSONObject queryJ = null;
		try {

		    queryJ = (JSONObject) parser.parse(swagerDef.getText());
		    resultC = (JSONObject) parser.parse(result.toString());
		    JSONObject properties = (JSONObject) resultC.get("properties");
		    JSONObject records = (JSONObject) properties.get("records");
		    JSONObject link = new JSONObject();

		    link.put("$ref", "#/definitions/" + swagerDef.getCode());
		    records.put("items", link);

		    resultC.put("title", "Result" + swagerDef.getCode());

		} catch (ParseException e) {
		    log.error("Error parsing data from this definition:" + swagerDef.getCode());
		}

		if (queryJ != null) {
		    definitiveDefinitions.put("Result" + swagerDef.getCode(), resultC);
		    definitiveDefinitions.put(swagerDef.getCode(), queryJ);
		}

	    }

	    JSONObject finalJSONObj;
	    try {
		finalJSONObj = (JSONObject) parser.parse(finalJSON);
		finalJSONObj.remove("definitions");
		finalJSONObj.put("definitions", definitiveDefinitions);
		finalJSON = finalJSONObj.toJSONString();
	    } catch (ParseException e) {
		log.error("Error adding definitions in the final JSON", e);
	    }

	}

	return finalJSON;

    }

    private String convertJavaTypesToSwaggerTypes(String type) {

	if (type.toLowerCase().equals(Constants.JAVA_LANG_STRING.toLowerCase()) || type.toLowerCase().equals("string")) {
	    return "string";
	} else if (type.toLowerCase().equals(Constants.JAVA_SQL_TIMESTAMP.toLowerCase())) {
	    return "string";
	} else if (type.toLowerCase().equals(Constants.JAVA_LANG_INTEGER.toLowerCase()) || type.toLowerCase().equals("integer")) {
	    return "integer";
	} else if (type.toLowerCase().equals(Constants.JAVA_LANG_BOOLEAN.toLowerCase()) || type.toLowerCase().equals("boolean")) {
	    return "boolean";
	} else if (type.toLowerCase().equals(Constants.JAVA_SQL_DATE.toLowerCase())) {
	    return "string";
	} else if (type.toLowerCase().equals(Constants.JAVA_SQL_TIME.toLowerCase())) {
	    return "string";
	} else if (type.toLowerCase().equals(Constants.JAVA_LANG_SHORT.toLowerCase())) {
	    return "integer";
	} else if (type.toLowerCase().equals(Constants.JAVA_LANG_LONG.toLowerCase())) {
	    return "integer";
	} else if (type.toLowerCase().equals(Constants.JAVA_LANG_LONG.toLowerCase())) {
	    return "integer";
	} else if (type.toLowerCase().equals(Constants.JAVA_MATH_BIGDECIMAL.toLowerCase())) {
	    return "number";
	} else if (type.toLowerCase().equals(Constants.JAVA_LANG_FLOAT.toLowerCase())) {
	    return "number";
	} else if (type.toLowerCase().equals(Constants.JAVA_LANG_DOUBLE.toLowerCase())) {
	    return "number";
	} else {
	    log.error("Tipo sin procesar: " + type);
	    return "string";
	}
    }

    @SuppressWarnings("unchecked")
    private void erroresGenericos(JSONObject responses) {

	JSONObject description = new JSONObject();
	description.put("description", "Estado no válido");
	responses.put("400", description);

	description = new JSONObject();
	description.put("description", "Límite excedido");
	responses.put("402", description);

	description = new JSONObject();
	description.put("description", "Acceso denegado");
	responses.put("403", description);

	description = new JSONObject();
	description.put("description", "No aceptable");
	responses.put("406", description);
    }

    @SuppressWarnings("unchecked")
    private JSONObject toJSONObject(QueryD query, List<ParamD> queryParams, Map<String, String> model, Map<String, String> descriptionMap) {

	log.info("toJSONObject("+query.getCode()+")");

	JSONObject object = new JSONObject();

	JSONObject get = new JSONObject();

	JSONArray tags = new JSONArray();

	JSONArray produces = new JSONArray();
	produces.add(Constants.mimeJSON);
	produces.add(Constants.mimeXML);
	produces.add(Constants.mimeCSV);

	JSONObject responses = new JSONObject();
	JSONObject description = new JSONObject();
	description.put("description", "Operación correcta");
	JSONObject schema = new JSONObject();
	if (query.getDefinition() != null) {
	    schema.put("$ref", "#/definitions/" + query.getDefinition());
	} else {
	    schema.put("$ref", "#/definitions/objects");
	}
	description.put("schema", schema);
	responses.put("200", description);

	erroresGenericos(responses);

	JSONArray parameters = new JSONArray();

	JSONObject page = new JSONObject();
	page.put("name", "page");
	page.put("in", "query");
	page.put("description", "Página actual");
	page.put("required", false);
	page.put("type", "integer");

	JSONObject pageSize = new JSONObject();
	pageSize.put("name", "pageSize");
	pageSize.put("in", "query");
	pageSize.put("description", "Número de resultados por página");
	pageSize.put("required", false);
	pageSize.put("type", "integer");

	JSONObject order = new JSONObject();
	order.put("name", "orderBy");
	order.put("in", "query");
	order.put("description", "Campo por el que ordenar los datos");
	order.put("required", false);
	order.put("type", "string");

	parameters.add(page);
	parameters.add(pageSize);
	parameters.add(order);

	for (ParamD param : queryParams) {
	    JSONObject tempParam = new JSONObject();
	    tempParam.put("name", param.getName());
	    tempParam.put("in", "query");
	    if ((param.getDescription() != null) && (!param.getDescription().equals(""))) {
		tempParam.put("description", param.getDescription());
	    }
	    if ((param.getExample() != null) && (!param.getExample().equals("")) && (param.getExample().contains(";"))) {
		String enumerate = "[";
		String[] splits = param.getExample().split(";");
		JSONArray splitsJSON = new JSONArray();
		for (String split : splits) {
		    splitsJSON.add(split);
		}
		tempParam.put("enum", splitsJSON);
		tempParam.put("default", splits[0]);
	    } else if ((param.getExample() != null) && (!param.getExample().equals(""))) {
		tempParam.put("default", param.getExample());
	    }

	    tempParam.put("required", true);
	    if (param.getType().equals(Constants.TEXT))
		tempParam.put("type", "string");
	    else if (param.getType().equals(Constants.NUMBER))
		tempParam.put("type", "integer");
	    else if (param.getType().equals(Constants.DATE))
		tempParam.put("type", "string");
	
		tempParam.put("required",false);

	    parameters.add(tempParam);
	}

	// Añadimos los parametros del modelo
	for (Map.Entry<String, String> param : model.entrySet()) {

	    JSONObject tempParam = new JSONObject();
	    tempParam.put("name", param.getKey());
	    tempParam.put("in", "query");
	    tempParam.put("required", false);

	    if ((descriptionMap != null) && (descriptionMap.get(param.getKey()) != null)) {
		tempParam.put("description", descriptionMap.get(param.getKey()));
	    }

	    tempParam.put("type", convertJavaTypesToSwaggerTypes(param.getValue()));

	    parameters.add(tempParam);
	}

	String[] tagSplited = query.getTags().split(",");

	for (String tag : tagSplited)
	    tags.add(tag.trim());

	get.put("tags", tags);
	get.put("summary", query.getSummary());
	get.put("operationId", "list" + query.getCode());
	get.put("produces", produces);
	get.put("responses", responses);
	get.put("parameters", parameters);

	object.put("get", get);

	return object;
    }

}