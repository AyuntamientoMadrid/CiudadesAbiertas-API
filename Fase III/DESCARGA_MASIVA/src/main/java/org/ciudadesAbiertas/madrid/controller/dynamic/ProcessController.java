package org.ciudadesAbiertas.madrid.controller.dynamic;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ciudadesAbiertas.madrid.model.dynamic.QueryConfD;
import org.ciudadesAbiertas.madrid.model.dynamic.QueryD;
import org.ciudadesAbiertas.madrid.model.dynamic.TaskD;
import org.ciudadesAbiertas.madrid.schedule.ScheduleTask;
import org.ciudadesAbiertas.madrid.service.EmailService;
import org.ciudadesAbiertas.madrid.service.dynamic.ProcessService;
import org.ciudadesAbiertas.madrid.service.dynamic.QueryConfService;
import org.ciudadesAbiertas.madrid.service.dynamic.QueryService;
import org.ciudadesAbiertas.madrid.service.dynamic.TaskService;
import org.ciudadesAbiertas.madrid.utils.StartVariables;
import org.ciudadesAbiertas.madrid.utils.TaskUtils;
import org.ciudadesAbiertas.madrid.utils.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProcessController implements IDynamicController
{

    private static final Logger log = LoggerFactory.getLogger(ProcessController.class);

    public static final String path = "/process/";

    @Autowired
    private ScheduleTask scheduleTask;
    
    @Autowired
    private TaskService taskService;

    @RequestMapping(value = { path + "{code}" }, method = { RequestMethod.GET })
    public @ResponseBody TaskD process(HttpServletRequest request, HttpServletResponse response, @PathVariable String code)
    {

	log.info("[process]" + code);
	
	TaskD task = new TaskD();
	task.setStatus(TaskD.RUNNING);
	
	if (TaskUtils.checkCode(code))
	{	  
	  return task;
	}
	
	task.setId(Util.generateID(taskService.nextId(), StartVariables.NODO_PATTERN, true));
	task.setQuery(code);
	task.setMode(QueryConfD.MODE_MANUAL);
	task.setStart(new Date());
	
	Thread thread = new Thread()
	{
	    public void run()
	    {
	      scheduleTask.listado(code, task, QueryConfD.MODE_MANUAL);
	    }
	};

	thread.start();

	return task;

    }  

}
