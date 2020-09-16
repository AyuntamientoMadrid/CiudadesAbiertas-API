package org.ciudadesAbiertas.madrid.service.dynamic;

import java.util.List;
import java.util.Map;

import org.ciudadesAbiertas.madrid.dao.dynamic.PrefixDao;
import org.ciudadesAbiertas.madrid.model.dynamic.SemanticPrefix;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("PrefixService")
public class PrefixService {

    private static final Logger log = LoggerFactory.getLogger(PrefixService.class);

    @Autowired
    private PrefixDao prefixDao;

    @Transactional(readOnly = true)
    public List<SemanticPrefix> list(int firstResult, int maxResults) {
	log.info("list");
	return prefixDao.list(firstResult, maxResults);
    }

    @Transactional(readOnly = true)
    public int listRowCount() {
	log.info("listRowCount");
	return prefixDao.listRowCount();
    }

    @Transactional(readOnly = true)
    public SemanticPrefix record(String id) {
	log.info("record");
	return prefixDao.record(id);
    }

    @Transactional
    public void add(SemanticPrefix entidad) {
	log.info("add");
	prefixDao.add(entidad);
    }

    @Transactional
    public void update(SemanticPrefix newQuery, SemanticPrefix oldQuery) {
	log.info("update");
	prefixDao.update(newQuery, oldQuery);

    }

    @Transactional
    public void delete(SemanticPrefix recordToDelete) {
	log.info("delete");
	prefixDao.delete(recordToDelete);

    }
    
    
    @Transactional(readOnly = true)
    public Map<String, SemanticPrefix> queryMap() {
	log.info("queryMap");
	return prefixDao.queryMap();

    }
    
    
    

}