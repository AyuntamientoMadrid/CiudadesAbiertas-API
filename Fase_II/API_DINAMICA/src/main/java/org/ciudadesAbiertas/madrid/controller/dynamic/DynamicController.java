package org.ciudadesAbiertas.madrid.controller.dynamic;

import java.io.IOException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.ciudadesAbiertas.madrid.exception.BadRequestException;
import org.ciudadesAbiertas.madrid.exception.InternalErrorException;
import org.ciudadesAbiertas.madrid.exception.TooManyRequestException;
import org.ciudadesAbiertas.madrid.model.Estadistica;
import org.ciudadesAbiertas.madrid.model.dynamic.ParamD;
import org.ciudadesAbiertas.madrid.model.dynamic.QueryD;
import org.ciudadesAbiertas.madrid.service.EstadisticaService;
import org.ciudadesAbiertas.madrid.service.dynamic.DynamicService;
import org.ciudadesAbiertas.madrid.service.dynamic.ParamService;
import org.ciudadesAbiertas.madrid.service.dynamic.QueryService;
import org.ciudadesAbiertas.madrid.utils.CoordinateTransformer;
import org.ciudadesAbiertas.madrid.utils.DynamicQueryUtils;
import org.ciudadesAbiertas.madrid.utils.LikeNoAccents;
import org.ciudadesAbiertas.madrid.utils.RSQLUtil;
import org.ciudadesAbiertas.madrid.utils.Result;
import org.ciudadesAbiertas.madrid.utils.StartVariables;
import org.ciudadesAbiertas.madrid.utils.StringToDateConverter;
import org.ciudadesAbiertas.madrid.utils.TableNameReplacer;
import org.ciudadesAbiertas.madrid.utils.Util;
import org.ciudadesAbiertas.madrid.utils.constants.Constants;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import cz.jirutka.rsql.parser.RSQLParser;
import cz.jirutka.rsql.parser.ast.Node;









@RestController
public class DynamicController implements IDynamicController{
	
	



	private static final Logger log = LoggerFactory.getLogger(DynamicController.class);
	
	public static final String path="/API/query/";
	
	
	@Autowired
	private Environment env;
		
	@Autowired
	private DynamicService dynamicDataBaseService;

	@Autowired
	private QueryService queryService;

	@Autowired
	private ParamService paramService;
	
	@Autowired
	private EstadisticaService estadisticaService;
	
	@RequestMapping(value= {path+"{code}"}, method = {RequestMethod.GET}, produces="text/csv")	
	public @ResponseBody ResponseEntity<Object> csvDynamicQuery(HttpServletRequest request, HttpServletResponse response,
															@PathVariable String code, 
															@RequestParam(value = "page", defaultValue = "1", required = false) String page, 
															@RequestParam(value = "pageSize", defaultValue = "100", required = false) String pageSize,															
															@RequestParam(value = "sort", defaultValue = "", required = false) String sort,
															@RequestParam(value = Constants.SRID, defaultValue = Constants.DOCUMENTATION_SRID, required = false) String srId,
															@RequestParam(value = Constants.RSQL_Q, defaultValue = "", required = false) String rsqlQ) 
																	throws TooManyRequestException, BadRequestException, InternalErrorException {

		log.info("[dynamicDataBaseAPIinCSV] [/dynamicAPI/" + code+"]");
		// Verifico la negociación de contenidos
		ResponseEntity<Object> negotiationResponseEntity = Util.negotiationContent(request);
		if (negotiationResponseEntity != null) {
			return negotiationResponseEntity;
		}

		petitionsControl();

		srIdControl(srId);

		if (errorExtension(request, response))
			return null;

		Result listado = listado(request, code, page, pageSize, sort, srId, rsqlQ);
		return createResponse(listado);

	}



	


	
	@RequestMapping(value= {path+"{code}"}, method = {RequestMethod.GET})
	public @ResponseBody ResponseEntity<Object> dynamicQuery(HttpServletRequest request, HttpServletResponse response,
															@PathVariable String code, 
															@RequestParam(value = "page", defaultValue = "1", required = false) String page, 
															@RequestParam(value = "pageSize", defaultValue = "100", required = false) String pageSize,
															@RequestParam(value = "sort", defaultValue = "", required = false) String sort,
															@RequestParam(value = Constants.SRID, defaultValue = Constants.DOCUMENTATION_SRID, required = false) String srId ,
															@RequestParam(value = Constants.RSQL_Q, defaultValue = "", required = false) String rsqlQ) 
	 
