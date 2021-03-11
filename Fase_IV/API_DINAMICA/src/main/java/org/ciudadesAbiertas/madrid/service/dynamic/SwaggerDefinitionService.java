package org.ciudadesAbiertas.madrid.service.dynamic;

import java.util.List;

import org.ciudadesAbiertas.madrid.dao.dynamic.SwaggerDefinitionDao;
import org.ciudadesAbiertas.madrid.model.dynamic.QueryD;
import org.ciudadesAbiertas.madrid.model.dynamic.SwaggerDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



@Service("SwaggerDefinitionService")
public class SwaggerDefinitionService {
	
	private static final Logger log = LoggerFactory.getLogger(SwaggerDefinitionService.class);

	@Autowired
	private SwaggerDefinitionDao swaggerDefinitionDao;
	
	
	@Transactional(readOnly = true)
    public List<SwaggerDefinition> list(int firstResult, int maxResults) {
    	log.info("list");
        return swaggerDefinitionDao.list(firstResult, maxResults);
    }
	
	@Transactional(readOnly = true)
	public int listRowCount() {
		log.info("listRowCount");
		return swaggerDefinitionDao.listRowCount();
	}
	
	

	@Transactional
	public void add(SwaggerDefinition def) {

		swaggerDefinitionDao.add(def);

	}

	@Transactional
	public void update(SwaggerDefinition oldDefinition, SwaggerDefinition newDefinition, List<QueryD> queriesWithThisDef) {

		//asignamos el nuevo codigo de definicion
		for (QueryD query:queriesWithThisDef)
		{
			query.setDefinition(newDefinition.getCode());
		}	
		
		swaggerDefinitionDao.update(oldDefinition, newDefinition, queriesWithThisDef);

	}

	
	@Transactional
	public SwaggerDefinition find(String code) {
		return swaggerDefinitionDao.find(code);
	}

	@Transactional
	public void remove(SwaggerDefinition def) {
		swaggerDefinitionDao.remove(def);

	}

}