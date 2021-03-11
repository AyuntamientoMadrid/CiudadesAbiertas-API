package org.ciudadesAbiertas.madrid.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.ciudadesAbiertas.madrid.model.dynamic.SemanticPrefix;
import org.ciudadesAbiertas.madrid.utils.constants.Constants;
import org.eclipse.rdf4j.model.Namespace;
import org.eclipse.rdf4j.model.impl.SimpleNamespace;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;

import be.ugent.rml.Executor;
import be.ugent.rml.Utils;
import be.ugent.rml.functions.FunctionLoader;
import be.ugent.rml.functions.lib.IDLabFunctions;
import be.ugent.rml.records.RecordsFactory;
import be.ugent.rml.store.QuadStore;
import be.ugent.rml.store.QuadStoreFactory;
import be.ugent.rml.store.RDF4JStore;


public class RMLMapper {

private static final Logger log = LoggerFactory.getLogger(RMLMapper.class);

public static void generateRDF(String ttlFile, List<SemanticPrefix> prefixes, String format, OutputStreamWriter outStream) {

  if (outStream == null) {
	outStream = new OutputStreamWriter(System.out);
  }

  if ((prefixes == null) || (prefixes.size() == 0)) {
	prefixes = new ArrayList<SemanticPrefix>();
  }

  File mappingFile = null;

  try {

	mappingFile = new File(ttlFile);

	// Get the mapping string stream
	InputStream mappingStream = new FileInputStream(mappingFile);

	// Load the mapping in a QuadStore
	QuadStore rmlStore = QuadStoreFactory.read(mappingStream);

	// Set up the basepath for the records factory, i.e., the basepath for the
	// (local file) data sources
	RecordsFactory factory = new RecordsFactory(mappingFile.getParent());

	List<Namespace> internalPrefixes = new ArrayList<Namespace>();
	for (SemanticPrefix semanticPrefix : prefixes) {
	  internalPrefixes.add(new SimpleNamespace(semanticPrefix.getCode(), semanticPrefix.getUrl()));
	}

	// Set up the outputstore (needed when you want to output something else than
	// nquads
	QuadStore outputStore = new RDF4JStore(internalPrefixes);

	//Inicio codigo fichero funciones
	// Si no utilizamos un fichero propio de funciones, se generan archivos temporales
	String functionPath = "./src/main/resources/rmlmapper/functions.ttl";

	ClassPathResource classPathResource = new ClassPathResource("rmlmapper/functions.ttl");
	File functionFile = classPathResource.getFile();
	log.debug("function file found: "+functionFile.exists() + "");
	if (functionFile.exists()) {
	  functionPath = functionFile.getAbsolutePath();
	}

	// Set up the functions used during the mapping
	Map<String, Class> libraryMap = new HashMap<>();
	libraryMap.put("IDLabFunctions", IDLabFunctions.class);

	
	File functionsFile = Utils.getFile(functionPath);
	FunctionLoader functionLoader = new FunctionLoader(QuadStoreFactory.read(functionsFile), libraryMap);
	//Fin codigo fichero funciones

	// Create the Executor
	//Executor executor = new Executor(rmlStore, factory, null, outputStore, Utils.getBaseDirectiveTurtle(mappingStream));
	Executor executor = new Executor(rmlStore, factory, functionLoader, outputStore, Utils.getBaseDirectiveTurtle(mappingStream));
		
	// Execute the mapping
	QuadStore result = executor.execute(null);

	// Output the result
	BufferedWriter bufferOut = new BufferedWriter(outStream);
	result.write(bufferOut, format);

	bufferOut.close();
  } catch (Exception e) {
	log.error("Error in RMLMapper", e);
  }
}

public static void generateRDF(String ttlFile, OutputStreamWriter outputStream) {
  generateRDF(ttlFile, null, "turtle", outputStream);

}

public static void generateRDF(String ttlFile, List<SemanticPrefix> prefixes) {
  generateRDF(ttlFile, prefixes, "turtle", null);
}

public static void generateRDF(String ttlFile) {
  generateRDF(ttlFile, null, "turtle", null);
}

public static void main(String[] args) {

  // generateRDF("./src/main/resources/rmlmapper/mapping.ttl");

  generateRDF("./src/main/resources/rmlmapper/subvencion.ttl");

}

}
