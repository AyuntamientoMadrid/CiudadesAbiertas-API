package org.ciudadesAbiertas.madrid.descargaMasivaTest;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.Date;

import org.ciudadesAbiertas.madrid.utils.Util;
import org.junit.Test;

public class CrontTest {

    @Test
    public void test1() {
	String cron="0 * * * * *";
	
	Date next = Util.calculateNextExecution(cron);
	
	assertNotNull(next);
	
    }
    
    @Test
    public void test2_too_far() {
	String cron="0 2 3 2 1 3";
	
	Date next = Util.calculateNextExecution(cron);
	
	assertNull(next);
	
    }
    
    
    @Test
    public void test3() {
	String cron="0 2 3 1 *";
	
	Date next = Util.calculateNextExecution(cron);
	
	assertNull(next);
	
    }
    
    
}
