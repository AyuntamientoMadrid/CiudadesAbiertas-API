package org.ciudadesAbiertas.madrid.controller.form;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.ciudadesAbiertas.madrid.bean.TaskBean;
import org.ciudadesAbiertas.madrid.controller.Commons;
import org.ciudadesAbiertas.madrid.model.dynamic.QueryConfD;
import org.ciudadesAbiertas.madrid.model.dynamic.QueryD;
import org.ciudadesAbiertas.madrid.model.dynamic.TaskD;
import org.ciudadesAbiertas.madrid.service.dynamic.TaskService;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class TaskController {

    private static final Logger log = LoggerFactory.getLogger(TaskController.class);

    @Autowired
    protected Environment env;
    
    @Autowired
    private TaskService taskService;
    
    private static SimpleDateFormat dateTimeFormFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    public static SimpleDateFormat dateFormFormatter = new SimpleDateFormat("dd/MM/yyyy");


    public static final String PATH = "/task";
    private static final String PARAM_ID = "{id}";

    public static final String LIST = PATH;
    public static final String RECORD = PATH + "/" + PARAM_ID;
   
    public static final String MODULO = "Tareas";
    public static final String OPERACION_LISTADO = "Listado";
    public static final String OPERACION_FICHA = "Ficha";
   
    public static List<String> modeList=new ArrayList<String>();
    public static List<String> statusList=new ArrayList<String>();
    
    static
    {
	modeList.add(QueryConfD.MODE_AUTO);
	modeList.add(QueryConfD.MODE_MANUAL);
	
	statusList.add(TaskD.FINALIZADA);
	statusList.add(TaskD.RUNNING);
	statusList.add(TaskD.ERROR);
    }
    

    @RequestMapping(value = LIST, method = {RequestMethod.GET, RequestMethod.POST })
    public ModelAndView list(HttpServletRequest request,
	    			@ModelAttribute("busquedaForm") TaskD taskSearch,
	    			@RequestParam(value = Constants.PAGE, defaultValue = "1", required = false) String page,
	    			@RequestParam(value = Constants.SORT, defaultValue = "-start", required = false) String sort,
	    			@RequestParam(value = "timeStart", defaultValue = "00:00", required = false) String timeStart,
	    			@RequestParam(value = "timeFinish", defaultValue = "23:59", required = false) String timeFinish) {

	log.info(LIST);

	String estadoOperacion = Constants.AUDITORIA_ESTADO_OK;

	ModelAndView model = new ModelAndView();
	model.setViewName("task/list");

	Commons.controllerCommon(model);

	int firstResult=0;
	int maxResults=StartVariables.defaultPageSize;
	try
	{
	    firstResult=Integer.parseInt(page)-1;
	}
	catch (Exception e)
	{
	    log.error("Wrong page number:"+page);
	}
	

	try {
	    
	    
	    
	    List<String> queryList=taskService.distinct("query");
	    
	    firstResult=firstResult*StartVariables.defaultPageSize;
	    
	    String order=" order by ";
	    String sortOrder="";
	    String sortField="";
	    String orderPage=request.getContextPath()+request.getServletPath()+"?"+Constants.PAGE+"="+page+"&"+Constants.SORT+"=";
	    if (sort.startsWith("-"))
	    {
		order+=sort.substring(1)+" desc";
		sortOrder="desc";
		sortField=sort.substring(1);
	    }
	    else if (sort.startsWith("+")||sort.startsWith(" "))
	    {
		order+=sort.substring(1)+" asc";
		sortOrder="asc";
		sortField=sort.substring(1);
	    }else if (sort.startsWith("-"))
	    {
		order+=sort.substring(1)+" desc";
		sortOrder="desc";
		sortField=sort.substring(1);
	    }else {
		order+=sort+" asc";
		sortOrder="asc";
		sortField=sort;
	    }
	    
	    
	    if (Util.validValue(taskSearch.getStart()) && Util.validValue(timeStart) && (timeStart.contentEquals("00:00")==false))
	    {
		Date start = taskSearch.getStart();
		String startString = Util.dateFormatter.format(start);
		startString=startString+" "+timeStart;
		start =dateTimeFormFormatter.parse(startString);
		taskSearch.setStart(start);		
	    }
	    
	    if (Util.validValue(taskSearch.getFinish()) && Util.validValue(timeFinish)&&(timeFinish.contentEquals("00:00")==false))
	    {
		Date finish = taskSearch.getFinish();
		String finishString = Util.dateFormatter.format(finish);
		finishString=finishString+" "+timeFinish;
		finish =dateTimeFormFormatter.parse(finishString);
		taskSearch.setFinish(finish);		
	    }

	    int total = taskService.listRowCount(taskSearch);
	    List<TaskD> list = taskService.list(firstResult, maxResults, order, taskSearch);

	    List<TaskBean> listBean = new ArrayList<TaskBean>();	    
	    
	    if (list != null && !list.isEmpty()) {
		for (TaskD taskD : list) {
		    TaskBean task = new TaskBean(taskD);
		    listBean.add(task);
		}
	    }

	    // model.addObject("list", list);
	    model.addObject("list", listBean);

	    Map<String, String> pMCalculation = Util.pageMetadataCalculation(request, total, StartVariables.defaultPageSize, env.getProperty(Constants.URI_BASE), env.getProperty(Constants.STR_CONTEXTO));

	    if (pMCalculation.get("actual").equals("1") == false) {
		model.addObject("first", pMCalculation.get(Constants.FIRST));
	    }
	    if (pMCalculation.get("actual").equals(pMCalculation.get("total")) == false) {
		model.addObject("last", pMCalculation.get(Constants.LAST));
	    }
	    	    
	    model.addObject("modeSearch", taskSearch.getMode());
	    model.addObject("querySearch", taskSearch.getQuery());
	    model.addObject("statusSearch", taskSearch.getStatus());	  
	    if (taskSearch.getStart()!=null)
	    {
		String format = dateFormFormatter.format(taskSearch.getStart());
		model.addObject("startDateSearch", format);	
	    }
	    if (taskSearch.getFinish()!=null)
	    {
		String format = dateFormFormatter.format(taskSearch.getFinish());
		model.addObject("finishDateSearch", format);	
	    }
	    model.addObject("startTimeSearch",timeStart);
	    model.addObject("finishTimeSearch",timeFinish);
	    
	    
	    
	    model.addObject("next", pMCalculation.get(Constants.NEXT));
	    model.addObject("prev", pMCalculation.get(Constants.PREV));
	    model.addObject("actual", pMCalculation.get(Constants.PREV));
	    model.addObject("total", pMCalculation.get("total"));
	    model.addObject("actual", pMCalculation.get("actual"));
	    model.addObject("sortField", sortField);
	    model.addObject("sortOrder", sortOrder);
	    model.addObject("orderPage", orderPage);
	    model.addObject("searchPage", LIST);
	    model.addObject("queryList",queryList);
	    model.addObject("modeList",modeList);
	    model.addObject("statusList",statusList);
	    model.addObject("method", request.getMethod());
	 
	    

	    

	} catch (Exception e) {
	    log.error("Error reading list", e);
	    model.addObject("list", new ArrayList<QueryD>());
	    model.addObject(Constants.ERROR_OBJ, "error reading list");
	    estadoOperacion = Constants.AUDITORIA_ESTADO_ERROR;
	}

	Commons.auditoria(MODULO, OPERACION_LISTADO, estadoOperacion);

	return model;
    }

    @RequestMapping(value = { RECORD }, method = { RequestMethod.GET })
    public @ResponseBody TaskD taskSearch(@PathVariable String id)
    {

	log.info("[taskSearch]" + id);

	TaskD task = taskService.record(id);

	return task;

    }  
    

   
}