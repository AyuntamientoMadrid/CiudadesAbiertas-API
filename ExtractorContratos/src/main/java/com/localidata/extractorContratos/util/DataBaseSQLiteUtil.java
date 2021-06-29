package com.localidata.extractorContratos.util;

import java.io.File;
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
import org.apache.log4j.Logger;

import com.localidata.extractorContratos.model.Item;

public class DataBaseSQLiteUtil {

private static Logger log = Logger.getLogger(DataBaseSQLiteUtil.class);

public static String DB_URL = "jdbc:sqlite:database.db";

static BasicDataSource basicDataSource;

private Connection connect() {

  // SQLite connection string
  String url = DB_URL;
  Connection conn = null;
  try {
	conn = DriverManager.getConnection(url);
  } catch (SQLException e) {
	log.error(e.getMessage());
  }
  return conn;
}

public boolean exiteIdInTable(String tableName, String id) {
	String sql = "SELECT id FROM " + tableName + " where id='" + id + "'";
	String result = null;
	try {
		Connection conn = this.connect();
		PreparedStatement stmt = conn.prepareStatement(sql);
		ResultSet rs = stmt.executeQuery();

		result = rs.getString("id");

		if (rs != null) {
			rs.close();
		}
		if (stmt != null) {
			stmt.close();
		}
		if (conn != null) {
			conn.close();
		}

	} catch (SQLException e) {
		log.error(e.getMessage());
	}
	return result==id;
}


public List<String> getIdentifiersFromTable(String tableName)
{
	List<String> identifierList=new ArrayList<String>();
	
	String sql = "SELECT distinct id FROM "+tableName;
	
	try 
	  {
	   Connection conn = this.connect(); 
	   PreparedStatement stmt = conn.prepareStatement(sql); 
	   ResultSet rs = stmt.executeQuery();
	// loop through the result set
	while (rs.next()) {
	  identifierList.add(rs.getString("id"));
	}
	
	if (rs != null) {
		rs.close();
	  }
	  if (stmt != null) {
		stmt.close();
	  }
	  if (conn != null) {
		conn.close();
	  }

} catch (SQLException e) {
	log.error(e.getMessage());
}
	return identifierList;
}

public void selectAll() {
  String sql = "SELECT id, title FROM contratos_process";

  try 
	  {
	   Connection conn = this.connect(); 
	   PreparedStatement stmt = conn.prepareStatement(sql); 
	   ResultSet rs = stmt.executeQuery();
	// loop through the result set
	while (rs.next()) {
	  log.info(rs.getString("id") + "\t" + rs.getString("title"));
	}
	
	if (rs != null) {
		rs.close();
	  }
	  if (stmt != null) {
		stmt.close();
	  }
	  if (conn != null) {
		conn.close();
	  }

  } catch (SQLException e) {
	log.error(e.getMessage());
  }
}




public void insertOne(String insertSQL) throws SQLException {

  try {
	Connection conn = this.connect();
	PreparedStatement pstmt = conn.prepareStatement(insertSQL);
	pstmt.executeUpdate();

	
	  if (pstmt != null) {
		pstmt.close();
	  }
	  if (conn != null) {
		conn.close();
	  }

  } catch (SQLException e) {
	log.error(e.getMessage());
	throw e;
  }
}

public void executeList(List<String> queries) throws SQLException {
  ResultSet rs = null;
  Connection conn = null;
  PreparedStatement preparedStatement = null;

  int queryNum = 0;

  try {
	// connect to the database
	conn = this.connect();
	if (conn == null)
	  return;

	// set auto-commit mode to false
	conn.setAutoCommit(false);

	for (String query : queries) {
	  preparedStatement = conn.prepareStatement(query);
	  preparedStatement.executeUpdate();
	  queryNum++;
	}

	// commit work
	conn.commit();

	log.info("[executeList] [size:"+ queries.size() + " updates]");

  } catch (SQLException e1) {
	log.error("Error in query: " + queries.get(queryNum), e1);

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
	} catch (SQLException e3) {
	  log.error(e3.getMessage());
	}
  }

}

public <T> List<T> queryObj(String queryText, Class<T> typeParameterClass) {
  if (basicDataSource == null) {
	basicDataSource = new BasicDataSource();
	basicDataSource.setUrl(DB_URL);
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

public File getFileDB()
{
	File dbFile=null;
	
	String fileName=DB_URL;
	if (fileName!=null)
	{
		fileName=fileName.replace("jdbc:sqlite:","");
		dbFile=new File(fileName);
		if (dbFile.exists())
		{
			return dbFile;
		}
		dbFile=null;
	}

	
	return dbFile;
}

public static void main(String[] args) {

  DataBaseSQLiteUtil dbUtil = new DataBaseSQLiteUtil();
  DataBaseSQLiteUtil.DB_URL = "jdbc:sqlite:C://sqlite/db/ciudadesAbiertas.db";

  dbUtil.selectAll();

  String sql = "INSERT INTO contratos_item(ikey,id,description,has_classification) VALUES('IT5', 'IT5', 'Artículos para amolar', 'C1')";
  try {
	dbUtil.insertOne(sql);
} catch (SQLException e1) {
	// TODO Auto-generated catch block
	e1.printStackTrace();
}

  List<String> updates = new ArrayList<String>();

  updates.add("INSERT INTO contratos_item(ikey,id,description,has_classification) VALUES('IT6', 'IT6', 'Vehículos diesel', 'C1')");
  updates.add("INSERT INTO contratos_item(ikey,id,description,has_classification) VALUES('IT7', 'IT7', 'Vehículos eléctricos', 'C1')");
  updates.add("INSERT INTO contratos_item(ikey,id,description,has_classification) VALUES('IT8', 'IT8', 'Farolas leds', 'C1')");

  try {
	dbUtil.executeList(updates);
} catch (SQLException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}

  updates = new ArrayList<String>();
  updates.add("INSERT INTO contratos_item(ikey,id,description,has_classification) VALUES('IT9', 'IT9', 'Papeleras', 'C1')");
  updates.add("INSERT INTO contratos_item(ikey,id,description,has_classification) VALUES('IT6', 'IT6', 'Vehículos diesel', 'C1')");

  try {
	dbUtil.executeList(updates);
} catch (SQLException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}

  updates = new ArrayList<String>();
  updates.add("DELETE from contratos_item WHERE id like 'IT8'");
  updates.add("DELETE from contratos_item WHERE id like 'IT7'");
  updates.add("DELETE from contratos_item WHERE id like 'IT6'");
  updates.add("DELETE from contratos_item WHERE id like 'IT5'");

  try {
	dbUtil.executeList(updates);
} catch (SQLException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}

  List<Item> list = dbUtil.queryObj("SELECT * FROM contratos_item", Item.class);

  for (Item item : list) {
	log.info(item);
  }

  log.info(list.size());

  log.info("End");

}// end main

}
