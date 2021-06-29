package com.localidata.extractorContratos;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.json.simple.JSONArray;
import org.json.simple.JSONAware;
import org.json.simple.JSONObject;

import com.localidata.extractorContratos.model.Award;
import com.localidata.extractorContratos.model.ContractingProcess;
import com.localidata.extractorContratos.model.Item;
import com.localidata.extractorContratos.model.Lot;
import com.localidata.extractorContratos.model.LotRelItem;
import com.localidata.extractorContratos.model.Organization;
import com.localidata.extractorContratos.model.Tender;
import com.localidata.extractorContratos.model.TenderRelItem;
import com.localidata.extractorContratos.service.MailService;
import com.localidata.extractorContratos.util.ConfigExtractor;
import com.localidata.extractorContratos.util.Constants;
import com.localidata.extractorContratos.util.DataBaseSQLiteUtil;
import com.localidata.extractorContratos.util.ISqlGeneric;
import com.localidata.extractorContratos.util.Util;


public class ExtractorContratos {
	
	private static final String _BETA = "_BETA";

	private static final String SIN_NOMBRE = "SIN_NOMBRE";

	private static Logger log = Logger.getLogger(ExtractorContratos.class);
		
	
	/* Los códigos CPV están sin mapear debido a su número tan grande */
	/*http://contrataciondelestado.es/codice/cl/1.04/CPV2007-1.04.gc*/
	
//	private String prefifoIdContractingProcess = "CP";
	private Integer numeroIdContractingProcess = Integer.valueOf(0);
	
//	private String prefifoIdOrganization = "OR";
	private Integer numeroIdOrganization = Integer.valueOf(0);
	
	private String prefifoIdTender = "TN";
	private Integer numeroIdTender = Integer.valueOf(0);
	
	private String prefifoIdAward = "AW";
	private Integer numeroIdAward = Integer.valueOf(0);
	
	private String prefifoIdLot = "LT";
	private Integer numeroIdLot = Integer.valueOf(0);
	
//	private String prefifoIdItem = "IT";
//	private Integer numeroIdItem = Integer.valueOf(0);
	
	private String prefifoIdTenderRelItem = "TRI";
	private Integer numeroIdTenderRelItem = Integer.valueOf(0);
	
	private String prefifoIdLotRelItem = "LRI";
	private Integer numeroIdLotRelItem = Integer.valueOf(0);

	
	List<Organization> organizationList = new ArrayList<Organization>();
	List<Tender> tenderList = new ArrayList<Tender>();

	List<Lot> lotList = new ArrayList<Lot>();
	List<Item> itemList = new ArrayList<Item>();
	List<TenderRelItem> tenderRelItemList = new ArrayList<TenderRelItem>();
	List<LotRelItem> lotRelItemList = new ArrayList<LotRelItem>();
	
	
	Set<String> idsOrganization = new HashSet<String>();
	Set<String> classificationItem = new HashSet<String>();
	Map<String,Item> idsItem = new HashMap<String,Item>();
	Map<String,ContractingProcess> processMapList = new HashMap<String,ContractingProcess>();
	Map<String,Award> awardMapList = new LinkedHashMap<String,Award>();
	 
	Map<String,List<String>> queriesGlobal = new LinkedHashMap<String,List<String>>();
	
	DataBaseSQLiteUtil dbUtil = new DataBaseSQLiteUtil();	
	
	
	public static final String CSVSeparator = ";";
	public static final String SQLSeparator = ",";
	public static final String CSVTextSeparator = "\"";
	public static final String SQLTextSeparator = "'";	
	
	private ConfigExtractor configuration= null;
	
	//Tratamiento de errores
	private static boolean processError=false;
	List<String> errors = new ArrayList<String>();
	
	//Tratamiento de los identificadores de BBDD para el control de insert o update
	List<String> identifiersFromOrganization = new ArrayList<String>();
	List<String> identifiersFromAward = new ArrayList<String>();
	List<String> identifiersFromTender = new ArrayList<String>();
	List<String> identifiersFromTenderRelItem = new ArrayList<String>();
	List<String> identifiersFromProcess = new ArrayList<String>();
	List<String> identifiersFromLot = new ArrayList<String>();
	List<String> identifiersFromLotRetItem = new ArrayList<String>();
	
	private static MailService serviceMail= new MailService();
	
	
	public ExtractorContratos() {

		initialization();
		
	}
	
	
	/**
	 * Main para la ejecución
	 * @param args
	 * @return 
	 */
	public static List<String> main(String[] args) 
	{
		PropertyConfigurator.configure("log4j.properties");
			
		ExtractorContratos extractor=new ExtractorContratos();
		List<String> filesGenerated = extractor.run();		
		
		return filesGenerated;
	}
	
	
	/* METODOS PRIVADOS 	 */
	
	private void initialization() {
		log.info("[initialization] [init]");
		// https://contrataciondelestado.es/codice/cl/2.02/ContractCode-2.02.gc

		// https://contrataciondelestado.es/codice/cl/2.07/SyndicationTenderingProcessCode-2.07.gc*/

		// http://contrataciondelestado.es/codice/cl/1.04/DiligenceTypeCode-1.04.gc
		
		configuration = new ConfigExtractor();
		
		//Inicializamos el listado de queriesGlobal
		if (ConfigExtractor.LIST_BBDD!=null) {
			for (String bd:ConfigExtractor.LIST_BBDD) {
				List<String> queryBdd=new ArrayList<String>();
				queriesGlobal.put(bd, queryBdd);
			}
		}	
		log.info("[initialization] [end]");
	}

	private String generateOrganizationId(String processId, String partyId, String name, boolean isContratante)
	{
		//Para evitar repetidos vamos a guardar solo su partyId
		//String orgId=processId+"-"+String.valueOf(partyId).toUpperCase();
		String orgId=null;
		if (!isContratante) {
			
			String nameCleaned=name;
			if (Util.validValue(partyId))
			{
				partyId=partyId.replace("-", "");
				orgId=String.valueOf(partyId).toUpperCase();
			}
			if (Util.validValue(nameCleaned)) {
				nameCleaned=Util.cleanString(nameCleaned);
			}else {
				nameCleaned=Util.cleanString(SIN_NOMBRE);
			}
			
			orgId+=Util.md5Hex(nameCleaned);
		}else {
			//Si es contratante se guarda el NIF / CIF 
			if (Util.validValue(partyId))
			{
				partyId=partyId.replace("-", "");
				orgId=String.valueOf(partyId).toUpperCase();
			}
		}
		
		return orgId;
	}
	
	private void addOrganization(Organization org)
	{	
		//Se añaden todas porque el control ahora se hace con la BBDD
		log.debug("Organization added");
		organizationList.add(org);	
	}

	
	private void addAward(Award award) throws Exception
	{	
		//Se añaden todas porque el control ahora se hace con la BBDD
		awardMapList.put(award.getId(),award);
		
	}
	
