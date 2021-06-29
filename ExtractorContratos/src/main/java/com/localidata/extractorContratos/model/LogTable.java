package com.localidata.extractorContratos.model;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;

import com.localidata.extractorContratos.ExtractorContratos;
import com.localidata.extractorContratos.util.Constants;
import com.localidata.extractorContratos.util.ISqlGeneric;
import com.localidata.extractorContratos.util.Util;

public class LogTable {
	
	private int id;
	private String resume;
	private Date loadTime;

	
	public LogTable() {
		super();
		loadTime=new Date();
	}	

	public LogTable(int id, String resume, Date loadTime) {
		super();
		this.id = id;
		this.resume = resume;
		this.loadTime = loadTime;
	}
	
	public LogTable(LogTable obj) {
		super();
		this.id = obj.id;
		this.resume = obj.resume;
		this.loadTime = obj.loadTime;
	}



	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getResume() {
		return resume;
	}


	public void setResume(String resume) {
		this.resume = resume;
	}


	public Date getLoadTime() {
		return loadTime;
	}


	public void setLoadTime(Date loadTime) {
		this.loadTime = loadTime;
	}

	

	

	
}
