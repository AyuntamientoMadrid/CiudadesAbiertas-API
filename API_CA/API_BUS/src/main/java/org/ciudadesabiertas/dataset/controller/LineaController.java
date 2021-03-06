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

package org.ciudadesabiertas.dataset.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaQuery;
import javax.servlet.http.HttpServletRequest;

import org.ciudadesabiertas.controller.CiudadesAbiertasController;
import org.ciudadesabiertas.controller.GenericController;
import org.ciudadesabiertas.dataset.model.Linea;
import org.ciudadesabiertas.dataset.model.Operator;
import org.ciudadesabiertas.dataset.model.Parada;
import org.ciudadesabiertas.dataset.model.Route;
import org.ciudadesabiertas.dataset.utils.BusConstants;
import org.ciudadesabiertas.dataset.utils.LineStringUtil;
import org.ciudadesabiertas.dataset.utils.LineaResult;
import org.ciudadesabiertas.dataset.utils.LineaSearch;
import org.ciudadesabiertas.dataset.utils.OperatorSearch;
import org.ciudadesabiertas.dataset.utils.ParadaSearch;
import org.ciudadesabiertas.dataset.utils.RouteSearch;
import org.ciudadesabiertas.exception.DAOException;
import org.ciudadesabiertas.service.DatasetService;
import org.ciudadesabiertas.utils.Constants;
import org.ciudadesabiertas.utils.DistinctSearch;
import org.ciudadesabiertas.utils.ObjectResult;
import org.ciudadesabiertas.utils.RequestType;
import org.ciudadesabiertas.utils.Result;
import org.ciudadesabiertas.utils.ResultError;
import org.ciudadesabiertas.utils.SecurityURL;
import org.ciudadesabiertas.utils.StartVariables;
import org.ciudadesabiertas.utils.SwaggerConstants;
import org.ciudadesabiertas.utils.Util;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.github.tennaito.rsql.jpa.JpaCriteriaQueryVisitor;

import cz.jirutka.rsql.parser.ast.RSQLVisitor;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Authorization;
import springfox.documentation.annotations.ApiIgnore;




/**
 * @author Juan Carlos Ballesteros (Localidata)
 * @author Carlos Martínez de la Casa (Localidata)
 * @author Hugo Lafuente Matesanz (Localidata)
 * @author Oscar Corcho (UPM, Localidata) 
 */
@SuppressWarnings("rawtypes")
@RestController
@Api(value="Linea",description = "Conjunto de operaciones relacionadas con el conjunto de datos Línea"+SwaggerConstants.VOCABULARIO_A_HREF+BusConstants.BusVocabURL+SwaggerConstants.VOCABULARIO_A_HREF_END, tags= {"Autobús - Línea"})
public class LineaController extends GenericController implements CiudadesAbiertasController
{
	public static final String LIST = "/autobus/linea";	

	public static final String SEARCH_DISTINCT = LIST+"/distinct";
	
	public static final String RECORD = LIST+ "/{id}";
	
	public static final String TRANSFORM = LIST+"/transform";
	
	public static final String ADD = LIST;
	public static final String UPDATE = RECORD;
	public static final String DELETE = RECORD;
	
	
	public static final String MODEL_VIEW_LIST = "autobus/linea/list";
	public static final String MODEL_VIEW_ID = "autobus/linea/id";
	
	private static List<RequestType> listRequestType = new ArrayList<RequestType>();
	
	private static String nameController = LineaController.class.getName();
	
	//Carga por defecto de las peticiones
	static {
		listRequestType.add(new RequestType("Linea_LIST", LIST, HttpMethod.GET,Constants.NO_AUTH));
		listRequestType.add(new RequestType("Linea_RECORD", RECORD, HttpMethod.GET,Constants.NO_AUTH));
		listRequestType.add(new RequestType("Linea_TRANSFORM", TRANSFORM, HttpMethod.POST,Constants.NO_AUTH));
		
		listRequestType.add(new RequestType("Linea_ADD", ADD, HttpMethod.POST,Constants.BASIC_AUTH));
		listRequestType.add(new RequestType("Linea_UPDATE", UPDATE, HttpMethod.PUT,Constants.BASIC_AUTH));
		listRequestType.add(new RequestType("Linea_DELETE", DELETE, HttpMethod.DELETE,Constants.BASIC_AUTH));
		
	}
	
