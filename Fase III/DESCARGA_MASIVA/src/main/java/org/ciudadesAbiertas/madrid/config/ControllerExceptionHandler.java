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

package org.ciudadesAbiertas.madrid.config;

import org.ciudadesAbiertas.madrid.exception.BadRequestException;
import org.ciudadesAbiertas.madrid.exception.InternalErrorException;
import org.ciudadesAbiertas.madrid.exception.TooManyRequestException;
import org.ciudadesAbiertas.madrid.utils.ErrorBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author Juan Carlos Ballesteros (Localidata)
 * @author Carlos Martínez de la Casa (Localidata)
 * @author Oscar Corcho (UPM, Localidata)
 *
 */
@ControllerAdvice
public class ControllerExceptionHandler
{

	private static final Logger log = LoggerFactory.getLogger(ControllerExceptionHandler.class);

	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ModelAndView processMethodNotSupportedException(Exception exception)
	{
		ModelAndView mv = new ModelAndView();
		mv.addObject("exception", exception);		
		mv.setViewName("error");		
		log.error("Generic exception",exception);
		return mv;
	}

	@ExceptionHandler(TooManyRequestException.class)
	@ResponseStatus(HttpStatus.TOO_MANY_REQUESTS)
	public @ResponseBody ErrorBean  processTooManyRequestException(Exception exception)
	{
		ErrorBean bean = new ErrorBean();
		bean.setName("Demasiadas peticiones");
		bean.setDescription("Se ha superado el número máximo de peticiones por segundo");
		log.error("TooManyRequestException",exception);
		return bean;
	}
	
	@ExceptionHandler(BadRequestException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public @ResponseBody ErrorBean  processBadRequestException(BadRequestException exception)
	{
		ErrorBean bean = new ErrorBean();
		bean.setName(exception.getMessage());
		bean.setDescription(exception.getDescription());
		log.error("BadRequestException",exception);
		return bean;
	}

	@ExceptionHandler(InternalErrorException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public @ResponseBody ErrorBean  processInternalErrorException(InternalErrorException exception)
	{
		ErrorBean bean = new ErrorBean();
		bean.setName("Error Interno");
		bean.setDescription(exception.getMessage());
		log.error("InternalErrorException",exception);
		return bean;
	}
	
	
	
}
