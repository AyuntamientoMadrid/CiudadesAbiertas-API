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




import org.ciudadesAbiertas.madrid.dao.EstadisticaDao;
import org.ciudadesAbiertas.madrid.model.Estadistica;
import org.ciudadesAbiertas.madrid.utils.StartVariables;
import org.ciudadesAbiertas.madrid.utils.constants.Constants;
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
@Service("EstadisticaService")
public class EstadisticaService {

	private static final Logger log = LoggerFactory.getLogger(EstadisticaService.class);

	@Autowired
	private EstadisticaDao estadisticasDao;
	
	@Transactional
	public void add(Estadistica Estadistica) {
		estadisticasDao.add(Estadistica);
		log.info("estadistica added");
	}
	
	@Transactional(readOnly = true)
	public int countLastWeek() {
		return estadisticasDao.countLastWeek();		
	}
	
	@Transactional(readOnly = true)
	public Estadistica recent() {
		return estadisticasDao.recent();		
	}
	
	
	@Transactional
	public boolean controlRequesPerSecond() {
		boolean result=true;
		long maximo = 0;
		long maxBBDD = 10;
		try 
		{
			
			maximo = StartVariables.MAX_NUMBER_REQUEST_PER_SECOND;
			
			maxBBDD= estadisticasDao.maxRequestPerSecond_with_JPA_Criteria();
			
			log.info("[controlRequesPerSecond] "+maxBBDD+"//"+maximo);
			result = maximo>maxBBDD;
			
		}catch (Exception e) {
			log.error("Error in the control de max request per day: "+ e.getMessage());
			result=false;
		}
		if (result==false)
		{
			log.info("estadistica control request second [result:"+result+"]");
		}
				
		return result;
	}

	@Transactional(readOnly = true)
	public String mostWanted() {		
		return estadisticasDao.mostWanted();
	}
	
	
	
}