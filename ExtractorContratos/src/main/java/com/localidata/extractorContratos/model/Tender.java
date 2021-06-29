package com.localidata.extractorContratos.model;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;

import com.localidata.extractorContratos.ExtractorContratos;
import com.localidata.extractorContratos.util.Constants;
import com.localidata.extractorContratos.util.ISqlGeneric;
import com.localidata.extractorContratos.util.Util;

public class Tender implements ISqlGeneric{
	
	public static String tableName = "contratos_tender";
	
	private String ikey = "";
	private String id;
	private String title;
	private String tenderStatus;
	private String mainProcCategory;
	private String additionalProcCategory;
	private Long numberOfTenderers;
	private String procurementMethod;
	private String procurementMethodDetails;
	private Integer periodDurationInDays;
	private Date periodStartDate;
	private Date periodEndDate;
	private BigDecimal valueAmount;
	private String hasSupplier;
	
	public Tender() {
		super();
	}

	public Tender(String ikey, String tenderId, String title, String tenderStatus, String mainProcurementCategory,
			Long numberOfTenderers, String procurementMethod, String procurementMethodDetails,
			Integer periodDurationInDays, Date periodStartDate, Date periodEndDate, BigDecimal valueAmount) {
		super();
		this.ikey = ikey;
		this.id = tenderId;
		this.title = title;
		this.tenderStatus = tenderStatus;
		this.mainProcCategory = mainProcurementCategory;
		this.numberOfTenderers = numberOfTenderers;
		this.procurementMethod = procurementMethod;
		this.procurementMethodDetails = procurementMethodDetails;
		this.periodDurationInDays = periodDurationInDays;
		this.periodStartDate = periodStartDate;
		this.periodEndDate = periodEndDate;
		this.valueAmount = valueAmount;
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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTenderStatus() {
		return tenderStatus;
	}

	public void setTenderStatus(String tenderStatus) {
		this.tenderStatus = tenderStatus;
	}

	public String getMainProcCategory() {
		return mainProcCategory;
	}

	public void setMainProcCategory(String mainProcCategory) {
		this.mainProcCategory = mainProcCategory;
	}

	public Long getNumberOfTenderers() {
		return numberOfTenderers;
	}

	public void setNumberOfTenderers(Long numberOfTenderers) {
		this.numberOfTenderers = numberOfTenderers;
	}

	public String getProcurementMethod() {
		return procurementMethod;
	}

	public void setProcurementMethod(String procurementMethod) {
		this.procurementMethod = procurementMethod;
	}

	public String getProcurementMethodDetails() {
		return procurementMethodDetails;
	}

	public void setProcurementMethodDetails(String procurementMethodDetails) {
		this.procurementMethodDetails = procurementMethodDetails;
	}

	public Integer getPeriodDurationInDays() {
		return periodDurationInDays;
	}

	public void setPeriodDurationInDays(Integer periodDurationInDays) {
		this.periodDurationInDays = periodDurationInDays;
	}

	public Date getPeriodStartDate() {
		return periodStartDate;
	}

	public void setPeriodStartDate(Date periodStartDate) {
		this.periodStartDate = periodStartDate;
	}
		
	public Date getPeriodEndDate() {
		return periodEndDate;
	}

	public void setPeriodEndDate(Date periodEndDate) {
		this.periodEndDate = periodEndDate;
	}

	public BigDecimal getValueAmount() {
		return valueAmount;
	}

	public void setValueAmount(BigDecimal valueAmount) {
		this.valueAmount = valueAmount;
	}
	
	public String getAdditionalProcCategory() {
		return additionalProcCategory;
	}

	public void setAdditionalProcCategory(String additionalProcCategory) {
		this.additionalProcCategory = additionalProcCategory;
	}

	public String getHasSupplier() {
		return hasSupplier;
	}

	public void setHasSupplier(String hasSupplier) {
		this.hasSupplier = hasSupplier;
	}
	
	public String getTableName() {
		return tableName;
	}

	@Override
	public String toString() {
		return "Tender [ikey=" + ikey + ", id=" + id + ", title=" + title + ", tenderStatus=" + tenderStatus
				+ ", mainProcCategory=" + mainProcCategory + ", additionalProcCategory=" + additionalProcCategory
				+ ", numberOfTenderers=" + numberOfTenderers + ", procurementMethod=" + procurementMethod
				+ ", procurementMethodDetails=" + procurementMethodDetails + ", periodDurationInDays="
				+ periodDurationInDays + ", periodStartDate=" + periodStartDate + ", periodEndDate=" + periodEndDate
				+ ", valueAmount=" + valueAmount + ", hasSupplier=" + hasSupplier + "]";
	}

	public static String toHeadCSVLine() {
		String result = "";
		
		result = "ikey"+ExtractorContratos.CSVSeparator+"id"+ExtractorContratos.CSVSeparator+"title"+ExtractorContratos.CSVSeparator+"tender_status"+ExtractorContratos.CSVSeparator+"main_proc_category"+ExtractorContratos.CSVSeparator+"number_of_tenderers"+ExtractorContratos.CSVSeparator+"proc_method"+ExtractorContratos.CSVSeparator+"proc_method_details"+ExtractorContratos.CSVSeparator+"period_duration_in_days"+ExtractorContratos.CSVSeparator+"period_start_date"+ExtractorContratos.CSVSeparator+"period_end_date"+ExtractorContratos.CSVSeparator+"value_amount"+ExtractorContratos.CSVSeparator+"has_supplier";
		
		return result;
	}
	
	public String toCSVLine() {
		String result = "";
		
		result = this.ikey+ExtractorContratos.CSVSeparator+this.id+ExtractorContratos.CSVSeparator+ExtractorContratos.CSVTextSeparator+this.title+ExtractorContratos.CSVTextSeparator+ExtractorContratos.CSVSeparator+this.tenderStatus+ExtractorContratos.CSVSeparator+this.mainProcCategory+ExtractorContratos.CSVSeparator+this.numberOfTenderers+ExtractorContratos.CSVSeparator+this.procurementMethod+ExtractorContratos.CSVSeparator+this.procurementMethodDetails+ExtractorContratos.CSVSeparator+this.periodDurationInDays+ExtractorContratos.CSVSeparator+this.periodStartDate+ExtractorContratos.CSVSeparator+this.periodEndDate+ExtractorContratos.CSVSeparator+this.valueAmount+ExtractorContratos.CSVSeparator+this.hasSupplier;
		
		return result;
	}
	
	public static String toFirstSQLLine() {
		String result = "";
		
		result = "INSERT INTO "+tableName+" (ikey, id, title, has_supplier, main_proc_category, additional_proc_category, number_of_tenderers, proc_method, proc_method_details, tender_status, period_duration_in_days, period_end_date, period_start_date, value_amount) VALUES";
		
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
		if(this.title!=null) {
			if(this.title.length()>399)
				this.title = this.title.substring(0, 398);
			result = result + ExtractorContratos.SQLTextSeparator+this.title+ExtractorContratos.SQLTextSeparator+ExtractorContratos.SQLSeparator;
		}else {
			if(this.title.length()>399)
				this.title = this.title.substring(0, 398);
			result = result + this.title+ExtractorContratos.SQLSeparator;
		}
		if(this.hasSupplier!=null) {
			result = result + ExtractorContratos.SQLTextSeparator+this.hasSupplier+ExtractorContratos.SQLTextSeparator+ExtractorContratos.SQLSeparator;
		}else {
			result = result + this.hasSupplier+ExtractorContratos.SQLSeparator;
		}
		if(this.mainProcCategory!=null) {
			result = result + ExtractorContratos.SQLTextSeparator+this.mainProcCategory+ExtractorContratos.SQLTextSeparator+ExtractorContratos.SQLSeparator;
		}else {
			result = result + this.mainProcCategory+ExtractorContratos.SQLSeparator;
		}
		if(this.additionalProcCategory!=null) {
			result = result + ExtractorContratos.SQLTextSeparator+this.additionalProcCategory+ExtractorContratos.SQLTextSeparator+ExtractorContratos.SQLSeparator;
		}else {
			result = result + this.additionalProcCategory+ExtractorContratos.SQLSeparator;
		}
		result = result + this.numberOfTenderers+ExtractorContratos.SQLSeparator;
		if(this.procurementMethod!=null) {
			result = result + ExtractorContratos.SQLTextSeparator+this.procurementMethod+ExtractorContratos.SQLTextSeparator+ExtractorContratos.SQLSeparator;
		}else {
			result = result + this.procurementMethod+ExtractorContratos.SQLSeparator;
		}
		if(this.procurementMethodDetails!=null) {
			result = result + ExtractorContratos.SQLTextSeparator+this.procurementMethodDetails+ExtractorContratos.SQLTextSeparator+ExtractorContratos.SQLSeparator;
		}else {
			result = result + this.procurementMethodDetails+ExtractorContratos.SQLSeparator;
		}
		if(this.tenderStatus!=null) {
			result = result + ExtractorContratos.SQLTextSeparator+this.tenderStatus+ExtractorContratos.SQLTextSeparator+ExtractorContratos.SQLSeparator;
		}else {
			result = result + this.tenderStatus+ExtractorContratos.SQLSeparator;
		}
		result = result + this.periodDurationInDays+ExtractorContratos.SQLSeparator;
		if(this.periodEndDate!=null) {
			if (BBDD.contentEquals(Constants.BBDD_SQL_SERVER))
			{
				result = result + Util.formatearFechaHoraSQLServer(Util.dateTimeFormatter.format(this.periodEndDate)) +ExtractorContratos.SQLSeparator;
				
			}
			else if(BBDD.contentEquals(Constants.BBDD_SQL_ORACLE)) {
				result = result + Util.formatearFechaOracle(Util.dateTimeFormatterWithoutT.format(this.periodEndDate),Constants.DATE_TIME_FORMAT_ORACLE) +ExtractorContratos.SQLSeparator;
			}
			//MYSQL, SQLITE Y POSTGRESQL FUNCIONA ASI
			else
			{
				result = result + ExtractorContratos.SQLTextSeparator+Util.dateTimeFormatter.format(this.periodEndDate)+ExtractorContratos.SQLTextSeparator+ExtractorContratos.SQLSeparator;
				
			}	
		}else {
			result = result + this.periodEndDate+ExtractorContratos.SQLSeparator;
		}
		if(this.periodStartDate!=null) {
			if (BBDD.contentEquals(Constants.BBDD_SQL_SERVER))
			{
				result = result + Util.formatearFechaHoraSQLServer(Util.dateTimeFormatter.format(this.periodStartDate)) +ExtractorContratos.SQLSeparator;
				
			}
			else if(BBDD.contentEquals(Constants.BBDD_SQL_ORACLE)) {
				result = result + Util.formatearFechaOracle(Util.dateTimeFormatterWithoutT.format(this.periodStartDate),Constants.DATE_TIME_FORMAT_ORACLE) +ExtractorContratos.SQLSeparator;
			}
			//MYSQL, SQLITE Y POSTGRESQL FUNCIONA ASI
			else
			{
				result = result + ExtractorContratos.SQLTextSeparator+Util.dateTimeFormatter.format(this.periodStartDate)+ExtractorContratos.SQLTextSeparator+ExtractorContratos.SQLSeparator;
				
			}	
		}else {
			result = result + this.periodStartDate+ExtractorContratos.SQLSeparator;
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
		String lastSQL = toLastSQLLine(BBDD);
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
		
		if (Util.validValue(mainProcCategory))
		{
			updateSQL+=" main_proc_category = "+mainProcCategory+",";
		}
		
		if (Util.validValue(additionalProcCategory))
		{
			updateSQL+=" additional_proc_category = '"+additionalProcCategory+"',";
		}
		
		if (Util.validValue(procurementMethod))
		{
			updateSQL+=" proc_method = '"+procurementMethod+"',";
		}
		
		if (Util.validValue(procurementMethodDetails))
		{
			updateSQL+=" proc_method_details = '"+procurementMethodDetails+"',";
		}
		
		if (Util.validValue(tenderStatus))
		{
			updateSQL+=" tender_status = '"+tenderStatus+"',";
		}
		
		//CMG la fecha startEnd no se actualiza solo se inserta.
//		if (Util.validValue(periodStartDate))
//		{
//			updateSQL+=" period_start_date = '"+Util.dateTimeFormatter.format(periodStartDate)+"',";
//		}
		
		if (Util.validValue(periodEndDate))
		{
			if (BBDD.contentEquals(Constants.BBDD_SQL_SERVER))
			{
				updateSQL+=" period_end_date = "+ Util.formatearFechaHoraSQLServer(Util.dateTimeFormatter.format(this.periodEndDate)) +",";
			}
			else if(BBDD.contentEquals(Constants.BBDD_SQL_ORACLE)) {
				updateSQL+=" period_end_date = "+ Util.formatearFechaOracle(Util.dateTimeFormatterWithoutT.format(this.periodEndDate),Constants.DATE_TIME_FORMAT_ORACLE) +",";
			}
			//MYSQL, SQLITE Y POSTGRESQL FUNCIONA ASI
			else
			{
				updateSQL+=" period_end_date = '"+Util.dateTimeFormatter.format(periodEndDate)+"',";
			}
		}
		
		if (Util.validValue(periodDurationInDays))
		{
			updateSQL+=" period_duration_in_days = "+periodDurationInDays+",";
		}
		
		
	
		if (updateSQL.endsWith(","))
		{
			updateSQL=StringUtils.chop(updateSQL);
		}
		
		updateSQL+=" WHERE ikey like '"+ikey+"'";
		
	
		
		return updateSQL;		
	}
}
