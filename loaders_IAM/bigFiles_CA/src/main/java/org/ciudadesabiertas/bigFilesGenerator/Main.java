package org.ciudadesabiertas.bigFilesGenerator;


import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

import org.ciudadesabiertas.dataset.model.Agenda;
import org.ciudadesabiertas.dataset.model.AgrupacionComercial;
import org.ciudadesabiertas.dataset.model.Alojamiento;
import org.ciudadesabiertas.dataset.model.AvisoQuejaSug;
import org.ciudadesabiertas.dataset.model.CalidadAireEstacion;
import org.ciudadesabiertas.dataset.model.CalidadAireObservacion;
import org.ciudadesabiertas.dataset.model.CalidadAireSensor;
import org.ciudadesabiertas.dataset.model.CallejeroPortal;
import org.ciudadesabiertas.dataset.model.CallejeroTramoVia;
import org.ciudadesabiertas.dataset.model.CallejeroVia;
import org.ciudadesabiertas.dataset.model.Equipamiento;
import org.ciudadesabiertas.dataset.model.LicenciaActividad;
import org.ciudadesabiertas.dataset.model.LocalComercial;
import org.ciudadesabiertas.dataset.model.Organigrama;
import org.ciudadesabiertas.dataset.model.PuntoInteresTuristico;
import org.ciudadesabiertas.dataset.model.Subvencion;
import org.ciudadesabiertas.dataset.model.Terraza;
import org.ciudadesabiertas.dataset.model.Tramite;
import org.ciudadesabiertas.dataset.utils.AparcamientoConstants;
import org.ciudadesabiertas.dataset.utils.EquipamientoConstants;
import org.ciudadesabiertas.dataset.utils.InstalacionDepConstants;
import org.ciudadesabiertas.dataset.utils.MonumentoConstants;
import org.ciudadesabiertas.dataset.utils.PuntoInteresTuristicoConstants;
import org.ciudadesabiertas.dataset.utils.PuntoWifiConstants;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;




public class Main 
{	
	
	/**
	 * 
	 */
	private static final String RDF_OUTPUT = "rdfOutput";
	/**
	 * 
	 */
	private static final String JSON_OUTPUT = "jsonOutput";
	private static final String TO = " to ";
	private static final String ITEMS_FROM = "Items from ";

	private static final Logger log = initLogBack();
	
	private static Configuration hibernateConfiguration;
	private static SessionFactory sessions;
	
	private static final int limiteRDF=10000;    
	private static final int limiteJSON=10000;
	
