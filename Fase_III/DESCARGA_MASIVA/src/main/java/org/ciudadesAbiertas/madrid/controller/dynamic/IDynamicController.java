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

package org.ciudadesAbiertas.madrid.controller.dynamic;

import java.io.IOException;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ciudadesAbiertas.madrid.exception.BadRequestException;
import org.ciudadesAbiertas.madrid.exception.InternalErrorException;
import org.ciudadesAbiertas.madrid.utils.ExceptionUtil;
import org.ciudadesAbiertas.madrid.utils.Util;
import org.ciudadesAbiertas.madrid.utils.constants.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * @author Juan Carlos Ballesteros (Localidata)
 * @author Carlos Martínez de la Casa (Localidata)
 * @author Oscar Corcho (UPM, Localidata)
 *
 */
public interface IDynamicController
{	
	
	
	static final Logger log = LoggerFactory.getLogger(IDynamicController.class);
	
		
	


	
	default void sendBadRequestError(String ext, ServletRequest request, ServletResponse response) throws IOException {	
		
		BadRequestException errorObj = new BadRequestException("Bad Request Error. Extension '"+ext+"' not valid");		
		createResponseError(request, response, errorObj);

	}
	
		
	default void createResponseError(ServletRequest request, ServletResponse response,Exception errorObj) throws IOException {
		createResponseError( request,  response, errorObj,null);
	}
	
	@SuppressWarnings("unchecked")
	default void createResponseError(ServletRequest request, ServletResponse response,Exception errorObj,String uri) throws IOException {
		
		String contextPath = ((HttpServletRequest) request).getServletPath();
		String contentType = "";
		if (((HttpServletRequest) request).getHeader(Constants.HEADDER_ACCEPT) != null)
		{
			contentType = ((HttpServletRequest) request).getHeader(Constants.HEADDER_ACCEPT);
		}

		if (errorObj instanceof BadRequestException)
		{
			((HttpServletResponse) response).setStatus(HttpServletResponse.SC_BAD_REQUEST);		
		}
		else if (errorObj instanceof InternalErrorException)
		{
			((HttpServletResponse) response).setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);		
		}
		
		response.setCharacterEncoding(Constants.ENCODING_UTF8);
		ResponseEntity<Object> responseEntity= new ResponseEntity<>(HttpStatus.OK);
		if (errorObj!=null) {
			responseEntity=(ResponseEntity<Object>) ExceptionUtil.checkException(errorObj);
		}else{
			HttpHeaders headers = new HttpHeaders();
			response.setCharacterEncoding(Constants.ENCODING_UTF8);
			headers.add(Constants.HEADER_LOCATION, uri.replace(uri, uri+".html"));
			responseEntity=new ResponseEntity<Object>(headers,HttpStatus.SEE_OTHER);
		}
		// json by default
		if (contextPath.endsWith(Constants.EXT_JSON) || contentType.contains(Constants.FORMATO_JSON)) {
			response.setContentType(Constants.CONTENT_TYPE_JSON_UTF8);
			response.getWriter().println(Util.convertObjectToJson(responseEntity));
		}
		else if (contextPath.endsWith(Constants.EXT_XML) || contentType.contains(Constants.FORMATO_XML)) {
			response.setContentType(Constants.CONTENT_TYPE_XML_UTF8);
			response.getWriter().println(Util.convertObjectToXML(responseEntity));			
		}
		else {
			response.setContentType(Constants.CONTENT_TYPE_HTML_XML_UTF8);			
			response.getWriter().println(Util.convertObjectToJson(responseEntity));
		}
		
		closeOutput(response);

	}
	
	//Función para cerrar el flujo de salida cuando devolvemos  error
	default void closeOutput(ServletResponse response) 
	{
		try
		{
			response.getWriter().flush();
			response.getWriter().close();
			log.debug("Output closed");
		}
		catch (Exception e1)
		{
			log.error("Error closing output in captured error",e1);
		}
	}
	

}
