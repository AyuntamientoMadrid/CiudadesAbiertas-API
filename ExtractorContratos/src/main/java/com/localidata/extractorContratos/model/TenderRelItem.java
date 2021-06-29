/**
 * 
 */
package com.localidata.extractorContratos.model;

import org.apache.commons.lang3.StringUtils;

import com.localidata.extractorContratos.ExtractorContratos;
import com.localidata.extractorContratos.util.ISqlGeneric;
import com.localidata.extractorContratos.util.Util;

/**
 * @author Hugo
 *
 */
public class TenderRelItem implements ISqlGeneric {
	
	public static String tableName = "contratos_tender_rel_item";
	
	private String ikey = "";
	private String id = "";
	private String tender_id = "";
	private String item_id = "";
	
	/**
	 * 
	 */
	public TenderRelItem() {
		super();
	}

	/**
	 * @param ikey
	 * @param id
	 * @param tender_id
	 * @param item_id
	 */
	public TenderRelItem(String ikey, String id, String tender_id, String item_id) {
		super();
		this.ikey = ikey;
		this.id = id;
		this.tender_id = tender_id;
		this.item_id = item_id;
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

	public String getTender_id() {
		return tender_id;
	}

	public void setTender_id(String tender_id) {
		this.tender_id = tender_id;
	}

	public String getItem_id() {
		return item_id;
	}

	public void setItem_id(String item_id) {
		this.item_id = item_id;
	}
	
	public String getTableName() {
		return tableName;
	}

	@Override
	public String toString() {
		return "TenderRelItem [ikey=" + ikey + ", id=" + id + ", tender_id=" + tender_id + ", item_id=" + item_id + "]";
	}

	public static String toHeadCSVLine() {
		String result = "";
		
		result = "ikey"+ExtractorContratos.CSVSeparator+"id"+ExtractorContratos.CSVSeparator+"tender_id"+ExtractorContratos.CSVSeparator+"item_id";
		
		return result;
	}
	
	public String toCSVLine() {
		String result = "";
		
		result = this.ikey+ExtractorContratos.CSVSeparator+this.id+ExtractorContratos.CSVSeparator+this.tender_id+ExtractorContratos.CSVSeparator+this.item_id;
		
		return result;
	}
	
	
	public static String toFirstSQLLine() {
		String result = "";
		
		result = "INSERT INTO "+tableName+" (ikey, id, tender_id, item_id) VALUES";
		
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
		if(this.tender_id!=null) {
			result = result + ExtractorContratos.SQLTextSeparator+this.tender_id+ExtractorContratos.SQLTextSeparator+ExtractorContratos.SQLSeparator;
		}else {
			result = result + this.tender_id+ExtractorContratos.SQLSeparator;
		}
		if(this.item_id!=null) {
			result = result + ExtractorContratos.SQLTextSeparator+this.item_id+ExtractorContratos.SQLTextSeparator;
		}else {
			result = result + this.item_id;
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
		
	
		if (Util.validValue(item_id))
		{
			updateSQL+=" item_id = '"+item_id+"',";
		}
		
		if (Util.validValue(tender_id))
		{
			updateSQL+=" tender_id = '"+tender_id+"',";
		}
				
	
		if (updateSQL.endsWith(","))
		{
			updateSQL=StringUtils.chop(updateSQL);
		}
		
		updateSQL+=" WHERE ikey like '"+ikey+"'";
		
	
		
		return updateSQL;		
	}
}