	public int executeLines(List<String> lines)
	{	
		log.info("[executeLines]  [lines:" + lines.size()+ "] process...");
		try {
			dbUtil.executeList(lines);
		} catch (SQLException e) {
			log.error("[executeLines] Error executing lines",e);
			errors.add("[executeLines] Error executing lines:"+e.getMessage());
			processError=true;
			return -1;
		}
		log.info("[executeLines]  [lines:" + lines.size()+ "] process OK ");
		return 0;
	}
	
	public List<String> run() {

		List<String> cleanClines = new ArrayList<String>();
		List<String> itemsLines = new ArrayList<String>();
		List<String> filesGenerated = new ArrayList<String>();
		
		log.info("[run] init");
		//TODO esto hay que borrarlo en cuanto se pueda
		List<String> queriesExecutedSQLServer = new ArrayList<String>();
		
		List<File> fileList = null;
		
		if (configuration.isLoadInitial()) {
			log.info("[run] Carga Inicial ...");
			try {
				cleanClines = FileUtils.readLines(new File("clean.sql"),"utf-8");
			} catch (IOException e1) {
				log.error("[run] Error reading clean.sql file",e1);
				errors.add("[run] Error reading clean.sql file:"+e1.getMessage());
				processError=true;
				return filesGenerated;
			}
			try {
				itemsLines = FileUtils.readLines(new File("item.sql"),"utf-8");
			} catch (IOException e1) {
				log.error("[run] Error reading item.sql file",e1);
				errors.add("[run] Error reading item.sql file:"+e1.getMessage());
				processError=true;
				return filesGenerated;
			}
			
			int ok=executeLines(cleanClines);
			if (ok<0)
			{
				return filesGenerated;
			}
			ok=executeLines(itemsLines);
			if (ok<0)
			{
				return filesGenerated;
			}
		}else {
			log.info("[run] Carga Periodica...");			
			//Generamos una copia de la bbdd SQL LITE (BETA)			
			try {
				FileUtils.copyFile(dbUtil.getFileDB(),new File(dbUtil.getFileDB().getPath()+_BETA));
			} catch (IOException e) {
				log.error("Error generating backup file",e);
				String errorMsg="Error generando el fichero de backup de la base de datos interna";
				serviceMail.enviar(Constants.MAIL_ASUNTO_PROCESO_ERROR, errorMsg);
				return filesGenerated;
			}
		}			
		
		/*
		 * Extrar los zip de esta URL en un directorio
		 * https://www.hacienda.gob.es/es-ES/GobiernoAbierto/Datos%20Abiertos/Paginas/licitaciones_plataforma_contratacion.aspx
		 */
		try {
			File directorio = FileUtils.getFile(configuration.getPATH_FILE_EXTRACTOR());
			String[] extensions = { "atom" };
			fileList = (List<File>) FileUtils.listFiles(directorio, extensions, true);
		}catch (Exception e) {
			log.error("[run] fileList not loading error: "+e.getMessage() );
			errors.add("[run] fileList not loading error:"+e.getMessage());
			processError=true;
		}
		int counterFile = 1;
		int internalError=0;
		if (fileList!=null) {
			log.info("[run] process...");
			
			loadIndentifierBD();
			
			for (File file : fileList) {
				String fileName = file.getName();
				log.info("[run] File " + (counterFile++) + " of " + fileList.size() + ": " + fileName );
				String jsonPrettyPrintString = Util.xmlFileToJSON(file);
				JSONArray entries = Util.getEntriesFromJSON(jsonPrettyPrintString);
				//CMG Se debe generar una nueva carga de queriesExecuted por cada proceso
				List<String> queriesExecuted = new ArrayList<String>();
				//Se Recargan los identificadores de BBDD
				
				if ((entries.size() > 0)&& (internalError==0)) {
					//log.info(entries.size()+" entries");
					for (int h = 0; h < entries.size(); h++) {
						String processId="";
						try {
							processId=processEntry((JSONObject) entries.get(h));
						} catch (Exception e) {
							log.error("[run] [File:"+fileName+"] Error proccesing entry("+h+")", e);
							processError=true;
							errors.add("[run] [File:"+fileName+"] Error proccesing entry("+h+")"+e.getMessage() );
							break;
						}
						
						log.debug("[run] [filename:"+fileName+"] generator SQL process...");						
						
						int status=processSQL(organizationList, processId, "contratos_organization", queriesExecuted, queriesExecutedSQLServer);
	
						status+=processSQL(awardMapList, processId, "contratos_award", queriesExecuted, queriesExecutedSQLServer);

						//Tender
						status+=processSQL(tenderList, processId, "contratos_tender", queriesExecuted, queriesExecutedSQLServer);
						
						//LOT
						status+=processSQL(lotList, processId, "contratos_lot", queriesExecuted, queriesExecutedSQLServer);
						
						//LOTRelItem
						status+=processSQL(lotRelItemList, processId, "contratos_lot_rel_item", queriesExecuted, queriesExecutedSQLServer);
						
						//contratos_process
						status+=processSQL(processMapList, processId, "contratos_process", queriesExecuted, queriesExecutedSQLServer);
						
						//tenderRelItem
						status+=processSQL(tenderRelItemList, processId, "contratos_tender_rel_item", queriesExecuted, queriesExecutedSQLServer);
						
						if (status<0)
						{
							String errorMsg="Error aplicando cambios en la base de datos interna";
							serviceMail.enviar(Constants.MAIL_ASUNTO_PROCESO_ERROR, errorMsg);
							
							internalError=1;
							
							try {
								FileUtils.copyFile(new File(dbUtil.getFileDB().getPath()+_BETA), dbUtil.getFileDB());
							} catch (IOException e) {
								log.error("Error generating backup file",e);
								errorMsg="Error restaurando el fichero de backup de la base de datos interna. Por favor realice esta tarea de manera manual.";
								serviceMail.enviar(Constants.MAIL_ASUNTO_PROCESO_ERROR, errorMsg);
							}
							
							
							break;
														
							//TODO Si no se puede restaurar el fichero, se debería dejar un fichero de aviso
							
							
						}
						
						log.debug("[run] [filename:"+fileName+"] generator SQL end.");
					}	
				}//Fin tratamiento de los atos
				
				if (internalError==1)
				{
					log.error("[run] [File:" + fileName + "]  [error in database internal] ERROR executeLines");
					break;
				}
				
				log.debug("[run] [filename:"+fileName+"] [processError:"+processError+"]");
				if (!processError) {
					// Se realiza la insercion de los mismos
					log.debug("[run] [filename:" + fileName + "] execute SQL process...");
					int resultado = executeLines(queriesExecuted);
					if (resultado == 0) {
						log.info("[run] [filename:" + fileName + "] execute SQL OK");
					} else {
						log.error("[run] [File:" + fileName + "]  [queriesExecuted] ERROR executeLines");
						errors.add("[run] [File:" + fileName + "] [queriesExecuted] ERROR executeLines");
					}
				} else {
					break;
				}
				
			}
			log.debug("[run] process finally [processError:"+processError+"] ");
			if (!processError) {
				filesGenerated = writeQueriesAll(cleanClines, itemsLines,configuration.isLoadInitial());
			}else {
				if(errors!=null && !errors.isEmpty()) {
					log.info("[run] Report of errors detected...");
					String errorMsg="Report of errors detected:";
					for (String error:errors) {
						log.error(error);
						errorMsg+="\n"+error;
					}
					serviceMail.enviar(Constants.MAIL_ASUNTO_PROCESO_ERROR, errorMsg);
				}
			}
		}
		
		log.info("[run] deleting files in "+DescargaPeriodica.FOLDER_LICITACIONES_PERIODICAS);
		try {
			File tempFolder=new File(DescargaPeriodica.FOLDER_LICITACIONES_PERIODICAS);
			if (tempFolder.exists())
			{
				FileUtils.cleanDirectory(tempFolder);
			}
		
		} catch (IOException e) {
			log.error("Error deleting files", e);
			log.error("Please remove the files manualy");
			String errorMail = "Error deleting files :" + e.getMessage();
			errorMail += "\n" + "Please remove the files manualy";
			serviceMail.enviar(Constants.MAIL_ASUNTO_PROCESO_ERROR, errorMail);
		}
		
		log.info("[run] end");
		return filesGenerated;
		
	}


