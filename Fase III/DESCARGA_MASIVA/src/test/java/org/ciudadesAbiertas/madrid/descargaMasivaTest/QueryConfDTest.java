package org.ciudadesAbiertas.madrid.descargaMasivaTest;

import static org.junit.Assert.*;

import org.ciudadesAbiertas.madrid.model.dynamic.QueryConfD;
import org.ciudadesAbiertas.madrid.model.dynamic.QueryD;
import org.junit.Test;

public class QueryConfDTest {

    @Test
    public void test1() {
	QueryConfD queryConf=new QueryConfD();
	queryConf.setCron("0 * * * * *");
	queryConf.setMinute("*");
	queryConf.setHour("*");
	queryConf.setDayM("*");
	queryConf.setDayW("*");
	queryConf.setMonth("*");
	queryConf.setItems(0);
	queryConf.setMode("auto");
	queryConf.setId("TestConf");
	queryConf.setOverwrite(true);
	queryConf.setPath("D:\\temp\\prueba");
	queryConf.setZip(true);
	
	QueryConfD copia=new QueryConfD(queryConf);
	
        boolean compare=copia.toString().equals(queryConf.toString());
	
	assertTrue(compare);
    }
    
    @Test
    public void test2() {
	QueryD query=new QueryD();
	query.setCode("a1");
	query.setDatabase("default");
	query.setSummary("Query A");
	query.setTexto("Texto Query A");
	
	QueryD anotherQuery=new QueryD("a1","default","Query A","Texto Query A");
	
	boolean compare=query.toString().equals(anotherQuery.toString());
	
	assertTrue(compare);
    }

}
