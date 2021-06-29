package com.localidata.extractorContratos.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

public class ConfigExtractor {

	private static Logger log = Logger.getLogger(ConfigExtractor.class);

	private List<String> filterList = new ArrayList<String>();
	private  String municipioId = null;
	private  String municipioTitle = null;
	private  String PATH_FILE_EXTRACTOR = null;
	private  Boolean loadInitial = false;
	private  String mailHost = null;
	private  String mailPort = null;
	private  Boolean mailAuth = false;
	private  String mailFrom = null;
	private  String mailTo = null;
	private  String mailUser = null;
	private  String mailPass = null;
	private  Boolean mailSimular = false;
	private int backupDays=0;
	private String bbddURL="";
	private String entorno="";
	
	private  static Map<String, String> config = null;

	// BBDD listado
	public static final String[] LIST_BBDD = { 
			//Constants.BBDD_SQLITE, 
			//Constants.BBDD_SQL_ORACLE,
			Constants.BBDD_SQL_SERVER
			//Constants.BBDD_SQL_MYSQL, 
			//Constants.BBDD_SQL_POSTGRESQL 
			};

	public ConfigExtractor() {

		if (config == null) {
			config = Util.readConfigProperties(Constants.FILE_CONFIG);
		}
		configuration();

	}

	// TODO leer información de properties
	private void configuration() {

		log.info("[configuration] [init]");
		String auxParam = null;
		if (config != null && !config.isEmpty()) {
			log.debug("[configuration] [loading params...]");

			// Load Intial (ON / OFF)
			loadInitial=loadParam(Constants.PARAM_CONFIG_LOAD_INITIAL, "on");

			// party
			auxParam = config.get(Constants.PARAM_CONFIG_PARTY_IDENTIFICATION);
			log.debug("[configuration] [PARAM_CONFIG_PARTY_IDENTIFICATION:" + auxParam + "]");
			if (auxParam != null) {
				String[] listado = auxParam.split(Constants.SEPARATOR_PARTY);
				if (listado != null && listado.length > 0) {
					for (String partyId : listado) {
						filterList.add(partyId);
					}

				} else {
					log.error("[configuration] ERROR LISTS PARTY IDENTIFICATION NOT FOUND");
				}
			} else {
				log.error("[configuration] ERROR PARAM_CONFIG_PARTY_IDENTIFICATION NOT FOUND");
			}

			// MUNICIPIO ID
			municipioId=loadParam(Constants.PARAM_CONFIG_MUNICIPIO_ID);

			// MUNICIPIO TITLE
			municipioTitle=loadParam(Constants.PARAM_CONFIG_MUNICIPIO_TITLE);

			// BBDD SQLITE URL
			DataBaseSQLiteUtil.DB_URL=loadParam(Constants.PARAM_CONFIG_BBDD_URL);

			
			// BBDD SQLFINAL CONF
			DataBaseFinalUtil.DB_URL=loadParam(Constants.PARAM_CONFIG_BBDD_FINAL_URL);
			DataBaseFinalUtil.DB_USER=loadParam(Constants.PARAM_CONFIG_BBDD_FINAL_USER);
			DataBaseFinalUtil.DB_PASSWORD=loadParam(Constants.PARAM_CONFIG_BBDD_FINAL_PASSWORD);
			DataBaseFinalUtil.DB_SCHEMA=loadParam(Constants.PARAM_CONFIG_BBDD_FINAL_SCHEMA);
			
			
			// PATH FILE EXTRACTOR
			PATH_FILE_EXTRACTOR=loadParam(Constants.PARAM_CONFIG_PATH_FILE_LICITACIONES );
			
			//Configuracion de correo
			mailAuth=loadParam(Constants.PARAM_MAIL_AUTH,"true");
			mailFrom=loadParam(Constants.PARAM_MAIL_FROM);
			mailTo=loadParam(Constants.PARAM_MAIL_TO);
			mailHost=loadParam(Constants.PARAM_MAIL_HOST);
			mailPort=loadParam(Constants.PARAM_MAIL_PORT);
			mailUser=loadParam(Constants.PARAM_MAIL_USER);
			mailPass=loadParam(Constants.PARAM_MAIL_PASS);
			mailSimular=loadParam(Constants.PARAM_MAIL_SIMULA,"true");
			
			bbddURL=loadParam(Constants.PARAM_CONFIG_BBDD_URL);
			
			try
			{
				String backup=loadParam(Constants.BBDD_BACKUP_DAYS);
				backupDays=Integer.parseInt(backup.trim());
			}
			catch (Exception e)
			{
				log.error("Error reading "+Constants.BBDD_BACKUP_DAYS+" param",e);
			}
			
			entorno=loadParam(Constants.PARAM_ENTORNO_EXTRACTOR);
			
		} else {
			log.error("[configuration] ERROR FILE 'config.properties' NOT FOUND");
		}

		log.info("[configuration] [end]");

	}

	// SOLO GET
	
	

	public List<String> getFilterList() {
		return filterList;
	}

	public String getBbddURL() {
		return bbddURL;
	}

	public String getMunicipioId() {
		return municipioId;
	}

	public String getMunicipioTitle() {
		return municipioTitle;
	}

	public String getPATH_FILE_EXTRACTOR() {
		return PATH_FILE_EXTRACTOR;
	}

	public Boolean isLoadInitial() {
		return loadInitial;
	}

	public String getMailHost() {
		return mailHost;
	}

	public String getMailPort() {
		return mailPort;
	}

	public Boolean getMailAuth() {
		return mailAuth;
	}

	public String getMailFrom() {
		return mailFrom;
	}

	public String getMailTo() {
		return mailTo;
	}

	public String getMailUser() {
		return mailUser;
	}

	public String getMailPass() {
		return mailPass;
	}
	
	public Boolean getMailSimular() {
		return mailSimular;
	}
	
	
	public int getBackupDays() {
		return backupDays;
	}
	
	public String getEntorno() {
		return entorno;
	}

	//Privados
	private String loadParam(String parametroFile) {
		String auxParam = config.get(parametroFile);
		log.debug("[loadParam] ["+parametroFile+":" + auxParam + "]");
		if (auxParam != null) {
			return auxParam;
		} else {
			log.error("[loadParam] ERROR "+parametroFile+" NOT FOUND");
		}
		return null;
	}
	
	private Boolean loadParam(String parametroFile,  String comparar) {
		String auxParam = config.get(parametroFile);
		log.debug("[loadParam] ["+parametroFile+":" + auxParam + "]");
		
		if (auxParam != null) {
			return comparar.equals(auxParam.toLowerCase());
		} else {
			log.error("[loadParam] ERROR "+parametroFile+" NOT FOUND");
		}
		return null;
	}
	


}
