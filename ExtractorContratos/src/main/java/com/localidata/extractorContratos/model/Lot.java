package com.localidata.extractorContratos.model;

import org.apache.commons.lang3.StringUtils;

import com.localidata.extractorContratos.ExtractorContratos;
import com.localidata.extractorContratos.util.ISqlGeneric;
import com.localidata.extractorContratos.util.Util;

public class Lot  implements ISqlGeneric{
	
	public static String tableName = "contratos_lot";

	private String ikey = "";
	private String id = "";
	private String title = "";
	private String description = "";
	private String valueAmount = "";
	private String hasSupplier = "";
	private String tenderId = "";
	
	public Lot() {

	}
	
	public Lot(String ikey, String lotId, String title, String description, String valueAmount, String hasAward, String hasSupplier, String tenderId) {
		super();
		this.ikey = ikey;
		this.id = lotId;
		this.title = title;
		this.description = description;
		this.valueAmount = valueAmount;
		this.hasSupplier = hasSupplier;
		this.tenderId = tenderId;
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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getValueAmount() {
		return valueAmount;
	}

	public void setValueAmount(String valueAmount) {
		this.valueAmount = valueAmount;
	}

	

	public String getHasSupplier() {
		return hasSupplier;
	}

	public void setHasSupplier(String hasSupplier) {
		this.hasSupplier = hasSupplier;
	}

	public String getTenderId() {
		return tenderId;
	}

	public void setTenderId(String tenderId) {
		this.tenderId = tenderId;
	}
	
	public String getTableName() {
		return tableName;
	}
	
	@Override
	public String toString() {
		return "Lot [ikey=" + ikey + ", id=" + id + ", title=" + title + ", description=" + description
				+ ", valueAmount=" + valueAmount + ", hasSupplier=" + hasSupplier + ", tenderId=" + tenderId + "]";
	}

	public static String toHeadCSVLine() {
		String result = "";
		
		result = "ikey"+ExtractorContratos.CSVSeparator+"id"+ExtractorContratos.CSVSeparator+"description"+ExtractorContratos.CSVSeparator+"title"+ExtractorContratos.CSVSeparator+"value_amount"+ExtractorContratos.CSVSeparator+"has_supplier"+ExtractorContratos.CSVSeparator+"tender_id";
		
		return result;
	}
	
	public String toCSVLine() {
		String result = "";
		
		result = this.ikey+ExtractorContratos.CSVSeparator+this.id+ExtractorContratos.CSVSeparator+this.description+ExtractorContratos.CSVSeparator+ExtractorContratos.CSVTextSeparator+this.title+ExtractorContratos.CSVTextSeparator+ExtractorContratos.CSVSeparator+this.valueAmount+ExtractorContratos.CSVSeparator+this.hasSupplier+ExtractorContratos.CSVSeparator+this.tenderId;
		
		return result;
	}
	
	public static String toFirstSQLLine() {
		String result = "";
		
		result = "INSERT INTO "+tableName+" (ikey, id, title, description, tender_id, has_supplier, value_amount) VALUES";
		
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
		if(this.title!=null) {
			result = result + ExtractorContratos.SQLTextSeparator+this.title+ExtractorContratos.SQLTextSeparator+ExtractorContratos.SQLSeparator;
		}else {
			result = result + this.title+ExtractorContratos.SQLSeparator;
		}
		if(this.description!=null) {
			result = result + ExtractorContratos.SQLTextSeparator+this.description+ExtractorContratos.SQLTextSeparator+ExtractorContratos.SQLSeparator;
		}else {
			result = result + this.description+ExtractorContratos.SQLSeparator;
		}
		if(this.tenderId!=null) {
			result = result + ExtractorContratos.SQLTextSeparator+this.tenderId+ExtractorContratos.SQLTextSeparator+ExtractorContratos.SQLSeparator;
		}else {
			result = result + this.tenderId+ExtractorContratos.SQLSeparator;
		}
		if(Util.validValue(this.hasSupplier)) {
			result = result + ExtractorContratos.SQLTextSeparator+this.hasSupplier+ExtractorContratos.SQLTextSeparator+ExtractorContratos.SQLSeparator;
		}else {
			this.hasSupplier=null;
			result = result + this.hasSupplier+ExtractorContratos.SQLSeparator;
		}
		result = result + this.valueAmount;
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
		
		if (Util.validValue(hasSupplier))
		{
			updateSQL+=" has_supplier = '"+hasSupplier+"',";
		}
		
		if (Util.validValue(description))
		{
			updateSQL+=" description = "+description+",";
		}
		
		if (Util.validValue(tenderId))
		{
			updateSQL+=" tender_id = '"+tenderId+"',";
		}
		
		
		if (Util.validValue(valueAmount))
		{
			updateSQL+=" value_amount = "+valueAmount+",";
		}
		
		
	
		if (updateSQL.endsWith(","))
		{
			updateSQL=StringUtils.chop(updateSQL);
		}
		
		updateSQL+=" WHERE ikey like '"+ikey+"'";
		
	
		
		return updateSQL;		
	}
}
