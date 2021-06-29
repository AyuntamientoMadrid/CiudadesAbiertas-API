package com.localidata.extractorContratos.model;

import org.apache.commons.lang3.StringUtils;

import com.localidata.extractorContratos.ExtractorContratos;
import com.localidata.extractorContratos.util.ISqlGeneric;
import com.localidata.extractorContratos.util.Util;

public class ContractingProcess  implements ISqlGeneric{
	
	public static String tableName = "contratos_process";
	
	private String ikey = "";
	private String id = "";
	private String identifier = "";
	private String title = "";
	private String description = "";
	private String URL = "";
	private String hasTender = "";
	private String isBuyerFor = "";
	
	
	public ContractingProcess() {
		super();
	}

	/**
	 * @param ikey
	 * @param id
	 * @param title
	 * @param description
	 * @param hasTender
	 */
	public ContractingProcess(String ikey, String identifier, String id, String title, String description, String hasTender, String isBuyerFor) {
		super();
		this.ikey = ikey;
		this.id = id;
		this.identifier = identifier;
		this.title = title;
		this.description = description;
		this.hasTender = hasTender;
		this.isBuyerFor = isBuyerFor;
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

	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}	
	
	public String getURL() {
		return URL;
	}

	public void setURL(String uRL) {
		URL = uRL;
	}

	public String getHasTender() {
		return hasTender;
	}

	public void setHasTender(String hasTender) {
		this.hasTender = hasTender;
	}

	public String getIsBuyerFor() {
		return isBuyerFor;
	}

	public void setIsBuyerFor(String isBuyerFor) {
		this.isBuyerFor = isBuyerFor;
	}
	
	public String getTableName() {
		return tableName;
	}

	@Override
	public String toString() {
		return "ContractingProcess [ikey=" + ikey + ", id=" + id + ", identifier=" + identifier + ", title=" + title
				+ ", description=" + description + ", URL=" + URL + ", hasTender=" + hasTender + ", isBuyerFor="
				+ isBuyerFor + "]";
	}

	public static String toHeadCSVLine() {
		String result = "";
		
		result = "ikey"+ExtractorContratos.CSVSeparator+"id"+ExtractorContratos.CSVSeparator+"identifier"+ExtractorContratos.CSVSeparator+"title"+ExtractorContratos.CSVSeparator+"description"+ExtractorContratos.CSVSeparator+"has_tender"+ExtractorContratos.CSVSeparator+"is_buyer_for"+ExtractorContratos.CSVSeparator+"URL";
		
		return result;
	}
	
	public String toCSVLine() {
		String result = "";
		
		result = this.ikey+ExtractorContratos.CSVSeparator+this.id+ExtractorContratos.CSVSeparator+this.identifier+ExtractorContratos.CSVSeparator+ExtractorContratos.CSVTextSeparator+this.title+ExtractorContratos.CSVTextSeparator+ExtractorContratos.CSVSeparator+ExtractorContratos.CSVTextSeparator+this.description+ExtractorContratos.CSVTextSeparator+ExtractorContratos.CSVSeparator+this.hasTender+ExtractorContratos.CSVSeparator+this.isBuyerFor+ExtractorContratos.CSVSeparator+this.URL;
		
		return result;
	}
	
	public static String toFirstSQLLine() {
		String result = "";
		
		result = "INSERT INTO "+tableName+" (ikey, id, identifier, title, URL, description, has_tender, is_buyer_for) VALUES";
		
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
		if(this.identifier!=null) {
			result = result + ExtractorContratos.SQLTextSeparator+this.identifier+ExtractorContratos.SQLTextSeparator+ExtractorContratos.SQLSeparator;
		}else {
			result = result + this.identifier+ExtractorContratos.SQLSeparator;
		}
		if(this.title!=null) {
			if(this.title.length()>399)
				this.title = this.title.substring(0, 398);
			result = result + ExtractorContratos.SQLTextSeparator+this.title+ExtractorContratos.SQLTextSeparator+ExtractorContratos.SQLSeparator;
		}else {
			if(this.title.length()>399)
				this.title = this.title.substring(0, 398);
			result = result + this.title+ExtractorContratos.SQLSeparator;
		}
		if(this.URL!=null) {
			result = result + ExtractorContratos.SQLTextSeparator+this.URL+ExtractorContratos.SQLTextSeparator+ExtractorContratos.SQLSeparator;
		}else {
			result = result + this.URL+ExtractorContratos.SQLSeparator;
		}
		if(this.description!=null) {
			result = result + ExtractorContratos.SQLTextSeparator+this.description+ExtractorContratos.SQLTextSeparator+ExtractorContratos.SQLSeparator;
		}else {
			result = result + this.description+ExtractorContratos.SQLSeparator;
		}
		if(this.hasTender!=null) {
			result = result + ExtractorContratos.SQLTextSeparator+this.hasTender+ExtractorContratos.SQLTextSeparator+ExtractorContratos.SQLSeparator;
		}else {
			result = result + this.hasTender+ExtractorContratos.SQLSeparator;
		}
		if(this.isBuyerFor!=null) {
			result = result + ExtractorContratos.SQLTextSeparator+this.isBuyerFor + ExtractorContratos.SQLTextSeparator;
		}else {
			result = result + this.isBuyerFor;
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
		
	
		if (Util.validValue(title))
		{
			updateSQL+=" title = '"+title+"',";
		}
		
		if (Util.validValue(description))
		{
			updateSQL+=" description = '"+description+"',";
		}
		
		if (Util.validValue(URL))
		{
			updateSQL+=" url = '"+URL+"',";
		}
		
		if (Util.validValue(hasTender))
		{
			updateSQL+=" has_tender = '"+hasTender+"',";
		}
		
		if (Util.validValue(isBuyerFor))
		{
			updateSQL+=" is_buyer_for = '"+isBuyerFor+"',";
		}
		
		
		
	
		if (updateSQL.endsWith(","))
		{
			updateSQL=StringUtils.chop(updateSQL);
		}
		
		updateSQL+=" WHERE ikey like '"+ikey+"'";
		
	
		
		return updateSQL;		
	}
	
}
