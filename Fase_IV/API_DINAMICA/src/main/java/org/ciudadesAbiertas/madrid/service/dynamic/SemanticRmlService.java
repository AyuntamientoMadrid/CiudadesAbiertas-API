package org.ciudadesAbiertas.madrid.service.dynamic;

import java.util.List;

import org.ciudadesAbiertas.madrid.dao.dynamic.SemanticRmlDao;
import org.ciudadesAbiertas.madrid.model.dynamic.SemanticRml;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("SemanticRmlService")
public class SemanticRmlService {

    private static final Logger log = LoggerFactory.getLogger(SemanticRmlService.class);

    @Autowired
    private SemanticRmlDao rmlDao;

    @Transactional(readOnly = true)
    public List<SemanticRml> list(int firstResult, int maxResults) {
	log.info("list");
	return rmlDao.list(firstResult, maxResults);
    }

    @Transactional(readOnly = true)
    public int listRowCount() {
	log.info("listRowCount");
	return rmlDao.listRowCount();
    }

    @Transactional
    public void add(SemanticRml entidad) {
	log.info("add");
	rmlDao.add(entidad);
    }

    

}