	public static List<String> availableFields=Util.extractPropertiesFromBean(Linea.class);

	private static final Logger log = LoggerFactory.getLogger(LineaController.class);	

	@Autowired
	protected DatasetService<Linea> service;
	@Autowired
	protected DatasetService<Parada> paradaService;
	@Autowired
	protected DatasetService<Operator> operatorService;
	@Autowired
	protected DatasetService<Route> routeService;

	/**
	 * Ejemplos de Llamadas a utilizar:
	 * http://localhost:8080/API/Linea.html
	 * http://localhost:8080/API/Linea.html?q=importe>11701&sort=-importe	
	 * http://localhost:8080/API/Linea?q=importe>11701&sort=-importe&fields=nombre,importe
	 * http://localhost:8080/API/Linea?fechaAdjudicacion=2016-03-29T00:00:00
	 * @param mv
	 * @param request
	 * @param search
	 * @param rsqlQ
	 * @param page
	 * @param pageSize
	 * @param sort
	 * @return
	 */
	
	@ApiIgnore
	@ApiOperation(value = SwaggerConstants.LISTADO_Y_BUSQUEDA_HTML, notes = SwaggerConstants.DESCRIPCION_BUSQUEDA, produces = SwaggerConstants.FORMATOS_CONSULTA_RESPONSE_HTML, authorizations = { @Authorization(value=Constants.APIKEY) })
	@ApiResponses({
	            @ApiResponse(code = 200, message = SwaggerConstants.RESULTADO_DE_BUSQUEDA_O_LISTADO),
	            @ApiResponse(code = 400, message = SwaggerConstants.PETICION_INCORRECTA,  response=ResultError.class),
	            @ApiResponse(code = 401, message = SwaggerConstants.NO_AUTORIZADO,  response=ResultError.class),
	            @ApiResponse(code = 500, message = SwaggerConstants.ERROR_INTERNO,  response=ResultError.class)
	   })
	@RequestMapping(value= {LIST+Constants.EXT_HTML, VERSION_1+LIST+Constants.EXT_HTML}, method = RequestMethod.GET)	
	public ModelAndView listHTML(
			ModelAndView mv, HttpServletRequest request,LineaSearch search, 
			@RequestParam(value = Constants.RSQL_Q, defaultValue = "", required = false) String rsqlQ,
			@RequestParam(value = Constants.PAGE, defaultValue = "", required = false) String page, 
			@RequestParam(value = Constants.PAGESIZE, defaultValue ="", required = false) String pageSize, 
			@RequestParam(value = Constants.SORT, defaultValue = "", required = false) String sort)
	{
		log.info("[listHTML][" + LIST + ".html]");
		
		String params="?";
		if (Util.validValue(rsqlQ))	{
			params+="&q="+rsqlQ;
		}else if (search!=null){
			params+=search.toUrlParam();
		}		
		
		return listHTML(mv, request, NO_HAY_SRID, page, pageSize, sort, params, MODEL_VIEW_LIST);
	}
	
