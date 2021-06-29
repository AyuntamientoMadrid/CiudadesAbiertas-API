package com.localidata.extractorContratos.model;

import org.apache.commons.lang3.StringUtils;

import com.localidata.extractorContratos.ExtractorContratos;
import com.localidata.extractorContratos.util.ISqlGeneric;
import com.localidata.extractorContratos.util.Util;

public class Item implements ISqlGeneric{
	
	public static String tableName = "contratos_item";
	
	private String ikey = "";
	private String id = "";
	private String description = "";
	private String has_classification = "";
	
	public Item() {
		
	}

	public Item(String ikey, String itemId, String description, String has_classification) {
		super();
		this.ikey = ikey;
		this.id = itemId;
		this.description = description;
		this.has_classification = has_classification;
	}
	
	public String getIkey() {
		return ikey;
	}

	public void setIkey(String ikey) {
		this.ikey = ikey;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getHas_classification() {
		return has_classification;
	}

	public void setHas_classification(String has_classification) {
		this.has_classification = has_classification;
	}
	
	public String getTableName() {
		return tableName;
	}
	
	@Override
	public String toString() {
		return "Item [ikey=" + ikey + ", id=" + id + ", description=" + description + ", has_classification="
				+ has_classification + "]";
	}

	public static String toHeadCSVLine() {
		String result = "";
		
		result = "ikey"+ExtractorContratos.CSVSeparator+"id"+ExtractorContratos.CSVSeparator+"description"+ExtractorContratos.CSVSeparator+"has_classification";
		
		return result;
	}
	
	public String toCSVLine() {
		String result = "";
		
		result = this.ikey+ExtractorContratos.CSVSeparator+this.id+ExtractorContratos.CSVSeparator+this.description+ExtractorContratos.CSVSeparator+this.has_classification;
		
		return result;
	}
	
	public static String toFirstSQLLine() {
		String result = "";
		
		result = "INSERT INTO "+tableName+" (ikey, id, description, has_classification) VALUES";
		
		return result;
	}
	
	
	
	public String toLastSQLLine() {
		String result = "";
		
		result = "(";
		if(this.ikey!=null) {
			result = result + ExtractorContratos.SQLTextSeparator+this.ikey+ExtractorContratos.SQLTextSeparator+ExtractorContratos.SQLSeparator;
		}else {
			result = result + this.ikey+ExtractorContratos.SQLSeparator;
		}
		if(this.id!=null) {
			result = result + ExtractorContratos.SQLTextSeparator+this.id+ExtractorContratos.SQLTextSeparator+ExtractorContratos.SQLSeparator;
		}else {
			result = result + this.id+ExtractorContratos.SQLSeparator;
		}
		if(this.description!=null) {
			result = result + ExtractorContratos.SQLTextSeparator+this.description+ExtractorContratos.SQLTextSeparator+ExtractorContratos.SQLSeparator;
		}else {
			result = result + this.description+ExtractorContratos.SQLSeparator;
		}
		if(this.has_classification!=null) {
			result = result + ExtractorContratos.SQLTextSeparator+this.has_classification+ExtractorContratos.SQLTextSeparator;
		}else {
			result = result + this.has_classification;
		}
		
		result = result + "),";
		
		return result;
	}
	
	public String getDelete() {
		String result = "DELETE from "+tableName+" WHERE id like '"+getId()+"'";
		
		return result;
	}
	
	public String generateInsert(String BBDD) {
		String lastSQL = toLastSQLLine();
		String result = toFirstSQLLine()+lastSQL.substring(0, lastSQL.length()-1);
		
		return result;
	}
	
public String generateUpdate(String BBDD) {		
		
		String updateSQL = "UPDATE "+tableName+" SET";	
		
	
		if (Util.validValue(description))
		{
			updateSQL+=" description = '"+description+"',";
		}
		
		if (Util.validValue(id))
		{
			updateSQL+=" id = '"+id+"',";
		}
		
		if (Util.validValue(has_classification))
		{
			updateSQL+=" value_amount = '"+has_classification+"',";
		}
		
	
		if (updateSQL.endsWith(","))
		{
			updateSQL=StringUtils.chop(updateSQL);
		}
		
		updateSQL+=" WHERE ikey like '"+ikey+"'";
		
		return updateSQL;		
	}
}
