package org.ciudadesAbiertas.madrid.apiDinamica;

import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.Set;

import org.ciudadesAbiertas.madrid.model.dynamic.ParamD;
import org.ciudadesAbiertas.madrid.model.dynamic.QueryD;
import org.junit.Test;

public class QueryD_Test {

    @Test
    public void test() {
	
	ParamD param=new ParamD();
	param.setName("p1");
	param.setExample("casa");
	
	Set<ParamD> params=new HashSet<ParamD>();
	params.add(param);
	
	QueryD query=new QueryD();
	query.setCode("default");
	query.setDatabase("default");
	query.setSummary("Consulta de test");
	query.setTags("test");
	query.setTexto("Select * from dual");
	query.setParams(params);
	
	QueryD copia=new QueryD(query);
	
	boolean compare=copia.toString().equals(query.toString());
	
	assertTrue(compare);
    }
}
