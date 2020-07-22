/**
 * 
 */
package org.ciudadesAbiertas.madrid.bean;

import java.util.Date;

import org.ciudadesAbiertas.madrid.model.dynamic.QueryConfD;
import org.ciudadesAbiertas.madrid.model.dynamic.QueryD;
import org.ciudadesAbiertas.madrid.utils.StartVariables;
import org.springframework.scheduling.support.CronSequenceGenerator;

/**
 * @author cmart
 *
 */
public class QueryBean {
	
	private QueryD queryD;
	private QueryConfD queryConfD;
	private Boolean errorFileConfig = Boolean.parseBoolean("false");
	private String textoError;
	private Boolean geo=false;
	private String nextExecution;
	
	
	
	public QueryBean(QueryD queryD) {
		super();
		this.queryD = queryD;
		validateQuery();
	}
	
	public QueryD getQueryD() {
		return queryD;
	}
	public void setQueryD(QueryD queryD) {
		this.queryD = queryD;
	}
	public Boolean getErrorFileConfig() {
		return errorFileConfig;
	}
	public void setErrorConfig(Boolean errorFileConfig) {
		this.errorFileConfig = errorFileConfig;
	}
	public String getTextoError() {
		return textoError;
	}
	public void setTextoError(String textoError) {
		this.textoError = textoError;
	}
		
	public Boolean getGeo() {
		return geo;
	}

	public void setGeo(Boolean geo) {
		this.geo = geo;
	}

	@Override
	public String toString() {
		return "QueryBean [queryD=" + queryD + ", errorFileConfig=" + errorFileConfig + ", textoError=" + textoError + "]";
	}
	
	
	
	
	public String getNextExecution() {
	    return nextExecution;
	}

	public void setNextExecution(String nextExecution) {
	    this.nextExecution = nextExecution;
	}

	public QueryConfD getQueryConfD() {
	    return queryConfD;
	}

	public void setQueryConfD(QueryConfD queryConfD) {
	    this.queryConfD = queryConfD;
	}

	public void setErrorFileConfig(Boolean errorFileConfig) {
	    this.errorFileConfig = errorFileConfig;
	}

	public void validateQuery() {
		
		if (this.queryD!=null) {
			if (!StartVariables.errorDatabaseTypes.isEmpty()) {
				for (String value:StartVariables.errorDatabaseTypes.keySet()) {
					if (this.queryD.getDatabase()!=null) {
						if (value.equals(this.queryD.getDatabase())) {
							this.errorFileConfig=Boolean.parseBoolean("true");
							this.textoError = "Database ["+value+"] No encontrada o no cargada correctamente.";
						}else {
							this.errorFileConfig=Boolean.parseBoolean("false");
						}
					}
				}
			}
		}
	}

	
	
	
}