	private void writeQueriesToFile(List<String> queriesToUpdate, String fileName) {
		StringBuffer queries=new StringBuffer();
		for (String line:queriesToUpdate)
		{
			if (line.endsWith(";")) {
				queries.append (line+System.getProperty("line.separator"));
			}else {
				queries.append (line+";"+System.getProperty("line.separator"));
			}
		}
		Util.writeFile(queries, fileName);
	}

	
	private String processEntry(JSONObject entry) throws Exception {
		
		ContractingProcess contractingProcess = new ContractingProcess();
		log.debug("[processEntry] init");
		
		/* meter reinicio de ids */
		numeroIdContractingProcess = Integer.valueOf(0);
		numeroIdOrganization = Integer.valueOf(0);
		numeroIdTender = Integer.valueOf(0);
		numeroIdAward = Integer.valueOf(0);
		numeroIdLot = Integer.valueOf(0);
//		numeroIdItem = Integer.valueOf(0);
		numeroIdTenderRelItem = Integer.valueOf(0);
		numeroIdLotRelItem = Integer.valueOf(0);

	
		String ikeyProcess = "";
		
		
		
		String summary = (String)((JSONObject) entry.get("summary")).get("content");
		//CMG Para las fechas de Tender (starDate y endDate)
		String fechaUpdate = (String) entry.get("updated");
		Object title = null;
		if(entry.get("title") instanceof String)
		{
			title = (String) entry.get("title");
		}else if(entry.get("title") instanceof Double) {
			title = (Double) entry.get("title");
		}
		else if(entry.get("title") instanceof Long){
			title = (Long) entry.get("title");
		}
		
			
		String link = (String)((JSONObject) entry.get("link")).get("href");
		JSONObject joContractFolderStatus = (JSONObject)entry.get("cac-place-ext:ContractFolderStatus");
		
		if(joContractFolderStatus!=null) {
			Object contractFolderID = (Object) joContractFolderStatus.get("cbc:ContractFolderID");
			
			JSONObject joLocatedContractingParty = (JSONObject)joContractFolderStatus.get("cac-place-ext:LocatedContractingParty");
			JSONObject joContractFolderStatusCode = (JSONObject)joContractFolderStatus.get("cbc-place-ext:ContractFolderStatusCode");
			JSONObject joProcurementProject = (JSONObject)joContractFolderStatus.get("cac:ProcurementProject");
//			JSONObject joTenderingTerms = (JSONObject)joContractFolderStatus.get("cac:TenderingTerms");
			JSONObject joTenderingProcess=null;
			if(joContractFolderStatus.get("cac:TenderingProcess") instanceof JSONObject) {
				joTenderingProcess = (JSONObject)joContractFolderStatus.get("cac:TenderingProcess");
			}
			JSONAware joTenderResult;
			if(joContractFolderStatus.get("cac:TenderResult") instanceof JSONArray) {
					joTenderResult = (JSONArray)joContractFolderStatus.get("cac:TenderResult");
					joTenderResult.toString();
				}else {
					joTenderResult = (JSONObject)joContractFolderStatus.get("cac:TenderResult");
				}
			
			JSONAware joProcurementProjectLot;
				if(joContractFolderStatus.get("cac:ProcurementProjectLot") instanceof JSONArray) {
					joProcurementProjectLot = (JSONArray)joContractFolderStatus.get("cac:ProcurementProjectLot");
					joProcurementProjectLot.toString();
				}else {
					joProcurementProjectLot = (JSONObject)joContractFolderStatus.get("cac:ProcurementProjectLot");
				}
			
			if(joLocatedContractingParty.get("cac:Party")!=null) {
				JSONObject joParty = (JSONObject)joLocatedContractingParty.get("cac:Party");
				JSONObject joPartyID = null;
				if(joParty.get("cac:PartyIdentification")!=null) {
					Object joWebsiteURI = joParty.get("cbc:WebsiteURI");
					
					/* HLM Este cambio es reciente ha que observar si está bien hecho */
					JSONObject joPartyIdentification=null;
					//CMG OBTENEMOS LA INFORMACION DE NIF Excluimos DIR3
					boolean encontradoSchemaNIF = false;
					
					if(joParty.get("cac:PartyIdentification") instanceof JSONObject) {
						joPartyIdentification = (JSONObject)joParty.get("cac:PartyIdentification");
						//CMG OBTENEMOS LA INFORMACION DE NIF
						joPartyID = (JSONObject)joPartyIdentification.get("cbc:ID");
						String valorAux = (String) joPartyID.get("schemeName");
						if (valorAux!=null && "NIF".equals(valorAux.toUpperCase())) {
							encontradoSchemaNIF = true;
						}
					}else if(joParty.get("cac:PartyIdentification") instanceof JSONArray) {
						JSONArray joPartyIdentificationArray = (JSONArray)joParty.get("cac:PartyIdentification");
						for(int i=0;i<((JSONArray) joPartyIdentificationArray).size();i++) {
							joPartyIdentification = (JSONObject) ((JSONArray) joPartyIdentificationArray).get(i);
							joPartyID = (JSONObject)joPartyIdentification.get("cbc:ID");
							String valorAux = (String) joPartyID.get("schemeName");
							if (valorAux!=null && "NIF".equals(valorAux.toUpperCase())) {
								encontradoSchemaNIF = true;
								break;
							}
						}
						
						//CMG OBTENEMOS LA INFORMACION DE NIF
					}
					log.debug("[processEntry] [joPartyID:"+joPartyID+"] [encontradoSchemaNIF:"+encontradoSchemaNIF+"]" );
					if(joPartyID!=null && encontradoSchemaNIF) {
						//JSONObject joPartyID = (JSONObject)joPartyIdentification.get("cbc:ID");
						if(joPartyID.get("content")!=null) {
							Object partyID = joPartyID.get("content");
							
//							if(filtro.equals(partyID) || filtro.equals(String.valueOf(joWebsiteURI))) {
							if(configuration.getFilterList().contains(partyID) || configuration.getFilterList().equals(String.valueOf(joWebsiteURI))) {	
								log.info("[processEntry] └─Process found for party: "+partyID);
								
								Tender tender = new Tender();
								numeroIdContractingProcess++;
//								contractingProcess.setIkey(numeroIdContractingProcess);
								if(!"".equals(contractFolderID)) {
									contractingProcess.setIdentifier(String.valueOf(contractFolderID));
									ikeyProcess = String.valueOf(contractFolderID).replace("/", "-");
									contractingProcess.setIkey(String.valueOf(ikeyProcess));
									contractingProcess.setId(String.valueOf(ikeyProcess));
									log.debug("[processEntry] [ikeyProcess:"+ikeyProcess+"]");
								}
								if(!"".equals(title)) {
									if(title instanceof String)
									{
										contractingProcess.setTitle(Util.normalizaTexto((String)title));
									}else 
									{
										contractingProcess.setTitle(((Long)title).toString());
									}
									
								}
								if(!"".equals(summary)) {
									contractingProcess.setDescription(Util.normalizaTexto(summary));
								}
								if(!"".equals(link)) {
									contractingProcess.setURL(Util.normalizaTexto(link));
								}
								
									
								//OJO: contractingProcess se actualiza en los siguientes ifs
								processMapList.put(contractingProcess.getId(), contractingProcess);
								
								if(joLocatedContractingParty!=null) {
									
									Organization organization = new Organization();
									numeroIdOrganization++;				
									
									
									String orgTitle=Util.normalizaTexto(String.valueOf( ((JSONObject)joParty.get("cac:PartyName")).get("cbc:Name")));
									String orgId=generateOrganizationId(ikeyProcess, String.valueOf(partyID), orgTitle,false);
									
									organization.setIkey(orgId);									
									organization.setId(orgId);	
									
									contractingProcess.setIsBuyerFor(organization.getId());
									
									organization.setTitle(orgTitle);	
									
									organization.setURI(String.valueOf(joParty.get("cbc:WebsiteURI")));
									
									JSONObject joContact = (JSONObject)joParty.get("cac:Contact");
									if(joContact!=null) {
										organization.setContactPoint_email(String.valueOf(joContact.get("cbc:ElectronicMail")));
										if(joContact.get("cbc:Telefax")!=null) {
											organization.setContactPoint_faxNumber(String.valueOf(joContact.get("cbc:Telefax")));
										}
										organization.setContactPoint_title(String.valueOf(joContact.get("cbc:Name")));
										if(joContact.get("cbc:Telephone")!=null) {
											organization.setContactPoint_telephone(String.valueOf(joContact.get("cbc:Telephone")));
										}
									}
									JSONObject joPostalAddress = (JSONObject)joParty.get("cac:PostalAddress");
									if(joPostalAddress!=null) {
										organization.setMunicipio_id(configuration.getMunicipioId());
										organization.setMunicipio_title(configuration.getMunicipioTitle());
										organization.setPostal_code(String.valueOf(joPostalAddress.get("cbc:PostalZone")));
										organization.setStreet_address(String.valueOf(((JSONObject)joPostalAddress.get("cac:AddressLine")).get("cbc:Line")));
									}
									
									if (orgId==null)
									{
										throw new Exception ("Organization without id: "+organization.getTitle());
									}
									
									addOrganization(organization);
									
									
								}

								if(joProcurementProject!=null) {
									
									++numeroIdTender;
									tender.setIkey(ikeyProcess+"-"+prefifoIdTender+numeroIdTender);
									tender.setId(ikeyProcess+"-"+prefifoIdTender+numeroIdTender);
									contractingProcess.setHasTender(tender.getId());
									tender.setTitle(Util.normalizaTexto(String.valueOf(joProcurementProject.get("cbc:Name"))));
									//CMG Eliminamos los mapeos
									//tender.setTenderStatus(mapStatus.get(String.valueOf(joContractFolderStatusCode.get("content"))));
									tender.setTenderStatus(String.valueOf(joContractFolderStatusCode.get("content")));
									//CMG dejamos cbc:TaxExclusiveAmount y excluimos cbc:TotalAmount
									tender.setValueAmount( new BigDecimal(String.valueOf(((JSONObject)((JSONObject)joProcurementProject.get("cac:BudgetAmount")).get("cbc:TaxExclusiveAmount")).get("content"))));
									//tender.setValueAmount( new BigDecimal(String.valueOf(((JSONObject)((JSONObject)joProcurementProject.get("cac:BudgetAmount")).get("cbc:TotalAmount")).get("content"))));
									//CMG Eliminamos los mapeos
									//tender.setMainProcCategory(mapProcurementProjectTypeCode.get(((JSONObject)joProcurementProject.get("cbc:TypeCode")).get("content").toString()));
									tender.setMainProcCategory(((JSONObject)joProcurementProject.get("cbc:TypeCode")).get("content").toString());
									if(joProcurementProject.get("cac:PlannedPeriod")!=null) {
										if(((JSONObject)((JSONObject)joProcurementProject.get("cac:PlannedPeriod")).get("cbc:DurationMeasure"))!=null) {
											if("ANN".equals(((JSONObject)((JSONObject)joProcurementProject.get("cac:PlannedPeriod")).get("cbc:DurationMeasure")).get("unitCode"))) {
												
												tender.setPeriodDurationInDays(Integer.valueOf(String.valueOf(((JSONObject)((JSONObject)joProcurementProject.get("cac:PlannedPeriod")).get("cbc:DurationMeasure")).get("content")))*365);
											}else if("MON".equals(((JSONObject)((JSONObject)joProcurementProject.get("cac:PlannedPeriod")).get("cbc:DurationMeasure")).get("unitCode"))) {
												
												tender.setPeriodDurationInDays(Integer.valueOf(String.valueOf(((JSONObject)((JSONObject)joProcurementProject.get("cac:PlannedPeriod")).get("cbc:DurationMeasure")).get("content")))*30);
											}else if("DAY".equals(((JSONObject)((JSONObject)joProcurementProject.get("cac:PlannedPeriod")).get("cbc:DurationMeasure")).get("unitCode"))) {
												tender.setPeriodDurationInDays(Integer.valueOf(String.valueOf(((JSONObject)((JSONObject)joProcurementProject.get("cac:PlannedPeriod")).get("cbc:DurationMeasure")).get("content"))));
											}																
										}
									}
									Date fechaTratada = Util.getFechaAtomSQlite(fechaUpdate);
									log.debug("[Tratamiento de fechas [cbc:StartDate] ] [fecha:"+fechaUpdate+"] [fechaTratada:"+fechaTratada+"]");
									tender.setPeriodStartDate(fechaTratada);
									tender.setPeriodEndDate(fechaTratada);
									
									tenderList.add(tender);
									
									//cac:RequiredCommodityClassification
									if(joProcurementProject.get("cac:RequiredCommodityClassification")!=null) {
										if(joProcurementProject.get("cac:RequiredCommodityClassification") instanceof JSONArray) {
										
											JSONArray jaRequiredCommodityClassification = (JSONArray) joProcurementProject.get("cac:RequiredCommodityClassification");
											for(int j=0;j<jaRequiredCommodityClassification.size();j++) {
												//Item item = new Item();
												JSONObject joRequiredCommodityClassificationChild = (JSONObject) jaRequiredCommodityClassification.get(j);
												//CMG Cambios en el alta de Item
												String has_classification = String.valueOf(((JSONObject)joRequiredCommodityClassificationChild.get("cbc:ItemClassificationCode")).get("content"));
												createTenderRelItem(ikeyProcess, has_classification, tender.getId());
											}
										}else {
											JSONObject joRequiredCommodityClassification = (JSONObject) joProcurementProject.get("cac:RequiredCommodityClassification");
											//CMG Cambios en el alta de Item
											if (joRequiredCommodityClassification!=null) {
												String has_classification = String.valueOf(((JSONObject)joRequiredCommodityClassification.get("cbc:ItemClassificationCode")).get("content"));
												//String identificadorItem = /*ikeyProcess+"-"+prefifoIdItem+numeroIdItem*/ has_classification;
												createTenderRelItem(ikeyProcess, has_classification, tender.getId());
											}
										}
									}
									
								}
								if(joTenderingProcess!=null && tender!=null) {
																					
									if(joTenderingProcess.get("cbc:ProcedureCode")!=null)
									{
										//CMG Eliminamos los mapeos
										//tender.setProcurementMethod(mapTenderingProcessProcedureCode.get(((JSONObject)joTenderingProcess.get("cbc:ProcedureCode")).get("content").toString()));
										tender.setProcurementMethod(((JSONObject)joTenderingProcess.get("cbc:ProcedureCode")).get("content").toString());
									}

									if(joTenderingProcess.get("cbc:UrgencyCode")!=null)
									{
										//CMG Eliminamos los mapeos
										//tender.setProcurementMethodDetails(mapTenderingProcessUrgencyCode.get(((JSONObject)joTenderingProcess.get("cbc:UrgencyCode")).get("content").toString()));
										tender.setProcurementMethodDetails(((JSONObject)joTenderingProcess.get("cbc:UrgencyCode")).get("content").toString());
									}
									
								}
								if(joTenderResult!=null) {
									if(joTenderResult instanceof JSONArray) {
										for(int i=0;i<((JSONArray) joTenderResult).size();i++) {
											Award award = new Award();
											
											JSONObject joTenderResultChild = (JSONObject) ((JSONArray) joTenderResult).get(i);
											
											++numeroIdAward;
											award.setIkey(ikeyProcess+"-"+prefifoIdAward+numeroIdAward);
											award.setId(ikeyProcess+"-"+prefifoIdAward+numeroIdAward);
											if(joTenderResultChild.get("cbc:ReceivedTenderQuantity")!=null && tender.getNumberOfTenderers()!=null) {
												tender.setNumberOfTenderers(tender.getNumberOfTenderers()+(Long)joTenderResultChild.get("cbc:ReceivedTenderQuantity"));
											}else if(joTenderResultChild.get("cbc:ReceivedTenderQuantity")!=null){
												tender.setNumberOfTenderers((Long)joTenderResultChild.get("cbc:ReceivedTenderQuantity"));
											}
											award.setDescription(Util.normalizaTexto(String.valueOf(joTenderResultChild.get("cbc:Description"))));
											//CMG Tratamiento de fechas
											String fecha = String.valueOf(joTenderResultChild.get("cbc:AwardDate"));
											Date fechaTratada = Util.getFechaAtomSQlite(fecha);
											log.debug("[Tratamiento de fechas[cbc:AwardDate]] [fecha:"+fecha+"] [fechaTratada:"+fechaTratada+"]");
											award.setAwardDate(fechaTratada);
											if((JSONObject)((JSONObject)joTenderResultChild.get("cac:AwardedTenderedProject")).get("cac:LegalMonetaryTotal")!=null) {
												//CMG dejamos cbc:TaxExclusiveAmount y excluimos cbc:PayableAmount
												award.setValueAmount(Double.valueOf(String.valueOf(((JSONObject)((JSONObject)((JSONObject)joTenderResultChild.get("cac:AwardedTenderedProject")).get("cac:LegalMonetaryTotal")).get("cbc:TaxExclusiveAmount")).get("content"))));
												//award.setValueAmount(Double.valueOf(String.valueOf(((JSONObject)((JSONObject)((JSONObject)joTenderResultChild.get("cac:AwardedTenderedProject")).get("cac:LegalMonetaryTotal")).get("cbc:PayableAmount")).get("content"))));
											}
											if(((JSONObject)joTenderResultChild.get("cac:AwardedTenderedProject")).get("cbc:ProcurementProjectLotID")!=null) 
											{
												award.setLotId(Long.valueOf(String.valueOf(((JSONObject)joTenderResultChild.get("cac:AwardedTenderedProject")).get("cbc:ProcurementProjectLotID"))));
											}
											
											if(joTenderResultChild.get("cac:WinningParty")!=null) {
												
												String orgTitle=Util.normalizaTexto(String.valueOf(((JSONObject)((JSONObject)joTenderResultChild.get("cac:WinningParty")).get("cac:PartyName")).get("cbc:Name")));
												String orgId=generateOrganizationId(ikeyProcess, String.valueOf(String.valueOf(((JSONObject)((JSONObject)((JSONObject)joTenderResultChild.get("cac:WinningParty")).get("cac:PartyIdentification")).get("cbc:ID")).get("content"))), orgTitle,true);
																								
												award.setIsSupplierFor(orgId);
												Organization organization = new Organization();
												numeroIdOrganization++;
												
												organization.setIkey(orgId);
												organization.setId(orgId);
												organization.setTitle(orgTitle);														
												
												addOrganization(organization);
											}else {
												award.setIsSupplierFor(null);
											}
											
											
											addAward(award);
										}
									}else {
										Award award = new Award();
										++numeroIdAward;
										award.setIkey(ikeyProcess+"-"+prefifoIdAward+numeroIdAward);
										award.setId(ikeyProcess+"-"+prefifoIdAward+numeroIdAward);
										tender.setHasSupplier(award.getId());
										if(tender.getNumberOfTenderers()!=null) {
											tender.setNumberOfTenderers(tender.getNumberOfTenderers()+(Long)((JSONObject)joTenderResult).get("cbc:ReceivedTenderQuantity"));
										}else {
											tender.setNumberOfTenderers((Long)((JSONObject)joTenderResult).get("cbc:ReceivedTenderQuantity"));
										}
										award.setDescription(Util.normalizaTexto((String)((JSONObject)joTenderResult).get("cbc:Description")+""));
										//CMG Tratamiento de fechas
										String fecha = (String)((JSONObject)joTenderResult).get("cbc:AwardDate");
										Date fechaTratada = Util.getFechaAtomSQlite(fecha);
										log.debug("[Tratamiento de fechas[cbc:AwardDate]] [fecha:"+fecha+"] [fechaTratada:"+fechaTratada+"]");
										award.setAwardDate(fechaTratada);

										if(((JSONObject)joTenderResult).get("cac:AwardedTenderedProject")!=null) {
											if((JSONObject)((JSONObject)((JSONObject)joTenderResult).get("cac:AwardedTenderedProject")).get("cac:LegalMonetaryTotal")!=null) {
												//CMG dejamos cbc:TaxExclusiveAmount e excluimos cbc:PayableAmount
												award.setValueAmount(Double.valueOf(String.valueOf(((JSONObject)((JSONObject)((JSONObject)((JSONObject)joTenderResult).get("cac:AwardedTenderedProject")).get("cac:LegalMonetaryTotal")).get("cbc:TaxExclusiveAmount")).get("content"))));
												//award.setValueAmount(Double.valueOf(String.valueOf(((JSONObject)((JSONObject)((JSONObject)((JSONObject)joTenderResult).get("cac:AwardedTenderedProject")).get("cac:LegalMonetaryTotal")).get("cbc:PayableAmount")).get("content"))));
											}
										}
										if(((JSONObject)joTenderResult).get("cac:WinningParty")!=null) {											
											
											String orgTitle=Util.normalizaTexto(String.valueOf((((JSONObject)((JSONObject)((JSONObject)joTenderResult).get("cac:WinningParty")).get("cac:PartyName")).get("cbc:Name"))));
											String orgId=generateOrganizationId(ikeyProcess, String.valueOf(((JSONObject)((JSONObject)((JSONObject)((JSONObject)joTenderResult).get("cac:WinningParty")).get("cac:PartyIdentification")).get("cbc:ID")).get("content")),orgTitle,true);
																						
											award.setIsSupplierFor(orgId);
											Organization organization = new Organization();
											numeroIdOrganization++;
											organization.setIkey(orgId);									
																				
											organization.setId(orgId);														
											organization.setTitle(orgTitle);
											
											addOrganization(organization);
										}else {
											award.setIsSupplierFor(null);
										}
										
										addAward(award);
									}
								}
								if(joProcurementProjectLot!=null) {
									
									if(joProcurementProjectLot instanceof JSONArray) {
										for(int k=0;k<((JSONArray) joProcurementProjectLot).size();k++) {
											
											Lot lot = new Lot();
											JSONObject joProcurementProjectLotChild = (JSONObject) ((JSONArray) joProcurementProjectLot).get(k);
																	
											++numeroIdLot;
											lot.setIkey(ikeyProcess+"-"+prefifoIdLot+numeroIdLot);
											lot.setId(ikeyProcess+"-"+prefifoIdLot+numeroIdLot);
											lot.setDescription(Util.normalizaTexto(String.valueOf(((JSONObject)joProcurementProjectLotChild.get("cbc:ID")).get("content"))));
											
											lot.setTitle(Util.normalizaTexto(String.valueOf(((JSONObject)joProcurementProjectLotChild.get("cac:ProcurementProject")).get("cbc:Name"))));
											//CMG Dejamos cbc:TaxExclusiveAmount y excluimos cbc:TotalAmount
											lot.setValueAmount(String.valueOf(((JSONObject)((JSONObject)((JSONObject)joProcurementProjectLotChild.get("cac:ProcurementProject")).get("cac:BudgetAmount")).get("cbc:TaxExclusiveAmount")).get("content")));
											//lot.setValueAmount(String.valueOf(((JSONObject)((JSONObject)((JSONObject)joProcurementProjectLotChild.get("cac:ProcurementProject")).get("cac:BudgetAmount")).get("cbc:TotalAmount")).get("content")));
											lot.setTenderId(tender.getId());
											
											for(String awardId: awardMapList.keySet()) {
												Award searchAward =awardMapList.get(awardId);											
												if(searchAward.getLotId().equals(Long.valueOf(String.valueOf(((JSONObject)joProcurementProjectLotChild.get("cbc:ID")).get("content")))))
												{
													lot.setHasSupplier(searchAward.getId());
													break;
												}else {
													lot.setHasSupplier(null);
												}
											}
											
											lotList.add(lot);

//											JSONArray joRequiredCommodityClassification;
//											joRequiredCommodityClassification = (JSONArray)((JSONObject)joProcurementProjectLotChild.get("cac:ProcurementProject")).get("cac:RequiredCommodityClassification");
											JSONAware joRequiredCommodityClassification;
				 							if(((JSONObject)joProcurementProjectLotChild.get("cac:ProcurementProject")).get("cac:RequiredCommodityClassification") instanceof JSONArray) {
				 								joRequiredCommodityClassification = (JSONArray)((JSONObject)joProcurementProjectLotChild.get("cac:ProcurementProject")).get("cac:RequiredCommodityClassification");
				 							}else {
				 								joRequiredCommodityClassification = (JSONObject)((JSONObject)joProcurementProjectLotChild.get("cac:ProcurementProject")).get("cac:RequiredCommodityClassification");
				 							}
											
											if(joRequiredCommodityClassification instanceof JSONArray)
											{
												for(int l=0;l<((JSONArray) joRequiredCommodityClassification).size();l++) {
													JSONObject joRequiredCommodityClassificationChild = (JSONObject) ((JSONArray) joRequiredCommodityClassification).get(l);													
													//CMG Cambio en el tratamiento de Item
													String has_classification = String.valueOf(((JSONObject)joRequiredCommodityClassificationChild.get("cbc:ItemClassificationCode")).get("content"));
													createLotRelItem(ikeyProcess, has_classification, lot.getId());
												}
											}else {											
												//CMG Cambio en el tratamiento de Item
												if (joRequiredCommodityClassification!=null) {
													String has_classification = String.valueOf(((JSONObject)((JSONObject)joRequiredCommodityClassification).get("cbc:ItemClassificationCode")).get("content"));
													createLotRelItem(ikeyProcess, has_classification, lot.getId());
												}
											}
											
											
										}
										
									}else {
										Lot lot = new Lot();
										++numeroIdLot;
										lot.setIkey(ikeyProcess+"-"+prefifoIdLot+numeroIdLot);
										lot.setId(ikeyProcess+"-"+prefifoIdLot+numeroIdLot);
										lot.setDescription(String.valueOf(((JSONObject)((JSONObject)joProcurementProjectLot).get("cbc:ID")).get("content")));
										lot.setTitle(String.valueOf(((JSONObject)((JSONObject)joProcurementProjectLot).get("cac:ProcurementProject")).get("cbc:Name")));
										lot.setValueAmount(String.valueOf(((JSONObject)((JSONObject)((JSONObject)((JSONObject)joProcurementProjectLot).get("cac:ProcurementProject")).get("cac:BudgetAmount")).get("cbc:TaxExclusiveAmount")).get("content")));
										//lot.setValueAmount(String.valueOf(((JSONObject)((JSONObject)((JSONObject)((JSONObject)joProcurementProjectLot).get("cac:ProcurementProject")).get("cac:BudgetAmount")).get("cbc:TotalAmount")).get("content")));
										lot.setTenderId(tender.getId());
										
										for(String awardId: awardMapList.keySet()) {
											Award searchAward =awardMapList.get(awardId);
											if(searchAward.getLotId().equals(Long.valueOf(String.valueOf(((JSONObject)((JSONObject)joProcurementProjectLot).get("cbc:ID")).get("content")))))
											{
												lot.setHasSupplier(searchAward.getId());
											}
										}
										
										lotList.add(lot);
										
										JSONAware joRequiredCommodityClassification;
			 							if(((JSONObject)((JSONObject)joProcurementProjectLot).get("cac:ProcurementProject")).get("cac:RequiredCommodityClassification") instanceof JSONArray) {
			 								joRequiredCommodityClassification = (JSONArray)((JSONObject)((JSONObject)joProcurementProjectLot).get("cac:ProcurementProject")).get("cac:RequiredCommodityClassification");
			 							}else {
			 								joRequiredCommodityClassification = (JSONObject)((JSONObject)((JSONObject)joProcurementProjectLot).get("cac:ProcurementProject")).get("cac:RequiredCommodityClassification");
			 							}
										
										if(joRequiredCommodityClassification instanceof JSONArray)
										{
											for(int l=0;l<((JSONArray) joRequiredCommodityClassification).size();l++) {
												JSONObject joRequiredCommodityClassificationChild = (JSONObject) ((JSONArray) joRequiredCommodityClassification).get(l);
												//CMG Cambio en el tratamiento de Item
												String has_classification = String.valueOf(((JSONObject)joRequiredCommodityClassificationChild.get("cbc:ItemClassificationCode")).get("content"));
												createLotRelItem(ikeyProcess, has_classification, lot.getId());
											}
										}else {						
											//CMG Cambio en el tratamiento de Item
											if (joRequiredCommodityClassification!=null) {
												String has_classification = String.valueOf(((JSONObject)((JSONObject)joRequiredCommodityClassification).get("cbc:ItemClassificationCode")).get("content"));
												createLotRelItem(ikeyProcess, has_classification, lot.getId());
											}
										}
									}
									
								}
																				
							}
						}
					}
				}
			}
			
		}
		
		log.debug("[processEntry] end");
		return contractingProcess.getId();
		
	}
	

