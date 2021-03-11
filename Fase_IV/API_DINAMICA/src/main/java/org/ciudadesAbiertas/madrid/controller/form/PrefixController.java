package org.ciudadesAbiertas.madrid.controller.form;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.ciudadesAbiertas.madrid.controller.Commons;
import org.ciudadesAbiertas.madrid.model.dynamic.SemanticPrefix;
import org.ciudadesAbiertas.madrid.model.dynamic.SemanticRelPrefix;
import org.ciudadesAbiertas.madrid.service.dynamic.PrefixRelService;
import org.ciudadesAbiertas.madrid.service.dynamic.PrefixService;
import org.ciudadesAbiertas.madrid.utils.ErrorBean;
import org.ciudadesAbiertas.madrid.utils.StartVariables;
import org.ciudadesAbiertas.madrid.utils.Util;
import org.ciudadesAbiertas.madrid.utils.constants.Constants;
import org.ciudadesAbiertas.madrid.utils.constants.LiteralConstants;
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
public class PrefixController {

	private static final Logger log = LoggerFactory.getLogger(PrefixController.class);

	@Autowired
	protected Environment env;

	@Autowired
	private PrefixService prefixService;
	
	@Autowired
	private PrefixRelService prefixRelService;

	private static final String PATH = "/prefix";
	private static final String PARAM_ID = "{id}";

	public static final String LIST = PATH;
	public static final String RECORD = PATH + "/" + PARAM_ID;
	public static final String ADD = PATH + "/add";
	public static final String SAVE = PATH + "/save";
	public static final String EDIT = PATH + "/edit/" + PARAM_ID;
	public static final String DELETE = PATH + "/delete/" + PARAM_ID;
	public static final String UPDATE = PATH + "/update/" + PARAM_ID;

	public static final String MODULO = "Prefijos";
	public static final String OPERACION_PREALTA = "PRE-ALTA";
	public static final String OPERACION_ALTA = "ALTA";
	public static final String OPERACION_LISTADO = "Listado";
	public static final String OPERACION_FICHA = "Ficha";
	public static final String OPERACION_PREBORRADO = "PRE-BORRADO";
	public static final String OPERACION_BORRADO = "BORRADO";

	public static final String OPERACION_PREEDICION = "PRE-EDICION";
	public static final String OPERACION_EDICION = "EDICION";

