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

package org.ciudadesAbiertas.madrid.dao;


import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.ciudadesAbiertas.madrid.exception.NotFoundException;
import org.ciudadesAbiertas.madrid.model.EntidadBase;
import org.ciudadesAbiertas.madrid.utils.StartVariables;
import org.ciudadesAbiertas.madrid.utils.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;






/**
 * @author Juan Carlos Ballesteros (Localidata)
 * @author Carlos Martínez de la Casa (Localidata)
 * @author Oscar Corcho (UPM, Localidata)
 *
 */
@Repository
public class EntidadBaseDao  {

	private static final Logger log = LoggerFactory.getLogger(EntidadBaseDao.class);
	
	@Autowired
	private SessionFactory sessionFactory;
	
	public long count_with_JPA()
	{
		CriteriaBuilder builder = sessionFactory.getCriteriaBuilder();
		CriteriaQuery<Long> criteria = builder.createQuery( Long.class );
		Root<EntidadBase> root = criteria.from( EntidadBase.class );
		criteria.multiselect(builder.count(root.get("id")));
		
		Long idCount = sessionFactory.getCurrentSession().createQuery(criteria).getResultList().get(0);
		
		if (idCount == null)
		{
			idCount = Long.valueOf(0);
		}

		return idCount;
	}
	
	public List<EntidadBase> list(int firstResult, int maxResult) {

		List<EntidadBase> result = new ArrayList<EntidadBase>();
		
		Query<EntidadBase> query = sessionFactory.getCurrentSession().createQuery("FROM EntidadBase",EntidadBase.class);
		
		query.setMaxResults(maxResult);
		query.setFirstResult(firstResult);
		
		result = query.list();
		
		return result;
	}
	
	public int listRowCount() {
		
		Query<EntidadBase> query = sessionFactory.getCurrentSession().createQuery("FROM EntidadBase",EntidadBase.class);
		return query.list().size();		
		
	}
	
	public EntidadBase  record(String id) {

		List<EntidadBase> result = new ArrayList<EntidadBase>();
		
		Query<EntidadBase> query = sessionFactory.getCurrentSession().createQuery("FROM EntidadBase where id like :id",EntidadBase.class);
		query.setParameter("id", id);
		result = query.list();
		
		if (result.size()>0)
		{
			return result.get(0);
		}
		
		return null;
	}
	
	public void add(EntidadBase entidad)
	{
		Session session = sessionFactory.getCurrentSession();
		
		if (Util.validValue(entidad.getId())==false)
		{		
			long count_with_JPA = count_with_JPA();
			log.debug("count_with_JPA:"+count_with_JPA);
			long numOfRows = count_with_JPA+1;		
			int randomNum=Util.genRandomNum(1000, 9999);		
			String id=numOfRows+""+ randomNum;
			entidad.setId(Util.generateID(id, StartVariables.NODO_PATTERN, true));		
		}
		session.save(entidad);
	}

	public void update(String id, EntidadBase entidad) {

		Session session = sessionFactory.getCurrentSession();						
		entidad.setId(id);				
		session.update(entidad);
		
	}

	public void delete(String id) {
		Session session = sessionFactory.getCurrentSession();
		
		List<EntidadBase> result = new ArrayList<EntidadBase>();
		
		Query<EntidadBase> query = sessionFactory.getCurrentSession().createQuery("FROM EntidadBase where id like :id",EntidadBase.class);
		query.setParameter("id", id);
		result = query.list();
		
		if (result.size()>0)
		{		
			session.delete(result.get(0));
		}else {
			throw new NotFoundException(id+" not found to delete");
		}
		
	}

	
	
	
	
}