	private <T> int processSQL(List<T> mapaList, String processId, String nameTabla, List<String> queriesExecuted, List<String> queriesSQLServer) {
		
		int status=0;
		if (mapaList.size()>0)
		{		
			log.debug("[processSQL] [processId:"+processId+"] [nameTabla :"+nameTabla+"] inicio");
			try {
				List<String> identifiersFromTable = getIndentifierBD(nameTabla);
				for (T iSql : mapaList) {
					String idToSearch= ((ISqlGeneric) iSql).getId();
					log.debug("[processSQL] [processId:"+processId+"]  [idToSearch :"+idToSearch+"]");
					if (identifiersFromTable.contains(idToSearch) == false) {									

						String sqlInsert = ((ISqlGeneric) iSql).generateInsert(Constants.BBDD_SQLITE);
						log.debug("[processSQL] [processId:"+processId+"] [nameTabla :"+nameTabla+"] [sqlInsert: "+sqlInsert +"]");
						queriesExecuted.add(sqlInsert);						
						generateQueryInsertGlobal(((ISqlGeneric) iSql));
						
						//Añado en la lista el identificador que acabamos de insertar
						identifiersFromTable.add(idToSearch);
					}else {
						String sqlUpdate = ((ISqlGeneric) iSql).generateUpdate(Constants.BBDD_SQLITE);
						log.debug("[processSQL] [processId:"+processId+"] [nameTabla :"+nameTabla+"] [sqlUpdate: "+sqlUpdate +"]");
						queriesExecuted.add(sqlUpdate);						
						generateQueryUpdateGlobal(((ISqlGeneric) iSql));

					}
				}
			} catch (Exception e) {
				log.error("Error adding processSQL", e);
				status=-1;
			}
			log.debug("[processSQL] [processId:"+processId+"]  [nameTabla :"+nameTabla+"] end");
			mapaList.clear();
		}
		return status;
	}
	