	@ApiIgnore
	@ApiOperation(value = SwaggerConstants.FICHA_HTML, notes = SwaggerConstants.DESCRIPCION_FICHA, produces = SwaggerConstants.FORMATOS_CONSULTA_RESPONSE_HTML, authorizations = { @Authorization(value=Constants.APIKEY) })
	@ApiResponses({
	            @ApiResponse(code = 200, message = SwaggerConstants.RESULTADO_DE_FICHA,  response=ModelAndView.class),
	            @ApiResponse(code = 400, message = SwaggerConstants.PETICION_INCORRECTA,  response=ResultError.class),
	            @ApiResponse(code = 401, message = SwaggerConstants.NO_AUTORIZADO,  response=ResultError.class),
	            @ApiResponse(code = 500, message = SwaggerConstants.ERROR_INTERNO,  response=ResultError.class)
	   })
	@RequestMapping(value= {RECORD+Constants.EXT_HTML, VERSION_1+RECORD+Constants.EXT_HTML}, method = RequestMethod.GET)
	public ModelAndView recordHTML(ModelAndView mv, @PathVariable String id, @RequestParam(value = Constants.SRID, defaultValue = Constants.SRID_DEFECTO, required = false) 
	@ApiParam(value = SwaggerConstants.PARAM_SRID, allowableValues = Constants.SUPPORTED_SRIDS) String srId, HttpServletRequest request)
	{
		log.info("[recordHTML][" + RECORD + Constants.EXT_HTML + "]");
		
		return recordHTML(mv, request, srId, id, MODEL_VIEW_ID);
	}
	

	@SuppressWarnings("unchecked")
	@ApiOperation(value = SwaggerConstants.LISTADO_Y_BUSQUEDA, notes = SwaggerConstants.DESCRIPCION_BUSQUEDA, produces = SwaggerConstants.FORMATOS_CONSULTA_RESPONSE_FULL, authorizations = { @Authorization(value=Constants.APIKEY) })
	@ApiResponses({
	            @ApiResponse(code = 200, message = SwaggerConstants.RESULTADO_DE_BUSQUEDA_O_LISTADO,  response=LineaResult.class),
	            @ApiResponse(code = 400, message = SwaggerConstants.PETICION_INCORRECTA,  response=ResultError.class),
	            @ApiResponse(code = 401, message = SwaggerConstants.NO_AUTORIZADO,  response=ResultError.class),
	            @ApiResponse(code = 500, message = SwaggerConstants.ERROR_INTERNO,  response=ResultError.class)
	   })
	@RequestMapping(value= {LIST,  VERSION_1+LIST}, method = {RequestMethod.GET})	
	public @ResponseBody ResponseEntity<?> list(HttpServletRequest request, LineaSearch search, 
			@RequestParam(value = Constants.FIELDS, defaultValue = "", required = false) 
				@ApiParam(value=SwaggerConstants.PARAM_FIELDS) String fields, 
			@RequestParam(value = Constants.RSQL_Q, defaultValue = "", required = false) 
				@ApiParam(value=SwaggerConstants.PARAM_Q) String rsqlQ, 
			@RequestParam(value = Constants.PAGE, defaultValue = "1", required = false) 
				@ApiParam(value=SwaggerConstants.PARAM_PAGE) String page, 
			@RequestParam(value = Constants.PAGESIZE, defaultValue = "", required = false) 
				@ApiParam(value=SwaggerConstants.PARAM_PAGESIZE) String pageSize, 
			@RequestParam(value = Constants.SORT, defaultValue = Constants.IDENTIFICADOR, required = false) 
				@ApiParam(value=SwaggerConstants.PARAM_SORT) String sort,
			@RequestParam(value = Constants.SRID, defaultValue = Constants.SRID_DEFECTO, required = false) 
				@ApiParam(value = SwaggerConstants.PARAM_SRID, allowableValues = Constants.SUPPORTED_SRIDS) String srId,
			@RequestHeader HttpHeaders headersRequest)
	{

		log.info("[list][" + LIST + "]");

		log.debug("[parmam] [page:" + page + "] [pageSize:" + pageSize + "] [fields:" + fields + "] [rsqlQ:" + rsqlQ + "] [sort:" + sort + "]");
		
		RSQLVisitor<CriteriaQuery<Linea>, EntityManager> visitor = new JpaCriteriaQueryVisitor<Linea>();
		
		ResponseEntity<Linea> list=list(request, search, fields, rsqlQ, page, pageSize, sort, srId, LIST,new Linea(), new LineaResult(),	availableFields, getKey(), visitor,service);
				
		HttpStatus statusCode = list.getStatusCode();
		
		if (statusCode.is2xxSuccessful())
		{
			boolean isSemantic=Util.isSemanticPetition(request);
		
			Object body = list.getBody();
		
    		if (body!=null)
    		{
        		Result<Linea> result=((Result<Linea>)body);
        		
        		List<Linea> records = result.getRecords();
        		
        		if (srId.equals(""))
    			{
    				srId=StartVariables.SRID_XY_APP;
    			}
    			
    			for (Linea linea:records) {	
    				LineStringUtil.addPolygon(isSemantic, srId, linea);
    			}
    		
    		}
		
		}
		
		return list;
	}

	

