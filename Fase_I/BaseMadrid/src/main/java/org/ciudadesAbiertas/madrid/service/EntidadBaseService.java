/**
 * Copyright 2019 Ayuntamiento de A Coruña, Ayuntamiento de Madrid, Ayuntamiento de Santiago de Compostela, Ayuntamiento de Zaragoza, Entidad Pública Empresarial Red.es
 * 
 * This file is part of the Open Cities API, developed within the "Ciudades Abiertas" project (https://ciudadesabiertas.es/).
 * 
 * Licensed under the EUPL, Version 1.2 or – as soon they will be approved by the European Commission - subsequent versions of the EUPL (the "Licence");
 * You may not use this work except in compliance with the Licence.
 * You may obtain a copy of the Licence at:
 * 
 * https://joinup.ec.europa.eu/software/page/eupl
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the Licence is distributed on an "AS IS" basis,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the Licence for the specific language governing permissions and limitations under the Licence.
 */

package org.ciudadesAbiertas.madrid.service;



import java.util.List;

import org.ciudadesAbiertas.madrid.dao.EntidadBaseDao;
import org.ciudadesAbiertas.madrid.model.EntidadBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
 

 
/**
 * @author Juan Carlos Ballesteros (Localidata)
 * @author Carlos Martínez de la Casa (Localidata)
 * @author Oscar Corcho (UPM, Localidata)
 *
 */
@Service("entidadBaseService")
@Transactional
public class EntidadBaseService {
	
	private static final Logger log = LoggerFactory.getLogger(EntidadBaseService.class);
	
	@Autowired
	private EntidadBaseDao entidadBaseDao;    
  
	@Transactional(readOnly = true)
    public List<EntidadBase> list(int firstResult, int maxResults) {
    	log.info("list");
        return entidadBaseDao.list(firstResult, maxResults);
    }
	
	@Transactional(readOnly = true)
	public int listRowCount() {
		log.info("listRowCount");
		return entidadBaseDao.listRowCount();
	}
	

	@Transactional(readOnly = true)
    public EntidadBase record(String id) {
    	log.info("record");
        return entidadBaseDao.record(id);
    }
     
	@Transactional
    public void add(EntidadBase entidad) {
    	log.info("add");
        entidadBaseDao.add(entidad);
    }

	@Transactional
	public void update(String id, EntidadBase entidad) {
		log.info("update");
		 entidadBaseDao.update(id,entidad);
		
	}
	
	@Transactional
	public void delete(String id) {
		log.info("delete");
		entidadBaseDao.delete(id);
		
	}

	

	
    
 
}