	private <T> int processSQL(Map<String,T> mapaList, String processId, String nameTabla , List<String> queriesExecuted,List<String> queriesSQLServer ) {
		
		int status=0;
		if (mapaList.size()>0)
		{		
			log.debug("[processSQL] [processId:"+processId+"]  [nameTabla :"+nameTabla+"] inicio");
			try {
				List<String> identifiersFromTable = getIndentifierBD(nameTabla);
				for (String id : mapaList.keySet()) {
					 T iSql= (T) mapaList.get(id);
					String idToSearch= ((ISqlGeneric) iSql).getId();
					log.debug("[processSQL] [processId:"+processId+"]  [idToSearch :"+idToSearch+"]");
					if (identifiersFromTable.contains(idToSearch) == false) {									
						String sqlInsert = ((ISqlGeneric) iSql).generateInsert(Constants.BBDD_SQLITE);
						log.debug("[processSQL] [processId:"+processId+"] [nameTabla :"+nameTabla+"] [sqlInsert: "+sqlInsert +"]");
						queriesExecuted.add(sqlInsert);						
						generateQueryInsertGlobal(((ISqlGeneric) iSql));						
						
						//Añado en la lista el identificador que acabamos de insertar
						identifiersFromTable.add(idToSearch);
					}else {
						String sqlUpdate = ((ISqlGeneric) iSql).generateUpdate(Constants.BBDD_SQLITE);
						log.debug("[processSQL] [processId:"+processId+"] [nameTabla :"+nameTabla+"] [sqlUpdate: "+sqlUpdate +"]");
						queriesExecuted.add(sqlUpdate);						
						generateQueryUpdateGlobal(((ISqlGeneric) iSql));
					}
				}
			} catch (Exception e) {
				log.error("Error adding processSQL", e);
				status=-1;
			}
			log.debug("[processSQL] [processId:"+processId+"]  [nameTabla :"+nameTabla+"] end");
			mapaList.clear();
		}
		return status;
	}
	