	public static void initSesionFactory()
	{
		if (sessions==null)
		{		
			Map<String, String> readHibernateProperties = new HashMap<String,String>();
			try
			{
				readHibernateProperties = readHibernateProperties();
			} catch (Exception e)
			{
				log.error("Error reading hibernate.properties",e);
			}
			
			hibernateConfiguration.setProperty("hibernate.dialect", readHibernateProperties.get("hibernate.dialect"));
			hibernateConfiguration.setProperty("hibernate.connection.url", readHibernateProperties.get("hibernate.connection.url"));
			hibernateConfiguration.setProperty("hibernate.connection.username", readHibernateProperties.get("hibernate.connection.username"));
			hibernateConfiguration.setProperty("hibernate.connection.password", Util.checkAndSetEncodedProperty(readHibernateProperties.get("hibernate.connection.password")));
			hibernateConfiguration.setProperty("hibernate.connection.driver_class", readHibernateProperties.get("hibernate.connection.driver_class"));						
			sessions = hibernateConfiguration.buildSessionFactory();
		}
			
	}
	

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static <T> List<T> dataset(String dataset) throws Exception
	{		
		List<T> result = new ArrayList<T>();
    	    	
    	Session session = sessions.openSession();    	
    	
    	String queryText="";
    	
    	if (dataset.equals("subvencion"))
    	{
    		queryText="From Subvencion";
    	}
    	else if (dataset.equals("agendaCultural"))
    	{
    		queryText="From Agenda";
    	}
    	else if (dataset.equals("alojamiento"))
    	{
    		queryText="From Alojamiento";
    	}
    	else if (dataset.equals("aparcamiento"))
    	{
    		queryText="From Equipamiento";
    		queryText+=" where "+ AparcamientoConstants.TIPO_EQUIPAMIENTO_FIELD + " like '"+AparcamientoConstants.TIPO_EQUIPAMIENTO+"'";
    	}
    	else if (dataset.equals("avisoQuejaSugerencia"))
    	{
    		queryText="From AvisoQuejaSug";
    	}
    	else if (dataset.equals("localComercial"))
    	{
    		queryText="From LocalComercial";
    	}
    	else if (dataset.equals("localComercialAgrupacionComercial"))
    	{
    		queryText="From AgrupacionComercial";
    	}
    	else if (dataset.equals("localComercialLicenciaActividad"))
    	{
    		queryText="From LicenciaActividad";
    	}
    	else if (dataset.equals("localComercialTerraza"))
    	{
    		queryText="From Terraza";
    	}    	
    	else if (dataset.equals("puntoInteresTuristico"))
    	{
    		queryText="From PuntoInteresTuristico";
    		queryText+=" where "+ PuntoInteresTuristicoConstants.CATEGORY_FIELD+ " like '"+PuntoInteresTuristicoConstants.CATEGORY+"'";
    	}
    	else if (dataset.equals("monumento"))
    	{
    		queryText="From PuntoInteresTuristico";
    		queryText+=" where "+ PuntoInteresTuristicoConstants.CATEGORY_FIELD+ " like '"+MonumentoConstants.CATEGORY+"'";
    	}    	
    	else if (dataset.equals("calidadAireEstacion"))
    	{
    		queryText="From CalidadAireEstacion";    		
    	}
    	else if (dataset.equals("calidadAireObservacion"))
    	{
    		queryText="From CalidadAireObservacion";    		
    	}
    	else if (dataset.equals("calidadAireSensor"))
    	{
    		queryText="From CalidadAireSensor";    		
    	}
    	else if (dataset.equals("callejeroPortal"))
    	{
    		queryText="From CallejeroPortal";    		
    	}
    	else if (dataset.equals("callejeroTramoVia"))
    	{
    		queryText="From CallejeroTramoVia";    		
    	}
    	else if (dataset.equals("callejeroVia"))
    	{
    		queryText="From CallejeroVia";    		
    	}
    	else if (dataset.equals("equipamiento"))
    	{
    		queryText="From Equipamiento";
    		queryText+=" where "+ EquipamientoConstants.TIPO_EQUIPAMIENTO_FIELD+ " like '"+EquipamientoConstants.TIPO_EQUIPAMIENTO+"' ";
    	}
    	else if (dataset.equals("instalacionDeportiva"))
    	{
    		queryText="From Equipamiento";
    		queryText+=" where "+ EquipamientoConstants.TIPO_EQUIPAMIENTO_FIELD+ " like '"+InstalacionDepConstants.TIPO_EQUIPAMIENTO+"' ";
    	}
    	else if (dataset.equals("organigrama"))
    	{
    		queryText="From Organigrama";    		
    	}
    	else if (dataset.equals("puntoWifi"))
    	{
    		queryText="From Equipamiento";
    		queryText+=" where "+ EquipamientoConstants.TIPO_EQUIPAMIENTO_FIELD+ " like '"+PuntoWifiConstants.TIPO_EQUIPAMIENTO+"' ";
    	}
    	else if (dataset.equals("tramite"))
    	{
    		queryText="From Tramite";    		
    	}
    	
    	if (queryText.equals(""))
    	{
    		throw new Exception("Dataset without control");
    	}
    	
    	/*
    	alojamiento=true
    	aparcamiento=true
    	avisoQuejaSugerencia=true
    	*/
    	    	
    	
		Query query = session.createQuery(queryText);
		result = query.list();
		
		log.info("There are "+result.size()+" items");
		
		session.close();
		
		return result;
	}
	
