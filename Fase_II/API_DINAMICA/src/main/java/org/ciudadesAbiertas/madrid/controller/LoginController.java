package org.ciudadesAbiertas.madrid.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.ciudadesAbiertas.madrid.config.CustomAuthenticationProvider;
import org.ciudadesAbiertas.madrid.model.Estadistica;
import org.ciudadesAbiertas.madrid.service.EstadisticaService;
import org.ciudadesAbiertas.madrid.service.dynamic.QueryService;
import org.ciudadesAbiertas.madrid.utils.SecurityUtils;
import org.ciudadesAbiertas.madrid.utils.StartVariables;
import org.ciudadesAbiertas.madrid.utils.Util;
import org.ciudadesAbiertas.madrid.utils.constants.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController {

    private static final Logger log = LoggerFactory.getLogger(LoginController.class);

    public static final String MODULO = "Login";
    public static final String HOME = "Acceso";
    public static final String LOGOUT = "Desconexión";

    @Autowired
    private CustomAuthenticationProvider authManager;

    @Autowired
    private EstadisticaService estadisticaService;

    @Autowired
    private QueryService queryService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView defaultPage(HttpServletRequest request) {

	log.info("defaultPage");

	return new ModelAndView("redirect:/login");
    }

    @RequestMapping(value = "/login", method = { RequestMethod.GET, RequestMethod.POST })
    public ModelAndView login(@RequestParam(value = Constants.PARAM_ERROR, required = false) String error, @RequestParam(value = Constants.PARAM_LOGOUT, required = false) String logout,
	    @RequestParam(value = Constants.PARAM_TIMEOUT, required = false) String timeout, @RequestParam(value = Constants.PARAM_LOGIN, required = false) String login,
	    @RequestParam(value = Constants.PARAM_CLAVE, required = false) String claveUsuario, @RequestParam(value = Constants.PARAM_CLAVE_APLICA, required = false) String claveAplica,
	    @RequestParam(value = Constants.PARAM_FECHA_CONEXION, required = false) String fechaConexion, HttpServletRequest request, HttpServletResponse response, HttpSession session) {

	log.info("/login");

	log.debug("[" + Constants.PARAM_LOGIN + "][" + login + "]");
	log.debug("[" + Constants.PARAM_CLAVE + "][" + claveUsuario + "]");
	log.debug("[" + Constants.PARAM_CLAVE_APLICA + "][" + claveAplica + "]");
	// log.info("["+Constants.PARAM_FECHA_CONEXION+"]["+fechaConexion+"]");

	ModelAndView model = new ModelAndView();
	model.setViewName("login");

	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	String name = auth.getName(); // get logged in username

	log.info("user logged: " + name);

	// Si recibo los parametros login y clave
	if (Util.validValue(login) && Util.validValue(claveUsuario) && Util.validValue(claveAplica)) {
	    // Autentico contra mi sistema
	    UsernamePasswordAuthenticationToken authReq = new UsernamePasswordAuthenticationToken(login, claveUsuario);
	    auth = authManager.authenticate(authReq);

	    if (claveAplica.equals(StartVariables.UWEB_APP) == false) {
		log.error("La clave de aplicación no coincide con la clave del fichero de configuración");
		model.addObject("error", "Error de autenticación en UWEB");
		return model;
	    }

	    if ((auth != null) && (auth.isAuthenticated())) {
		SecurityContextHolder.getContext().setAuthentication(auth);
		return new ModelAndView("redirect:/home");
	    } else {
		model.addObject("error", "Error de autenticación en UWEB");
		return model;
	    }
	}

	if (error != null) {
	    model.addObject("error", getErrorMessage(request, Constants.SPRING_SECURITY_LAST_EXCEPTION));
	}

	if ((logout != null)) {
	    Commons.auditoria(MODULO, LOGOUT, Constants.AUDITORIA_ESTADO_OK);
	    if (auth != null) {
		new SecurityContextLogoutHandler().logout(request, response, auth);
	    }
	    SecurityContextHolder.getContext().setAuthentication(null);
	    model = new ModelAndView();
	    model.setViewName("login");

	    

	    return model;
	}

	if (Util.validValue(timeout)) {
	    model.addObject("expired", "true");
	    return model;
	}

	if (SecurityUtils.isUser(auth)) {
	    return new ModelAndView("redirect:/home");
	}

	return model;

    }

    @RequestMapping(value = "/home", method = { RequestMethod.GET, RequestMethod.POST })
    public ModelAndView home() {

	log.info("/home");

	ModelAndView model = new ModelAndView();

	// Control para las BBDD erroneas:
	if (!StartVariables.errorDatabaseTypes.isEmpty()) {
	    String errorFicheros = "";
	    for (String value : StartVariables.errorDatabaseTypes.keySet()) {
		errorFicheros += value + " ";
		model.addObject("errorFicheros", errorFicheros);
	    }
	}

	int queries = queryService.listRowCount();
	int lastWeek = estadisticaService.countLastWeek();
	Estadistica lastPetition = estadisticaService.recent();
	String urlMostWanted = estadisticaService.mostWanted();

	String lastPetitionString = "-";
	if (lastPetition != null) {
	    lastPetitionString = Util.dateTimeFormatter.format(lastPetition.getFechaPeticion());
	}
	String mostWanted = "-";
	if (urlMostWanted != null) {
	    mostWanted = urlMostWanted;
	    mostWanted = mostWanted.substring(mostWanted.indexOf(StartVariables.context) + StartVariables.context.length());
	    if (mostWanted.contains("?"))
	    {
		mostWanted=mostWanted.substring(0,mostWanted.indexOf("?"));
	    }
	}
	model.addObject("numQueries", queries);
	model.addObject("lastWeekPetitions", lastWeek);
	model.addObject("lastPetition", lastPetitionString);
	model.addObject("urlMostWanted", mostWanted);

	Commons.controllerCommon(model);

	Commons.auditoria(MODULO, HOME, Constants.AUDITORIA_ESTADO_OK);

	model.setViewName("home");
	return model;

    }

    @RequestMapping(value = "/swagger", method = RequestMethod.GET)
    public ModelAndView swagger() {
	String URL = "swagger/index.html";
	return new ModelAndView("redirect:/" + URL);
    }

    // customize the error message
    private String getErrorMessage(HttpServletRequest request, String key) {

	Exception exception = (Exception) request.getSession().getAttribute(key);

	String error = "";
	if (exception instanceof BadCredentialsException) {
	    error = "invalidUsernamePassword";
	} else if (exception instanceof DisabledException) {
	    error = "disabledError";
	} else if (exception instanceof InternalAuthenticationServiceException) {
	    error = "connectionError";
	} else {
	    error = "internalError";
	}

	log.error("Login error", error);

	return error;
    }

    // for 403 access denied page
    @RequestMapping(value = "/403", method = RequestMethod.GET)
    public ModelAndView accesssDenied() {

	ModelAndView model = new ModelAndView();

	// check if user is login
	Authentication auth = SecurityContextHolder.getContext().getAuthentication();

	if (!(auth instanceof AnonymousAuthenticationToken)) {
	    UserDetails userDetail = (UserDetails) auth.getPrincipal();
	    model.addObject("username", userDetail.getUsername());

	}

	model.setViewName("403");
	return model;

    }

    // for 404 page
    @RequestMapping(value = "/404", method = RequestMethod.GET)
    public ModelAndView notExists() {

	ModelAndView model = new ModelAndView();
	model.setViewName("404");
	return model;

    }

    // for 500 page
    @RequestMapping(value = "/500", method = RequestMethod.GET)
    public ModelAndView internalError() {

	ModelAndView model = new ModelAndView();
	model.setViewName("500");
	return model;

    }

    // Fin de código para controlar Errores

}