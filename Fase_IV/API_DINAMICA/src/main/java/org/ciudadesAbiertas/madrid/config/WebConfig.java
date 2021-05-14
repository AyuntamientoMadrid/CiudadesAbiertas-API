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

import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.logging.LogManager;
import java.util.regex.Pattern;

import javax.annotation.PostConstruct;
import javax.naming.NamingException;
import javax.persistence.Column;
import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.ciudadesAbiertas.madrid.config.multipe.MultipleConf;
import org.ciudadesAbiertas.madrid.config.multipe.MultipleDataSource;
import org.ciudadesAbiertas.madrid.config.multipe.MultipleSessionFactory;
import org.ciudadesAbiertas.madrid.utils.StartVariables;
import org.ciudadesAbiertas.madrid.utils.StringToDateConverter;
import org.ciudadesAbiertas.madrid.utils.StringToNumberConverter;
import org.ciudadesAbiertas.madrid.utils.Util;
import org.ciudadesAbiertas.madrid.utils.constants.Constants;
import org.ciudadesAbiertas.madrid.utils.converters.CSVConverter;
import org.ciudadesAbiertas.madrid.utils.converters.GEOJSONConverter;
import org.ciudadesAbiertas.madrid.utils.converters.GEORSSConverter;
import org.ciudadesAbiertas.madrid.utils.converters.RDFConverter;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.core.type.filter.RegexPatternTypeFilter;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.xml.MappingJackson2XmlHttpMessageConverter;
import org.springframework.jndi.JndiObjectFactoryBean;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBuilder;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

/**
 * @author Juan Carlos Ballesteros (Localidata)
 * @author Carlos Martínez de la Casa (Localidata)
 * @author Oscar Corcho (UPM, Localidata)
 *
 */
@Configuration
@EnableTransactionManagement
@EnableWebMvc
@EnableAsync
@ComponentScan(basePackages =
{
    "org.ciudadesAbiertas.madrid"
})
public class WebConfig extends WebMvcConfigurerAdapter
{
  private static final Logger log = LoggerFactory.getLogger(WebConfig.class);

  @Autowired
  private Environment env;

  @Autowired
  private ApplicationContext applicationContext;

