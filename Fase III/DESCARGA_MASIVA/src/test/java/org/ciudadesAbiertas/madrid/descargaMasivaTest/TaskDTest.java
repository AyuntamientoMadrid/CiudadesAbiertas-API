package org.ciudadesAbiertas.madrid.descargaMasivaTest;

import static org.junit.Assert.*;

import java.util.Date;

import org.ciudadesAbiertas.madrid.model.dynamic.QueryD;
import org.ciudadesAbiertas.madrid.model.dynamic.TaskD;
import org.junit.Test;

public class TaskDTest {

    @Test
    public void test1() {
	TaskD task=new TaskD();
	task.setId("t1");
	task.setStatus("Terminado");
	task.setQuery("Q1");
	task.setStart(new Date());
	task.setFinish(new Date());
	
	TaskD copy=new TaskD(task);
	
	boolean compare=task.toString().equals(copy.toString());
	
	assertTrue(compare);
    }
    
    

}