	private void generateQueryInsertGlobal(ISqlGeneric isql) {
		log.debug("[generateQueryInsertGlobal] [isql:"+isql+"] process...");
		if (queriesGlobal.size()>0 && isql!=null)
		{
			for (String bd:ConfigExtractor.LIST_BBDD) {
				List<String> queryAux=  queriesGlobal.get(bd);
				queryAux.add(isql.generateInsert(bd));
			}
		}
	}
	
	private void generateQueryUpdateGlobal(ISqlGeneric isql) {
		log.debug("[generateQueryUpdateGlobal] [isql:"+isql+"] process...");
		if (queriesGlobal.size()>0 && isql!=null)
		{
			for (String bd:ConfigExtractor.LIST_BBDD) {
				List<String> queryAux=  queriesGlobal.get(bd);
				queryAux.add(isql.generateUpdate(bd));
			}
		}
	}
	
	
	private List<String> writeQueriesAll(List<String> cleanClines,List<String> itemsLines, boolean initialLoad) {
		log.debug("[writeQueriesAll]  init");
		List <String> filesGenerated=new ArrayList<String>();
		if (queriesGlobal.size()>0)
		{
			for (String bd:ConfigExtractor.LIST_BBDD) {
				List<String> queriesToWrite=new ArrayList<String>();
				List<String> queryAux=  queriesGlobal.get(bd);
				queriesToWrite.addAll(cleanClines);
				queriesToWrite.addAll(itemsLines);
				queriesToWrite.addAll(queryAux);
				String fecha_actual = Util.formatearFechas(new Date(), Constants.DATE_TIME_FORMAT_BACKUP_FILE);
				if (initialLoad)
				{
					String outputFile=Constants.INITIAL_OUTPUT_FOLDER+File.separator+"queries_"+bd+"_"+fecha_actual+".sql";					
					writeQueriesToFile(queriesToWrite, outputFile);
					filesGenerated.add(outputFile);
				}
				else
				{
					String outputFile=Constants.PERIODIC_OUTPUT_FOLDER+File.separator+"queries_"+bd+"_"+fecha_actual+".sql";	
					writeQueriesToFile(queriesToWrite,outputFile);
					filesGenerated.add(outputFile);
				}
			}
		}		
		
		log.debug("[writeQueriesAll]  end");
		return filesGenerated;
	}
	
	
	private void createLotRelItem(String ikeyProcess, String has_classification, String lote_id) {
		log.debug("[createLotRelItem] [ikeyProcess:"+ikeyProcess+"] [has_classification:"+has_classification+"] [lote_id:"+lote_id+"] ");
		++numeroIdLotRelItem;
		String identifier = ikeyProcess+"-"+prefifoIdLotRelItem+numeroIdLotRelItem;
		LotRelItem lotRelItem = new LotRelItem();
		lotRelItem.setIkey(identifier);
		lotRelItem.setId(identifier);			
		lotRelItem.setItem_id(has_classification);
		lotRelItem.setLot_id(lote_id);
		lotRelItemList.add(lotRelItem);
		
	}
	
