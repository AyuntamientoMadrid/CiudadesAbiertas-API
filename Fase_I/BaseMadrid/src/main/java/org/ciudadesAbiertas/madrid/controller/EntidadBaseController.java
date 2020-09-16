package org.ciudadesAbiertas.madrid.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.ciudadesAbiertas.madrid.model.EntidadBase;
import org.ciudadesAbiertas.madrid.service.EntidadBaseService;
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
public class EntidadBaseController {

	
	private static final Logger log = LoggerFactory.getLogger(EntidadBaseController.class);
	
	@Autowired
	protected Environment env;

	@Autowired
	private EntidadBaseService entidadBaseService;

	private static final String PATH = "/entidadBase";
	private static final String PARAM_ID = "{id}";	
	
	public static final String LIST = PATH;
	public static final String RECORD = PATH+"/"+PARAM_ID;
	public static final String ADD = PATH+"/add";
	public static final String SAVE = PATH+"/save";
	public static final String EDIT = PATH+"/edit/"+PARAM_ID;
	public static final String DELETE = PATH+"/delete/"+PARAM_ID;
	public static final String UPDATE = PATH+"/update/"+PARAM_ID;

	@RequestMapping(value = LIST, method = RequestMethod.GET)
	public ModelAndView list(	HttpServletRequest request,	
								@RequestParam(value = Constants.PAGE, defaultValue = "1", required = false) String page,							
								@RequestParam(value = Constants.PARAM_ERROR, required = false) String error,
								@RequestParam(value = Constants.PARAM_DELETED, required = false) String deleted,
								@RequestParam(value = Constants.PARAM_UPDATED, required = false) String updated,
								@RequestParam(value = Constants.PARAM_ADDED, required = false) String added) {

		log.info(LIST);

		ModelAndView model = new ModelAndView();
		model.setViewName("entidadBase/list");		

		Commons.controllerCommon(model);
		
		if ((deleted != null)) {			
			model.addObject("deleted","true");
		}
		
		if ((updated != null)) {			
			model.addObject("updated","true");
		}
		
		if ((added != null)) {			
			model.addObject("added","true");
		}
		
		if ((error != null)) {			
			model.addObject("error","true");
		}
		
		try
		{
			int firstResult=1;
			int pageNumber=1;
			try
			{
				pageNumber=Integer.parseInt(page);
				pageNumber--;
			}
			catch (Exception e)
			{
				log.error("Wrong page number",e);
			}
			if (pageNumber >= 0)
			{
				firstResult=pageNumber*StartVariables.defaultPageSize;
			}	else {
				firstResult=0;
			}
			
			int total=entidadBaseService.listRowCount();
			
			List<EntidadBase> list = entidadBaseService.list(firstResult,StartVariables.defaultPageSize);
			model.addObject("list", list);
			
			Map<String, String> pMCalculation = Util.pageMetadataCalculation(request, total, StartVariables.defaultPageSize, env.getProperty(Constants.URI_BASE), env.getProperty(Constants.STR_CONTEXTO));
			
			
			
			if (pMCalculation.get("actual").equals("1")==false)
			{
				model.addObject("first", pMCalculation.get(Constants.FIRST));
			}
			if (pMCalculation.get("actual").equals(pMCalculation.get("total"))==false)
			{
				model.addObject("last", pMCalculation.get(Constants.LAST));
			}			
			model.addObject("next", pMCalculation.get(Constants.NEXT));			
			model.addObject("prev", pMCalculation.get(Constants.PREV));
			model.addObject("actual", pMCalculation.get(Constants.PREV));
			model.addObject("total", pMCalculation.get("total"));
			model.addObject("actual", pMCalculation.get("actual"));
			
			
			System.out.println(pMCalculation);
			
		}
		catch (Exception e)
		{			
			log.error("Error reading list",e);
			model.addObject("list", new ArrayList<EntidadBase>());			
			model.addObject(Constants.ERROR_OBJ,"error reading list");
		}
		return model;
	}
	

