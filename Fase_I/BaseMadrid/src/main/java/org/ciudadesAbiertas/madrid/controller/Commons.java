package org.ciudadesAbiertas.madrid.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.ModelAndView;

public class Commons {

	public static void controllerCommon(ModelAndView model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		model.addObject("userName", auth.getName());
	}
	
}
