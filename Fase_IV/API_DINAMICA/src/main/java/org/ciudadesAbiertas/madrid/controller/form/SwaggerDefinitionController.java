package org.ciudadesAbiertas.madrid.controller.form;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.ciudadesAbiertas.madrid.controller.Commons;
import org.ciudadesAbiertas.madrid.model.dynamic.QueryD;
import org.ciudadesAbiertas.madrid.model.dynamic.SwaggerDefinition;
import org.ciudadesAbiertas.madrid.service.dynamic.QueryService;
import org.ciudadesAbiertas.madrid.service.dynamic.SwaggerDefinitionService;
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

import com.google.gson.stream.MalformedJsonException;

@Controller
public class SwaggerDefinitionController
{

  private static final Logger log = LoggerFactory.getLogger(SwaggerDefinitionController.class);

  @Autowired
  protected Environment env;

  @Autowired
  private SwaggerDefinitionService swaggerService;

  @Autowired
  private QueryService queryService;

  public static final String MODULO = "SWAGGER DEF";
  public static final String OPERACION_PREALTA = "PRE-ALTA";
  public static final String OPERACION_ALTA = "ALTA";
  public static final String OPERACION_LISTADO = "Listado";
  public static final String OPERACION_FICHA = "Ficha";
  public static final String OPERACION_PREBORRADO = "PRE-BORRADO";
  public static final String OPERACION_BORRADO = "BORRADO";

  public static final String OPERACION_PREEDICION = "PRE-EDICION";
  public static final String OPERACION_EDICION = "EDICION";

  private static final String PATH = "/swaggerDef";
  private static final String PARAM_ID = "{id:.+}";

  public static final String LIST = PATH;
  public static final String RECORD = PATH + "/" + PARAM_ID;
  public static final String ADD = PATH + "/add";
  public static final String SAVE = PATH + "/save";
  public static final String EDIT = PATH + "/edit/" + PARAM_ID;
  public static final String DELETE = PATH + "/delete/" + PARAM_ID;
  public static final String UPDATE = PATH + "/update/" + PARAM_ID;

  @RequestMapping(value = LIST, method = RequestMethod.GET)
  public ModelAndView list(HttpServletRequest request, @RequestParam(value = Constants.PAGE, defaultValue = "1", required = false) String page, @RequestParam(value = Constants.PARAM_ERROR, required = false) String error,
      @RequestParam(value = Constants.PARAM_DELETED, required = false) String deleted, @RequestParam(value = Constants.PARAM_UPDATED, required = false) String updated,
      @RequestParam(value = Constants.PARAM_ADDED, required = false) String added)
  {

    log.info(LIST);

    String estadoOperacion = Constants.AUDITORIA_ESTADO_OK;

    ModelAndView model = new ModelAndView();
    model.setViewName("swaggerDef/list");

    Commons.controllerCommon(model);

    if ((deleted != null))
    {
      model.addObject("deleted", "true");
    }

    if ((updated != null))
    {
      model.addObject("updated", "true");
    }

    if ((added != null))
    {
      model.addObject("added", "true");
    }

    if ((error != null))
    {
      model.addObject("error", "true");
      if (Util.validValue(error))
      {
	error = Util.decodeURL(error);
      }
      model.addObject("errorMessage", error);
    }

    try
    {
      int firstResult = 1;
      int pageNumber = 1;
      try
      {
	pageNumber = Integer.parseInt(page);
	pageNumber--;
      }
      catch (Exception e)
      {
	log.error("Wrong page number", e);
      }
      if (pageNumber >= 0)
      {
	firstResult = pageNumber * StartVariables.defaultPageSize;
      }
      else
      {
	firstResult = 0;
      }

      int total = swaggerService.listRowCount();

      List<SwaggerDefinition> list = swaggerService.list(firstResult, StartVariables.defaultPageSize);
      model.addObject("list", list);

      Map<String, String> pMCalculation = Util.pageMetadataCalculation(request, total, StartVariables.defaultPageSize, env.getProperty(Constants.URI_BASE), env.getProperty(Constants.STR_CONTEXTO));

      if (pMCalculation.get("actual").equals("1") == false)
      {
	model.addObject("first", pMCalculation.get(Constants.FIRST));
      }
      if (pMCalculation.get("actual").equals(pMCalculation.get("total")) == false)
      {
	model.addObject("last", pMCalculation.get(Constants.LAST));
      }
      model.addObject("next", pMCalculation.get(Constants.NEXT));
      model.addObject("prev", pMCalculation.get(Constants.PREV));
      model.addObject("actual", pMCalculation.get(Constants.PREV));
      model.addObject("total", pMCalculation.get("total"));
      model.addObject("actual", pMCalculation.get("actual"));

    }
    catch (Exception e)
    {
      log.error("Error reading list", e);
      model.addObject("list", new ArrayList<SwaggerDefinition>());
      model.addObject(Constants.ERROR_OBJ, "error reading list");
      estadoOperacion = Constants.AUDITORIA_ESTADO_ERROR;
    }

    Commons.auditoria(MODULO, OPERACION_LISTADO, estadoOperacion);
    return model;
  }

