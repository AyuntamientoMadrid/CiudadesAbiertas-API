package org.ciudadesAbiertas.madrid.controller.form;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.ciudadesAbiertas.madrid.bean.QueryBean;
import org.ciudadesAbiertas.madrid.config.multipe.MultipleDataSource;
import org.ciudadesAbiertas.madrid.controller.Commons;
import org.ciudadesAbiertas.madrid.model.dynamic.QueryConfD;
import org.ciudadesAbiertas.madrid.model.dynamic.QueryD;
import org.ciudadesAbiertas.madrid.service.dynamic.ProcessService;
import org.ciudadesAbiertas.madrid.service.dynamic.QueryConfService;
import org.ciudadesAbiertas.madrid.service.dynamic.QueryService;
import org.ciudadesAbiertas.madrid.utils.ErrorBean;
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
public class QueryController {

    private static final Logger log = LoggerFactory.getLogger(QueryController.class);

    @Autowired
    protected Environment env;

    @Autowired
    private QueryService queryService;

    @Autowired
    private QueryConfService queryConfService;

    @Autowired
    private MultipleDataSource multipleDataSource;
    
    @Autowired
    private ProcessService processService;

    public static final String PATH = "/query";
    private static final String PARAM_ID = "{id}";

    public static final String LIST = PATH;
    public static final String RECORD = PATH + "/" + PARAM_ID;
    public static final String ADD = PATH + "/add";
    public static final String SAVE = PATH + "/save";
    public static final String EDIT = PATH + "/edit/" + PARAM_ID;
    public static final String DELETE = PATH + "/delete/" + PARAM_ID;
    public static final String UPDATE = PATH + "/update/" + PARAM_ID;

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

	int firstResult = 0;
	try {
	    firstResult = Integer.parseInt(page) - 1;
	} catch (Exception e) {
	    log.error("Wrong page number:" + page);
	}

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

		    QueryConfD queryConf = queryConfService.record(query.getQueryD().getCode());
		    query.setQueryConfD(queryConf);

		    if (queryConf.getMode().equals(QueryConfD.MODE_AUTO)) {
			Date next = Util.calculateNextExecution(queryConf.getCron());
			if (next != null) {
			    query.setNextExecution(Util.dateTimeFormatter.format(next));
			} else {
			    query.setNextExecution("");
			}
		    } else {
			query.setNextExecution("");
		    }

		    listBean.add(query);
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

	log.info(RECORD + " " + id);

	String estadoOperacion = Constants.AUDITORIA_ESTADO_OK;

	ModelAndView model = new ModelAndView();
	model.setViewName("query/record");

	Commons.controllerCommon(model);

	cronObjectsToModel(model);

	try {
	    QueryD entidad = queryService.record(id);
	    model.addObject("object", entidad);
	    setDatabaseSelected(model, entidad);

	    QueryConfD queryConf = queryConfService.record(entidad.getCode());
	    
	    String path = queryConf.getPath();
	    path = path.replace(File.separator, "\\");
	    queryConf.setPath(path);
	    
	    model.addObject("configuration", queryConf);
	    Commons.auditoria(MODULO, OPERACION_FICHA, estadoOperacion);

	    timeValuesToModel(model, queryConf);

	    return model;
	} catch (Exception e) {
	    log.error("Error reading object", e);
	    estadoOperacion = Constants.AUDITORIA_ESTADO_ERROR;
	}

	Commons.auditoria(MODULO, OPERACION_FICHA, estadoOperacion);

