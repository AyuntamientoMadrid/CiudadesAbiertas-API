package org.ciudadesAbiertas.madrid.service.dynamic;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.ciudadesAbiertas.madrid.dao.dynamic.DynamicDao;
import org.ciudadesAbiertas.madrid.dao.dynamic.DynamicDaoMultipleDatabase;
import org.ciudadesAbiertas.madrid.model.dynamic.QueryConfD;
import org.ciudadesAbiertas.madrid.model.dynamic.QueryD;
import org.ciudadesAbiertas.madrid.utils.CsvWritter;
import org.ciudadesAbiertas.madrid.utils.StartVariables;
import org.ciudadesAbiertas.madrid.utils.Util;
import org.ciudadesAbiertas.madrid.utils.constants.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

@Service("ProcessService")
public class ProcessService {

    private static final Logger log = LoggerFactory.getLogger(ProcessService.class);

    @Autowired
    private DynamicDao dynamicDao;

	@Autowired
	private DynamicDaoMultipleDatabase dynamicDaoMultipleDatabase;

	SimpleDateFormat fileDateFormat = new SimpleDateFormat("yyyyMMdd'T'HHmmss");

	@Transactional(readOnly = true)
	public String query(QueryD query, QueryConfD conf) {
	    
	    	boolean removeOriginalFile=true;

		String database = query.getDatabase();
		String queryText = query.getTexto();

		boolean errors = false;
		String errorMessage = null;
		List<Object> results = new ArrayList<Object>();

		try {
			if (database.equals(Constants.DEFAULT_DATABASE)) {
				results = dynamicDao.query(database, queryText);
			} else {
				results = dynamicDaoMultipleDatabase.query(database, queryText);
			}
		} catch (Exception e) {
			errors = true;
			errorMessage = "[ProcessService] [query] [database:" + database + "] [queryText:"+queryText+"] Error:" + e.getMessage();
			return errorMessage;
		}

		int size = results.size();
		log.info("Results: " + size);
		List<Integer> listPaginations = new ArrayList<Integer>();

		if ((conf.getItems() > 0) && (size > conf.getItems())) {
			long numberSplits = size / conf.getItems();

			for (int i = 0; i <= numberSplits; i++) {
				long pagination = i * conf.getItems();
				if (pagination > 0) {
					pagination--;
				}
				listPaginations.add(Util.integerFromLong(pagination));
			}

			long rest = size % conf.getItems();
			if (rest > 0) {
				long lastPagination = (numberSplits * conf.getItems()) + rest;
				lastPagination--;
				listPaginations.add(Util.integerFromLong(lastPagination));
			}

		}

		List<String> generatedFiles = new ArrayList<String>();

		log.info("generating files (CSV)...");
		//No generamos errores para esta petición
		String errorCSV = generateCsvFiles(conf, results, listPaginations, generatedFiles);
		if (errorCSV!=null && !"".equals(errorCSV)) {
			log.error("[query] " + errorCSV);
		}
		
		log.info("generating files (JSON y XML)...");
		//No generamos errores para esta petición
		String errorJson = generateJsonXmlFiles(conf, results, listPaginations, generatedFiles);
		if (errorJson!=null && !"".equals(errorJson)) {
			log.error("[query] " + errorJson);
		}

		log.info("files generated: " + generatedFiles.size());

		if ((conf.getZip() == true) && (generatedFiles.size() > 0)) {
			log.info("zipping " + generatedFiles.size() + " files");
			for (String file : generatedFiles) {
				int extensionPosition = file.lastIndexOf(".");
				String extension = file.substring(extensionPosition + 1);
				String filePath = file.substring(0, extensionPosition);

				filePath = filePath + extension.toUpperCase() + ".zip";
				log.info(filePath + "...");

				if (conf.getOverwrite() == false) {
					errorMessage = renameOldFile(conf, filePath);
					if (errorMessage != null && !"".equals(errorMessage)) {
						errors = true;
						return errorMessage;
					}
				}
				Util.zipFile(file, filePath, removeOriginalFile);
			}
		}

		return errorMessage;

	}

