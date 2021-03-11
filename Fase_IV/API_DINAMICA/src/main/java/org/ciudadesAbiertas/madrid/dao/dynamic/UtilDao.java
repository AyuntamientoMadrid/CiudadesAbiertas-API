package org.ciudadesAbiertas.madrid.dao.dynamic;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.ciudadesAbiertas.madrid.model.dynamic.SemanticField;
import org.ciudadesAbiertas.madrid.model.dynamic.SemanticPrefix;
import org.ciudadesAbiertas.madrid.model.dynamic.SemanticRelPrefix;
import org.ciudadesAbiertas.madrid.model.dynamic.SemanticRml;
import org.ciudadesAbiertas.madrid.utils.Util;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.springframework.stereotype.Repository;

@Repository
public class UtilDao {
    
    private static final int IDENTIFIER_COUNTER_LENGTH = 10;
    private static final int IDENTIFIER_CLASS_LENGTH = 4;

    private static Map<String,Integer> mapTableCode =new HashMap<String,Integer>();
    
    private static int DEFAULT_CODE=9999;    
    static
    {
	mapTableCode.put(SemanticPrefix.class.getSimpleName(),200);
	mapTableCode.put(SemanticRelPrefix.class.getSimpleName(),201);
	mapTableCode.put(SemanticRml.class.getSimpleName(),202);
	mapTableCode.put(SemanticField.class.getSimpleName(),203);
    }
    
    public static synchronized int maxId(Session session, Class c) {	
	Criteria criteria = session.createCriteria(c.getClass()).setProjection(Projections.rowCount());
	Integer idCount = Integer.valueOf(0);
	Object uniqueResult = criteria.uniqueResult();
	if (uniqueResult != null) {
	    idCount = Integer.parseInt(uniqueResult.toString());

	}
	return idCount.intValue();    
    }
    
    
    public static int getCodeFromClass(String className)
    {
	Integer value=mapTableCode.get(className);
	if (value!=null)
	{
	    return value.intValue();
	}
	else
	{
	    return DEFAULT_CODE;
	}	
    }
    
    /*Generador del prefijo para insertar identificador en bbdd (recibe el id)*/
    public static String getSuffixForId(String className, int id)
    {
	return StringUtils.leftPad(UtilDao.getCodeFromClass(className)+"",IDENTIFIER_CLASS_LENGTH,"0")+StringUtils.leftPad(id+"", IDENTIFIER_COUNTER_LENGTH, "0")+Util.genRandomNum(1000, 9999);
    }
    
    
    

}