	@ApiOperation(value = SwaggerConstants.LISTADO_Y_BUSQUEDA, notes = SwaggerConstants.DESCRIPCION_BUSQUEDA_HEAD, produces = SwaggerConstants.FORMATOS_CONSULTA_RESPONSE_FULL, authorizations = { @Authorization(value=Constants.APIKEY) })
	@ApiResponses({
	            @ApiResponse(code = 200, message = SwaggerConstants.RESULTADO_DE_BUSQUEDA_O_LISTADO),
	            @ApiResponse(code = 400, message = SwaggerConstants.PETICION_INCORRECTA,  response=ResultError.class),
	            @ApiResponse(code = 401, message = SwaggerConstants.PETICION_INCORRECTA,  response=ResultError.class),
	            @ApiResponse(code = 500, message = SwaggerConstants.ERROR_INTERNO,  response=ResultError.class)
	   })
	@RequestMapping(value= {LIST,  VERSION_1+LIST}, method = {RequestMethod.HEAD})	
	public @ResponseBody ResponseEntity<?> listHead(HttpServletRequest request, LineaSearch search, 
			@RequestParam(value = Constants.FIELDS, defaultValue = "", required = false) 
				@ApiParam(value=SwaggerConstants.PARAM_FIELDS) String fields, 
			@RequestParam(value = Constants.RSQL_Q, defaultValue = "", required = false) 
				@ApiParam(value=SwaggerConstants.PARAM_Q) String rsqlQ, 
			@RequestParam(value = Constants.PAGE, defaultValue = "1", required = false) 
				@ApiParam(value=SwaggerConstants.PARAM_PAGE) String page, 
			@RequestParam(value = Constants.PAGESIZE, defaultValue = "", required = false) 
				@ApiParam(value=SwaggerConstants.PARAM_PAGESIZE) String pageSize, 
			@RequestParam(value = Constants.SORT, defaultValue = Constants.IDENTIFICADOR, required = false) 
				@ApiParam(value=SwaggerConstants.PARAM_SORT) String sort,
			@RequestParam(value = Constants.SRID, defaultValue = Constants.SRID_DEFECTO, required = false) 
				@ApiParam(value = SwaggerConstants.PARAM_SRID, allowableValues = Constants.SUPPORTED_SRIDS) String srId,
			@RequestHeader HttpHeaders headersRequest)
	{

		log.info("[listHead][" + LIST + "]");		
		return list(request, search, fields, rsqlQ, page, pageSize, sort, srId, headersRequest);
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value={ADD,  VERSION_1+ADD}, method = RequestMethod.POST, consumes=SwaggerConstants.FORMATOS_ADD_REQUEST)
	@ApiOperation(value = SwaggerConstants.INSERCION, notes = SwaggerConstants.DESCRIPCION_INSERCION, produces = SwaggerConstants.FORMATOS_ADD_RESPONSE, consumes=SwaggerConstants.FORMATOS_ADD_REQUEST, authorizations = { @Authorization(value=Constants.APIKEY) })
	@ApiResponses({
	            @ApiResponse(code = 201, message = SwaggerConstants.RESULTADO_DE_INSERCION,  response=LineaResult.class),
	            @ApiResponse(code = 400, message = SwaggerConstants.PETICION_INCORRECTA,  response=ResultError.class),
	            @ApiResponse(code = 401, message = SwaggerConstants.NO_AUTORIZADO,  response=ResultError.class),
	            @ApiResponse(code = 409, message = SwaggerConstants.EL_RECURSO_YA_EXISTE,  response=ResultError.class),
	            @ApiResponse(code = 500, message = SwaggerConstants.ERROR_INTERNO,  response=ResultError.class)
	   })
	public @ResponseBody ResponseEntity<?> add(			
			@ApiParam(required = true, name = Constants.OBJETO, value = SwaggerConstants.PARAM_BUS_LINEA_TEXT) 			
			@RequestBody Linea obj 
			)
	{

		log.info("[add][" + ADD + "]");

		log.debug("[parmam][dato:" + obj + "] ");		
		
		List<String> erroresFK = checkClavesExternas(obj.getCabeceraLinea(), obj.getFinalLinea(), obj.getOperating() , null, Constants.BASIC_OPERATION_ADD);
		
		if (obj.getHasGeometry()!=null)
		{
		  Map geometryMap=(Map)obj.getHasGeometry();
		  if (geometryMap.size()>0)
		  {
			JSONObject geometry = new JSONObject();
			geometry.putAll(geometryMap);
			obj.setHasGeometry(null);
			obj.setGeometry(geometry.toJSONString());
		  }else {
			obj.setHasGeometry(null);
			obj.setGeometry(null);
		  }
		}
		
		return add(obj, nameController, ADD, service,getKey(), erroresFK);
	}
	
