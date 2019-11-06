package org.ciudadesabiertas.solrLoader;

import java.io.IOException;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.BasicConfigurator;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.ciudadesabiertas.util.Util;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Query
{
	private static final Logger log = LoggerFactory.getLogger(Query.class);

	public static void main(String[] args)
	{
		BasicConfigurator.configure();
    	org.apache.log4j.Logger.getRootLogger().setLevel(org.apache.log4j.Level.INFO);
    	
    	Properties prop = Util.readProperties("config.properties");    	
    	
    	String urlString =prop.getProperty("solrCoreURI");
	    String apiBaseURI= prop.getProperty("apiBaseURI");
	    String datasets=prop.getProperty("datasetsToLoad");

	    String[] datasetsArray=datasets.split(",");
	    
	    
    	SolrClient client=new SolrClient(urlString);
    	
    	SolrQuery query = new SolrQuery();
    	query.setQuery("datasetName:agenda");
    	query.setStart(0);
    	query.setRows(10);
    	 
    	QueryResponse response = client.query(query);
    	
    	log.info(response.toString());
    
    	    	
    	log.info("end");
	}

	
	
	
	
	

}