	return new ModelAndView("redirect:" + LIST + "?" + Constants.PARAM_ERROR);

    }

    private void timeValuesToModel(ModelAndView model, QueryConfD conf) {
	String minutes = conf.getMinute();
	if (minutes != null) {
	    List<String> minutesSelected = Arrays.asList(minutes.split(","));
	    model.addObject("minutesSel", minutesSelected);
	} else {
	    model.addObject("minutesSel", "*");
	}

	String hours = conf.getHour();
	if (hours != null) {
	    List<String> hoursSelected = Arrays.asList(hours.split(","));
	    model.addObject("hoursSel", hoursSelected);
	} else {
	    model.addObject("hoursSel", "*");
	}

	String daysM = conf.getDayM();
	if (daysM != null) {
	    List<String> daysMSelected = Arrays.asList(daysM.split(","));
	    model.addObject("daysMSel", daysMSelected);
	} else {
	    model.addObject("daysMSel", "*");
	}

	String daysW = conf.getDayW();
	if (daysW != null) {
	    List<String> daysWSelected = Arrays.asList(daysW.split(","));
	    model.addObject("daysWSel", daysWSelected);
	} else {
	    model.addObject("daysWSel", "*");
	}

	String month = conf.getMonth();
	if (month != null) {
	    List<String> monthSelected = Arrays.asList(month.split(","));
	    model.addObject("monthSel", monthSelected);
	} else {
	    model.addObject("monthSel", "*");
	}
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

	    Commons.controllerCommon(model);

	    cronObjectsToModel(model);

	    String defaultValue = "*";
	    List<String> defaultList = new ArrayList<String>();
	    defaultList.add(defaultValue);

	    model.addObject("minutesSel", defaultList);
	    model.addObject("hoursSel", defaultList);
	    model.addObject("daysMSel", defaultList);
	    model.addObject("daysWSel", defaultList);
	    model.addObject("monthSel", defaultList);

	} catch (Exception e) {
	    log.error("Error updating object", e);
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
	    // if (!StartVariables.errorDatabaseTypes.containsKey(key)) {
	    databases.add(key);
	    // }
	}
	// Configuracion con JNDI
	for (String key : multipleDataSource.getJndis().keySet()) {
	    // if (!StartVariables.errorDatabaseTypes.containsKey(key)) {
	    databases.add(key);
	    // }
	}
	return databases;
    }

    @RequestMapping(value = SAVE, method = RequestMethod.POST)
    public ModelAndView save(HttpServletRequest request, @ModelAttribute("queryForm") QueryD entidad, @ModelAttribute("queryConfForm") QueryConfD queryConf) {

	log.info(SAVE);

	String estadoOperacion = Constants.AUDITORIA_ESTADO_OK;
	
	boolean validCron=false;

	try {
	    checkDefaultDB(entidad);

	    entidad.setTexto(Util.formatSQL(entidad.getTexto()));

	    String code = entidad.getCode().toLowerCase();
	    code = StringUtils.replace(code, " ", "");

	    entidad.setCode(code);
	    
	    boolean checkDays=true;
		//No se pueden combinar dia del mes y dia de la semana
		if ((queryConf.getDayM().equals("*")==false) && (queryConf.getDayW().equals("*")==false))
		{
		  checkDays=false;
		  queryConf.setDayM("*");
		  queryConf.setDayW("*");		  
		}
	    
	    String cron = generateCron(queryConf);
	    queryConf.setCron(cron);
	    
	    Date next = Util.calculateNextExecution(cron);
	    if (next!=null)
	    {
		validCron=true;
	    }

	    if (!Util.validValue(queryConf.getZip())) {
		queryConf.setZip(false);
	    }
	    if (!Util.validValue(queryConf.getOverwrite())) {
		queryConf.setOverwrite(false);
	    }

	    queryConf.setId(entidad.getCode());

	    String path = queryConf.getPath();
	    path = path.replace("\\", File.separator);
	    queryConf.setPath(path);

	    boolean folderOK = checkFilePath(path);

	    queryService.add(entidad, queryConf);
	    
	    Commons.auditoria(MODULO, OPERACION_ALTA, estadoOperacion);
	    
	    //Validar query	    
	    String errores = processService.query(entidad, queryConf);	   
	    
		if (Util.validValue(errores)) {
		  return edit(entidad.getCode(), errores);
		} else {
		  if (checkDays==false)  {
			return edit(entidad.getCode(), "Día del mes y día de la semana no están soportados si se utilizan a la vez");
		  } else if (folderOK && validCron) {
			return new ModelAndView("redirect:" + LIST + "?" + Constants.PARAM_ADDED);
		  } else if (folderOK == false) {
			return edit(entidad.getCode(), "Directorio no accesible: "+queryConf.getPath());
		  } else if (validCron == false) {
			return edit(entidad.getCode(), "Fecha de ejecución muy lejana");
		  }
		}

	} catch (Exception e) {
	    log.error("Error saving object", e);
	    estadoOperacion = Constants.AUDITORIA_ESTADO_ERROR;
	}

	Commons.auditoria(MODULO, OPERACION_ALTA, estadoOperacion);
	return new ModelAndView("redirect:" + LIST + "?" + Constants.PARAM_ERROR);

    }

    @RequestMapping(value = EDIT, method = RequestMethod.GET)
    public ModelAndView edit(@PathVariable String id, @RequestParam(value = Constants.PARAM_ERROR, required = false, defaultValue = "") String errorMessage) {

	log.info(EDIT);

	String estadoOperacion = Constants.AUDITORIA_ESTADO_OK;

	ModelAndView model = new ModelAndView();
	model.setViewName("query/edit");

	Commons.controllerCommon(model);

	List<String> databases = getDatabases();
	model.addObject("databases", databases);

	if (Util.validValue(errorMessage)) {
	    model.addObject("errorMessage", errorMessage);
	}

	cronObjectsToModel(model);

	try {
	    QueryD entidad = queryService.record(id);
	    model.addObject("object", entidad);
	    setDatabaseSelected(model, entidad);

	    QueryConfD queryConf = queryConfService.record(entidad.getCode());
	    
	    String path = queryConf.getPath();
	    path = path.replace(File.separator, "\\");
	    queryConf.setPath(path);
	    
	    model.addObject("configuration", queryConf);

	    timeValuesToModel(model, queryConf);

	    Commons.auditoria(MODULO, OPERACION_PREEDICION, estadoOperacion);
	    return model;
	} catch (Exception e) {
	    log.error("Error updating object", e);
	    estadoOperacion = Constants.AUDITORIA_ESTADO_ERROR;
	}

	Commons.auditoria(MODULO, OPERACION_PREEDICION, estadoOperacion);
	return new ModelAndView("redirect:" + LIST + "?" + Constants.PARAM_ERROR);

    }

    private void cronObjectsToModel(ModelAndView model) {
	model.addObject("minutes", Constants.minute);
	model.addObject("hours", Constants.hour);
	model.addObject("daysM", Constants.dayM);

	List<String> valuesDayW = new ArrayList<String>();
	List<String> textDayW = new ArrayList<String>();
	Set<Integer> keySet = Constants.dayW.keySet();
	for (Integer key : keySet) {
	    valuesDayW.add(key + "");
	    textDayW.add(Constants.dayW.get(key));
	}
	model.addObject("valuesDayW", valuesDayW);
	model.addObject("textDayW", textDayW);

	List<String> valuesMonth = new ArrayList<String>();
	List<String> textMonth = new ArrayList<String>();
	Set<String> keySetMonth = Constants.month.keySet();
	for (String key : keySetMonth) {
	    valuesMonth.add(key + "");
	    textMonth.add(Constants.month.get(key));
	}
	model.addObject("valuesMonth", valuesMonth);
	model.addObject("textMonth", textMonth);
    }

    private void setDatabaseSelected(ModelAndView model, QueryD entidad) {
	if (entidad.getDatabase().equals(Constants.DEFAULT_DATABASE)) {
	    model.addObject("databaseSelected", Constants.BASE_DE_DATOS_POR_DEFECTO);
	} else {
	    model.addObject("databaseSelected", entidad.getDatabase());
	}
    }

    @RequestMapping(value = UPDATE, method = RequestMethod.POST)
    public ModelAndView update(HttpServletRequest request, @PathVariable String id, @ModelAttribute("queryForm") QueryD queryForm, @ModelAttribute("confForm") QueryConfD queryConf, @RequestParam String originalCode) {

	log.info(UPDATE);

	String estadoOperacion = Constants.AUDITORIA_ESTADO_OK;

	checkDefaultDB(queryForm);
	
	boolean validCron=false;

	try {
	    QueryD oldQuery = queryService.record(id);

	    QueryConfD oldQueryConf = queryConfService.record(oldQuery.getCode());

	    queryForm.setTexto(Util.formatSQL(queryForm.getTexto()));

	    String code = queryForm.getCode().toLowerCase();
	    code = StringUtils.replace(code, " ", "");
	    queryForm.setCode(code);

	    boolean checkDays=true;
		//No se pueden combinar dia del mes y dia de la semana
		if ((queryConf.getDayM().equals("*")==false) && (queryConf.getDayW().equals("*")==false))
		{
		  checkDays=false;
		  queryConf.setDayM("*");
		  queryConf.setDayW("*");		
		}
	    
	    String cron = generateCron(queryConf);	    
	    queryConf.setCron(cron);
	    
	    Date next = Util.calculateNextExecution(cron);
	    if (next!=null)
	    {
		validCron=true;
	    }

	    String path = queryConf.getPath();
	    path = path.replace("\\", File.separator);
	    queryConf.setPath(path);

	    boolean folderOK = checkFilePath(path);

	    queryConf.setId(queryForm.getCode());

	    if (!Util.validValue(queryConf.getZip())) {
		queryConf.setZip(false);
	    }
	    if (!Util.validValue(queryConf.getOverwrite())) {
		queryConf.setOverwrite(false);
	    }

	    queryService.update(queryForm, oldQuery, queryConf, oldQueryConf);
	    Commons.auditoria(MODULO, OPERACION_EDICION, estadoOperacion);
	    
	    //Validando query
	    String errores = processService.query(queryForm, queryConf);  
	    
		if (Util.validValue(errores)) {
		  return edit(id, errores);
		} else {
		  if (checkDays==false)  {
			return edit(id, "Día del mes y día de la semana no están soportados si se utilizan a la vez");
		  }else  if (folderOK && validCron) {
			return new ModelAndView("redirect:" + LIST + "?" + Constants.PARAM_UPDATED);
		  } else if (folderOK == false) {
			return edit(id, "Directorio no accesible: "+queryConf.getPath());
		  } else if (validCron == false) {
			return edit(id, "Fecha de ejecución muy lejana");
		  }
		}

	} catch (Exception e) {
	    log.error("Error updating object", e);
	    estadoOperacion = Constants.AUDITORIA_ESTADO_ERROR;
	}

	Commons.auditoria(MODULO, OPERACION_EDICION, estadoOperacion);
	return new ModelAndView("redirect:" + LIST + "?" + Constants.PARAM_ERROR);

    }

    private boolean checkFilePath(String path) throws IOException {
	boolean folderOK = false;
	boolean checkParents = false;
	try 
	{
	    File filePath = new File(path);
	    
	    if (filePath.getParentFile().exists()==false)
	    {
		checkParents=filePath.getParentFile().mkdirs();
	    }
	    else
	    {
		checkParents=true;
	    }
	    
	    if (checkParents==true)
	    {
		folderOK=filePath.createNewFile();		    
		filePath.delete();
	    }
	    
	    return folderOK;	    
	} catch (Exception e) {
	    log.error("Error testing path", e);
	}
	return folderOK;
    }

    private String generateCron(QueryConfD queryConf) {

	String cronExpresion = "";

	//second, minute, hour, day, month, weekday
	List<String> validateWildcard = new ArrayList<String>();
	validateWildcard.add("0");
	validateWildcard.add(queryConf.getMinute());
	validateWildcard.add(queryConf.getHour());
	validateWildcard.add(queryConf.getDayM());
	validateWildcard.add(queryConf.getMonth());
	validateWildcard.add(queryConf.getDayW());

	for (String actual : validateWildcard) {
	    if (actual != null) {
		if (actual.contains(",") && (actual.contains("*"))) {
		    actual = "*";
		}
	    } else {
		actual = "*";
	    }
	    cronExpresion += actual + " ";
	}
	cronExpresion = cronExpresion.trim();
	return cronExpresion;
    }

    @RequestMapping(value = DELETE, method = RequestMethod.GET)
    public ModelAndView preDelete(@PathVariable String id) {

	log.info(DELETE + " - get");

	String estadoOperacion = Constants.AUDITORIA_ESTADO_OK;

	ModelAndView model = new ModelAndView();
	model.setViewName("query/delete");

	Commons.controllerCommon(model);

	cronObjectsToModel(model);

	try {
	    QueryD entidad = queryService.record(id);
	    model.addObject("object", entidad);
	    setDatabaseSelected(model, entidad);
	    Commons.auditoria(MODULO, OPERACION_PREBORRADO, estadoOperacion);

	    QueryConfD conf = queryConfService.record(entidad.getCode());
	    model.addObject("configuration", conf);

	    timeValuesToModel(model, conf);

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

	    QueryConfD conf = queryConfService.record(recordToDelete.getCode());

	    queryService.delete(recordToDelete, conf);

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

}