	@SuppressWarnings("unchecked")
	@ApiOperation(value = SwaggerConstants.MODIFICACION, notes = SwaggerConstants.DESCRIPCION_MODIFICACION, produces = SwaggerConstants.FORMATOS_ADD_RESPONSE, consumes=SwaggerConstants.FORMATOS_ADD_REQUEST, authorizations = { @Authorization(value=Constants.APIKEY) })
	@ApiResponses({
	            @ApiResponse(code = 201, message = SwaggerConstants.RESULTADO_DE_MODIFICACION,  response=LineaResult.class),
	            @ApiResponse(code = 400, message = SwaggerConstants.PETICION_INCORRECTA,  response=ResultError.class),
	            @ApiResponse(code = 401, message = SwaggerConstants.NO_AUTORIZADO,  response=ResultError.class),
	            @ApiResponse(code = 409, message = SwaggerConstants.EL_RECURSO_YA_EXISTE,  response=ResultError.class),
	            @ApiResponse(code = 500, message = SwaggerConstants.ERROR_INTERNO,  response=ResultError.class)
	   })
	@RequestMapping(value={UPDATE,  VERSION_1+UPDATE}, method = RequestMethod.PUT, consumes="application/json;charset=UTF-8")
	public @ResponseBody ResponseEntity<?> update(
			@ApiParam(required = true, name = Constants.IDENTIFICADOR, value = SwaggerConstants.PARAM_ID_TEXT+SwaggerConstants.PARAM_ID_BUS_LINEA) 
			@PathVariable(Constants.IDENTIFICADOR) String id, 
			@ApiParam(required = true, name = Constants.OBJETO, value = SwaggerConstants.PARAM_BUS_LINEA_TEXT) 
			@RequestBody Linea obj)
	{

		log.info("[update][" + UPDATE + "]");

		log.debug("[parmam][id:" + id + "] [dato:" + obj + "] ");		
		
		List<String> erroresFK = checkClavesExternas(obj.getCabeceraLinea(), obj.getFinalLinea(), obj.getOperating() , null, Constants.BASIC_OPERATION_UPDATE);
					
		return update(id, obj, nameController, UPDATE, service,getKey(), erroresFK);
	}
	