  @RequestMapping(value = RECORD, method = RequestMethod.GET)
  public ModelAndView record(@PathVariable String id)
  {
    log.info(RECORD+" "+id);
    
    String estadoOperacion = Constants.AUDITORIA_ESTADO_OK;

    ModelAndView model = new ModelAndView();
    model.setViewName("swaggerDef/record");

    Commons.controllerCommon(model);

    try
    {
      SwaggerDefinition entidad = swaggerService.find(id);

      entidad.setText(Util.jsonPrettyPrint(entidad.getText()));

      model.addObject("object", entidad);
      Commons.auditoria(MODULO, OPERACION_FICHA, estadoOperacion);
      return model;
    }
    catch (Exception e)
    {
      log.error("Error reading object", e);
      estadoOperacion = Constants.AUDITORIA_ESTADO_ERROR;
    }

    Commons.auditoria(MODULO, OPERACION_FICHA, estadoOperacion);
    return new ModelAndView("redirect:" + LIST + "?" + Constants.PARAM_ERROR);

  }

  @RequestMapping(value = ADD, method = RequestMethod.GET)
  public ModelAndView add()
  {

    log.info(ADD);

    String estadoOperacion = Constants.AUDITORIA_ESTADO_OK;

    ModelAndView model = new ModelAndView();
    try
    {
      model.setViewName("swaggerDef/add");
      Commons.controllerCommon(model);
    }
    catch (Exception e)
    {
      log.error("Error adding object", e);
      estadoOperacion = Constants.AUDITORIA_ESTADO_ERROR;
    }

    Commons.auditoria(MODULO, OPERACION_PREALTA, estadoOperacion);

    return model;

  }

  @RequestMapping(value = SAVE, method = RequestMethod.POST)
  public ModelAndView save(@ModelAttribute("swaggerDefForm") SwaggerDefinition entidad)
  {
    log.info(SAVE);
    
    String estadoOperacion = Constants.AUDITORIA_ESTADO_OK;

    try
    {
      entidad.setText(Util.jsonPrettyPrint(entidad.getText()));

      swaggerService.add(entidad);
      Commons.auditoria(MODULO, OPERACION_ALTA, estadoOperacion);
      return new ModelAndView("redirect:" + LIST + "?" + Constants.PARAM_ADDED);

    }
    catch (MalformedJsonException mje)
    {
      String errorJSON = "JSON Malformado: " + mje.getMessage();
      return new ModelAndView("redirect:" + LIST + "?" + Constants.PARAM_ERROR + "=" + errorJSON);
    }
    catch (Exception e)
    {
      log.error("Error saving object", e);
      estadoOperacion = Constants.AUDITORIA_ESTADO_ERROR;
    }

    Commons.auditoria(MODULO, OPERACION_ALTA, estadoOperacion);
    return new ModelAndView("redirect:" + LIST + "?" + Constants.PARAM_ERROR);

  }

  @RequestMapping(value = EDIT, method = RequestMethod.GET)
  public ModelAndView edit(@PathVariable String id)
  {

    log.info(EDIT);

    String estadoOperacion = Constants.AUDITORIA_ESTADO_OK;

    ModelAndView model = new ModelAndView();
    model.setViewName("swaggerDef/edit");

    Commons.controllerCommon(model);

    try
    {
      SwaggerDefinition entidad = swaggerService.find(id);
      model.addObject("object", entidad);
      Commons.auditoria(MODULO, OPERACION_PREEDICION, estadoOperacion);
      return model;
    }
    catch (Exception e)
    {
      log.error("Error updating object", e);
      estadoOperacion = Constants.AUDITORIA_ESTADO_ERROR;
    }

    Commons.auditoria(MODULO, OPERACION_PREEDICION, estadoOperacion);
    return new ModelAndView("redirect:" + LIST + "?" + Constants.PARAM_ERROR);

  }

