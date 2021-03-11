package org.ciudadesAbiertas.madrid.config;

import java.rmi.RemoteException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import org.ciudadesAbiertas.madrid.utils.StartVariables;
import org.ciudadesAbiertas.madrid.utils.Util;
import org.ciudadesAbiertas.madrid.utils.constants.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import iam.uweb.beans.UwebBean;
import iam.uweb.ws.ServicesUwebProxy;

public class CustomAuthenticationProvider implements AuthenticationProvider  {
	
	private static final Logger log = LoggerFactory.getLogger(CustomAuthenticationProvider.class);
	
	@Autowired
	private Environment env;
	
	@Autowired
	private UserDetailsService userDetailsService;
		
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        
		//Parametros de autenticación del formulario de login
		String username = authentication.getName();        
        
        
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        grantedAuthorities.add(new SimpleGrantedAuthority(Constants.ROLE_USER));
        
        if ((StartVariables.tipoAutenticacion.equals(Constants.TIPO_AUTENTICACION_MIXTA))||(StartVariables.tipoAutenticacion.equals(Constants.TIPO_AUTENTICACION_BBDD)))
        {
        	String password = authentication.getCredentials().toString();
        	 //Replica de autorización standard
	        UserDetails user = userDetailsService.loadUserByUsername(username);
	        if (user!=null)
	        {                
		        boolean passOk=passwordEncoder.matches(password, user.getPassword());        
		        if (user != null && (user.getUsername().equals(username) && passOk)) {
		        	Authentication auth = new UsernamePasswordAuthenticationToken(username, password, user.getAuthorities());
		        	return auth;
		        }else {
		        	return null;  
		        }
	        }
	        //Fin replica autorización standard        
        }       
        
        
        if ((StartVariables.tipoAutenticacion.equals(Constants.TIPO_AUTENTICACION_MIXTA))||(StartVariables.tipoAutenticacion.equals(Constants.TIPO_AUTENTICACION_UWEB)))
        {
        	String claveUsuario = authentication.getCredentials().toString();
	        boolean resultado=llamadaWebService(username, claveUsuario);
	        if ((resultado))
	        {	
	        	return new UsernamePasswordAuthenticationToken(username, claveUsuario, grantedAuthorities);
	        }
        }
        
        return null;  

        
    }

    private boolean llamadaWebService(String username, String claveUsuario) {
    	
    	String claveAplica=StartVariables.UWEB_APP; 
    	Integer claveAplicaInteger = 0;
    	Integer claveUsuarioInteger = 0;
    	//Tratamineto de claveAplica
    	try  {
	    	 claveAplicaInteger = Integer.valueOf(claveAplica);
	    	//Tratamineto de claveUsuario
	    	 claveUsuarioInteger = Integer.valueOf(claveUsuario);
    	}catch (NumberFormatException numEx) {
    		log.error("[Error al obtener] [claveAplicaInteger:"+claveAplicaInteger+"] [claveUsuarioInteger:"+claveUsuarioInteger+"] [Error:"+numEx.getMessage()+"]");
    	}
    	
    	ServicesUwebProxy uWebClient = new ServicesUwebProxy();        	
    	
    	String uWebURI=env.getProperty("UWEB_URL");
    	
    	log.info("Quering UWEB: "+uWebURI);
    	
    	uWebClient.setEndpoint(uWebURI);
    	
    	UwebBean userBean=new UwebBean();
    	userBean.setLogin(username);
    	userBean.setPassword(claveUsuario);
    	userBean.setAppKey(claveAplicaInteger);
    	userBean.setUserKey(claveUsuarioInteger);
    	
    	log.debug("Userbean params:");
    	log.debug("\t login: "+userBean.getLogin());
    	log.debug("\t password: "+userBean.getPassword());
    	log.debug("\t appKey: "+userBean.getAppKey());
    	log.debug("\t userKey: "+userBean.getUserKey());
    	
    	String applicationsUserList = "";
		try {
			applicationsUserList = uWebClient.getApplicationsUserList(userBean);
			log.info("uWebResponse:"+applicationsUserList);
		} catch (RemoteException e) {			
			log.error("Error invocando uWeb",e);			
		}

		List<String> xmlToStringList = Util.xmlToStringList(applicationsUserList, "/APLICACIONES/APLICACION/CLAVE_APLICACION/text()");
    	log.debug("UWEB USER APPS: "+xmlToStringList.toString());
    	if (xmlToStringList.contains(StartVariables.UWEB_APP.trim()))
    	{
    		log.info("This app("+StartVariables.UWEB_APP.trim()+") is in the user list");
    		return true;
    	}
    	else 
    	{    	
    		log.info("This app("+StartVariables.UWEB_APP.trim()+") is in the user list");
    		return false;
    	}
	}

	@Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
	
	public String getMD5(String data) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance("MD5");

        messageDigest.update(data.getBytes());
        byte[] digest = messageDigest.digest();
        StringBuffer sb = new StringBuffer();
        for (byte b : digest) {
            sb.append(Integer.toHexString((int) (b & 0xff)));
        }
        return sb.toString();
    }
}
