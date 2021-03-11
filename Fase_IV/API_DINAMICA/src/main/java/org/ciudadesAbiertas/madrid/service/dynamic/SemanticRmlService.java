package org.ciudadesAbiertas.madrid.service.dynamic;

import java.util.List;

import org.ciudadesAbiertas.madrid.dao.dynamic.SemanticRmlDao;
import org.ciudadesAbiertas.madrid.model.dynamic.SemanticField;
import org.ciudadesAbiertas.madrid.model.dynamic.SemanticRelPrefix;
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
public List<SemanticRml> listAll() {
  log.info("listAll");
  return rmlDao.listAll();
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

@Transactional(readOnly = true)
public List<String> queriesWithRML() {
  log.info("queriesWithRML");
  return rmlDao.queriesWithRML();
}

@Transactional(readOnly = true)
public SemanticRml record(String id) {
  log.info("record");
  return rmlDao.record(id);

}

@Transactional(readOnly = true)
public SemanticRml recordByQuery(String code) {
  log.info("recordByQuery");
  return rmlDao.recordByQuery(code);
}

@Transactional
public void addComplex(SemanticRml rml, SemanticField rdfType, List<SemanticField> semanticFields, List<SemanticRelPrefix> relPrefixesList) {
  log.info("addComplex");
  rmlDao.addComplex(rml, rdfType, semanticFields, relPrefixesList);
}

@Transactional
public void removeComplex(SemanticRml rml, SemanticField rdfType, List<SemanticField> semanticFields, List<SemanticRelPrefix> relPrefixesList) {
  log.info("removeComplex");
  rmlDao.removeComplex(rml, rdfType, semanticFields, relPrefixesList);

}

@Transactional
public void updateComplex(SemanticRml rml, SemanticField rdfType, List<SemanticField> semanticFields, List<SemanticRelPrefix> relPrefixesList, SemanticRml oldRmlObj, SemanticField oldType, List<SemanticField> oldFieldsFromQuery,
	List<SemanticRelPrefix> oldPrefixFromQuery) {
  log.info("updateComplex");
  rmlDao.updateComplex(rml, rdfType, semanticFields, relPrefixesList,oldRmlObj, oldType, oldFieldsFromQuery, oldPrefixFromQuery);
  

}

}