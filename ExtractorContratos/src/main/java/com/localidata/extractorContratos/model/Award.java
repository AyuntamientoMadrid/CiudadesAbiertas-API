package com.localidata.extractorContratos.model;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;

import com.localidata.extractorContratos.ExtractorContratos;
import com.localidata.extractorContratos.util.Constants;
import com.localidata.extractorContratos.util.ISqlGeneric;
import com.localidata.extractorContratos.util.Util;

public class Award implements ISqlGeneric{
	
	public static String tableName = "contratos_award";
	
	private String ikey = "";
	private String id ;
	private String description ;
	private Date awardDate = null;
	private Double valueAmount = Double.valueOf(0);
	private String isSupplierFor ;
	private Long lotId = Long.valueOf(0);

	
		
	public Award() {
		
	}

	public Award(String ikey, String id, String description, Date awardDate, String isSupplierFor) {
		super();
		this.ikey = ikey;
		this.id = id;
		this.description = description;
		this.awardDate = awardDate;
		this.isSupplierFor = isSupplierFor;

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

	public Date getAwardDate() {
		return awardDate;
	}

	public void setAwardDate(Date awardDate) {
		this.awardDate = awardDate;
	}
	
	public String getIsSupplierFor() {
		return isSupplierFor;
	}

	public void setIsSupplierFor(String isSupplierFor) {
		this.isSupplierFor = isSupplierFor;
	}

	public Double getValueAmount() {
		return valueAmount;
	}

	public void setValueAmount(Double valueAmount) {
		this.valueAmount = valueAmount;
	}

	public Long getLotId() {
		return lotId;
	}

	public void setLotId(Long lotId) {
		this.lotId = lotId;
	}
	
	public String getTableName() {
		return tableName;
	}

	@Override
	public String toString() {
		return "Award [ikey=" + ikey + ", id=" + id + ", description=" + description + ", awardDate=" + awardDate
				+ ", valueAmount=" + valueAmount + ", isSupplierFor=" + isSupplierFor + "]";
	}

	public static String toHeadCSVLine() {
		String result = "";
		
		result = "ikey"+ExtractorContratos.CSVSeparator+"id"+ExtractorContratos.CSVSeparator+"description"+ExtractorContratos.CSVSeparator+"award_date"+ExtractorContratos.CSVSeparator+"value_amount"+ExtractorContratos.CSVSeparator+"is_supplier_for";
		
		return result;
	}
	
	public String toCSVLine() {
		String result = "";
		
		result = this.ikey+ExtractorContratos.CSVSeparator+this.id+ExtractorContratos.CSVSeparator+ExtractorContratos.CSVTextSeparator+this.description+ExtractorContratos.CSVTextSeparator+ExtractorContratos.CSVSeparator+this.awardDate+ExtractorContratos.CSVSeparator+this.valueAmount+ExtractorContratos.CSVSeparator+this.isSupplierFor;
		
		return result;
	}
	
	public static String toFirstSQLLine() {
		String result = "";
		
		result = "INSERT INTO "+tableName+" (ikey, id, description, award_date, value_amount, is_supplier_for) VALUES";
		
		return result;
	}
	
	
	public String toLastSQLLine(String BBDD) {
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
		if(this.awardDate!=null) 
		{			
			if (BBDD.contentEquals(Constants.BBDD_SQL_SERVER))
			{
				result = result + Util.formatearFechaSQLServer(Util.dateFormatter.format(this.awardDate)) +ExtractorContratos.SQLSeparator;
			}
			else if(BBDD.contentEquals(Constants.BBDD_SQL_ORACLE)) {
				result = result + Util.formatearFechaOracle(Util.dateFormatter.format(this.awardDate),Constants.DATE_FORMAT_ORACLE) +ExtractorContratos.SQLSeparator;
			}
			//MYSQL, SQLITE Y POSTGRESQL FUNCIONA ASI
			else
			{
				result = result + ExtractorContratos.SQLTextSeparator+Util.dateFormatter.format(this.awardDate)+ExtractorContratos.SQLTextSeparator+ExtractorContratos.SQLSeparator;
			}			
		}else {
			result = result + this.awardDate+ExtractorContratos.SQLSeparator;
		}
		result = result + this.valueAmount+ExtractorContratos.SQLSeparator;
		if(this.isSupplierFor!=null) {
			result = result + ExtractorContratos.SQLTextSeparator+this.isSupplierFor+ExtractorContratos.SQLTextSeparator;
		}else if(this.isSupplierFor!="") {
			result = result + this.isSupplierFor;
		}else {
			result = result + null;
		}
		result = result + "),";
		
		return result;
	}
	
	public String getDelete() {
		String result = "DELETE from "+tableName+" WHERE id like '"+getId()+"'";
		
		return result;
	}
	
	public String generateInsert(String BBDD) {
		String lastSQL = toLastSQLLine(BBDD);
		String result = toFirstSQLLine()+lastSQL.substring(0, lastSQL.length()-1);
		
		return result;
	}
	
	public String generateUpdate(String BBDD) {		
		
		String updateSQL = "UPDATE "+tableName+" SET";	
		
	
		if (Util.validValue(description))
		{
			updateSQL+=" description = '"+description+"',";
		}
		
		if (Util.validValue(awardDate))
		{
			
			
			if (BBDD.contentEquals(Constants.BBDD_SQL_SERVER))
			{
				updateSQL+=" award_date = "+ Util.formatearFechaSQLServer(Util.dateFormatter.format(this.awardDate)) +",";
			}
			else if(BBDD.contentEquals(Constants.BBDD_SQL_ORACLE)) {
				updateSQL+=" award_date = "+ Util.formatearFechaOracle(Util.dateFormatter.format(this.awardDate),Constants.DATE_FORMAT_ORACLE) +",";
			}
			//MYSQL, SQLITE Y POSTGRESQL FUNCIONA ASI
			else
			{
				updateSQL+=" award_date = '"+Util.dateFormatter.format(awardDate)+"',";
			}
			
			
		}
		
		if (Util.validValue(valueAmount))
		{
			updateSQL+=" value_amount = "+valueAmount+",";
		}
		
		if (Util.validValue(isSupplierFor))
		{
			updateSQL+=" is_supplier_for = '"+isSupplierFor+"',";
		}
		
	
		if (updateSQL.endsWith(","))
		{
			updateSQL=StringUtils.chop(updateSQL);
		}
		
		updateSQL+=" WHERE ikey like '"+ikey+"'";
		
		return updateSQL;		
	}

	
}