	@RequestMapping(value = RECORD, method = RequestMethod.GET)
	public ModelAndView record(@PathVariable String id) {

		log.info(LIST);

		ModelAndView model = new ModelAndView();
		model.setViewName("entidadBase/record");

		Commons.controllerCommon(model);
		
		try
		{
			EntidadBase entidad = entidadBaseService.record(id);
			model.addObject("object", entidad);
			return model;
		}
		catch (Exception e)
		{
			log.error("Error reading object",e);
		}
		return new ModelAndView("redirect:"+LIST+"?"+Constants.PARAM_ERROR);

		
	}

	@RequestMapping(value = ADD, method = RequestMethod.GET)
	public ModelAndView add() {

		log.info(ADD);

		ModelAndView model = new ModelAndView();
		model.setViewName("entidadBase/add");

		Commons.controllerCommon(model);

		return model;

	}
	
	
	@RequestMapping(value = SAVE, method = RequestMethod.POST)
	public ModelAndView save(@ModelAttribute("entidadBaseForm") EntidadBase entidad) {

		log.info(SAVE);
		
		try
		{
			entidadBaseService.add(entidad);
			
			return new ModelAndView("redirect:"+LIST+"?"+Constants.PARAM_ADDED);
			
		}
		catch (Exception e)
		{
			log.error("Error saving object",e);
		}
		return new ModelAndView("redirect:"+LIST+"?"+Constants.PARAM_ERROR);


	}

	
	@RequestMapping(value = EDIT, method = RequestMethod.GET)
	public ModelAndView edit(@PathVariable String id) {

		log.info(EDIT);

		ModelAndView model = new ModelAndView();
		model.setViewName("entidadBase/edit");

		Commons.controllerCommon(model);
		
		try
		{
			EntidadBase entidad = entidadBaseService.record(id);
			model.addObject("object", entidad);
			return model;
		}
		catch (Exception e)
		{
			log.error("Error updating object",e);
		}
		return new ModelAndView("redirect:"+LIST+"?"+Constants.PARAM_ERROR);
		

	}
	
	
	@RequestMapping(value = UPDATE, method = RequestMethod.POST)
	public ModelAndView update(@PathVariable String id, @ModelAttribute("entidadBaseForm") EntidadBase entidad) {

		log.info(UPDATE);		
		
		try
		{
			entidadBaseService.update(id, entidad);			
			return new ModelAndView("redirect:"+LIST+"?"+Constants.PARAM_UPDATED);
			
		}
		catch (Exception e)
		{
			log.error("Error updating object",e);
		}
		return new ModelAndView("redirect:"+LIST+"?"+Constants.PARAM_ERROR);

	}
	
	
	@RequestMapping(value = DELETE, method = RequestMethod.GET)
	public ModelAndView preDelete(@PathVariable String id) {

		log.info(DELETE + " - get");

		ModelAndView model = new ModelAndView();
		model.setViewName("entidadBase/delete");
		
		Commons.controllerCommon(model);
		
		try
		{
			EntidadBase entidad = entidadBaseService.record(id);
			model.addObject("object", entidad);
			return model;
		}
		catch (Exception e)
		{
			log.error("Error searching object",e);
		}
		return new ModelAndView("redirect:"+LIST+"?"+Constants.PARAM_ERROR);
		
	}
	
	
	@RequestMapping(value = DELETE, method = RequestMethod.POST)
	public ModelAndView delete(@PathVariable String id) {

		log.info(DELETE + " - post");
		
		try
		{
			entidadBaseService.delete(id);
			return new ModelAndView("redirect:"+LIST+"?"+Constants.PARAM_DELETED);
		}
		catch (Exception e)
		{
			log.error("Error deleting object",e);
		}
		return new ModelAndView("redirect:"+LIST+"?"+Constants.PARAM_ERROR);
		
	}
	

}