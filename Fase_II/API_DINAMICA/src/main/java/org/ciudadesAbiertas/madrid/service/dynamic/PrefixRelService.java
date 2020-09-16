package org.ciudadesAbiertas.madrid.service.dynamic;

import java.util.List;

import org.ciudadesAbiertas.madrid.dao.dynamic.RelPrefixDao;
import org.ciudadesAbiertas.madrid.model.dynamic.SemanticPrefix;
import org.ciudadesAbiertas.madrid.model.dynamic.SemanticRelPrefix;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("PrefixRelService")
public class PrefixRelService {

    private static final Logger log = LoggerFactory.getLogger(PrefixRelService.class);

    @Autowired
    private RelPrefixDao relPrefixDao;

      

    @Transactional
    public void add(SemanticRelPrefix entidad) {
	log.info("add");
	relPrefixDao.add(entidad);
    }


    @Transactional(readOnly = true)
    public List<SemanticRelPrefix> getPrefixFromQuery(String query) {
	log.info("getPrefixFromQuery");
	return relPrefixDao.getPrefixFromQuery(query);
    }

    @Transactional
    public void delete(SemanticRelPrefix rel) {
	log.info("delete");
	relPrefixDao.delete(rel);
	
    }

   

   

}