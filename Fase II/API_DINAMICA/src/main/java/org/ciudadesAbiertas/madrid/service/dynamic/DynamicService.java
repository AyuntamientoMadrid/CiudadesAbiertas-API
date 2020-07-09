package org.ciudadesAbiertas.madrid.service.dynamic;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.ciudadesAbiertas.madrid.dao.dynamic.DynamicDao;
import org.ciudadesAbiertas.madrid.dao.dynamic.DynamicDaoMultipleDatabase;
import org.ciudadesAbiertas.madrid.exception.InternalErrorException;
import org.ciudadesAbiertas.madrid.model.dynamic.ParamD;
import org.ciudadesAbiertas.madrid.model.dynamic.QueryD;
import org.ciudadesAbiertas.madrid.utils.constants.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



@Service("GenericService")
public class DynamicService  {

	@Autowired
	private DynamicDao dynamicDao;
	
	@Autowired
	private DynamicDaoMultipleDatabase dynamicDaoMultipleDatabase;
	
	@Autowired
	private Environment env;
	
	@Transactional(readOnly=true)	
	public List<Object> query(String code, String database, String page, String pageSize) throws InternalErrorException  {	
		
		int actualPage=Integer.parseInt(page);
		if (actualPage<1)
		{
			actualPage=1;
		}
		int size=Integer.parseInt(pageSize);
		String maxSizeString = env.getProperty("pagina.maximo");
		if (maxSizeString==null)
			maxSizeString="50";
		int maxSize=Integer.parseInt(maxSizeString);
		if(size>maxSize){
			size=maxSize;
		}
		
		//restamos 1 a la pagina que se nos ha enviado (porque la primera página es la numero 0)
		actualPage--;	
		
		List<Object> results = new ArrayList<Object>();
		
		if (database.equals(Constants.DEFAULT_DATABASE))
			results = dynamicDao.query(database, code,actualPage,size);
		else 
			results = dynamicDaoMultipleDatabase.query(database,code,actualPage,size);
		
				
		return results;		
	}
	
	@Transactional(readOnly=true)	
	public long rowCount(String code, String database) throws InternalErrorException  {	
		
		long result = 0;
		
		if (database.equals(Constants.DEFAULT_DATABASE))
			result = dynamicDao.rowCount(database, code);
		else 
			result = dynamicDaoMultipleDatabase.rowCount(database,code);
		
				
		return result;		
	}

	
	
			


	@Transactional(readOnly=true)	
	public Map<String,String> typesQuery(QueryD query, List<ParamD> parameters) throws Exception {
		String database=query.getDatabase();
		
		if (database.equals(Constants.DEFAULT_DATABASE))
		{
			return dynamicDao.typesQuery(query, parameters);
		}
		else
		{
			return dynamicDaoMultipleDatabase.typesQuery(query,parameters);
		}		
	}

}