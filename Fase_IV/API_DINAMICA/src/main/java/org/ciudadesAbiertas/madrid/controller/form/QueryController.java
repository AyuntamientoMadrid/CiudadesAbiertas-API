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
import org.ciudadesAbiertas.madrid.bean.QueryBean;
import org.ciudadesAbiertas.madrid.config.multipe.MultipleDataSource;
import org.ciudadesAbiertas.madrid.controller.Commons;
import org.ciudadesAbiertas.madrid.model.dynamic.ParamD;
import org.ciudadesAbiertas.madrid.model.dynamic.QueryD;
import org.ciudadesAbiertas.madrid.model.dynamic.SwaggerDefinition;
import org.ciudadesAbiertas.madrid.service.dynamic.DynamicService;
import org.ciudadesAbiertas.madrid.service.dynamic.ParamService;
import org.ciudadesAbiertas.madrid.service.dynamic.QueryService;
import org.ciudadesAbiertas.madrid.service.dynamic.SwaggerDefinitionService;
import org.ciudadesAbiertas.madrid.utils.ErrorBean;
import org.ciudadesAbiertas.madrid.utils.StartVariables;
import org.ciudadesAbiertas.madrid.utils.Util;
import org.ciudadesAbiertas.madrid.utils.constants.Constants;
import org.json.simple.JSONObject;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class QueryController {

    private static final Logger log = LoggerFactory.getLogger(QueryController.class);

    @Autowired
    protected Environment env;

    @Autowired
    private QueryService queryService;

    @Autowired
    private ParamService paramService;

    @Autowired
    private SwaggerDefinitionService swaggerDefinitionService;

    @Autowired
    private DynamicService dynamicDataBaseService;

    @Autowired
    private MultipleDataSource multipleDataSource;

    @Autowired
    private SwaggerDefinitionService swaggerService;

    private static final String PATH = "/query";
    private static final String PARAM_ID = "{id}";

    public static final String LIST = PATH;
    public static final String RECORD = PATH + "/" + PARAM_ID;
    public static final String ADD = PATH + "/add";
    public static final String SAVE = PATH + "/save";
    public static final String EDIT = PATH + "/edit/" + PARAM_ID;
    public static final String DELETE = PATH + "/delete/" + PARAM_ID;
    public static final String UPDATE = PATH + "/update/" + PARAM_ID;

    public static final String QUERY_FIELDS = PATH+"/fields/" + PARAM_ID;

    public static final String MODULO = "Consultas SQL";
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
	model.setViewName("query/list");

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

	    int total = queryService.listRowCount();

	    List<QueryD> list = queryService.list(firstResult, StartVariables.defaultPageSize);

	    List<QueryBean> listBean = new ArrayList<QueryBean>();
	    Map<String, String> exampleParams = new HashMap<String, String>();
	    boolean hayErrores = false;
	    if (list != null && !list.isEmpty()) {
		for (QueryD queryD : list) {
		    QueryBean query = new QueryBean(queryD);
		    if (!hayErrores) {
			hayErrores = query.getErrorFileConfig();
		    }

		    if (query.getQueryD().getTexto().contains(StartVariables.geometry_field) || (query.getQueryD().getTexto().contains(StartVariables.xETRS89_field) && query.getQueryD().getTexto().contains(StartVariables.yETRS89_field))) {
			query.setGeo(true);
		    }
		    listBean.add(query);
		    // Genero los parametros por defecto para testear las queries si es necesario
		    List<ParamD> params = paramService.list(queryD.getCode());
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
			exampleParams.put(queryD.getCode(), param);
		    }

		}
	    }

	    if (hayErrores) {
		ErrorBean errorObject = new ErrorBean();
		errorObject.setName("Error ficheros de configuración");
		errorObject.setDescription("Hay errores en alguno de los ficheros de configuración");
		model.addObject("errorObject", errorObject);
	    }

	    // model.addObject("list", list);
	    model.addObject("list", listBean);

	    // Añadimos los parametros al modelo
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

    @RequestMapping(value = RECORD, method = RequestMethod.GET)
    public ModelAndView record(@PathVariable String id) {

	log.info(RECORD);

	String estadoOperacion = Constants.AUDITORIA_ESTADO_OK;

	ModelAndView model = new ModelAndView();
	model.setViewName("query/record");

	Commons.controllerCommon(model);

	try {
	    QueryD entidad = queryService.record(id);
	    model.addObject("object", entidad);
	    setDatabaseSelected(model, entidad);

	    List<ParamD> paramList = paramService.list(entidad.getCode());
	    model.addObject("paramList", paramList);
	    Commons.auditoria(MODULO, OPERACION_FICHA, estadoOperacion);
	    return model;
	} catch (Exception e) {
	    log.error("Error reading object", e);
	    estadoOperacion = Constants.AUDITORIA_ESTADO_ERROR;
	}

	Commons.auditoria(MODULO, OPERACION_FICHA, estadoOperacion);

	return new ModelAndView("redirect:" + LIST + "?" + Constants.PARAM_ERROR);

    }

    @RequestMapping(value = ADD, method = RequestMethod.GET)
    public ModelAndView add(HttpServletRequest request) {

	String estadoOperacion = Constants.AUDITORIA_ESTADO_OK;

	log.info(ADD);

	ModelAndView model = new ModelAndView();
	model.setViewName("query/add");

	try {
	    List<String> databases = getDatabases();
	    model.addObject("databases", databases);

	    List<SwaggerDefinition> definitionList = swaggerService.list(0, 0);
	    model.addObject("definitions", definitionList);

	    Commons.controllerCommon(model);

	} catch (Exception e) {
	    log.error("Error adding object", e);
	    estadoOperacion = Constants.AUDITORIA_ESTADO_ERROR;
	}

	Commons.auditoria(MODULO, OPERACION_PREALTA, estadoOperacion);

	return model;

    }

    private List<String> getDatabases() {
	List<String> databases = new ArrayList<String>();
	databases.add(Constants.BASE_DE_DATOS_POR_DEFECTO);
	// Configuración basica
	for (String key : multipleDataSource.getUserNames().keySet()) {
	    log.debug("basic: " + key);
	    // if (!StartVariables.errorDatabaseTypes.containsKey(key)) {
	    databases.add(key);
	    // }
	}
	// Configuracion con JNDI
	for (String key : multipleDataSource.getJndis().keySet()) {
	    log.debug("jndi: " + key);
	    // if (!StartVariables.errorDatabaseTypes.containsKey(key)) {
	    databases.add(key);
	    // }
	}
	log.debug("databases: " + databases.size());
	return databases;
    }

    @RequestMapping(value = SAVE, method = RequestMethod.POST)
    public ModelAndView save(HttpServletRequest request, @ModelAttribute("queryForm") QueryD entidad) {

	log.info(SAVE);

	String estadoOperacion = Constants.AUDITORIA_ESTADO_OK;

	Enumeration<String> parameterNames = request.getParameterNames();

	List<ParamD> queryParameters = extractParamsFromRequest(request, parameterNames);

	try {
	    checkDefaultDB(entidad);

	    entidad.setTexto(Util.formatSQL(entidad.getTexto()));

	    if (entidad.getDefinition() != null && entidad.getDefinition().equals("-1")) {
		entidad.setDefinition("");
	    }

	    String code = entidad.getCode().toLowerCase();
	    code = StringUtils.replace(code, " ", "");

	    entidad.setCode(code);

	    // Añado o actualizo el modelo
	    Map<String, String> modeloQuery = dynamicDataBaseService.typesQuery(entidad, queryParameters);
	    StartVariables.modelsForDynamicQuerys.put(entidad.getCode(), modeloQuery);

	    // Si no hay definición la generamos y la asignamos
	    if (Util.validValue(entidad.getDefinition()) == false) {

		SwaggerDefinition find = swaggerDefinitionService.find(StringUtils.capitalize(entidad.getCode()));

		if (find == null) {
		    // guardamos en el modelo en BBDD
		    SwaggerDefinition def = new SwaggerDefinition();
		    def.setCode(StringUtils.capitalize(entidad.getCode()));
		    def.setDescription("Descripción " + entidad.getCode());
		    JSONObject modelJSON = new JSONObject();
		    modelJSON.put("type", "object");
		    modelJSON.put("title", StringUtils.capitalize(entidad.getCode()));
		    JSONObject properties = new JSONObject();

		    for (String key : modeloQuery.keySet()) {
			JSONObject actualProperty = new JSONObject();
			actualProperty.put("type", modeloQuery.get(key));
			actualProperty.put("description", "");
			actualProperty.put("example", "");
			properties.put(key, actualProperty);
		    }

		    modelJSON.put("properties", properties);
		    def.setText(Util.jsonPrettyPrint(modelJSON.toJSONString()));
		    swaggerDefinitionService.add(def);
		    // Actualizamos la definición
		    entidad.setDefinition(StringUtils.capitalize(entidad.getCode()));
		}
	    }

	    queryService.add(entidad, queryParameters);

	    Commons.auditoria(MODULO, OPERACION_ALTA, estadoOperacion);
	    return new ModelAndView("redirect:" + LIST + "?" + Constants.PARAM_ADDED);

	} catch (Exception e) {
	    log.error("Error saving object", e);
	    estadoOperacion = Constants.AUDITORIA_ESTADO_ERROR;
	}

	Commons.auditoria(MODULO, OPERACION_ALTA, estadoOperacion);
	return new ModelAndView("redirect:" + LIST + "?" + Constants.PARAM_ERROR);

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
	model.setViewName("query/edit");

	Commons.controllerCommon(model);

	List<String> databases = getDatabases();
	model.addObject("databases", databases);

	try {
	    QueryD entidad = queryService.record(id);
	    model.addObject("object", entidad);
	    setDatabaseSelected(model, entidad);

	    List<ParamD> paramList = paramService.list(entidad.getCode());
	    model.addObject("paramList", paramList);

	    List<SwaggerDefinition> definitionList = swaggerService.list(0, 0);
	    model.addObject("definitions", definitionList);

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

	checkDefaultDB(queryForm);
	Enumeration<String> parameterNames = request.getParameterNames();
	List<ParamD> newParameters = extractParamsFromRequest(request, parameterNames);

	try {
	    List<ParamD> oldParamList = paramService.list(id);
	    QueryD oldQuery = queryService.record(id);

	    queryForm.setTexto(Util.formatSQL(queryForm.getTexto()));

	    if (queryForm.getDefinition() != null && queryForm.getDefinition().equals("-1")) {
		queryForm.setDefinition("");
	    }

	    String code = queryForm.getCode().toLowerCase();
	    code = StringUtils.replace(code, " ", "");
	    queryForm.setCode(code);

	    queryService.update(queryForm, oldQuery, newParameters, oldParamList);

	    // Actualizo el modelo
	    Map<String, String> modeloQuery = dynamicDataBaseService.typesQuery(queryForm, newParameters);
	    StartVariables.modelsForDynamicQuerys.put(queryForm.getCode(), modeloQuery);
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
	model.setViewName("query/delete");

	Commons.controllerCommon(model);

	try {
	    QueryD entidad = queryService.record(id);
	    model.addObject("object", entidad);
	    setDatabaseSelected(model, entidad);

	    List<ParamD> paramList = paramService.list(entidad.getCode());
	    model.addObject("paramList", paramList);
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
	    List<ParamD> paramList = paramService.list(id);

	    QueryD recordToDelete = queryService.record(id);

	    queryService.delete(recordToDelete, paramList);
	    Commons.auditoria(MODULO, OPERACION_BORRADO, estadoOperacion);
	    return new ModelAndView("redirect:" + LIST + "?" + Constants.PARAM_DELETED);
	} catch (Exception e) {
	    log.error("Error deleting object", e);
	    estadoOperacion = Constants.AUDITORIA_ESTADO_ERROR;
	}

	Commons.auditoria(MODULO, OPERACION_BORRADO, estadoOperacion);
	return new ModelAndView("redirect:" + LIST + "?" + Constants.PARAM_ERROR);

    }

    private void checkDefaultDB(QueryD entidad) {
	if (entidad.getDatabase().contentEquals(Constants.BASE_DE_DATOS_POR_DEFECTO)) {
	    entidad.setDatabase(Constants.DEFAULT_DATABASE);
	}
    }

    @RequestMapping(value = { QUERY_FIELDS }, method = { RequestMethod.GET })
    public @ResponseBody Map<String, String> queryFields(@PathVariable String id) {

	log.info("[queryFields]" + id);

	Map<String, String> map = StartVariables.modelsForDynamicQuerys.get(id);

	if (map == null) {
	    QueryD query = queryService.record(id);

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

	if (map == null) {
	    map = new LinkedHashMap<String, String>();
	    return map;
	}

	return map;

    }

}