																	throws TooManyRequestException, BadRequestException, InternalErrorException {
	
		log.info("[dynamicDataBaseAPI] [/dynamicAPI/" + code+"]");
		
		log.info(Util.getFullURL(request));
		
		//Verifico la negociación de contenidos
		ResponseEntity<Object> negotiationResponseEntity=Util.negotiationContent(request);
		if (negotiationResponseEntity!=null){
			return negotiationResponseEntity;
		}
		
		if (!Util.validValue(pageSize))
		{
			pageSize=StartVariables.defaultPageSize+"";
		}
		
		petitionsControl();		
		
		srIdControl(srId);		
		
		if (errorExtension(request, response))
			return null;
		
		Result listado = listado(request,code,page,pageSize,sort,srId, rsqlQ);
		return createResponse(listado);
	}







	

	
	

	/**
	 * ***********************************************************
	 * METODOS ESPECIFICOS PARA EL CONTROLADOR - PRIVADOS
	 * ***********************************************************
	 *  ||												||
	 *  \/												\/
	 */
	
	/**
	 * @param request
	 * @param response
	 */
	private boolean errorExtension(HttpServletRequest request, HttpServletResponse response) {
		
		boolean result = false;
		String theURL=Util.getFullURL((HttpServletRequest)request);
		theURL=request.getRequestURL().toString();
		
		String extension=Util.getExtensionUri(theURL);
		
		if (!Util.isValidExtensionOfUri(extension) /* & validar */) {
			log.info("Extension not allowed: "+extension+" in "+Constants.FORMATOS_EXTENSIONES);
			try {
				sendBadRequestError(extension, request, response);
				result = true;
			} catch (IOException e) {
				log.error("[controlExtension] [ERROR] ["+e.getMessage()+"]");
			}
		}
		return result;
	}

