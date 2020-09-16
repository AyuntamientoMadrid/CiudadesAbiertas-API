package org.ciudadesAbiertas.madrid.descargaMasivaTest;

import static org.junit.Assert.*;

import org.ciudadesAbiertas.madrid.model.dynamic.QueryD;
import org.junit.Test;

public class QueryDTest {

    @Test
    public void test1() {
	QueryD query=new QueryD();
	query.setCode("a1");
	query.setDatabase("default");
	query.setSummary("Query A");
	query.setTexto("Texto Query A");
	
	QueryD anotherQuery=new QueryD(query);
	
	boolean compare=query.toString().equals(anotherQuery.toString());
	
	assertTrue(compare);
    }
    
    

}
