package org.ciudadesAbiertas.madrid.service.dynamic;

import java.util.List;

import org.ciudadesAbiertas.madrid.dao.dynamic.TaskDao;
import org.ciudadesAbiertas.madrid.model.dynamic.TaskD;
import org.ciudadesAbiertas.madrid.utils.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("TaskService")
public class TaskService
{

    private static final Logger log = LoggerFactory.getLogger(TaskService.class);

    @Autowired
    private TaskDao taskDao;

    @Transactional(readOnly = true)
    public List<TaskD> list(int firstResult, int defaultPageSize, String order, TaskD taskSearch)
    {
	log.info("list");
	return taskDao.list(firstResult,defaultPageSize, order, taskSearch);
    }
    
    @Transactional(readOnly = true)
    public List<TaskD> list(int firstResult, int pageSize, String sort)
    {
	log.info("list");
	return taskDao.list(firstResult, pageSize, sort);
    }

    @Transactional(readOnly = true)
    public int listRowCount()
    {
	log.info("listRowCount");
	return taskDao.listRowCount(new TaskD());
    }
    
    @Transactional(readOnly = true)
    public int listRowCount(TaskD taskSearch)
    {
	log.info("listRowCount");
	return taskDao.listRowCount(taskSearch);
    }

    @Transactional(readOnly = true)
    public TaskD record(String id)
    {
	log.info("record");
	return taskDao.record(id);
    }

    @Transactional
    public TaskD add(TaskD entidad)
    {
	log.info("add");
	return taskDao.add(entidad);
    }

    @Transactional
    public void update(TaskD task)
    {
	log.info("update");
	taskDao.update(task);

    }

    @Transactional
    public void delete(TaskD recordToDelete)
    {
	log.info("delete");
	taskDao.delete(recordToDelete);

    }
    

    
    @Transactional(readOnly = true)
    public String nextId()
    {
      	log.info("nextId");
    	return taskDao.nextId();
    }

    @Transactional(readOnly = true)
    public List<String> distinct(String field) {
	log.info("distinct");
    	return taskDao.distinct(field);
	
    }
    
    @Transactional(readOnly = true)
    public String mostUsed() {
	log.info("mostUsed");
    	return taskDao.mostUsed();
	
    }
    
    @Transactional(readOnly = true)
    public String lastWeekTags() {
    	return taskDao.lastDateTags(Util.lastSevenDaysDate());
    }

}