	private String generateJsonXmlFiles(QueryConfD conf, List<Object> results, List<Integer> listPaginations,
			List<String> generatedFiles) {
		boolean errors = false;
		ObjectMapper jsonMapper = StartVariables.jsonConverter.getObjectMapper();
		ObjectMapper xmlMapper = StartVariables.xmlConverter.getObjectMapper();
		String errores = null;
		if (listPaginations.size() == 0) {
			//CMG: Controlamos los patrones FECHA y HORA
			String pathFiltro = Util.filtroPath(conf.getPath());
			String jsonFilePath = pathFiltro + ".json";
			String xmlFilePath = pathFiltro + ".xml";
			if (conf.getOverwrite() == false) {
				errores = renameOldFile(conf, jsonFilePath);
				if (errores != null && !"".equals(errores)) {
					return errores;
				}
				errores = renameOldFile(conf, xmlFilePath);
				if (errores != null && !"".equals(errores)) {
					return errores;
				}
			}

			// Preactions JSON
			addContentToFile(jsonFilePath, "[", false);
			// Preactions XML
			addContentToFile(xmlFilePath, " <records>", false);

			int counter = 0;
			for (Object record : results) {
				try {
					String jsonObject = jsonMapper.writeValueAsString(record);
					addContentToFile(jsonFilePath, jsonObject, true);

					String xmlObject = xmlMapper.writeValueAsString(record);
					addContentToFile(xmlFilePath, xmlObject.replace("LinkedHashMap", "record"), true);

				} catch (Exception e) {
					log.error("Error generating json for " + counter + " object: " + record.toString());
					errores = "[ProcessService] [generateJsonXmlFiles] Error generating json for " + counter
							+ " object: " + record.toString() + ". Error: " + e.getMessage();
					errors = true;
				}

				if (counter != listPaginations.size() - 1) {
					addContentToFile(jsonFilePath, ",", true);
				}
				counter++;

				if (counter % 1000 == 0) {
					log.info(counter + " items added");
				}
			}

			// Post actions JSON
			addContentToFile(jsonFilePath, "]", true);
			generatedFiles.add(jsonFilePath);

			// Post actions XML
			addContentToFile(xmlFilePath, " </records>", true);
			generatedFiles.add(xmlFilePath);
		} else {
			String zeros = addZeros(listPaginations.size());
			for (int i = 0; i < listPaginations.size() - 1; i++) {
				//CMG: Controlamos los patrones FECHA y HORA
				String pathFiltro = Util.filtroPath(conf.getPath());
				String jsonFilePath = pathFiltro + (zeros + (i + 1)) + ".json";
				String xmlFilePath = pathFiltro + (zeros + (i + 1)) + ".xml";

				if (conf.getOverwrite() == false) {
					errores = renameOldFile(conf, jsonFilePath);
					if (errores != null && !"".equals(errores)) {
						return errores;
					}
					errores = renameOldFile(conf, xmlFilePath);
					if (errores != null && !"".equals(errores)) {
						return errores;
					}
				}

				// Preactions JSON
				addContentToFile(jsonFilePath, "[", false);
				// Preactions XML
				addContentToFile(xmlFilePath, " <records>", false);

				List<Object> subList = results.subList(listPaginations.get(i), listPaginations.get(i + 1));
				int counter = 0;
				for (Object record : subList) {
					try {
						String jsonObject = jsonMapper.writeValueAsString(record);
						addContentToFile(jsonFilePath, jsonObject, true);

						String xmlObject = xmlMapper.writeValueAsString(record);
						addContentToFile(xmlFilePath, xmlObject.replace("LinkedHashMap", "record"), true);
					} catch (Exception e) {
						log.error("Error generating json for " + counter + " object: " + record.toString());
						errores = "[ProcessService] [generateJsonXmlFiles] Error generating json for " + counter
								+ " object: " + record.toString() + ". Error: " + e.getMessage();
						errors = true;
					}

					if (counter != conf.getItems() - 1) {
						addContentToFile(jsonFilePath, ",", true);
					}
					counter++;
				}
				// Post actions JSON
				addContentToFile(jsonFilePath, "]", true);
				generatedFiles.add(jsonFilePath);

				// Post actions XML
				addContentToFile(xmlFilePath, " </records>", true);
				generatedFiles.add(xmlFilePath);

				log.info("Sublist " + (i + 1) + " JSON and XML files generated");
			}
		}
		return errores;
	}

