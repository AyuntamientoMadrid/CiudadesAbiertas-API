package com.localidata.extractorContratos.util;

public class Constants {
	
	public static String FILE_CONFIG = "config.properties";
	public static String FILE_LAST_RUN = "lastRun.properties";
	
	public static String PARAM_CONFIG_LOAD_INITIAL = "load.initial";
	public static String PARAM_CONFIG_PARTY_IDENTIFICATION = "party.identification";
	public static String PARAM_CONFIG_MUNICIPIO_ID = "municipio.id";
	public static String PARAM_CONFIG_MUNICIPIO_TITLE = "municipio.title";
	
	//BBDD SQLITE
	public static String PARAM_CONFIG_BBDD_URL = "db.url";
	
	//BBDD FINAL
	public static String PARAM_CONFIG_BBDD_FINAL_URL = "db.final.url";
	public static String PARAM_CONFIG_BBDD_FINAL_USER = "db.final.user";
	public static String PARAM_CONFIG_BBDD_FINAL_PASSWORD = "db.final.password";
	public static String PARAM_CONFIG_BBDD_FINAL_SCHEMA = "db.final.schema";
	

	
	
	
	//Ruta de las licitaciones para el extractor
	public static String PARAM_CONFIG_PATH_FILE_LICITACIONES = "path.extractor.licitaciones";
	
	public static String SEPARATOR_PARTY=",";
	
	public static final String DATE_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";
	public static final String DATE_TIME_FORMAT_B = "yyyy-MM-dd HH:mm:ss";
	public static final String DATE_TIME_FORMAT_BACKUP_FILE = "yyyy-MM-dd_HH_mm_ss";
	public static final String DATE_FORMAT = "yyyy-MM-dd";
	public static final String DATE_FORMAT_B = "dd-MM-yyyy";
	public static final String TIME_FORMAT = "HH:mm:ss";
	public static final String TIME_FORMAT_SHORT = "HH:mm";
	
	public static final String DATE_TIME_FORMAT_ORACLE = "yyyy/mm/dd hh24:mi:ss";
	public static final String DATE_FORMAT_ORACLE = "yyyy/mm/dd";
	
	public static final String DATE_TIME_FORMAT_SQLITE = "YYYY-MM-DDTHH:MM:SS";
	public static final String DATE_FORMAT_SQLITE = "YYYY-MM-DD";

	public static final String BBDD_SQLITE="sqlite";
	public static final String BBDD_SQL_SERVER="sqlserver";
	public static final String BBDD_SQL_ORACLE="oracle";
	public static final String BBDD_SQL_MYSQL="mysql";
	public static final String BBDD_SQL_POSTGRESQL="postgresql";
	
	public static final String BBDD_BACKUP_DAYS="db.backup.days";
	
	public static final String INITIAL_OUTPUT_FOLDER="output_initial";
	public static final String PERIODIC_OUTPUT_FOLDER = "output_periodic";
	
	//Configuracion del correo
	public static final String PARAM_MAIL_HOST="mail.host";
	public static final String PARAM_MAIL_PORT="mail.port";
	public static final String PARAM_MAIL_AUTH="mail.auth";
	public static final String PARAM_MAIL_FROM="mail.from";
	public static final String PARAM_MAIL_TO="mail.to";
	public static final String PARAM_MAIL_USER="mail.username";
	public static final String PARAM_MAIL_PASS="mail.password";
	public static final String PARAM_MAIL_SIMULA="mail.simular";
	
	//Asutos configurados para el MAil
	public static final String MAIL_ASUNTO_PROCESO_OK = "[Extractor de Contratos] Extracción - OK";
	public static final String MAIL_ASUNTO_PROCESO_ERROR = "[Extractor de Contratos] Errores en la extracción";
	public static final String MAIL_ASUNTO_TEST = "[Extractor de Contratos] Tests de emails";
	
	public static final String PARAM_ENTORNO_EXTRACTOR = "entorno.extractor";
	
	
}