	private void addStat(HttpServletRequest request) {
		Estadistica statData = Util.genStatisticData(request);
		estadisticaService.add(statData);
	}
	
	
	@SuppressWarnings("unused")
	private Result listado(HttpServletRequest request, String code, String page, String pageSize,
			String sort, String srId, String rsqlQ) throws TooManyRequestException, InternalErrorException, BadRequestException {


		log.info("[listado]" + code);

		long totalRegistros = Constants.defaultLongValue;

		addStat(request);

		List<Object> results = new ArrayList<Object>();
		List<ParamD> parameters = new ArrayList<ParamD>();
		List<String> conditions= new ArrayList<String>();
		boolean isDistinctQuery = false;
		boolean isGroupByQuery = false;

		Map<String, String> requestParameters = Util.getQueryMap(request);

		QueryD query = queryService.record(code);
		
		if (query==null)
		{
			throw new BadRequestException("Petición incorrecta","La URL no existe");
		}

		parameters = paramService.list(code);	
		
		if (StartVariables.modelsForDynamicQuerys.get(query.getCode())==null)
		{
			Map<String, String> modeloQuery=new HashMap<String,String>();
			try {
				modeloQuery = dynamicDataBaseService.typesQuery(query, parameters);
			} catch (Exception e) {
				log.error("Error extracting model",e);
			}
			StartVariables.modelsForDynamicQuerys.put(query.getCode(),modeloQuery);
		}
		
		//Validamos los parametros de la URL (admitimos los del modelo y los especificos)
		List<String> allowedParams=new ArrayList<String>();		
		allowedParams.addAll(StartVariables.modelsForDynamicQuerys.get(query.getCode()).keySet());
		allowedParams.add(Constants.AJAX_PARAM);	
		allowedParams.add(Constants.RSQL_Q);	
		allowedParams.add(Constants.PAGE);
		allowedParams.add(Constants.PAGESIZE);		
		allowedParams.add(Constants.SORT);
		allowedParams.add(Constants.AJAX_PARAM);
		allowedParams.add(Constants.SRID);	
		for (ParamD p:parameters)
		{
			allowedParams.add(p.getName());
		}
		
		if (allowedParams.containsAll(requestParameters.keySet())==false)
		{			
			requestParameters.keySet().removeAll(allowedParams);
			String parametrosNoValidos=requestParameters.keySet().toString();
			parametrosNoValidos=parametrosNoValidos.replace("[", "").replace("]","");
			throw new BadRequestException("Parámetro/s no valido", parametrosNoValidos);
		}
		
		//parametros para filtrar que vienen de la request
		List<String> requestParams=new ArrayList<String>();
		Set<String> keySet = StartVariables.modelsForDynamicQuerys.get(query.getCode()).keySet();
		for (String key:keySet)
		{
			if (requestParameters.get(key)!=null)
			{
				requestParams.add(key);
			}
		}
		
		String databaseType = StartVariables.databaseTypes.get(query.getDatabase());	

		if (query == null) 
		{
			log.error("Query not found on DB");			
		}
		else 
		{
			String queryText = query.getTexto();			
			isDistinctQuery = DynamicQueryUtils.checkDistinctQuery(queryText);
			isGroupByQuery =  DynamicQueryUtils.checkGroupByQuery(queryText);
			
			log.debug(queryText);

			if (parameters.size() > 0) {
				log.debug(parameters.size() + " parameters founded");

				log.debug("Original query: " + queryText);

				for (ParamD param : parameters) {
					
					String requestParamValue = requestParameters.get(param.getName());
					
					if (param.getName().contentEquals("fieldsP"))
					{				
						requestParamValue = addAliasToParamFieldP(Util.decodeURL(requestParamValue));
					}
							
					queryText = DynamicQueryUtils.replaceParameter(queryText,param,requestParamValue, isDistinctQuery, isGroupByQuery, databaseType);
				}

				log.debug("Parameters replaced query: " + queryText);
			} 
			
			
			//Parametros pasados por request			
			Map<String, String> queryModel = StartVariables.modelsForDynamicQuerys.get(query.getCode());
			for (String requestParam:requestParams)
			{
				String type=queryModel.get(requestParam);
				String value=requestParameters.get(requestParam);
				
				String condition=generateConditionFromRequestParam(requestParam,value,type, databaseType, query.getDatabase());				
				
				if (Util.validValue(condition))
				{
					conditions.add(condition);
				}
			}
			
					
			String rsqlConditions="";
			if (Util.validValue(rsqlQ))
			{
				try
				{
					rsqlConditions=generateConditionsFromRSQL(rsqlQ,StartVariables.modelsForDynamicQuerys.get(query.getCode()), databaseType, query.getDatabase());
				}
				catch (Exception e)
				{
					log.error("Error in rsql text",e);
					throw new BadRequestException("RSQL Error","Se ha producido un error con la consulta RSQL");
				}
			}
			
			if (Util.validValue(rsqlConditions))
			{
				queryText=DynamicQueryUtils.SELECT_FROM+queryText+DynamicQueryUtils.AS_SELECT_PRINCIPAL_WHERE+rsqlConditions;
			}
			else if (conditions.size()>0)
			{
				queryText=DynamicQueryUtils.SELECT_FROM+queryText+DynamicQueryUtils.AS_SELECT_PRINCIPAL_WHERE+conditions.get(0);
				for (int i=1;i<conditions.size();i++)
				{
					queryText+=" and "+conditions.get(i);
				}
				
			}
			
			if ((sort != null) && (!sort.equals(""))) {
				if (sort.indexOf("-") == 0)
					queryText += " order by " + sort.substring(1) + " DESC";
				else
					queryText += " order by " + sort + " ASC";

				log.debug("Ordered query: " + queryText);
			}
			

			results = dynamicDataBaseService.query(queryText, query.getDatabase(), page, pageSize);
			
			if (isDistinctQuery)
			{	
				List<Object> distinctResults = new ArrayList<Object>();
				String key="";
				Iterator it=results.iterator();
				while (it.hasNext())
				{
					LinkedHashMap map=(LinkedHashMap) it.next();
					if (key.equals(""))
					{
						Object[] array = map.keySet().toArray();
						key=(String) array[0];
					}
					distinctResults.add(map.get(key));
				}
				results=distinctResults;
			}

			/*
			if (isGroupByQuery)
			{					
				List<Object> groupResults = new ArrayList<Object>();
				List<String> keys=new ArrayList<String>();
				Iterator it=results.iterator();
				while (it.hasNext())
				{
					List<Object> actualValues=new ArrayList<Object>();
					LinkedHashMap map=(LinkedHashMap) it.next();
					if (keys.size()==0)
					{
						Object[] keyArray = map.keySet().toArray();
						for (Object o:keyArray)
						{
							keys.add((String)o);
						}						
					}
					for (String key:keys)
					{
						actualValues.add(map.get(key));
					}
					groupResults.add(actualValues);
				}
				results=groupResults;				
			}
			*/
			
			//borramos el orderBy si lo tiene
			queryText = StringUtils.normalizeSpace(queryText);
			if (queryText.toLowerCase().contains("order by"))
			{
				queryText=queryText.substring(0,queryText.toLowerCase().indexOf("order by"));
			}
			
			totalRegistros = dynamicDataBaseService.rowCount(queryText, query.getDatabase());
		} 

		log.info("[listado] [end]");
		return guardarResult(results, totalRegistros, srId, request, (isDistinctQuery || isGroupByQuery));

	}







	private String addAliasToParamFieldP(String requestParamValue) {
		
		String addAliases = TableNameReplacer.addAliases("SELECT "+requestParamValue+" FROM TEST");
		addAliases=addAliases.replace("SELECT ", "");
		addAliases=addAliases.replace(" FROM TEST", "");
		return addAliases;
	}







	







	
	
	
	






