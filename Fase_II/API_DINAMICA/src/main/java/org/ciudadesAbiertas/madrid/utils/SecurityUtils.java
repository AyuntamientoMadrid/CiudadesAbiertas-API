package org.ciudadesAbiertas.madrid.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.ciudadesAbiertas.madrid.utils.constants.Constants;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

public class SecurityUtils {

	public static boolean isUser(Authentication auth) {
		return isRole(auth,Constants.ROLE_USER);
	}
	
	public static boolean isRole(Authentication auth, String role) {
		boolean isAdmin=false;
		Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
		
		List<String> roles = new ArrayList<String>();
		 
        for (GrantedAuthority a : authorities) {
            roles.add(a.getAuthority());
        }
		
		if (roles.contains(role))
			isAdmin=true;
		
		return isAdmin;
	}
	
}
