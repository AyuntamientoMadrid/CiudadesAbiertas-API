package com.localidata.extractorContratos.util;

public interface  ISqlGeneric {
	
	public String getId();

	public String generateInsert(String BBDD);
	
	public String generateUpdate(String BBDD);
	
	public String getTableName();
	

}