	@SuppressWarnings("unchecked")
	@ApiOperation(value = SwaggerConstants.DELETE, notes = SwaggerConstants.DESCRIPCION_DELETE, produces = SwaggerConstants.FORMATOS_ADD_RESPONSE, consumes=SwaggerConstants.FORMATOS_ADD_REQUEST, authorizations = { @Authorization(value=Constants.APIKEY) })
	@ApiResponses({
	            @ApiResponse(code = 200, message = SwaggerConstants.RESULTADO_DE_DELETE,  response=LineaResult.class),
	            @ApiResponse(code = 400, message = SwaggerConstants.PETICION_INCORRECTA,  response=ResultError.class),
	            @ApiResponse(code = 401, message = SwaggerConstants.NO_AUTORIZADO,  response=ResultError.class),
	            @ApiResponse(code = 409, message = SwaggerConstants.EL_RECURSO_YA_EXISTE,  response=ResultError.class),
	            @ApiResponse(code = 500, message = SwaggerConstants.ERROR_INTERNO,  response=ResultError.class)
	   })
	@RequestMapping(value={DELETE,  VERSION_1+DELETE}, method = RequestMethod.DELETE)
	public @ResponseBody ResponseEntity<?> delete(
			@ApiParam(required = true, name = Constants.IDENTIFICADOR, value = SwaggerConstants.PARAM_ID_TEXT+SwaggerConstants.PARAM_ID_BUS_LINEA) 
			@PathVariable(Constants.IDENTIFICADOR) String id)
	{

		log.info("[delete][" + DELETE + "]");

		log.debug("[parmam][id:" + id + "] ");	
		
		List<String> erroresFK = checkClavesExternas(null, null, null, id, Constants.BASIC_OPERATION_DELETE);
		
		return delete(id, new Linea(), nameController, DELETE, service,getKey(), erroresFK);
	}
	
	
	@SuppressWarnings("unchecked")
	@ApiOperation(value = SwaggerConstants.FICHA, notes = SwaggerConstants.DESCRIPCION_FICHA, produces = SwaggerConstants.FORMATOS_CONSULTA_RESPONSE_FULL, authorizations = { @Authorization(value=Constants.APIKEY) })
	@ApiResponses({
	            @ApiResponse(code = 200, message = SwaggerConstants.RESULTADO_DE_FICHA,  response=LineaResult.class),
	            @ApiResponse(code = 400, message = SwaggerConstants.PETICION_INCORRECTA,  response=ResultError.class),
	            @ApiResponse(code = 401, message = SwaggerConstants.NO_AUTORIZADO,  response=ResultError.class),
	            @ApiResponse(code = 409, message = SwaggerConstants.EL_RECURSO_YA_EXISTE,  response=ResultError.class),
	            @ApiResponse(code = 500, message = SwaggerConstants.ERROR_INTERNO,  response=ResultError.class)
	   })
	@RequestMapping(value= {RECORD,  VERSION_1+RECORD}, method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<?> record(HttpServletRequest request, @PathVariable @ApiParam(required = true, value=SwaggerConstants.PARAM_ID+SwaggerConstants.PARAM_ID_BUS_LINEA) String id, @RequestParam(value = Constants.SRID, defaultValue = Constants.SRID_DEFECTO, required = false) 
	@ApiParam(value = SwaggerConstants.PARAM_SRID, allowableValues = Constants.SUPPORTED_SRIDS) String srId)
	{

		log.info("[record][" + RECORD + "]");

		log.debug("[parmam][id:" + id + "]");
		
		ResponseEntity<Linea> record=record(request, id, new Linea(), new LineaResult(), srId, nameController, RECORD, service,getKey());
			
		return record;

	}
	
	@ApiOperation(value = SwaggerConstants.FICHA, notes = SwaggerConstants.DESCRIPCION_FICHA_HEAD, produces = SwaggerConstants.FORMATOS_CONSULTA_RESPONSE_FULL, authorizations = { @Authorization(value=Constants.APIKEY) })
	@ApiResponses({
	            @ApiResponse(code = 200, message = SwaggerConstants.RESULTADO_DE_FICHA,  response=LineaResult.class),
	            @ApiResponse(code = 400, message = SwaggerConstants.PETICION_INCORRECTA,  response=ResultError.class),
	            @ApiResponse(code = 401, message = SwaggerConstants.NO_AUTORIZADO,  response=ResultError.class),
	            @ApiResponse(code = 409, message = SwaggerConstants.EL_RECURSO_YA_EXISTE,  response=ResultError.class),
	            @ApiResponse(code = 500, message = SwaggerConstants.ERROR_INTERNO,  response=ResultError.class)
	   })
	@RequestMapping(value= {RECORD,  VERSION_1+RECORD}, method =  RequestMethod.HEAD)
	public @ResponseBody ResponseEntity<?> recordHead(HttpServletRequest request, @PathVariable @ApiParam(required = true, value=SwaggerConstants.PARAM_ID+SwaggerConstants.PARAM_ID_BUS_LINEA) String id, @RequestParam(value = Constants.SRID, defaultValue = Constants.SRID_DEFECTO, required = false) 
	@ApiParam(value = SwaggerConstants.PARAM_SRID, allowableValues = Constants.SUPPORTED_SRIDS) String srId)
	{

		log.info("[recordHead][" + RECORD + "]");
		return record(request, id, srId);
		
	}
		
	@SuppressWarnings("unchecked")
	@RequestMapping(value = { TRANSFORM,
			VERSION_1 + TRANSFORM }, method = RequestMethod.POST, consumes = SwaggerConstants.FORMATOS_ADD_REQUEST)
	@ApiOperation(value = SwaggerConstants.TRANSFORMACION, notes = SwaggerConstants.DESCRIPCION_TRANSFORMACION, produces = SwaggerConstants.FORMATOS_CONSULTA_RESPONSE_FULL, authorizations = {
			@Authorization(value = Constants.APIKEY) })
	@ApiResponses({
			@ApiResponse(code = 200, message = SwaggerConstants.RESULTADO_DE_FICHA, response = LineaResult.class),
			@ApiResponse(code = 400, message = SwaggerConstants.PETICION_INCORRECTA, response = ResultError.class),
			@ApiResponse(code = 500, message = SwaggerConstants.ERROR_INTERNO, response = ResultError.class) })
	public @ResponseBody ResponseEntity<?> transform(
			@ApiParam(required = true, name = Constants.OBJETO, value = SwaggerConstants.PARAM_BUS_LINEA_TEXT) @RequestBody Linea obj) {
		
		log.info("[transform]");

		log.debug("[parmam][obj:" + obj + "]");

		return transform(obj, nameController, TRANSFORM);

	}
	
	
	
	@SuppressWarnings("unchecked")
	@ApiOperation(value = SwaggerConstants.BUSQUEDA_DISTINCT, notes = SwaggerConstants.DESCRIPCION_BUSQUEDA_DISTINCT, produces = SwaggerConstants.FORMATOS_CONSULTA_RESPONSE_GROUPBY, authorizations = { @Authorization(value=Constants.APIKEY) })
	@ApiResponses({
	            @ApiResponse(code = 200, message = SwaggerConstants.RESULTADO_DE_BUSQUEDA_DISTINCT,  response=ObjectResult.class),
	            @ApiResponse(code = 400, message = SwaggerConstants.PETICION_INCORRECTA,  response=ResultError.class),
	            @ApiResponse(code = 401, message = SwaggerConstants.NO_AUTORIZADO,  response=ResultError.class),
	            @ApiResponse(code = 409, message = SwaggerConstants.EL_RECURSO_YA_EXISTE,  response=ResultError.class),
	            @ApiResponse(code = 500, message = SwaggerConstants.ERROR_INTERNO,  response=ResultError.class)
	   })
	@RequestMapping(value= {SEARCH_DISTINCT, VERSION_1+SEARCH_DISTINCT}, method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<?> distinctSearch(HttpServletRequest request, DistinctSearch search,															
				@RequestParam(value = Constants.PAGE, defaultValue = Constants.defaultPage+"", required = false) 
					@ApiParam(value=SwaggerConstants.PARAM_PAGE) String page,
				@RequestParam(value = Constants.PAGESIZE, defaultValue = Constants.defaultGroupByPageSize+"", required = false) 
					@ApiParam(value=SwaggerConstants.PARAM_PAGESIZE) String pageSize)
	{

		
		log.info("[distinctSearch][" + SEARCH_DISTINCT + "]");

		log.debug("[parmam][field:" + search.getField() + "] ");
		

		return distinctSearch(request, search, availableFields, page, pageSize,getKey(),NO_HAY_SRID, SEARCH_DISTINCT, new Linea(), new ObjectResult(),  service);

	}
	
	
	/**
	 * ***********************************************************
	 * METODOS ESPECIFICOS PARA EL CONTROLADOR - NO GENERALIZABLES
	 * ***********************************************************
	 *  ||												||
	 *  \/												\/
	 */
	
		
	@Override
	public ArrayList<SecurityURL> getURLsWithRoles()
	{		
		
		ArrayList<SecurityURL> urlRoles = new ArrayList<SecurityURL>();
		
		Properties properties = mConf.getDatabasesConfig().get(getKey());
				
		listRequestType = Util.getRequestType(properties,getKey(), listRequestType);
		
		for (RequestType rObj:listRequestType) {
			urlRoles.add(rObj.getSecurityURL());
		}		
		
		
		return urlRoles;
	}
	
	@Override
	public String getKey()
	{
		return BusConstants.KEY;
	}

	@Override
	public String getListURI()
	{
		return LIST;
	}
	
	private List<String> checkClavesExternas(String cabeceraLineaId, String finalLineaId, String operatorId, String lineaId, String operation) {
		
		List<String> errors=new ArrayList<String>();
		
		if (activeFK==false)
		{
			return errors;
		}
		
		try
		{	
			if (operation.equals(Constants.BASIC_OPERATION_ADD)||(operation.equals(Constants.BASIC_OPERATION_UPDATE)))
			{
				if (cabeceraLineaId!=null)
				{
					ParadaSearch search=new ParadaSearch();
					search.setId(cabeceraLineaId);
					long rowcount = paradaService.rowcount(getKey(), Parada.class, search);
					if (rowcount==0)			
					{
						errors.add("La parada '"+cabeceraLineaId+"' no existe");		
					}
				}
				
				if (finalLineaId!=null)
				{
					ParadaSearch search=new ParadaSearch();
					search.setId(finalLineaId);
					long rowcount = paradaService.rowcount(getKey(), Parada.class, search);
					if (rowcount==0)			
					{
						errors.add("La parada '"+finalLineaId+"' no existe");		
					}
				}
				
				if (operatorId!=null)
				{
					OperatorSearch search=new OperatorSearch();
					search.setId(operatorId);
					long rowcount = operatorService.rowcount(getKey(), Operator.class, search);
					if (rowcount==0)			
					{
						errors.add("El operador '"+operatorId+"' no existe");		
					}
				}
				
				
			}
			else if (operation.equals(Constants.BASIC_OPERATION_DELETE))
			{
				if (lineaId!=null)
				{					
					
					RouteSearch search=new RouteSearch();
					search.setOnId(lineaId);
					long rowcount = routeService.rowcount(getKey(), Route.class, search);
					

					if (rowcount>0)			
					{
						errors.add("La linea'"+lineaId+"' está siendo usado en la relación con ruta");
					}
					
				}
				
			}
			
			
		}
		catch (DAOException e)
		{
			errors.add("Internal Error");		
		}
		
		return errors;
	}
	
	
}
