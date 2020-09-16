package org.ciudadesAbiertas.madrid.service.dynamic;

import java.util.List;

import org.ciudadesAbiertas.madrid.dao.dynamic.ParamDao;
import org.ciudadesAbiertas.madrid.model.dynamic.ParamD;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;





@Service("ParamService")
public class ParamService {

	@Autowired
	private ParamDao paramDao;

	
	
	@Transactional
	public List<ParamD> list(String code) {

		return paramDao.list(code);

	}



	
	
	

}