	private String addZeros(int size) {
		String sizeSt = "" + size;
		String zerosString = "";
		for (int i = 0; i < sizeSt.length() - 1; i++) {
			zerosString += "0";
		}
		return zerosString;
	}

	private String generateCsvFiles(QueryConfD conf, List<Object> results, List<Integer> listPaginations,
			List<String> generatedFiles) {
		boolean errors = false;
		String errores = null;
		if (listPaginations.size() == 0) {
			//CMG: Controlamos los patrones FECHA y HORA
			String filePath = Util.filtroPath(conf.getPath()) + ".csv";
			if (conf.getOverwrite() == false) {
				errores = renameOldFile(conf, filePath);
				if (errores != null && !"".equals(errores)) {
					return errores;
				}
			}
			try {
				CsvWritter csvWritter = new CsvWritter(filePath);
				csvWritter.write(results);
				log.info("Full CSV file generated");
				//CMG: Controlamos los patrones FECHA y HORA
				generatedFiles.add(Util.filtroPath(conf.getPath()) + ".csv");
			} catch (Exception e) {
				log.error("Errors generating CSV", e);
				errores = "[ProcessService] [generateCsvFiles] Errors generating CSV: "+e.getMessage();
				errors = true;
			}
		} else {
			String zeros = addZeros(listPaginations.size());
			for (int i = 0; i < listPaginations.size() - 1; i++) {
				List<Object> subList = results.subList(listPaginations.get(i), listPaginations.get(i + 1));
				//CMG: Controlamos los patrones FECHA y HORA
				String filePath = Util.filtroPath(conf.getPath()) + (zeros + (i + 1)) + ".csv";
				if (conf.getOverwrite() == false) {
					errores = renameOldFile(conf, filePath);
					if (errores != null && !"".equals(errores)) {
						return errores;
					}
				}
				try {
					CsvWritter csvWritter = new CsvWritter(filePath);
					csvWritter.write(subList);
					log.info("Sublist " + (i + 1) + " CSV file generated");
					generatedFiles.add(filePath);
				} catch (Exception e) {
					log.error("Errors generating CSV sublist: from " + listPaginations.get(i) + " to "
							+ listPaginations.get(i + 1), e);
					errors = true;
					errores = "[ProcessService] [generateCsvFiles] Errors generating CSV sublist: from " + listPaginations.get(i)
							+ " to " + listPaginations.get(i + 1) + e.getMessage();
				}
			}
		}
		return errores;
	}

    private String renameOldFile(QueryConfD conf, String filePath) {
	try {
	    File previousFile = new File(filePath);
	    if (previousFile.exists()) {
		String fileDate = fileDateFormat.format(previousFile.lastModified());
		String fileNameWithOutExt = FilenameUtils.removeExtension(filePath);		
		String oldFilePath = fileNameWithOutExt + fileDate + "." + FilenameUtils.getExtension(filePath);
		log.debug(previousFile.getAbsolutePath());
		log.debug("to");
		log.debug(oldFilePath);
		previousFile.renameTo(new File(oldFilePath));
	    }
	} catch (Exception e) {
	    log.error("Error renaming old file", e);
	    String error = "[ProcessService] [renameOldFile] [QueryConfD:"+conf+"] [filePath:"+filePath+"] Error renaming old file: "+e.getMessage();
	    return error;
	}
	return null;
    }

    private void addContentToFile(String path, String data, boolean append) {
	try {
	    FileUtils.write(new File(path), data, "utf8", append);
	} catch (IOException e) {
	    log.error("Error writting data in file", e);
	}
    }

}