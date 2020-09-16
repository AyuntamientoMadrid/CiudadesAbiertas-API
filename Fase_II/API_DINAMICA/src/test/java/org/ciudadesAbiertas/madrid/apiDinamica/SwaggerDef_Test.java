package org.ciudadesAbiertas.madrid.apiDinamica;

import static org.junit.Assert.assertTrue;

import org.ciudadesAbiertas.madrid.model.dynamic.SwaggerDefinition;
import org.junit.Test;

public class SwaggerDef_Test {

    @Test
    public void test() {
	
	SwaggerDefinition def=new SwaggerDefinition();
	def.setCode("code");
	def.setDescription("description");
	def.setText("text");
		
	SwaggerDefinition copia=new SwaggerDefinition(def);
	
	boolean compare=copia.toString().equals(def.toString());
	
	assertTrue(compare);
    }
}
