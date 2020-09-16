/**
 * 
 */
package org.ciudadesAbiertas.madrid.bean;

import org.ciudadesAbiertas.madrid.model.dynamic.QueryD;
import org.ciudadesAbiertas.madrid.utils.StartVariables;

/**
 * @author cmart
 *
 */
public class QueryBean {
	
	private QueryD queryD;
	private Boolean errorFileConfig = new Boolean(false);
	private String textoError;
	private Boolean geo=false;
	
	
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
	
	public void validateQuery() {
		
		if (this.queryD!=null) {
			if (!StartVariables.errorDatabaseTypes.isEmpty()) {
				for (String value:StartVariables.errorDatabaseTypes.keySet()) {
					if (this.queryD.getDatabase()!=null) {
						if (value.equals(this.queryD.getDatabase())) {
							this.errorFileConfig=new Boolean(true);
							this.textoError = "Database ["+value+"] No encontrada o no cargada correctamente.";
						}else {
							this.errorFileConfig=new Boolean(false);
						}
					}
				}
			}
		}
	}
	
	
}