	private void createTenderRelItem(String ikeyProcess, String has_classification, String tender_id) {
		log.debug("[createTenderRelItem] [ikeyProcess:"+ikeyProcess+"] [has_classification:"+has_classification+"] [tender_id:"+tender_id+"] ");
		TenderRelItem tenderRelItem = new TenderRelItem();
		++numeroIdTenderRelItem;
		tenderRelItem.setIkey(ikeyProcess+"-"+prefifoIdTenderRelItem+numeroIdTenderRelItem);
		tenderRelItem.setId(ikeyProcess+"-"+prefifoIdTenderRelItem+numeroIdTenderRelItem);
		tenderRelItem.setItem_id(has_classification);
		tenderRelItem.setTender_id(tender_id);		
		tenderRelItemList.add(tenderRelItem);
	}	
	
	private void loadIndentifierBD() {
		 identifiersFromOrganization = dbUtil.getIdentifiersFromTable(Organization.tableName);
		 identifiersFromAward = dbUtil.getIdentifiersFromTable(Award.tableName);
		 identifiersFromTender = dbUtil.getIdentifiersFromTable(Tender.tableName);
		 identifiersFromTenderRelItem = dbUtil.getIdentifiersFromTable(TenderRelItem.tableName);
		 identifiersFromProcess = dbUtil.getIdentifiersFromTable(ContractingProcess.tableName);
		 identifiersFromLot = dbUtil.getIdentifiersFromTable(Lot.tableName);
		 identifiersFromLotRetItem = dbUtil.getIdentifiersFromTable(LotRelItem.tableName);
	}
	
	private List<String> getIndentifierBD(String tableName) {
		if (Organization.tableName.equals(tableName)) {
			return identifiersFromOrganization;
		}	
		else if (Award.tableName.equals(tableName)) {
			return identifiersFromAward ;
		}	
		else if (Tender.tableName.equals(tableName)) {
				return identifiersFromTender ;
		}
		else if (TenderRelItem.tableName.equals(tableName)) {
			return identifiersFromTenderRelItem ;
		}
		else if (ContractingProcess.tableName.equals(tableName)) {
			return identifiersFromProcess ;
		}
		else if (Lot.tableName.equals(tableName)) {
			return identifiersFromLot ;
		}
		else if (LotRelItem.tableName.equals(tableName)) {
			return identifiersFromLotRetItem ;
		}else {
			log.error("[getIndentifierBD] [tableName:"+tableName+"] NOT FOUND!");
		}
		return null;
		
		
	}

}