	private String generateConditionsFromRSQL(String rsqlQ, Map<String, String> map, String databaseType, String databaseKey) throws Exception {		
		
		RSQLUtil rUtil=new RSQLUtil();
		rUtil.setDatabaseType(databaseType);
		rUtil.setDatabaseKey(databaseKey);
		rUtil.setMap(map);
		rUtil.parseQuery(rsqlQ);
		rUtil.checkNode();
		String queryTranslated=rUtil.generateSingleRSQL();	
		
		return queryTranslated;
	}


	private String generateConditionFromRequestParam(String paramName, String paramValue, String paramType, String databaseType, String databaseKey)  
	{		
		String condition="";
		if (Util.validValue(paramValue)) {		
			if (Constants.TYPE_STRING_CLASS.equals(paramType)) 
			{
				paramValue = Util.decodeURL(paramValue);
				paramValue = paramValue.replace("*", "%");
				
				LikeNoAccents c1 = new LikeNoAccents(paramName, paramValue, databaseType);
				condition=c1.toSqlString(databaseKey);						
			}
			else if ( (paramType.equals(Constants.JAVA_SQL_TIMESTAMP)||(paramType.equals(Constants.JAVA_SQL_DATE))||(paramType.equals(Constants.JAVA_SQL_TIME))) )
			{		    	
		    	condition= paramName +" = '"+paramValue+"'";
			}			
			else
			{
				condition= paramName +" = "+paramValue;
			}
		}		
			
		return condition;
	}





	/**
	 * Guardar los resultados
	 * @param srId
	 * @param listado
	 * @param total
	 * @param obj
	 * @param request
	 * @return
	 * @throws Exception
	 */
	private Result guardarResult( List<Object> listado, 
											long total, 
											String srId, 
											HttpServletRequest request,
											boolean generateCoords)  {

		int numPage = Constants.defaultPage;
		int numPageSize = StartVariables.defaultPageSize;
		log.info("[guardarResult] [init]");

		Result resultado = new Result();
		
		if (generateCoords==false)
		{
			Util.generaCoordenadasAll(StartVariables.SRID_XY_APP, srId, listado);
		}
		
		try {

			Map<String, String> pageMetadataCalculation = Util.pageMetadataCalculation(request, total, numPageSize,env.getProperty(Constants.URI_BASE), env.getProperty(Constants.STR_CONTEXTO));
	
			// Pagina actual
			resultado.setSelf(pageMetadataCalculation.get(Constants.SELF));
			
			if (numPage!=Constants.NO_PAGINATION) {
				// Pagina inicial
				resultado.setFirst(pageMetadataCalculation.get(Constants.FIRST));
				// Ultima página
				resultado.setLast(pageMetadataCalculation.get(Constants.LAST));
				// Pagina siguiente
				resultado.setNext(pageMetadataCalculation.get(Constants.NEXT));
				// Pagina anterior
				resultado.setPrev(pageMetadataCalculation.get(Constants.PREV));
			}
			// MD5
			resultado.setContentMD5(Util.generateHash(Util.toString(listado)));
	
			if (numPage!=Constants.NO_PAGINATION) {
				resultado.setPage(numPage);
			}else {
				resultado.setPage(1);
			}
			
			
			if (numPageSize!=Constants.NO_PAGINATION) {
				resultado.setPageSize(numPageSize);
			}	else {
				resultado.setPageSize((int)total);
			}		
			
			resultado.setPageRecords(listado.size());
			resultado.setRecords(listado);
			resultado.setTotalRecords(total);
	
			resultado.setStatus(200);
		}catch (Exception ex) {
			log.error("[guardarResult] [ERROR] ["+ex.getMessage()+"]");
		}

		log.info("[guardarResult] [end]");
		return resultado;
	}
	

	private ResponseEntity<Object> createResponse(Result result) {
		
		HttpHeaders headers = Util.extractHeaders(result);
		return new ResponseEntity<Object>(result, headers, HttpStatus.OK);
	}
	
	private void petitionsControl() throws TooManyRequestException {
		boolean control=estadisticaService.controlRequesPerSecond();		
		if (!control)
		{
			throw new TooManyRequestException(" limit Exceeded: Maximum number of requests per second");
		}
	}
	
	private void srIdControl(String srId) throws BadRequestException {
		if (Util.validValue(srId))
		{
			if (CoordinateTransformer.isValidSrId(srId)==false)
			{				
				throw new BadRequestException("srId no valido", "los srIds disponibles son: "+Constants.SUPPORTED_SRIDS);				
			}
		}
	}
}