  @RequestMapping(value = UPDATE, method = RequestMethod.POST)
  public ModelAndView update(@PathVariable String id, @ModelAttribute("SwaggerDefinitionForm") SwaggerDefinition entidadForm)
  {

    log.info(UPDATE);
    
    String estadoOperacion = Constants.AUDITORIA_ESTADO_OK;

    try
    {
      boolean error = false;
      List<QueryD> queryList = queryService.list(0, 0);
      List<QueryD> queriesWithThisDef = new ArrayList<QueryD>();
      for (QueryD query : queryList)
      {
	if (query.getDefinition()!=null && query.getDefinition().equals(id))
	{
	  queriesWithThisDef.add(query);
	}
      }

      SwaggerDefinition entidadFromBBDD = swaggerService.find(id);
      entidadForm.setText(Util.jsonPrettyPrint(entidadForm.getText()));
      swaggerService.update(entidadFromBBDD, entidadForm, queriesWithThisDef);
      
      Commons.auditoria(MODULO, OPERACION_EDICION, estadoOperacion);
      return new ModelAndView("redirect:" + LIST + "?" + Constants.PARAM_UPDATED);

    }
    catch (Exception e)
    {
      log.error("Error updating object", e);
      estadoOperacion = Constants.AUDITORIA_ESTADO_ERROR;
    }

    Commons.auditoria(MODULO, OPERACION_EDICION, estadoOperacion);
    return new ModelAndView("redirect:" + LIST + "?" + Constants.PARAM_ERROR);

  }

  @RequestMapping(value = DELETE, method = RequestMethod.GET)
  public ModelAndView preDelete(@PathVariable String id)
  {

    log.info(DELETE + " - get");

    String estadoOperacion = Constants.AUDITORIA_ESTADO_OK;

    ModelAndView model = new ModelAndView();
    model.setViewName("swaggerDef/delete");

    String errorDetails = "Esta definición se está utilizando en la siguientes consultas: ";

    boolean error = false;
    List<QueryD> queryList = queryService.list(0, 0);
    for (QueryD query : queryList)
    {
      if (query.getDefinition()!=null && query.getDefinition().equals(id))
      {
	error = true;
	errorDetails += query.getCode() + ",";
      }
    }
    errorDetails = StringUtils.chop(errorDetails);
    errorDetails = Util.encodeURL(errorDetails);

    if (error == false)
    {

      Commons.controllerCommon(model);

      try
      {
	SwaggerDefinition entidad = swaggerService.find(id);
	model.addObject("object", entidad);
	Commons.auditoria(MODULO, OPERACION_PREBORRADO, estadoOperacion);
	return model;
      }
      catch (Exception e)
      {
	log.error("Error searching object", e);
	errorDetails = "";
	estadoOperacion = Constants.AUDITORIA_ESTADO_ERROR;
      }

    }

    Commons.auditoria(MODULO, OPERACION_PREBORRADO, estadoOperacion);

    return new ModelAndView("redirect:" + LIST + "?" + Constants.PARAM_ERROR + "=" + errorDetails);

  }

  @RequestMapping(value = DELETE, method = RequestMethod.POST)
  public ModelAndView delete(@PathVariable String id)
  {

    log.info(DELETE + " - post");

    String estadoOperacion = Constants.AUDITORIA_ESTADO_OK;

    try
    {
      SwaggerDefinition entidad = swaggerService.find(id);
      entidad.setText(Util.jsonPrettyPrint(entidad.getText()));
      swaggerService.remove(entidad);
      Commons.auditoria(MODULO, OPERACION_BORRADO, estadoOperacion);
      return new ModelAndView("redirect:" + LIST + "?" + Constants.PARAM_DELETED);
    }
    catch (Exception e)
    {
      log.error("Error deleting object", e);
      estadoOperacion = Constants.AUDITORIA_ESTADO_ERROR;
    }

    Commons.auditoria(MODULO, OPERACION_BORRADO, estadoOperacion);
    return new ModelAndView("redirect:" + LIST + "?" + Constants.PARAM_ERROR);

  }

}