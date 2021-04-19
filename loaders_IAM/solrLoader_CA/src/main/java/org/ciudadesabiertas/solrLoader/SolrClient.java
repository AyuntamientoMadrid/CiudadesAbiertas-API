package org.ciudadesabiertas.solrLoader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.log4j.BasicConfigurator;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrRequest;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.request.QueryRequest;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrInputDocument;
import org.ciudadesabiertas.util.Constants;
import org.ciudadesabiertas.util.Util;
import org.apache.solr.client.solrj.request.UpdateRequest;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SolrClient {
	
static Map<String,String> checkURIsMap=new HashMap<String,String>();
	
public final static String DATASET_NAME = "datasetName";
public final static String URI = "objectURI";

private static final Logger log = LoggerFactory.getLogger(SolrClient.class);

private String urlString;

private HttpSolrClient solr;

private String datasetName;
private String datasetURI;
private String prefixURI;

public String getDatasetName() {
  return datasetName;
}

public void setDatasetName(String datasetName) {
  this.datasetName = datasetName;
}

public String getPrefixURI() {
  return prefixURI;
}

public void setPrefixURI(String prefixURI) {
  this.prefixURI = prefixURI;
}

public String getDatasetURI() {
  return datasetURI;
}

public void setDatasetURI(String datasetURI) {
  this.datasetURI = datasetURI;
}

public SolrClient(String urlString) {
  super();
  solr = new HttpSolrClient.Builder(urlString).build();
  this.urlString = urlString;
}

public UpdateResponse add(SolrInputDocument document, String datasetName) {
  UpdateResponse response = null;
  try {
	addCustomFields(document, datasetName);

	response = solr.add(document);
	solr.commit();
	return response;
  } catch (SolrServerException | IOException e) {
	log.error("Error adding document", e);
  }
  return response;

}

public UpdateResponse remove(String id) {
  UpdateResponse response = null;
  try {
	response = solr.deleteById(id);
	solr.commit();
	return response;
  } catch (SolrServerException | IOException e) {
	log.error("Error removing document", e);
  }
  return response;

}

public UpdateResponse remove(String id, String username, String password) {

  UpdateResponse response = null;
  try {
	UpdateRequest up = new UpdateRequest();
	up.setBasicAuthCredentials(username, password);
	up.deleteById(id);
	response = up.process(solr);

	up.commit(solr, null);
	return response;
  } catch (SolrServerException | IOException e) {
	log.error("Error removing document", e);
  }
  return response;

}

public QueryResponse query(SolrQuery query) {
  QueryResponse response = null;
  try {
	response = solr.query(query);
  } catch (SolrServerException | IOException e) {
	log.error("Error in query", e);
  }
  return response;
}

public QueryResponse query(SolrQuery query, String username, String password) {
  QueryResponse response = null;
  try {
	SolrRequest<QueryResponse> req = new QueryRequest(query);
	req.setBasicAuthCredentials(username, password);
	response = req.process(solr);

  } catch (SolrServerException | IOException e) {
	log.error("Error in query", e);
  }
  return response;
}

public UpdateResponse add(JSONObject jsonObject, String datasetName) {
  UpdateResponse updateResponse = null;

  if (jsonObject != null) {
	Collection<SolrInputDocument> batch = new ArrayList<SolrInputDocument>();
	SolrInputDocument inputDocument = new SolrInputDocument();
	Iterator<String> keys = jsonObject.keySet().iterator();

	while (keys.hasNext()) {
	  String key = keys.next();
	  inputDocument.addField(key, jsonObject.get(key));
	}

	addCustomFields(inputDocument, datasetName);

	batch.add(inputDocument);

	try {
	  updateResponse = solr.add(batch);
	  solr.commit();
	} catch (SolrServerException | IOException e) {
	  log.error("Error adding simple document", e);
	  e.printStackTrace();
	}

  }

  return updateResponse;

  /*
   * // Docs to submit Collection<SolrInputDocument> batch = new
   * ArrayList<SolrInputDocument>();
   * 
   * // Parent Doc 1, a person mamed John Jones SolrInputDocument person1 = new
   * SolrInputDocument(); person1.addField( "id", "john_jones" );
   * person1.addField( "content_type", "person" ); // "_t" suffix tells Solr that
   * it's text person1.addField( "first_name_t", "John" ); person1.addField(
   * "last_name_t", "Jones" ); // states and history used in edismax examples
   * person1.addField( "states_t", "California Nevada Idaho Maine" );
   * person1.addField( "history_t",
   * "safe accident accident accident accident accident" );
   * 
   * // child docs, the vehicles he owns SolrInputDocument p1_car1 = new
   * SolrInputDocument(); p1_car1.addField( "id", "jj_car1" ); p1_car1.addField(
   * "content_type", "car" ); // For cars "make" is an alias for "manufacturer"
   * p1_car1.addField( "make_t", "Honda" ); p1_car1.addField( "model_t", "Accord"
   * );
   * 
   * SolrInputDocument p1_car2 = new SolrInputDocument(); p1_car2.addField( "id",
   * "jj_car2" ); p1_car2.addField( "content_type", "car" ); p1_car2.addField(
   * "make_t", "Nissan" ); p1_car2.addField( "model_t", "Maxima" );
   * 
   * SolrInputDocument p1_bike1 = new SolrInputDocument(); p1_bike1.addField(
   * "id", "jj_bike1" ); p1_bike1.addField( "content_type", "bike" );
   * p1_bike1.addField( "make_t", "Yamaha" ); p1_bike1.addField( "model_t",
   * "Passion" );
   * 
   * SolrInputDocument p1_bike2 = new SolrInputDocument(); p1_bike2.addField(
   * "id", "jj_bike2" ); p1_bike2.addField( "content_type", "bike" );
   * p1_bike2.addField( "make_t", "Peugeot" ); p1_bike2.addField( "model_t",
   * "Vivacity" );
   * 
   * // Add children to parent person1.addChildDocument( p1_car1 );
   * person1.addChildDocument( p1_car2 ); person1.addChildDocument( p1_bike1 );
   * person1.addChildDocument( p1_bike2 );
   * 
   * // Add parent to batch batch.add( person1 );
   * 
   * 
   * // Parent Doc 2, person mamed Satish Smith SolrInputDocument person2 = new
   * SolrInputDocument(); person2.addField( "id", "satish_smith" );
   * person2.addField( "content_type", "person" ); person2.addField(
   * "first_name_t", "Satish" ); person2.addField( "last_name_t", "Smith" );
   * person2.addField( "states_t",
   * "California Texas California Maine Vermont Connecticut" ); person2.addField(
   * "history_t", "safe safe safe safe safe safe safe safe accident" );
   * 
   * // Vehicles (child docs) SolrInputDocument p2_car1 = new SolrInputDocument();
   * p2_car1.addField( "id", "ss_car1" ); p2_car1.addField( "content_type", "car"
   * ); p2_car1.addField( "make_t", "Peugeot" ); p2_car1.addField( "model_t",
   * "iOn" ); SolrInputDocument p2_bike1 = new SolrInputDocument();
   * p2_bike1.addField( "id", "ss_bike1" ); p2_bike1.addField( "content_type",
   * "bike" ); p2_bike1.addField( "make_t", "Honda" ); p2_bike1.addField(
   * "model_t", "Spree" ); // link objects and add to batch
   * person2.addChildDocument( p2_car1 ); person2.addChildDocument( p2_bike1 );
   * batch.add( person2 );
   * 
   * System.out.println( "Adding batch of " + batch.size() + " parent docs" );
   * 
   * // Submit as a group UpdateResponse updateResponse = solr.add( batch );
   * solr.commit();
   * 
   * 
   * 
   * return updateResponse;
   */

}

public UpdateResponse add(JSONObject jsonObject, String datasetName, String username, String password) {
  UpdateResponse updateResponse = null;

  if (jsonObject != null) {
	Collection<SolrInputDocument> batch = new ArrayList<SolrInputDocument>();
	SolrInputDocument inputDocument = new SolrInputDocument();
	Iterator<String> keys = jsonObject.keySet().iterator();

	while (keys.hasNext()) {
	  String key = keys.next();
	  inputDocument.addField(key, jsonObject.get(key));
	}

	addCustomFields(inputDocument, datasetName);

	batch.add(inputDocument);

	try {

	  UpdateRequest up = new UpdateRequest();
	  up.setBasicAuthCredentials(username, password);
	  up.add(batch);
	  updateResponse = up.process(solr);

	  up.commit(solr, null);
	} catch (SolrServerException | IOException e) {
	  log.error("Error adding simple document", e);
	}

  }
  return updateResponse;
}

public UpdateResponse deleteByQuery(String query, String username, String password) {
  UpdateResponse response = null;
  try {
	/*
	UpdateRequest up = new UpdateRequest();
	  up.setBasicAuthCredentials(username, password);
	  up.add(batch);
	  updateResponse = up.process(solr);
	*/
	
	UpdateRequest up = new UpdateRequest();
	  up.setBasicAuthCredentials(username, password);
	  up.deleteByQuery(query);
	  response = up.process(solr);
	  up.commit(solr, null);
  } catch (Exception e) {
	log.error("Error deleting query", e);
  }
  return response;
}

public UpdateResponse deleteByQuery(String query) {
  UpdateResponse response = null;
  try {
	response = solr.deleteByQuery(query);
	solr.commit();
  } catch (Exception e) {
	log.error("Error deleting query", e);
  }
  return response;
}

public UpdateResponse add(JSONArray jsonArray, String datasetName) {
  UpdateResponse updateResponse = null;

  if (jsonArray != null) {
	Collection<SolrInputDocument> batch = new ArrayList<SolrInputDocument>();

	for (int i = 0; i < jsonArray.size(); i++) {
	  JSONObject jsonObject = (JSONObject) jsonArray.get(i);

	  SolrInputDocument inputDocument = new SolrInputDocument();
	  Iterator<String> keys = jsonObject.keySet().iterator();

	  while (keys.hasNext()) {
		String key = keys.next();
		inputDocument.addField(key, jsonObject.get(key));
	  }

	  addCustomFields(inputDocument, datasetName);

	  batch.add(inputDocument);
	}

	try {
	  log.info("cleaning old data in " + datasetName);
	  deleteByQuery("datasetName:" + datasetName);
	  log.info("sending " + batch.size() + " to solr");
	  updateResponse = solr.add(batch);
	  solr.commit();
	} catch (SolrServerException | IOException e) {
	  log.error("Error adding array of documents", e);
	  e.printStackTrace();
	}

  }

  return updateResponse;

  /*
   * // Docs to submit Collection<SolrInputDocument> batch = new
   * ArrayList<SolrInputDocument>();
   * 
   * // Parent Doc 1, a person mamed John Jones SolrInputDocument person1 = new
   * SolrInputDocument(); person1.addField( "id", "john_jones" );
   * person1.addField( "content_type", "person" ); // "_t" suffix tells Solr that
   * it's text person1.addField( "first_name_t", "John" ); person1.addField(
   * "last_name_t", "Jones" ); // states and history used in edismax examples
   * person1.addField( "states_t", "California Nevada Idaho Maine" );
   * person1.addField( "history_t",
   * "safe accident accident accident accident accident" );
   * 
   * // child docs, the vehicles he owns SolrInputDocument p1_car1 = new
   * SolrInputDocument(); p1_car1.addField( "id", "jj_car1" ); p1_car1.addField(
   * "content_type", "car" ); // For cars "make" is an alias for "manufacturer"
   * p1_car1.addField( "make_t", "Honda" ); p1_car1.addField( "model_t", "Accord"
   * );
   * 
   * SolrInputDocument p1_car2 = new SolrInputDocument(); p1_car2.addField( "id",
   * "jj_car2" ); p1_car2.addField( "content_type", "car" ); p1_car2.addField(
   * "make_t", "Nissan" ); p1_car2.addField( "model_t", "Maxima" );
   * 
   * SolrInputDocument p1_bike1 = new SolrInputDocument(); p1_bike1.addField(
   * "id", "jj_bike1" ); p1_bike1.addField( "content_type", "bike" );
   * p1_bike1.addField( "make_t", "Yamaha" ); p1_bike1.addField( "model_t",
   * "Passion" );
   * 
   * SolrInputDocument p1_bike2 = new SolrInputDocument(); p1_bike2.addField(
   * "id", "jj_bike2" ); p1_bike2.addField( "content_type", "bike" );
   * p1_bike2.addField( "make_t", "Peugeot" ); p1_bike2.addField( "model_t",
   * "Vivacity" );
   * 
   * // Add children to parent person1.addChildDocument( p1_car1 );
   * person1.addChildDocument( p1_car2 ); person1.addChildDocument( p1_bike1 );
   * person1.addChildDocument( p1_bike2 );
   * 
   * // Add parent to batch batch.add( person1 );
   * 
   * 
   * // Parent Doc 2, person mamed Satish Smith SolrInputDocument person2 = new
   * SolrInputDocument(); person2.addField( "id", "satish_smith" );
   * person2.addField( "content_type", "person" ); person2.addField(
   * "first_name_t", "Satish" ); person2.addField( "last_name_t", "Smith" );
   * person2.addField( "states_t",
   * "California Texas California Maine Vermont Connecticut" ); person2.addField(
   * "history_t", "safe safe safe safe safe safe safe safe accident" );
   * 
   * // Vehicles (child docs) SolrInputDocument p2_car1 = new SolrInputDocument();
   * p2_car1.addField( "id", "ss_car1" ); p2_car1.addField( "content_type", "car"
   * ); p2_car1.addField( "make_t", "Peugeot" ); p2_car1.addField( "model_t",
   * "iOn" ); SolrInputDocument p2_bike1 = new SolrInputDocument();
   * p2_bike1.addField( "id", "ss_bike1" ); p2_bike1.addField( "content_type",
   * "bike" ); p2_bike1.addField( "make_t", "Honda" ); p2_bike1.addField(
   * "model_t", "Spree" ); // link objects and add to batch
   * person2.addChildDocument( p2_car1 ); person2.addChildDocument( p2_bike1 );
   * batch.add( person2 );
   * 
   * System.out.println( "Adding batch of " + batch.size() + " parent docs" );
   * 
   * // Submit as a group UpdateResponse updateResponse = solr.add( batch );
   * solr.commit();
   * 
   * 
   * 
   * return updateResponse;
   */

}

public UpdateResponse add(JSONArray jsonArray, String datasetName, String username, String password) {
  UpdateResponse updateResponse = null;

  if (jsonArray != null) {
	Collection<SolrInputDocument> batch = new ArrayList<SolrInputDocument>();

	for (int i = 0; i < jsonArray.size(); i++) {
	  JSONObject jsonObject = (JSONObject) jsonArray.get(i);

	  SolrInputDocument inputDocument = new SolrInputDocument();
	  Iterator<String> keys = jsonObject.keySet().iterator();

	  while (keys.hasNext()) {
		String key = keys.next();
		inputDocument.addField(key, jsonObject.get(key));
	  }

	  addCustomFields(inputDocument, datasetName);

	  batch.add(inputDocument);
	}

	try {
	  
	  log.info("cleaning old data in " + datasetName);
	  deleteByQuery("datasetName:" + datasetName, username, password );
	  	  
	  log.info("sending " + batch.size() + " to solr");
	  UpdateRequest up = new UpdateRequest();
	  up.setBasicAuthCredentials(username, password);
	  up.add(batch);
	  updateResponse = up.process(solr);
	  up.commit(solr, null);	  

	} catch (SolrServerException | IOException e) {
	  log.error("Error adding array of documents", e);
	  e.printStackTrace();
	}
	

  }

  return updateResponse;

}

private void addCustomFields(SolrInputDocument inputDocument, String datasetName) {

  String finalURL="";
  if (Util.validValue(datasetName)) {
	inputDocument.addField(DATASET_NAME, datasetName);
  }
  if (Util.validValue(prefixURI)) {
	if (datasetName.equals(Constants.CALIDAD_AIRE_SENSOR)) {
	  String estacion = inputDocument.get("isHostedBy").getValue().toString();
	  String calidadAirePrefixURI = prefixURI.replace("{isHostedBy}", estacion);
	  String id = inputDocument.get("observesId").getValue().toString();
	  finalURL=calidadAirePrefixURI + "/" + id;
	} else if (datasetName.equals(Constants.DSD_DIMENSION_VALUE)) {
	  String dimensionId = inputDocument.get("topConceptOf").getValue().toString();
	  String newDimensionId = "";
	  dimensionId = dimensionId.substring(dimensionId.lastIndexOf("/") + 1);

	  if (dimensionId.contains("#")) {
		dimensionId = dimensionId.substring(dimensionId.lastIndexOf("#") + 1);
	  }

	  dimensionId = dimensionId.replace("-", " ");
	  String[] split = dimensionId.split(" ");
	  for (int i = 0; i < split.length; i++) {
		String w = split[i];
		if (i == 0) {
		  newDimensionId += w;
		} else {
		  String first = w.substring(0, 1);
		  String afterfirst = w.substring(1);
		  newDimensionId += first.toUpperCase() + afterfirst;
		}
	  }

	  log.info(newDimensionId);

	  String dimensionValueURI = prefixURI.replace("{dimensionId}", newDimensionId);
	  finalURL=dimensionValueURI;
	} else if (datasetName.equals(Constants.TERRITORIO_PAIS) || datasetName.equals(Constants.TERRITORIO_AUTONOMIA) || datasetName.equals(Constants.TERRITORIO_PROVINCIA)) {
	  String id = inputDocument.get("identifier").getValue().toString();
	  finalURL=prefixURI + "/" + id;
	} else {
	  String id = inputDocument.get("id").getValue().toString();	  
	  finalURL=prefixURI + "/" + id;
	}
	inputDocument.addField(URI, finalURL);
	
	checkURIsMap.put(datasetName, finalURL);
  }
}

public static void main(String[] args) {
  BasicConfigurator.configure();
  org.apache.log4j.Logger.getRootLogger().setLevel(org.apache.log4j.Level.INFO);

  String urlString = "http://localhost:8983/solr/ciudadesAbiertas";
  SolrClient client = new SolrClient(urlString);
  UpdateResponse response = null;

  String username = "solr";
  String password = "Localidat@39";

  String id = "552199";

  JSONObject json = new JSONObject();
  json.put("id", id);
  json.put("name", "Gouda cheese wheel");
  json.put("price", "49.99");

  response = client.add(json, "test", username, password);
  log.info(response.toString());

  SolrQuery query = new SolrQuery();
  query.set("q", "id:" + id);

  QueryResponse queryResponse = client.query(query, username, password);
  log.info(queryResponse.toString());

  response = client.remove(id, username, password);
  log.info(response.toString());

  JSONObject obj = new JSONObject();
  obj.put("id", "Sujeto23");
  obj.put("a", "b");
  obj.put("b", 23);
  obj.put("c", new Date());

  client.add(obj, "test", username, password);

  JSONObject obj2 = new JSONObject();
  obj2.put("id", "Sujeto24");
  obj2.put("d", "g");
  obj2.put("e", 24);
  obj2.put("f", new Date());

  JSONArray array = new JSONArray();
  array.add(obj);
  array.add(obj2);

  client.add(array, "test", username, password);

  log.info("end");
}
}
