package org.ciudadesAbiertas.madrid.service.dynamic;

import java.util.List;
import java.util.Map;

import org.ciudadesAbiertas.madrid.dao.dynamic.SemanticFieldDao;
import org.ciudadesAbiertas.madrid.model.dynamic.SemanticField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("SemanticFieldService")
public class SemanticFieldService {

    private static final Logger log = LoggerFactory.getLogger(SemanticFieldService.class);

    @Autowired
    private SemanticFieldDao semanticFieldDao;

    @Transactional
    public void add(SemanticField entidad) {
	log.info("add");
	semanticFieldDao.add(entidad);
    }

    @Transactional(readOnly = true)
    public List<SemanticField> getFieldsFromQuery(String query, String order) {
	log.info("getFieldsFromQuery");
	return semanticFieldDao.getFieldsFromQuery(query, order);
    }

    @Transactional
    public void delete(SemanticField field) {
	log.info("delete");
	semanticFieldDao.delete(field);
	
    }
    
    
    @Transactional(readOnly = true)
    public Map<String, List<SemanticField>> getMapQueryFields() {
	log.info("getMapQueryFields");
	return semanticFieldDao.getMapQueryFields();
    }


    

}