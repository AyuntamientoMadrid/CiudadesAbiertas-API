package org.ciudadesAbiertas.madrid.bean;


import org.ciudadesAbiertas.madrid.model.dynamic.TaskD;

public class TaskBean {

    private TaskD taskD;

    public TaskD getTaskD() {
        return taskD;
    }

    public void setTaskD(TaskD taskD) {
        this.taskD = taskD;
    }

    public TaskBean(TaskD taskD) {
	super();
	this.taskD = taskD;
    }
    
    
}
