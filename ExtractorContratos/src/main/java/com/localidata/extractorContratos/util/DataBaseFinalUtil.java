package com.localidata.extractorContratos.util;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import com.microsoft.sqlserver.jdbc.SQLServerDriver;

public class DataBaseFinalUtil {

	

	public static final String ERROR_DETECTED = "ERROR_DETECTED: ";

	private static Logger log = Logger.getLogger(DataBaseFinalUtil.class);

	public static String DB_URL = "";
	public static String DB_USER = "user";
	public static String DB_PASSWORD = "pass";
	public static String DB_SCHEMA = "dbo";
	
	private static String SQLSERVER_DRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";  

	static BasicDataSource basicDataSource;

	private Connection connect() {
		
		try {
			Class.forName(SQLSERVER_DRIVER);
		} catch (ClassNotFoundException e1) {
			log.error("Driver not found",e1);
		}
		
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(DB_URL + ";currentSchema=" + DB_SCHEMA, DB_USER, DB_PASSWORD);
		} catch (SQLException e) {
			log.error("Error in connect", e);
			e.printStackTrace();
		}
		return conn;
	}
	
	public void execute(String query) throws Exception
	{
		List<String> queries=new ArrayList<String>();
		queries.add(query);
		executeList(queries);
	}

	public void executeList(List<String> queries) throws Exception {
		ResultSet rs = null;
		Connection conn = null;
		PreparedStatement preparedStatement = null;

		int queryNum = 0;

		try {
			// connect to the database
			conn = this.connect();
			if (conn != null)
			{
				// set auto-commit mode to false
				conn.setAutoCommit(false);
	
				for (String query : queries) {
					queryNum++;
					preparedStatement = conn.prepareStatement(query);
					preparedStatement.executeUpdate();
				}
	
				// commit work
				conn.commit();
	
				log.info("[executeList] [size:" + queries.size() + " updates]");
			}else {
				throw new Exception("Connection is null");
			}

		} catch (Exception e1) {
			if (queryNum==0)
			{
				queryNum=1;
			}
			log.error("Error in line: " + queryNum);
			log.error(queries.get(queryNum - 1), e1);

			try {
				if (conn != null) {
					conn.rollback();
				}
			} catch (SQLException e2) {
				log.error(e2.getMessage());
			}

			throw e1;

		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (preparedStatement != null) {
					preparedStatement.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (Exception e3) {
				log.error(e3.getMessage());
			}
		}

	}

	public String executeFile(String fileToLoad) {
		String status="";
		List<String> lines = new ArrayList<String>();
		try {
			lines = FileUtils.readLines(new File(fileToLoad), "utf8");
		} catch (IOException e) {
			log.error("Error reading script lines", e);
		}
		if (lines.size() > 0) {
			int numInserts=0;
			int numUpdates=0;
			for (int i=0;i<lines.size();i++)
			{
				String line=lines.get(i);
				if (line.toLowerCase().startsWith("insert"))
				{
					numInserts++;
				}else if (line.toLowerCase().startsWith("update")) {
					numUpdates++;
				}else {
					status=ERROR_DETECTED+"line: "+(i+1)+" query: "+line;
					return status;
				}
			}
			status=numInserts+" inserciones y "+numUpdates+" actualizaciones ";
			log.info(lines.size() + " queries to update");
			try {
				executeList(lines);
				status+="ejecutadas correctamente.";
			} catch (Exception e) {
				log.error("Error executing script lines", e);
				status=ERROR_DETECTED+" "+e.getMessage();
			}
		}
		return status;
	}
	
	public <T> List<T> queryObj(String queryText, Class<T> typeParameterClass) {
		
		
		
		 if (basicDataSource == null) {
			basicDataSource = new BasicDataSource();
			//basicDataSource.setUrl(DB_URL + ";currentSchema=" + DB_SCHEMA);
			basicDataSource.setUrl(DB_URL);
			basicDataSource.setUsername(DB_USER);
			basicDataSource.setPassword(DB_PASSWORD);
			basicDataSource.setDefaultSchema(DB_SCHEMA);
			basicDataSource.setDriverClassName(SQLSERVER_DRIVER);
			
		  }
		  QueryRunner run = new QueryRunner(basicDataSource);

		  List<T> results = new ArrayList<T>();

		  ResultSetHandler<List<T>> h = new BeanListHandler<T>(typeParameterClass);

		  try {
			results = run.query(queryText, h);
		  } catch (SQLException e) {
			log.error("Error obtaining data", e);
		  }

		  if (basicDataSource != null) {
			try {
			  basicDataSource.close();
			} catch (SQLException e) {
			  log.error("Error closing datasource", e);
			}
		  }

		  return results;

		}

	public static void main(String[] args) {

		PropertyConfigurator.configure("log4j.properties");

		DataBaseFinalUtil dbUtil = new DataBaseFinalUtil();
		DataBaseFinalUtil.DB_URL = "jdbc:sqlserver://localhost:1433;databaseName=ciudadesAbiertas";
		DataBaseFinalUtil.DB_USER = "ciudadesAbiertas";
		DataBaseFinalUtil.DB_PASSWORD = "Primera1";
		DataBaseFinalUtil.DB_SCHEMA = "ciudadesAbiertas";

		dbUtil.executeFile(
				"D:\\git\\CiudadesAbiertas\\ExtractorContratos\\output_periodic\\alcobendasMayoPeriodico.sql");

		log.info("End");

	}// end main

}
