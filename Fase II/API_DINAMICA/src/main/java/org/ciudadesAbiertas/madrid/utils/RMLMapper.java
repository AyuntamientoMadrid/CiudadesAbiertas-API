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

import org.ciudadesAbiertas.madrid.model.dynamic.SemanticPrefix;
import org.eclipse.rdf4j.model.Namespace;
import org.eclipse.rdf4j.model.impl.SimpleNamespace;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import be.ugent.rml.Executor;
import be.ugent.rml.Utils;
import be.ugent.rml.functions.FunctionLoader;
import be.ugent.rml.functions.lib.IDLabFunctions;
import be.ugent.rml.records.RecordsFactory;
import be.ugent.rml.store.Quad;
import be.ugent.rml.store.QuadStore;
import be.ugent.rml.store.QuadStoreFactory;
import be.ugent.rml.store.RDF4JStore;

public class RMLMapper {
    
    private static final Logger log = LoggerFactory.getLogger(RMLMapper.class);
    
    public static void generateRDF(String ttlFile, List<SemanticPrefix> prefixes, OutputStreamWriter outStream) {
	
	if (outStream==null)
	{
	    outStream=new OutputStreamWriter(System.out);
	}
	
	
	try {

	    File mappingFile = new File(ttlFile);


	    // Get the mapping string stream
	    InputStream mappingStream = new FileInputStream(mappingFile);

	    // Load the mapping in a QuadStore
	    QuadStore rmlStore = QuadStoreFactory.read(mappingStream);
	   	    
	    // Set up the basepath for the records factory, i.e., the basepath for the
	    // (local file) data sources
	    RecordsFactory factory = new RecordsFactory(mappingFile.getParent());
      	    
	    List<Namespace> internalPrefixes=new ArrayList<Namespace>();
	    for (SemanticPrefix semanticPrefix : prefixes) {
		internalPrefixes.add(new SimpleNamespace(semanticPrefix.getId(), semanticPrefix.getUrl()));
	    }
	   	   
	    // Set up the outputstore (needed when you want to output something else than
	    // nquads
	    QuadStore outputStore = new RDF4JStore(internalPrefixes);
	        	    
	    
	    // Create the Executor
	    Executor executor = new Executor(rmlStore, factory, null, outputStore, Utils.getBaseDirectiveTurtle(mappingStream));
	    
	    // Execute the mapping
	    QuadStore result = executor.execute(null);

	    // Output the result
	    BufferedWriter bufferOut = new BufferedWriter(outStream);
	    result.write(bufferOut, "turtle");
	    //result.write(bufferOut, "rdf");
	    
	    bufferOut.close();
	} catch (Exception e) {
	    log.error("Error in RMLMapper",e);
	}
    }

    public static void generateRDF(String ttlFile, OutputStreamWriter outputStream) {
	generateRDF(ttlFile, null, outputStream);
	
    }
    
    public static void generateRDF(String ttlFile, List<SemanticPrefix> prefixes) {
	generateRDF(ttlFile,prefixes,null);
    }
    
    public static void generateRDF(String ttlFile) {
	generateRDF(ttlFile, null, null);
    }

    public static void main(String[] args) {
	generateRDF("./src/main/resources/rmlmapper/subvencion.ttl");
    }

    
}