  // para poder servir recursos
  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry)
  {
    registry.addResourceHandler(Constants.RESOURCES + "**").addResourceLocations(Constants.RESOURCES);    
    registry.addResourceHandler(Constants.SWAGGER + "**").addResourceLocations(Constants.SWAGGER);
  }

  @Override
  public void configureContentNegotiation(ContentNegotiationConfigurer configurer)
  {
    // JSON como contenido por defecto
    configurer.defaultContentType(MediaType.APPLICATION_JSON).
    // le decimos que la extesion jsonld se corresponde con su mimetype
    // mediaType(Constants.FORMATO_HTML,
    // MediaType.valueOf(Constants.MEDIA_TYPE_TEXT)).
	mediaType(Constants.FORMATO_CSV, MediaType.valueOf(Constants.MEDIA_TYPE_CSV)).
	mediaType(Constants.FORMATO_JSONLD, MediaType.valueOf(Constants.mimeJSONLD)).
	mediaType(Constants.FORMATO_TTL, MediaType.valueOf(Constants.mimeTURTLE)).
	mediaType(Constants.FORMATO_N3, MediaType.valueOf(Constants.mimeN3)).
	mediaType(Constants.FORMATO_RDF, MediaType.valueOf(Constants.mimeRDF_XML)).
	mediaType(Constants.FORMATO_GEOJSON, MediaType.valueOf(Constants.MEDIA_TYPE_GEOJSON))
	.mediaType(Constants.FORMATO_GEORSS, MediaType.valueOf(Constants.MEDIA_TYPE_GEORSS))
	.mediaType(Constants.FORMATO_ODATA, MediaType.valueOf(Constants.MEDIA_TYPE_GEORSS));

  }

  // Funcion formatear el JSON que devolvemos
  public void extendMessageConverters(List<HttpMessageConverter<?>> converters)
  {
    for (HttpMessageConverter<?> converter : converters)
    {
      // System.out.println(("Converter: "+converter.getClass().getName()));
      if (converter instanceof MappingJackson2HttpMessageConverter)
      {
	MappingJackson2HttpMessageConverter jsonMessageConverter = (MappingJackson2HttpMessageConverter) converter;

	// eliminamos de formatos soportados por el JSON el *+json para que el xml no se
	// apodere de ld+json
	MediaType JSON_FORMAT[] =
	{
	    MediaType.valueOf(Constants.CONTENT_TYPE_JSON_UTF8)
	};
	jsonMessageConverter.setSupportedMediaTypes(Arrays.asList(JSON_FORMAT));

	ObjectMapper objectMapper = jsonMessageConverter.getObjectMapper();
	// Fechas sin timestamps
	objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
	// Y con este formato
	objectMapper.setDateFormat(new SimpleDateFormat(Constants.DATE_TIME_FORMAT));
	objectMapper.configure(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY, false);
	jsonMessageConverter.setPrettyPrint(true);

	objectMapper.setSerializationInclusion(Include.NON_NULL);

	StartVariables.jsonConverter = jsonMessageConverter;

      }
      else if (converter instanceof MappingJackson2XmlHttpMessageConverter)
      {
	MappingJackson2XmlHttpMessageConverter xmlMessageConverter = (MappingJackson2XmlHttpMessageConverter) converter;

	// eliminamos de formatos soportados por el XML el *+xml para que el xml no se
	// apodere de rdf+xml
	MediaType XMLs[] =
	{
	    MediaType.valueOf(Constants.CONTENT_TYPE_XML_UTF8), MediaType.valueOf(Constants.CONTENT_TYPE_TEXT_UTF8)
	};
	xmlMessageConverter.setSupportedMediaTypes(Arrays.asList(XMLs));

	ObjectMapper objectMapper = xmlMessageConverter.getObjectMapper();
	// Fechas sin timestamps
	objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
	// Y con este formato
	objectMapper.setDateFormat(new SimpleDateFormat(Constants.DATE_TIME_FORMAT));
	objectMapper.configure(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY, false);
	xmlMessageConverter.setPrettyPrint(true);

	objectMapper.setSerializationInclusion(Include.NON_NULL);

      }
    }

    StartVariables.context = applicationContext.getApplicationName();
    if (Util.validValue(env.getProperty(Constants.STR_CONTEXTO).trim()))
    {
      StartVariables.context = env.getProperty(Constants.STR_CONTEXTO).trim();
    }

    // Añadimos el CSV
    converters.add(new CSVConverter<>());

    // Añadimos geojson
    converters.add(new GEOJSONConverter());

    // Añadimos georss
    converters.add(new GEORSSConverter());

    // Añadimos turtle
    converters.add(new RDFConverter<>(Constants.mimeTURTLE));
    
    // Añadimos JSONLD
    converters.add(new RDFConverter<>(Constants.mimeJSONLD));
    
    // Añadimos N3
    converters.add(new RDFConverter<>(Constants.mimeN3));    
    
    // Añadimos RDF_XML
    converters.add(new RDFConverter<>(Constants.mimeRDF_XML));
    
    
  }

  private Properties getHibernateProperties()
  {
    Properties prop = new Properties();
    prop.put(Constants.DB_HIBERNATE_FORMAT_SQL, env.getProperty(Constants.DB_HIBERNATE_FORMAT_SQL));
    prop.put(Constants.DB_HIBERNATE_SHOW_SQL, env.getProperty(Constants.DB_HIBERNATE_SHOW_SQL));
    prop.put(Constants.DB_HIBERNATE_DIALECT, env.getProperty(Constants.DB_HIBERNATE_DIALECT));
    prop.put(Constants.HIBERNATE_DIALECT,  env.getProperty(Constants.DB_HIBERNATE_DIALECT));
    prop.put(Constants.DB_HIBERNATE_USE_SQL_COMMENTS, env.getProperty(Constants.DB_HIBERNATE_USE_SQL_COMMENTS));
    return prop;
  }

  @Bean(name = "dataSource")
  public DataSource dataSource()
  {

    BasicDataSource ds = new BasicDataSource();
    // Basic
    if ((Util.validValue(env.getProperty(Constants.DB_DRIVER)) && (Util.validValue(env.getProperty(Constants.DB_URL)))))
    {
      ds.setDriverClassName(env.getProperty(Constants.DB_DRIVER));
      ds.setUrl(env.getProperty(Constants.DB_URL));
      ds.setUsername(env.getProperty(Constants.DB_USER));
      ds.setPassword(Util.checkAndSetEncodedProperty(env.getProperty(Constants.DB_PASSWORD)));

      StartVariables.db_schema = env.getProperty(Constants.DB_SCHEMA);
      
      StartVariables.databaseTypes.put(Constants.DEFAULT_DATABASE, Util.getDatabaseTypeFromDriver(env.getProperty(Constants.DB_DRIVER)));
      
      if (Util.getDatabaseTypeFromDriver(env.getProperty(Constants.DB_DRIVER)).contains(Constants.SQLSERVER))
   	  {
    	StartVariables.sqlServerSchemas.put(Constants.DEFAULT_DATABASE, env.getProperty(Constants.DB_SCHEMA));
   	  }    

      ds.setInitialSize(Integer.parseInt(env.getProperty(Constants.DB_INITIAL_SIZE)));
      ds.setMaxActive(Integer.parseInt(env.getProperty(Constants.DB_MAX_ACTIVE)));
      ds.setMaxIdle(Integer.parseInt(env.getProperty(Constants.DB_MAX_IDLE)));
      ds.setMinIdle(Integer.parseInt(env.getProperty(Constants.DB_MIN_IDLE)));

      // Evitar cierre de conexiones después de horas sin uso
      ds.setTestOnBorrow(true);
      ds.setValidationQuery(env.getProperty(Constants.DB_VALIDATION_QUERY));

      log.debug("[dataSource] [BasicDataSource:" + ds + "]");
      return (DataSource) ds;
    }

    // JNDI
    if (Util.validValue(env.getProperty(Constants.DB_JNDI_NAME)))
    {
      String keyJNDI = env.getProperty(Constants.DB_JNDI_NAME);

      JndiObjectFactoryBean bean = new JndiObjectFactoryBean();

      String envJndi = env.getProperty(Constants.STR_ENV_JNDI_CONTEXT);
      if (envJndi != null && "".equals(envJndi))
      {
	bean.setJndiName(envJndi + keyJNDI);
      }
      else
      {
	bean.setJndiName(Constants.ENV_JNDI_CONTEXT + keyJNDI);
      }
      bean.setProxyInterface(DataSource.class);
      // bean.setProxyInterface(BasicDataSource.class);
      bean.setLookupOnStartup(false);

      StartVariables.db_schema = env.getProperty(Constants.DB_SCHEMA);
      StartVariables.databaseTypes.put(Constants.DEFAULT_DATABASE, Util.getDatabaseTypeFromDriver(env.getProperty(Constants.DB_DRIVER)));
      
      if (Util.getDatabaseTypeFromDriver(env.getProperty(Constants.DB_DRIVER)).contains(Constants.SQLSERVER))
   	  {
    	StartVariables.sqlServerSchemas.put(Constants.DEFAULT_DATABASE, env.getProperty(Constants.DB_SCHEMA));
   	  }   

      try
      {
	bean.afterPropertiesSet();
      }
      catch (IllegalArgumentException | NamingException e)
      {
	log.error("[dataSource] [Error:" + e.getMessage() + "] ");
      }

      return (DataSource) bean.getObject();
    }
    log.error("[dataSource] [Error:NO  BasicDataSource created ");
    return null;
  }

  @Bean
  public SessionFactory sessionFactory()
  {
    LocalSessionFactoryBuilder builder = new LocalSessionFactoryBuilder(dataSource());

    Properties hibernateProperties = getHibernateProperties();

    if (Util.validValue(StartVariables.db_schema))
    {
      hibernateProperties.put(Constants.DB_HIBERNATE_DEFAULT_SCHEMA, StartVariables.db_schema);
    }

    builder.scanPackages(Constants.PAQUETE_MODELO_CONJUNTOS_DATOS_USUARIOS).scanPackages(Constants.PAQUETE_MODELO_CONJUNTOS_DATOS).addProperties(hibernateProperties);
    return builder.buildSessionFactory();
  }

  @Bean(name = "txManager")
  @Primary
  public HibernateTransactionManager txManager()
  {
    log.info("txManager");
    return new HibernateTransactionManager(sessionFactory());
  }

  // Configuración CORS
  @Override
  public void addCorsMappings(CorsRegistry registry)
  {
    registry.addMapping("/**");
  }

  // Formateadores para Spring
  @Override
  public void addFormatters(FormatterRegistry registry)
  {
    registry.addConverter(new StringToDateConverter());
    registry.addConverterFactory(new StringToNumberConverter());
  }

  // Beans para controlar multiples ficheros de configuración ->multiples bbdd

  @Bean
  public MultipleConf multipleConf()
  {

    ClassLoader classLoader = getClass().getClassLoader();
    MultipleConf multipleConf = new MultipleConf(classLoader);
    return multipleConf;
  }

  @Bean
  public MultipleDataSource multipleDataSource()
  {

    MultipleDataSource multipleDataSource = new MultipleDataSource(multipleConf());
    return multipleDataSource;
  }

  @Bean(name = "MultipleSessionFactory")
  public MultipleSessionFactory multipleSessionFactory()
  {

    MultipleSessionFactory multipleSessionFactory = new MultipleSessionFactory(multipleDataSource());

    return multipleSessionFactory;

  }

  // Fin Beans Multiples conexiones

  // Metodo para realizar tareas depués de cargar
  @PostConstruct
  public void postConstruct() throws MalformedURLException
  {
    String maxSize = env.getProperty(Constants.STR_PAGE_MAX);
    String defaultSize = env.getProperty(Constants.STR_PAGE_DEFAULT);
    String maxPetitionsPerSecondAnonymous = env.getProperty(Constants.STR_NUMBER_REQUEST_PER_SECOND);
    String nodoPattern = env.getProperty(Constants.STR_NODO_PATTERN);
    String srId_XY_App = env.getProperty(Constants.STR_XY_ETRS89_EPSG);
    String srId_LATLON_App = env.getProperty(Constants.STR_LAT_LON_ETRS89_EPSG);
    String str_pathTemplate = env.getProperty(Constants.STR_PATH_TEMPLATE);
    String str_rsql_log_active = env.getProperty(Constants.STR_RSQL_LOG_ACTIVE);
    String tipoAutenticacion = env.getProperty(Constants.TIPO_AUTENTICACION);

    
    if (Util.validValue(env.getProperty(Constants.STR_CONTEXTO)))
    {
    	StartVariables.context=env.getProperty(Constants.STR_CONTEXTO);
    }
    
    if (Util.validValue(env.getProperty(Constants.URI_BASE)))
    {
      if (Util.validateURL(env.getProperty(Constants.URI_BASE)))
      {
    	  StartVariables.uriBase = env.getProperty(Constants.URI_BASE);
    	  StartVariables.serverPort = StartVariables.uriBase.substring(StartVariables.uriBase.indexOf("//") + 2);
    	  StartVariables.schema = StartVariables.uriBase.substring(0, StartVariables.uriBase.indexOf("://") + 3);
      }
      else
      {
    	  throw new MalformedURLException(Constants.URI_BASE);
      }

    }

    if (Util.validValue(str_pathTemplate))
    {
      StartVariables.PATH_TEMPLATE = str_pathTemplate;
      log.info("PATH TEMPLATE: " + StartVariables.PATH_TEMPLATE);
    }
    else
    {
      log.error("PATH TEMPLATE (DEFAULT): " + StartVariables.PATH_TEMPLATE);
    }

    try
    {
      StartVariables.maxPageSize = Integer.parseInt(maxSize);
      log.info("max. size set to " + maxSize);
    }
    catch (Exception e)
    {
      log.error("Wrong maxSize in properties file: " + maxSize);
    }
    try
    {
      StartVariables.defaultPageSize = Integer.parseInt(defaultSize);
      log.info("default page size set to " + defaultSize);
    }
    catch (Exception e)
    {
      log.error("Wrong default page size in properties file: " + defaultSize);
    }
    try
    {
      StartVariables.MAX_NUMBER_REQUEST_PER_SECOND = Integer.parseInt(maxPetitionsPerSecondAnonymous);
      log.info("max petitions per second by users anonymous se to " + maxPetitionsPerSecondAnonymous);
    }
    catch (Exception e)
    {
      log.error("Wrong max petitions per second by users anonymous in properties file: " + maxPetitionsPerSecondAnonymous);
    }

    if (Util.validValue(nodoPattern))
    {
      StartVariables.NODO_PATTERN = nodoPattern;
      log.info("Nodo Configuration Character: " + StartVariables.NODO_PATTERN);
    }
    else
    {
      log.error("Nodo No Configuration Character (Default): " + StartVariables.NODO_PATTERN);
    }

    if (Util.validValue(srId_XY_App))
    {
      StartVariables.SRID_XY_APP = srId_XY_App;
      log.info("APP Configuration " + Constants.STR_PROJECTEDCOORDINATES + " " + StartVariables.SRID_XY_APP);
    }
    else
    {
      log.error("APP NO Configuration " + Constants.STR_PROJECTEDCOORDINATES + " (Default) " + StartVariables.SRID_XY_APP);
    }

    if (Util.validValue(srId_LATLON_App))
    {
      StartVariables.SRID_LAT_LON_APP = srId_LATLON_App;
      log.info("APP Configuration " + Constants.STR_GEOGRAPHICCOORDINATES + " " + StartVariables.SRID_LAT_LON_APP);
    }
    else
    {
      log.error("APP NO Configuration " + Constants.STR_GEOGRAPHICCOORDINATES + " (Default) " + StartVariables.SRID_LAT_LON_APP);
    }

    List<String> autenticationTypes = new ArrayList<String>();
    autenticationTypes.add(Constants.TIPO_AUTENTICACION_BBDD);
    autenticationTypes.add(Constants.TIPO_AUTENTICACION_MIXTA);
    autenticationTypes.add(Constants.TIPO_AUTENTICACION_UWEB);

    if (autenticationTypes.contains(tipoAutenticacion))
    {
      StartVariables.tipoAutenticacion = tipoAutenticacion;
      log.info("APP tipoAutenticacion " + StartVariables.tipoAutenticacion);
    }
    else
    {
      log.error("WRONG AUTHENTICATION TYPE: " + tipoAutenticacion);
      log.error("AUTHENTICATION DEFAULT SETTED: " + StartVariables.tipoAutenticacion);
    }

    StartVariables.UWEB_APP = env.getProperty(Constants.UWEB_APP);

    StartVariables.mapaClasesColumnas = generarMapaTablasColumnas();

    if (Util.validValue(str_rsql_log_active))
    {
      StartVariables.RSQL_LOG_ACTIVE = Constants.STR_ACTIVE_ON.equals(str_rsql_log_active.toLowerCase());
      log.info("RSQL LOG ACTIVE: " + StartVariables.RSQL_LOG_ACTIVE);
    }
    else
    {
      log.error("RSQL LOG ACTIVE (Default): " + StartVariables.RSQL_LOG_ACTIVE);
    }

    // CMG: DESACTIVAMOS EL LOG PARA LA LIBRERIA RSQL : com.github.tennaito.rsql.jpa
    if (!StartVariables.RSQL_LOG_ACTIVE)
    {
      LogManager.getLogManager().reset();
      java.util.logging.Logger globalLogger = java.util.logging.Logger.getLogger(java.util.logging.Logger.GLOBAL_LOGGER_NAME);
      globalLogger.setLevel(java.util.logging.Level.OFF);
    }
    // FIN DESACTIVACION

    if (env.getProperty("geo.xETRS89.field") != null)
    {
      StartVariables.xETRS89_field = env.getProperty("geo.xETRS89.field");
    }
    if (env.getProperty("geo.yETRS89.field") != null)
    {
      StartVariables.yETRS89_field = env.getProperty("geo.yETRS89.field");
    }
    if (env.getProperty("geo.latWGS84.field") != null)
    {
      StartVariables.latWGS84_field = env.getProperty("geo.latWGS84.field");
    }
    if (env.getProperty("geo.lonWGS84.field") != null)
    {
      StartVariables.lonWGS84_field = env.getProperty("geo.lonWGS84.field");
    }
    if (env.getProperty("geo.geometry.field") != null)
    {
      StartVariables.geometry_field = env.getProperty("geo.geometry.field");
    }
    
    //CMG 2021-01-19: Control para definir el separador de los CSV
    if (Util.validValue(env.getProperty(Constants.SEPARATOR_CSV_VALUE)))
    {
    	String auxSeparator = env.getProperty(Constants.SEPARATOR_CSV_VALUE);
    	if (auxSeparator.length()==1) {
    		StartVariables.separator_csv = env.getProperty(Constants.SEPARATOR_CSV_VALUE).charAt(0);
    		log.info("CSV SEPARATOR: " + StartVariables.separator_csv);
    	}else {
    		log.error("[ERROR]: El Caracter Separador definido no es valido: "+ auxSeparator );
    	}
    }
    
    //CMG 2021-01-22: Control para definir el comodin para el tab de los CSV
    if (Util.validValue(env.getProperty(Constants.SEPARATOR_COMODIN_TAB_VALUE)))
    {
    	String auxComodin = env.getProperty(Constants.SEPARATOR_COMODIN_TAB_VALUE);
    	if (auxComodin.length()==1) {
    		StartVariables.separator_comodin_tab = env.getProperty(Constants.SEPARATOR_COMODIN_TAB_VALUE).charAt(0);
    		log.info("CSV COMODIN TAB: " + StartVariables.separator_comodin_tab);
    	}else {
    		log.error("[ERROR]: El Caracter Comodin (TAB) definido no es valido: "+ auxComodin );
    	}
    }

  }

  /*
   * Funcion para extraer las columnas de cada tabla y guardar su nombre en java y
   * su nombre en bbdd
   */
  private HashMap<String, Map<String, String>> generarMapaTablasColumnas()
  {
    HashMap<String, Map<String, String>> mapaClasesColumnas = new HashMap<String, Map<String, String>>();

    try
    {

      final ClassPathScanningCandidateComponentProvider provider = new ClassPathScanningCandidateComponentProvider(false);

      provider.addIncludeFilter(new RegexPatternTypeFilter(Pattern.compile(".*")));

      // get matching classes defined in the package
      final Set<BeanDefinition> classes = provider.findCandidateComponents("dataset.model");

      for (BeanDefinition bean : classes)
      {
	Map<String, String> mapaJavaNameColumna = new HashMap<String, String>();
	Class<?> clazz = Class.forName(bean.getBeanClassName());
	log.debug(clazz.getName());
	Method[] methods = Class.forName(clazz.getName()).getDeclaredMethods();
	for (Method method : methods)
	{
	  if (method.getAnnotation(Column.class) != null)
	  {
	    Column annotation = method.getAnnotation(Column.class);
	    String columna = annotation.name();
	    String metodo = method.getName();
	    // borramos el 'get' inicial
	    metodo = metodo.substring(3);
	    // pasamos a minuscula la primera letra
	    metodo = metodo.substring(0, 1).toLowerCase() + metodo.substring(1);
	    log.debug(metodo + " " + columna);
	    mapaJavaNameColumna.put(metodo, columna);
	  }
	}
	mapaClasesColumnas.put(clazz.getName(), mapaJavaNameColumna);
      }
    }
    catch (Exception e)
    {
      log.error("Error generating mapaClasesColumnas", e);
    }
    return mapaClasesColumnas;
  }

  @Bean
  public InternalResourceViewResolver viewResolver()
  {   
    InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
    viewResolver.setViewClass(JstlView.class);
    viewResolver.setPrefix("/WEB-INF/pages/");
    viewResolver.setSuffix(".jsp"); 
    return viewResolver;
  }

  @Bean
  public CustomAuthenticationProvider customAuthenticationProvider()
  {
    CustomAuthenticationProvider customAuthenticationProvider = new CustomAuthenticationProvider();
    return customAuthenticationProvider;
  }

}
