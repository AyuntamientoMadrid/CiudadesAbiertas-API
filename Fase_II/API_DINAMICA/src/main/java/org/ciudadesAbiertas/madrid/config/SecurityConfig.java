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

import java.util.ArrayList;
import java.util.List;

import org.ciudadesAbiertas.madrid.controller.dynamic.ApiJsonController;
import org.ciudadesAbiertas.madrid.controller.dynamic.DynamicController;
import org.ciudadesAbiertas.madrid.controller.form.QueryController;
import org.ciudadesAbiertas.madrid.controller.form.SwaggerDefinitionController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author Juan Carlos Ballesteros (Localidata)
 * @author Carlos Martínez de la Casa (Localidata)
 * @author Oscar Corcho (UPM, Localidata)
 *
 */

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter
{

  @Autowired
  @Qualifier("userDetailsService")
  private UserDetailsService userDetailsService;

  @Autowired
  Environment env;

  @Autowired
  CustomAuthenticationProvider customAuthenticationProvider;

  @Autowired
  public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception
  {
    auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());

    // auth.authenticationProvider(new CustomAuthenticationProvider());

  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception
  {
    auth.authenticationProvider(customAuthenticationProvider);
  }

  /*
   * @Override protected void configure(AuthenticationManagerBuilder auth) throws
   * Exception { // SecurityContextHolder.getContext().getAuthentication();
   * auth.authenticationProvider(new CustomAuthenticationProvider()); }
   */

  @Override
  public void configure(WebSecurity web) throws Exception
  {
    web.ignoring().antMatchers("/resources/**");
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception
  {

    String https = env.getProperty("https");
    if (https != null && https.toLowerCase().equals("on"))
    {
      // Only https access
      http.requiresChannel().anyRequest().requiresSecure();
    }

    http.sessionManagement().invalidSessionUrl("/login?timeout");

    List<String> roleAnonymousURLs = new ArrayList<String>();
    roleAnonymousURLs.add(DynamicController.path + "**");
    roleAnonymousURLs.add(ApiJsonController.path);

    List<String> roleUserURLs = new ArrayList<String>();
    roleUserURLs.add("/home/**");
    roleUserURLs.add(QueryController.LIST + "/**");
    roleUserURLs.add(SwaggerDefinitionController.LIST + "/**");

    for (String tempURL : roleAnonymousURLs)
    {
      http.authorizeRequests().antMatchers(tempURL).permitAll();
    }

    for (String tempURL : roleUserURLs)
    {
      http.authorizeRequests().antMatchers(tempURL).access("hasRole('ROLE_USER')").and().formLogin().loginPage("/login").failureUrl("/login?error").usernameParameter("username").passwordParameter("password")
	  .successHandler(new MyAuthenticationSuccessHandler()).and().logout().logoutSuccessUrl("/login?logout").and().csrf().and().exceptionHandling().accessDeniedPage("/403");
    }

  }

  @Bean
  public PasswordEncoder passwordEncoder()
  {
    PasswordEncoder encoder = new BCryptPasswordEncoder();

    return encoder;
  }

  // Esta funcion es para que funcione el authorization server
  // También necesario para el filtro de Login
  @Bean
  @Override
  public AuthenticationManager authenticationManagerBean() throws Exception
  {
    return super.authenticationManagerBean();
  }

}