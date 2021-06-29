package com.localidata.extractorContratos;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.localidata.extractorContratos.service.MailService;
import com.localidata.extractorContratos.util.Constants;
import com.localidata.extractorContratos.util.Util;

public class DescargaPeriodica {
	
	private static final int CRITICAL_ERROR = -2;

	private static final int ERROR = -1;

	private static Logger log = Logger.getLogger(DescargaPeriodica.class);

	private static Map<String, String> config = null;
	private static Map<String, String> lastRunConfig = null;
	private static Map<String, Boolean> downloadedFileStatus = new LinkedHashMap<String,Boolean>();
	
	public static String FOLDER_LICITACIONES_PERIODICAS = "licitacionesPeriodicas";
	
	private static MailService serviceMail= new MailService();

	public int run() {

		boolean criticalError=false;
		
		FOLDER_LICITACIONES_PERIODICAS=config.get(Constants.PARAM_CONFIG_PATH_FILE_LICITACIONES);
		
		List<String> atomList = new ArrayList<String>();
		atomList.add(config.get("carga.periodica.atom"));

		
		Date lastRunDate = Util.generateDateFromString((String) lastRunConfig.get("lastRun"));
		if (lastRunDate == null) {
			log.error("[run] [Error: Fichero lastRun.properties no ha sido configurado correctamente no se ha encontrado fecha de ultima ejecución]" );
			String errorMail = "Error: Fichero lastRun.properties no ha sido configurado correctamente no se ha encontrado fecha de ultima ejecución";
			serviceMail.enviar(Constants.MAIL_ASUNTO_PROCESO_ERROR, errorMail);
			return ERROR;
		}
		String[] extensions={"atom"};
		Collection<File> files = FileUtils.listFiles(new File(FOLDER_LICITACIONES_PERIODICAS), extensions, true);
		if (files==null)
		{
			log.error("The folder: "+FOLDER_LICITACIONES_PERIODICAS+" is not found");
			String errorMail = "Error: The folder: "+FOLDER_LICITACIONES_PERIODICAS+" is not found";
			serviceMail.enviar(Constants.MAIL_ASUNTO_PROCESO_ERROR, errorMail);
			return ERROR;
		}
		else if (files.size()>0)
		{
			log.error("The folder: "+FOLDER_LICITACIONES_PERIODICAS+" is not empty, clean before throw this proccess");
			String errorMail = "Error: El directorio: "+FOLDER_LICITACIONES_PERIODICAS+" no esta vacio, por elimine los ficheros antes de lanzar el proceso";
			serviceMail.enviar(Constants.MAIL_ASUNTO_PROCESO_ERROR, errorMail);
			return ERROR;
		} else {

			for (String atom : atomList) {

				int numPagina = 0;
				boolean descarga = true;
				while (descarga) {
					log.info(numPagina + " " + atom);
					String content = Util.getContentURL(atom);
					if ((content == null) || (content.contentEquals(""))) {
						criticalError = true;
						break;
					}
					String jsonText = Util.xmlToJSON(content);
					if (jsonText == null) {
						criticalError = true;
						break;
					}
					JSONObject joContent = Util.parseJSONObject(jsonText);
					if (joContent == null) {
						criticalError = true;
						break;
					}

					JSONObject joFeed = (JSONObject) joContent.get("feed");
					String updated = (String) joFeed.get("updated");
					String updatedCompleto = updated.substring(0, updated.indexOf("."));

					Date updateDate = Util.generateDateFromString(updatedCompleto);

					log.info(lastRunDate + " before " + updateDate + ":" + lastRunDate.before(updateDate));
					if (lastRunDate.before(updateDate)) {
						descarga = true;
					} else {
						descarga = false;
					}

					if (descarga) {

						String fileName = atom.substring(atom.lastIndexOf("/") + 1);

						int status = Util.readAndWriteURL(atom, FOLDER_LICITACIONES_PERIODICAS + File.separator
								+ "licitacionesPerfilesContratanteCompleto3" + File.separator + fileName);

						if (status == 0) {
							downloadedFileStatus.put(fileName + "    " + atom, Boolean.TRUE);
						} else {
							downloadedFileStatus.put(fileName + "    " + atom, Boolean.FALSE);
						}

					}

					JSONArray jaLink = (JSONArray) joFeed.get("link");
					if (jaLink.size() > 0) {
						for (int a = 0; a < jaLink.size(); a++) {
							JSONObject link = (JSONObject) jaLink.get(a);
							String rel = (String) link.get("rel");
							if (rel.equals("next")) {
								atom = (String) link.get("href");
							}
						}
					}

					numPagina++;
				}
				if (criticalError) {
					break;
				}
			}
			if (criticalError) {
				try {
					FileUtils.cleanDirectory(new File(FOLDER_LICITACIONES_PERIODICAS));
				} catch (IOException e) {
					log.error("Error deleting files", e);
					log.error("Please remove the files manualy");
					String errorMail = "Error deleting files :" + e.getMessage();
					errorMail += "\n" + "Please remove the files manualy";
					serviceMail.enviar(Constants.MAIL_ASUNTO_PROCESO_ERROR, errorMail);
				}
				return CRITICAL_ERROR;
			} else {
				boolean allFilesOK = true;
				log.info("download info:");
				for (String key : downloadedFileStatus.keySet()) {
					log.info("\t" + downloadedFileStatus.get(key) + "\t" + key);
					if (downloadedFileStatus.get(key).booleanValue() == false) {
						allFilesOK = false;
					}
				}
				if (allFilesOK) {					
					return 0;
				} else {
					log.info("All files deleted by a previous error");
					try {
						FileUtils.cleanDirectory(new File(FOLDER_LICITACIONES_PERIODICAS));
					} catch (IOException e) {
						log.error("Error deleting files", e);
						log.error("Please remove the files manualy");
						String errorMail = "Error deleting files :" + e.getMessage();
						errorMail += "\n" + "Please remove the files manualy";
						serviceMail.enviar(Constants.MAIL_ASUNTO_PROCESO_ERROR, errorMail);
					}
					return ERROR;
				}

			}
		}

	}

	public static int main(String[] args) {
		PropertyConfigurator.configure("log4j.properties");

		config = Util.readConfigProperties(Constants.FILE_CONFIG);

		lastRunConfig = Util.readConfigProperties(Constants.FILE_LAST_RUN);

		DescargaPeriodica carga = new DescargaPeriodica();
		int status=carga.run();

		return status;

	}

}
