package org.ciudadesAbiertas.madrid.config;

import java.io.IOException;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ciudadesAbiertas.madrid.utils.constants.Constants;
import org.springframework.security.core.Authentication;

import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

public class MyAuthenticationSuccessHandler implements AuthenticationSuccessHandler
{
	//Metodo para setear el tiempo maximo de session
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException 
    {
        request.getSession(false).setMaxInactiveInterval(60*Constants.MinutosInactividad);
        response.sendRedirect(request.getContextPath());
    }
}
