package org.ciudadesAbiertas.madrid.controller;

import java.util.Date;

import org.ciudadesAbiertas.madrid.utils.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.ModelAndView;

public class Commons {
	
	private static final String RESULTADO = ", Resultado: ";
	private static final String USUARIO = ", Usuario: ";
	private static final String SEPARADOR = ", ";
	private static final String AUDITORIA = "Auditoria: ";
	private static final Logger log = LoggerFactory.getLogger(Commons.class);
	

	public static void controllerCommon(ModelAndView model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		model.addObject("userName", auth.getName());
	}
	
	
	public static void auditoria(String modulo, String operacion, String estado)
	{
		String userName=SecurityContextHolder.getContext().getAuthentication().getName();	
		String hora=Util.formatearFechas(new Date());
		
		log.info(AUDITORIA+hora+SEPARADOR+modulo+SEPARADOR+operacion+USUARIO+userName+RESULTADO+estado);
	}
}
