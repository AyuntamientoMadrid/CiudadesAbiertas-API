package org.ciudadesAbiertas.madrid.schedule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.support.CronSequenceGenerator;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.ciudadesAbiertas.madrid.model.dynamic.QueryConfD;
import org.ciudadesAbiertas.madrid.model.dynamic.QueryD;
import org.ciudadesAbiertas.madrid.model.dynamic.TaskD;
import org.ciudadesAbiertas.madrid.service.EmailService;
import org.ciudadesAbiertas.madrid.service.dynamic.ProcessService;
import org.ciudadesAbiertas.madrid.service.dynamic.QueryConfService;
import org.ciudadesAbiertas.madrid.service.dynamic.QueryService;
import org.ciudadesAbiertas.madrid.service.dynamic.TaskService;
import org.ciudadesAbiertas.madrid.utils.StartVariables;
import org.ciudadesAbiertas.madrid.utils.TaskUtils;
import org.ciudadesAbiertas.madrid.utils.Util;
import org.ciudadesAbiertas.madrid.utils.constants.LiteralConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class ScheduleTask {
    private static final Logger log = LoggerFactory.getLogger(ScheduleTask.class);

    @Autowired
    private Environment env;

    @Autowired
    private ProcessService processService;

    @Autowired
    private QueryService queryService;

    @Autowired
    private QueryConfService queryConfService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private EmailService emailService;

    public void runFromScheduler() {
	List<QueryConfD> configurations = queryConfService.list();
	for (Iterator iterator = configurations.iterator(); iterator.hasNext();) {
	    QueryConfD queryConfD = (QueryConfD) iterator.next();
	    if (queryConfD.getMode().contentEquals(QueryConfD.MODE_AUTO)) {
		QueryD recordByCode = queryService.recordByCode(queryConfD.getId());
		// ahora hay que evaluar la expresion cron utilizando la fecha actual pero
		// dejandola en segundo 0
		String cronExpression = queryConfD.getCron();

		if (cronExpression != null) {
		    log.info(queryConfD.getId());

		    CronSequenceGenerator generator = new CronSequenceGenerator(cronExpression);

		    String format = "yyyy-MM-dd'T'HH:mm";

		    Calendar cal = Calendar.getInstance();
		    cal.setTime(new Date());
		    cal.set(Calendar.MILLISECOND, 0);
		    cal.set(Calendar.SECOND, 0);

		    Date actualDate = cal.getTime();
		    String fechaActual = Util.formatearFechas(actualDate, format);

		    log.info("date: " + fechaActual);

		    cal.set(Calendar.MINUTE, cal.get(Calendar.MINUTE) - 1);
		    Date postDate = cal.getTime();
		    String fechaMenos1Minuto = Util.formatearFechas(postDate, format);
		    log.info("less1: " + fechaMenos1Minuto);

		    Date nextRunDate = generator.next(postDate);
		    String fechaSiguienteEjecucion = Util.formatearFechas(nextRunDate, format);

		    log.info("nextExec: " + fechaSiguienteEjecucion);

		    if (fechaActual.contentEquals(fechaSiguienteEjecucion)) {
			log.info("executing");
			TaskD task = new TaskD();
			task.setQuery(queryConfD.getId());
			task.setMode(QueryConfD.MODE_AUTO);
			task.setStatus(TaskD.RUNNING);
			task.setStart(new Date());

			Thread thread = new Thread() {
			    public void run() {
				listado(queryConfD.getId(), task, QueryConfD.MODE_AUTO);
			    }
			};

			thread.start();

		    }

		}
	    }

	}
	
	searchAndCleanRunning();

    }

    // Metodo que busca posibles procesos en ejecución que se hayan quedado en ese estado debido a errores
    private void searchAndCleanRunning() {
	TaskD taskSearch = new TaskD();
	taskSearch.setStatus(TaskD.RUNNING);
	try {
	    List<TaskD> runningList = taskService.list(-1, -1, "", taskSearch);

	    for (Iterator<TaskD> iterator = runningList.iterator(); iterator.hasNext();) {
		TaskD runningTask = (TaskD) iterator.next();
		// Si no esta dentro hay que pasar el estado a error
		if (TaskUtils.checkCode(runningTask.getId()) == false) {
		    log.info("Zombie task founded: "+ runningTask.getId());
		    runningTask.setStatus(TaskD.ERROR);
		    runningTask.setFinish(new Date());
		    taskService.update(runningTask);
		    log.info("Zombie task finished");
		}
	    }
	} catch (Exception e) {
	    log.error("Error searching zombie tasks", e);
	}
    }

    public void listado(String code, TaskD task, String who) {
	log.info("[listado]" + code);
	List<String> errorMessage = new ArrayList<String>();
	StringBuffer buffer = new StringBuffer();
	if (TaskUtils.addCode(code)) {
	    QueryD query = queryService.record(code);
	    QueryConfD configuration = null;

	    if (query == null) {
		log.error("Consulta inexistente: " + query);
		task.setStatus(TaskD.ERROR);
		try {
		    taskService.add(task);
		} catch (Exception e) {
		    log.error("Error adding task", e);
		    errorMessage.add("Error adding task: "+e.getMessage());
		}
		TaskUtils.finishCode(code);
	    } else {
		boolean errors = false;
		try {
		    task = taskService.add(task);
		} catch (Exception e) {
		    log.error("Error adding task", e);
		    TaskUtils.finishCode(code);
		    errorMessage.add("Error adding task: "+e.getMessage());
		    return;
		}
		try {
		    configuration = queryConfService.record(query.getCode());
		} catch (Exception e) {
		    log.error("Error reading configuration", e);
		    errorMessage.add("Error reading configuration: "+e.getMessage());
		    errors = true;
		}

		if (errors == false) {
		    String errores = processService.query(query, configuration);
		    if (errores!=null && !"".equals(errores)) {
		    	errorMessage.add(errores);
		    	errors=true;
		    }
		}
		// task = taskService.record(task.getId());
		task.setFinish(new Date());
		if (errors) {
		    task.setStatus(TaskD.ERROR);
		    buffer.append(LiteralConstants.TEXTO_ERROR_MAIL);
		    buffer.append(StringUtils.LF);
		    for (String errores: errorMessage) {
		    	buffer.append(errores);
			    buffer.append(StringUtils.LF);
		    }
		    buffer.append(LiteralConstants.TEXTO_SALUDOS_MAIL);
		    emailService.sendSimpleMessage(StartVariables.mailTo, StartVariables.mailPrefix + " " + code + " Error" + " " + who, buffer.toString(), StartVariables.mailFrom);
		} else {
		    task.setStatus(TaskD.FINALIZADA);
		    buffer.append(LiteralConstants.TEXTO_NO_ERROR_MAIL);
		    buffer.append(StringUtils.LF);
		    buffer.append(LiteralConstants.TEXTO_SALUDOS_MAIL);
		    emailService.sendSimpleMessage(StartVariables.mailTo, StartVariables.mailPrefix + " " + code + " OK" + " " + who, buffer.toString(), StartVariables.mailFrom);
		}

		try {
		    taskService.update(task);
		} catch (Exception e) {
		    log.error("Error updating task", e);
		}

		TaskUtils.finishCode(code);
	    }
	} else {
	    task.setStatus(TaskD.RUNNING);
	}

    }

}