	public static void initDatasetClasses(Configuration config)
	{
		config.addAnnotatedClass(Subvencion.class);
		config.addAnnotatedClass(Agenda.class);
		config.addAnnotatedClass(Alojamiento.class);
		config.addAnnotatedClass(Equipamiento.class);
		config.addAnnotatedClass(AvisoQuejaSug.class);
		config.addAnnotatedClass(CalidadAireEstacion.class);
		config.addAnnotatedClass(CalidadAireObservacion.class);
		config.addAnnotatedClass(CalidadAireSensor.class);
		config.addAnnotatedClass(CallejeroPortal.class);
		config.addAnnotatedClass(CallejeroTramoVia.class);
		config.addAnnotatedClass(CallejeroVia.class);
		config.addAnnotatedClass(Equipamiento.class);		
		config.addAnnotatedClass(AgrupacionComercial.class);
		config.addAnnotatedClass(LicenciaActividad.class);
		config.addAnnotatedClass(LocalComercial.class);
		config.addAnnotatedClass(Terraza.class);		
		config.addAnnotatedClass(Organigrama.class);		
		config.addAnnotatedClass(PuntoInteresTuristico.class);		
		config.addAnnotatedClass(Tramite.class);
		
		//TODO CMG: Ver si es posible realizar la carga desde fichero
		/*
		try {
			config.addAnnotatedClass(Class.forName("Subvencion"));
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
	}
	
	public static <T> void generarJSON(List<T> listado, String path, String jsonFileName)
	{
		log.info("JSON");
		int limite=limiteJSON;
		String extension=".json";
		File filePath=new File(path);
		if (filePath.exists()==false)
		{
			filePath.mkdir();
		}		
		
		if (listado.size()<=limite)
    	{	
    		Util.toJSON(listado,path+File.separator+jsonFileName+extension);
    	}
    	else
    	{
    		int numFiles=listado.size()/limite;
    		int rest=listado.size()%limite;
    		
    		for (int i=0;i<numFiles;i++)
    		{
    			int start=i*limite;
    			int stop=((i+1)*limite)-1;
    			log.info(ITEMS_FROM+start+TO+stop);    			
    			Util.toJSON(listado.subList(start, stop),path+File.separator+jsonFileName+i+extension);
    		
    		}
    		if (rest>0)
    		{
	    		int start=numFiles*limite;
				int stop=start+rest;
	    		log.info(ITEMS_FROM+start+TO+stop);	    		
				Util.toJSON(listado.subList(start, stop),path+File.separator+jsonFileName+(numFiles+1)+extension);				
    		}
    	}
		
		
	}


	
	

	public static <T> void generarRDF(List<T> listado, String path, String turtleFileName, String URIBase, String context)
	{
		log.info("RDF");
		int limite=limiteRDF;
		String extension=".ttl";
		File filePath=new File(path);
		if (filePath.exists()==false)
		{
			filePath.mkdir();
		}
		
		//Util.deleteFilesInFolder(path);		
		    	
    	if (listado.size()<=limite)
    	{	
    		Util.toRDF(listado,  path+File.separator+turtleFileName+extension, URIBase, context);
    	}
    	else
    	{
    		int numFiles=listado.size()/limite;
    		int rest=listado.size()%limite;
    		
    		for (int i=0;i<numFiles;i++)
    		{
    			int start=i*limite;
    			int stop=((i+1)*limite)-1;
    			log.info(ITEMS_FROM+start+TO+stop);    			
    			Util.toRDF(listado.subList(start, stop),path+File.separator+turtleFileName+i+extension, URIBase, context);
    		
    		}
    		if (rest>0)
    		{
	    		int start=numFiles*limite;
				int stop=start+rest;
	    		log.info(ITEMS_FROM+start+TO+stop);
	    		
					Util.toRDF(listado.subList(start, stop),path+File.separator+turtleFileName+(numFiles+1)+extension, URIBase, context);
				
    		}
    	}
	}
	
	private static Map<String,String> readConfigProperties() throws Exception
	{
		Map<String,String> config = new HashMap<String,String>();
		
		InputStream input = new FileInputStream("config.properties"); 

        Properties prop = new Properties();
        prop.load(input);

        Set<Entry<Object, Object>> entrySet = prop.entrySet();
        
        Iterator<Entry<Object, Object>> iterator = entrySet.iterator();
        
        while (iterator.hasNext())
        {
        	Entry<Object, Object> next = iterator.next();
        	
        	//Boolean value=Boolean.parseBoolean( (String) next.getValue());
        	
        	config.put((String)next.getKey(),next.getValue().toString().trim());        	
        }
        
        return config;
		
	}
	
	
	private static Map<String,String> readHibernateProperties() throws Exception
	{
		Map<String,String> config = new HashMap<String,String>();
		
		InputStream input = new FileInputStream("hibernate.properties"); 

        Properties prop = new Properties();
        prop.load(input);

        Set<Entry<Object, Object>> entrySet = prop.entrySet();
        
        Iterator<Entry<Object, Object>> iterator = entrySet.iterator();
        
        while (iterator.hasNext())
        {
        	Entry<Object, Object> next = iterator.next();
        	
        	config.put((String)next.getKey(),(String)next.getValue());        	
        }
        
        return config;
		
	}

	
	public static Logger initLogBack()	
	{	
		Logger log = null;
		try
    	{
	    	LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();
	    	context.reset();
	    	JoranConfigurator configurator = new JoranConfigurator();
	    	configurator.setContext(context);
	    	configurator.doConfigure(new File(System.getProperty("user.dir") + File.separator+"logback.xml"));
	    	log = LoggerFactory.getLogger(Main.class);    	
    	}
    	catch (Exception e)
    	{
    		log.error("Errore reading logbax.xml",e);
    	}
		return log;
	}
	
    public static void main( String[] args )
    {    	
    	String URIBase="https://ciudadesabiertas.es";
    	String context="/OpenCitiesAPI";
    	boolean formatoJSON=false;
    	boolean formatoRDF=false;
    	boolean deleteFolder=false;
    	
    	log.info("start"); 
    	hibernateConfiguration = new Configuration();
    	
    	Map<String, String> configProperties=new HashMap<String,String>();
    	
    	
    	    	
    	try
		{
    		configProperties = readConfigProperties();
		} catch (Exception e)
		{
			log.error("Error reading config.properties",e);
			return;
		}
    	
    	try
    	{
    		URIBase=configProperties.get("URIBase");
    		configProperties.remove("URIBase");
    		context=configProperties.get("context");
    		configProperties.remove("context");
    		formatoRDF=Boolean.parseBoolean(configProperties.get("formatoRDF"));
    		configProperties.remove("formatoRDF");
    		formatoJSON=Boolean.parseBoolean(configProperties.get("formatoJSON"));
    		configProperties.remove("formatoJSON");
    		deleteFolder=Boolean.parseBoolean(configProperties.get("deleteOutputFolderOnInit"));
    		configProperties.remove("deleteOutputFolderOnInit");
    	}
    	catch (Exception e)
    	{
    		log.error("Error with configuration info",e);
    		return;
    	}
    	
    	if ((formatoJSON==false)&&(formatoRDF==false))
    	{
    		log.info("Both format are setted as false, I can't work");
    		return;
    	}
    	
    	if (deleteFolder)
    	{
    		log.info("old files clean activated");
	    	if ((formatoJSON)||(formatoRDF))
	    	{
		    	if (formatoJSON)
				{
		    		File filePath=new File(JSON_OUTPUT);
		    		if (filePath.exists())
		    		{
		    			Util.deleteFilesInFolder(JSON_OUTPUT);
		    		}
				}
				if (formatoRDF)
				{				
					File filePath=new File(RDF_OUTPUT);
		    		if (filePath.exists())
		    		{
		    			Util.deleteFilesInFolder(RDF_OUTPUT);
		    		}
				}
	    	}
    	}
    	
    	
    	if (configProperties.size()>0)
    	{
    		initDatasetClasses(hibernateConfiguration);
    		initSesionFactory();    	
    		    		    		
    		for (String dataset:configProperties.keySet())
    		{   
    			if (configProperties.get(dataset).toString().equals("true"))
    			{
    				log.info("......................"+dataset);
    				List<Subvencion> listado=new ArrayList<Subvencion>();
					try
					{
						listado = dataset(dataset);
					} 
					catch (Exception e)
					{
						log.error("Error retrieving data",e);
					}
					if (listado.size()>0)
					{						
						if (formatoJSON)
						{
							generarJSON(listado, JSON_OUTPUT, dataset);
						}
						if (formatoRDF)
						{
							generarRDF(listado, RDF_OUTPUT, dataset, URIBase, context);
						}
					}
    			}	
    		}			
    	}
    	    	
    	sessions.close();    
    	
    	log.info("exit");
		
    	
    }


	
	
}
