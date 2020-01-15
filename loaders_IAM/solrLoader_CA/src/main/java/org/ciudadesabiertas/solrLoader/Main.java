package org.ciudadesabiertas.solrLoader;

import java.io.File;
import java.util.ArrayList;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.BasicConfigurator;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.ciudadesabiertas.util.Constants;
import org.ciudadesabiertas.util.Datasets;
import org.ciudadesabiertas.util.Util;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main
{
	private static final Logger log = LoggerFactory.getLogger(Main.class);

	private static JSONParser parser = new JSONParser();

	public static void main(String[] args) throws Exception
	{
		BasicConfigurator.configure();
		org.apache.log4j.Logger.getRootLogger().setLevel(org.apache.log4j.Level.INFO);

		Properties prop = Util.readProperties("config.properties");

		Datasets datasets = null;
		try
		{
			datasets = Util.readDatasetsToImport(prop);
		} catch (Exception e1)
		{
			log.error("Error reading datasets info", e1);
			return;
		}

		if (args.length == 0)
		{
			String urlString = prop.getProperty(Constants.FIELD_PROPERTIES_SOLRCOREURI);
			String apiBaseURI = prop.getProperty(Constants.FIELD_PROPERTIES_APIBASEURI);

			String username = prop.getProperty(Constants.FIELD_PROPERTIES_SOLRUSERNAME);
			String password = prop.getProperty(Constants.FIELD_PROPERTIES_SOLRPASSWORD);
			if (username != null)
			{
				username = username.trim();
			}
			if (password != null)
			{
				password = password.trim();
			}

			SolrClient client = new SolrClient(urlString);
			

			String jsonPath = prop.getProperty(Constants.FIELD_PROPERTIES_JSONPATH);
			File dir = new File(jsonPath);
			File[] directoryListing = dir.listFiles();

			//Leemos solo los ficheros que se soliciten en datasets
			ArrayList<File> filesToLoad = generateFilesToLoad(datasets, directoryListing);
			
			if (filesToLoad.size()==0)
			{
				log.error("No files to load, check configuration");
			}
			
			for (File child : filesToLoad)
			{
				log.info(child.getName());
				String jsonText = null;
				JSONArray respuestaJSON = null;
				try
				{
					jsonText = FileUtils.readFileToString(child, "UTF-8");
					respuestaJSON = (JSONArray) parser.parse(jsonText);
				} catch (Exception e)
				{
					log.error("Error reading file " + child.getName(), e);
				}

				if (respuestaJSON != null)
				{
					Util.sleep(100);

					UpdateResponse add = null;
					
					String datasetName=child.getName().replace(".json", "");
					String URL=apiBaseURI+Util.urlForDataset(datasetName);
					
					log.info("Dataset: "+datasetName);
					
					client.setDatasetName(datasetName);
					client.setPrefixURI(URL);					

					if ((Util.validValue(username)) || (Util.validValue(password)))
					{
						add = client.add((JSONArray) respuestaJSON, datasetName, username, password);
					} else
					{
						add = client.add((JSONArray) respuestaJSON, datasetName);
					}
					log.info(add.toString());
				}
			}

		} 
		
		
		log.info("end");
	}

	private static ArrayList<File> generateFilesToLoad(Datasets datasets, File[] directoryListing)
	{
		ArrayList<File> filesToLoad = new ArrayList<File>();
		for (File child : directoryListing)
		{
			String name = child.getName();
			if (name.equals(Constants.PLACEHOLDER)==false)
			{
				name = name.replace(".json", "");
				try
				{
					if (datasets.getDataset(name))
					{
						filesToLoad.add(child);
					}
				} catch (Exception e)
				{
					log.error("dataset not found", e);
				}
			}
		}
		return filesToLoad;
	}

	
}
