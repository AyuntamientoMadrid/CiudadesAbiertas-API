package org.ciudadesAbiertas.madrid.apiDinamica;

import static org.junit.Assert.assertTrue;

import org.ciudadesAbiertas.madrid.model.dynamic.ParamD;
import org.junit.Test;

public class ParamD_Test {

    @Test
    public void test() {
	
	ParamD param=new ParamD();
	param.setName("p1");
	param.setExample("casa");
	param.setDescription("descripcion");
	param.setId(11111);
	param.setQuery(null);
		
	ParamD copia=new ParamD(param);
	
	boolean compare=copia.toString().equals(param.toString());
	
	assertTrue(compare);
    }
}
