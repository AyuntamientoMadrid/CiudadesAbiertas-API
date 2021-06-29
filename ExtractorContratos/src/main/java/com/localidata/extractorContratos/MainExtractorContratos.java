package com.localidata.extractorContratos;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.localidata.extractorContratos.model.LogTable;
import com.localidata.extractorContratos.service.MailService;
import com.localidata.extractorContratos.util.ConfigExtractor;
import com.localidata.extractorContratos.util.Constants;
import com.localidata.extractorContratos.util.DataBaseFinalUtil;
import com.localidata.extractorContratos.util.Util;

public class MainExtractorContratos {
	
	private static final String SEPARATOR_BACKUP = "_";

	private static final String PREFIX_BACKUP_FILE = "backup_";

	private static Logger log = Logger.getLogger(MainExtractorContratos.class);

	private static MailService serviceMail= new MailService();
	
	private static DataBaseFinalUtil dbFinalUtil = new DataBaseFinalUtil();	

	public static void main(String[] args) {
		PropertyConfigurator.configure("log4j.properties");

		ConfigExtractor config= new ConfigExtractor();
		
		if (args.length==1)
		{
			if (args[0]!=null && args[0].equals("email_check"))
			{
				log.info("checking email...");
				serviceMail.enviar(Constants.MAIL_ASUNTO_TEST, "Prueba de correo");
				return;
			}
		}
		
		
		
		
		log.info("start process");

		//String cargaInicial=config.get(Constants.PARAM_CONFIG_LOAD_INITIAL);
		if (Util.validValue(config.isLoadInitial()))
		{			
			if (config.isLoadInitial())
			{
				ExtractorContratos.main(null);
				String texto="carga inicial completa";
				log.info("[main] "+texto);
				serviceMail.enviar(Constants.MAIL_ASUNTO_PROCESO_OK, texto);
			}else {
				
				Date thisRunDate = new Date();
				int status=DescargaPeriodica.main(null);
				if (status==0)
				{
					if (config.getBackupDays()>0)
					{
						String origin=config.getBbddURL();
					
						if (origin.contains("sqlite")&&config.getBackupDays()>0)
						{
							origin=origin.replace("jdbc:sqlite:", "");
							File originFile=new File(origin);							
							File backupPath=new File(originFile.getParent());							
							String destFileName=backupPath.getAbsolutePath()+File.separator+PREFIX_BACKUP_FILE+originFile.getName()+SEPARATOR_BACKUP+Util.backupDateTimeFormatter.format(new Date());
							File desFile=new File(destFileName);
							try {
								FileUtils.copyFile(originFile, desFile);
							} catch (IOException e) {
								log.error("Error copying database",e);
							}
							Collection<File> listFiles = FileUtils.listFiles(desFile.getParentFile(), null, false);
							List<File> filesToDelete=new ArrayList<File>();
							for (File checkFile:listFiles)
							{
								Date today=new Date();
								Calendar c = Calendar.getInstance();
							    c.setTime(today);
							    c.add(Calendar.DATE, -1*config.getBackupDays());
								Date dateToCompare=c.getTime();
								if (checkFile.getName().startsWith(PREFIX_BACKUP_FILE))
								{
									String name=checkFile.getName();
									name=name.replace(PREFIX_BACKUP_FILE,"");
									name=name.substring(name.indexOf(SEPARATOR_BACKUP)+SEPARATOR_BACKUP.length());
									Date lastModifiedTime=null;
									try {
										lastModifiedTime = Util.backupDateTimeFormatter.parse(name);
									} catch (ParseException e) {
										log.error("Error extracting date from file",e);
									}
									
									if (lastModifiedTime!=null)
									{
										if (lastModifiedTime.before(dateToCompare))
										{
											filesToDelete.add(checkFile);
										}
									}
								}
							}
							log.info(filesToDelete.size()+" files to delete");
							for (File checkFile:filesToDelete)
							{
								log.info("\tDeleting file: "+checkFile.getName());
								FileUtils.deleteQuietly(checkFile);
							}
						}
					}
					
					List<String> scriptsToLoad = ExtractorContratos.main(null);
					
					boolean errorExecutingScripts=false;
					List<String> reports=new ArrayList<String>();
					for (String fileToLoad:scriptsToLoad)
					{
						//TODO guardar información de actualización, avisar si no se ejecuta nada						
						String report = dbFinalUtil.executeFile(fileToLoad);				
						if (report.startsWith(DataBaseFinalUtil.ERROR_DETECTED))
						{
							errorExecutingScripts=true;
							log.error(report);
							String textoError=report;
							serviceMail.enviar(Constants.MAIL_ASUNTO_PROCESO_ERROR, textoError);
							break;
						}
						else {
							//Si es "" es que no se hace nada
							if (report.equals("")==false)
							{
								reports.add(report);
							}
						}
					}
					
					if (errorExecutingScripts==false)
					{
					
						String texto="Carga periodica completa: sin cambios";
						if (reports.size()>0)
						{
							texto="Carga periodica completa:";
							for (String report:reports)
							{
								texto+="\n\t"+report;
							}
						}
						log.info("[main] "+texto);
						
						//Actualizamos el fichero last read
						String lastRun = Util.dateTimeFormatter.format(thisRunDate);
						String line = "lastRun=" + lastRun;
						Util.writeFile(new StringBuffer().append(line), Constants.FILE_LAST_RUN);
						
						//Solo actualiza si todo ha ido bien
						updateLogTable(texto);
												
						serviceMail.enviar(Constants.MAIL_ASUNTO_PROCESO_OK, texto);
					
					}
				}
			}
		}

		log.info("end");

	}

	private synchronized static void updateLogTable(String texto) {
		LogTable logTable=new LogTable();
		List<LogTable> lastLog = dbFinalUtil.queryObj("select * from contratos_log order by id desc", LogTable.class);
		if (lastLog.size()==0)
		{						
			logTable.setId(1);						
		}else {
			LogTable previouslogTable=lastLog.get(0);						
			int actualId=previouslogTable.getId()+1;
			logTable.setId(actualId);
		}
		
		
		logTable.setResume(texto);
		
		String logInsert="Insert into contratos_log values ("+logTable.getId()+","+Util.formatearFechaHoraSQLServer(Util.dateTimeFormatter.format(new Date()))+",'"+logTable.getResume()+"')";
		
		try {
			dbFinalUtil.execute(logInsert);
		} catch (Exception e) {
			log.error("Error adding data in log table",e);
		}
	}

}