	@RequestMapping(value = LIST, method = RequestMethod.GET)
	public ModelAndView list(HttpServletRequest request,
			@RequestParam(value = Constants.PAGE, defaultValue = "1", required = false) String page,
			@RequestParam(value = Constants.PARAM_ERROR, required = false) String error,
			@RequestParam(value = Constants.PARAM_DELETED, required = false) String deleted,
			@RequestParam(value = Constants.PARAM_UPDATED, required = false) String updated,
			@RequestParam(value = Constants.PARAM_ADDED, required = false) String added) {

		log.info(LIST);

		String estadoOperacion = Constants.AUDITORIA_ESTADO_OK;

		ModelAndView model = new ModelAndView();
		model.setViewName("prefix/list");

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
			
			
			if (Util.validValue(error)&&(!error.equals("true")))
			{
				ErrorBean errorBean=new ErrorBean();
				errorBean.setName(LiteralConstants.TEXT_ERROR);
				errorBean.setDescription(error);
				model.addObject("errorObject",errorBean);				
			}else {
				model.addObject("error", "true");
			}
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

			int total = prefixService.listRowCount();

			List<SemanticPrefix> list = prefixService.list(firstResult, StartVariables.defaultPageSize);

			model.addObject("list", list);

			Map<String, String> pMCalculation = Util.pageMetadataCalculation(request, total,
					StartVariables.defaultPageSize, env.getProperty(Constants.URI_BASE),
					env.getProperty(Constants.STR_CONTEXTO));

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
			model.addObject("list", new ArrayList<SemanticPrefix>());
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
		model.setViewName("prefix/record");

		Commons.controllerCommon(model);

		try {
			int idParsed = Integer.parseInt(id);

			SemanticPrefix entidad = prefixService.record(idParsed);
			model.addObject("object", entidad);
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
		model.setViewName("prefix/add");

		try {
			Commons.controllerCommon(model);

		} catch (Exception e) {
			log.error("Error adding object", e);
			estadoOperacion = Constants.AUDITORIA_ESTADO_ERROR;
		}

		Commons.auditoria(MODULO, OPERACION_PREALTA, estadoOperacion);

		return model;

	}

	@RequestMapping(value = SAVE, method = RequestMethod.POST)
	public ModelAndView save(HttpServletRequest request, @ModelAttribute("queryForm") SemanticPrefix entidad) {

		log.info(SAVE);

		String estadoOperacion = Constants.AUDITORIA_ESTADO_OK;

		try {

			String code = entidad.getCode().toLowerCase();
			code = StringUtils.replace(code, " ", "");

			entidad.setCode(code);

			prefixService.add(entidad);

			Commons.auditoria(MODULO, OPERACION_ALTA, estadoOperacion);
			return new ModelAndView("redirect:" + LIST + "?" + Constants.PARAM_ADDED);

		} catch (Exception e) {
			log.error("Error saving object", e);
			estadoOperacion = Constants.AUDITORIA_ESTADO_ERROR;
		}

		Commons.auditoria(MODULO, OPERACION_ALTA, estadoOperacion);
		return new ModelAndView("redirect:" + LIST + "?" + Constants.PARAM_ERROR);

	}

	@RequestMapping(value = EDIT, method = RequestMethod.GET)
	public ModelAndView edit(HttpServletRequest request, @PathVariable String id) {

		log.info(EDIT);

		String estadoOperacion = Constants.AUDITORIA_ESTADO_OK;

		ModelAndView model = new ModelAndView();
		model.setViewName("prefix/edit");

		Commons.controllerCommon(model);
		
		try {
			int idParsed = Integer.parseInt(id);

			SemanticPrefix entidad = prefixService.record(idParsed);
			model.addObject("object", entidad);
			
			List<String> queriesWithPrefix = prefixRelService.getQueriesWithPrefix(entidad.getCode());			
			if (queriesWithPrefix.size()>0)
			{
				return prefixUsedError(request, entidad, queriesWithPrefix);
			}

			Commons.auditoria(MODULO, OPERACION_PREEDICION, estadoOperacion);
			return model;
		} catch (Exception e) {
			log.error("Error updating object", e);
			estadoOperacion = Constants.AUDITORIA_ESTADO_ERROR;
		}

		Commons.auditoria(MODULO, OPERACION_PREEDICION, estadoOperacion);
		return new ModelAndView("redirect:" + LIST + "?" + Constants.PARAM_ERROR);

	}

	private ModelAndView prefixUsedError(HttpServletRequest request, SemanticPrefix entidad,
			List<String> queriesWithPrefix) {
		String queries="";
		for (String q:queriesWithPrefix)
		{
			queries=queries+" <b>"+q+"</b>"+",";
		}
		queries=StringUtils.chop(queries);
		return list(request, Constants.defaultPage+"", "El prefijo <b>"+entidad.getCode()+"</b> no se puede modificar o borrar porque se usa en las siguientes consultas: "+queries, null, null, null);
	}

	@RequestMapping(value = UPDATE, method = RequestMethod.POST)
	public ModelAndView update(HttpServletRequest request, @PathVariable String id,
			@ModelAttribute("queryForm") SemanticPrefix form, @RequestParam String originalCode) {

		log.info(UPDATE);

		String estadoOperacion = Constants.AUDITORIA_ESTADO_OK;

		try {
			int idParsed = Integer.parseInt(id);

			prefixService.update(form);

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
	public ModelAndView preDelete(HttpServletRequest request, @PathVariable String id) {

		log.info(DELETE + " - get");

		String estadoOperacion = Constants.AUDITORIA_ESTADO_OK;

		ModelAndView model = new ModelAndView();
		model.setViewName("prefix/delete");

		Commons.controllerCommon(model);

		try {
			int idParsed = Integer.parseInt(id);

			SemanticPrefix entidad = prefixService.record(idParsed);
			model.addObject("object", entidad);
			
			List<String> queriesWithPrefix = prefixRelService.getQueriesWithPrefix(entidad.getCode());			
			if (queriesWithPrefix.size()>0)
			{
				return prefixUsedError(request, entidad, queriesWithPrefix);
			}


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
	public ModelAndView delete(HttpServletRequest request, @PathVariable String id) {

		log.info(DELETE + " - post");

		String estadoOperacion = Constants.AUDITORIA_ESTADO_OK;

		try {
			int idParsed = Integer.parseInt(id);

			SemanticPrefix recordToDelete = prefixService.record(idParsed);
			
			
			prefixService.delete(recordToDelete);
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