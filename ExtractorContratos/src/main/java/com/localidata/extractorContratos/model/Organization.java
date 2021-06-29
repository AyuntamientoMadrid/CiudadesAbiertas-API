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
public class Organization implements ISqlGeneric {
	
	public static String tableName = "contratos_organization";
	
	private String ikey = "";
	private String id = "";
	private String title = "";
	private String URI = "";
	private String contactPoint_email = "";
	private String contactPoint_faxNumber = "";
	private String contactPoint_title = "";
	private String contactPoint_telephone = "";
	private String municipio_id = "";
	private String municipio_title = "";
	private String street_address = "";
	private String postal_code = "";
	private String portal_id = "";
	
	/**
	 * 
	 */
	public Organization() {
		super();
	}

	/**
	 * @param ikey
	 * @param id
	 * @param title
	 * @param uRI
	 * @param contactPoint_email
	 * @param contactPoint_faxNumber
	 * @param contactPoint_title
	 * @param contactPoint_telephone
	 * @param municipio_id
	 * @param municipio_title
	 * @param street_address
	 * @param postal_code
	 * @param portal_id
	 */
	public Organization(String ikey, String id, String title, String uRI, String contactPoint_email,
			String contactPoint_faxNumber, String contactPoint_title, String contactPoint_telephone,
			String municipio_id, String municipio_title, String street_address, String postal_code, String portal_id) {
		super();
		this.ikey = ikey;
		this.id = id;
		this.title = title;
		URI = uRI;
		this.contactPoint_email = contactPoint_email;
		this.contactPoint_faxNumber = contactPoint_faxNumber;
		this.contactPoint_title = contactPoint_title;
		this.contactPoint_telephone = contactPoint_telephone;
		this.municipio_id = municipio_id;
		this.municipio_title = municipio_title;
		this.street_address = street_address;
		this.postal_code = postal_code;
		this.portal_id = portal_id;
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

	public String getURI() {
		return URI;
	}

	public void setURI(String uRI) {
		URI = uRI;
	}

	public String getContactPoint_email() {
		return contactPoint_email;
	}

	public void setContactPoint_email(String contactPoint_email) {
		this.contactPoint_email = contactPoint_email;
	}

	public String getContactPoint_faxNumber() {
		return contactPoint_faxNumber;
	}

	public void setContactPoint_faxNumber(String contactPoint_faxNumber) {
		this.contactPoint_faxNumber = contactPoint_faxNumber;
	}

	public String getContactPoint_title() {
		return contactPoint_title;
	}

	public void setContactPoint_title(String contactPoint_title) {
		this.contactPoint_title = contactPoint_title;
	}

	public String getContactPoint_telephone() {
		return contactPoint_telephone;
	}

	public void setContactPoint_telephone(String contactPoint_telephone) {
		this.contactPoint_telephone = contactPoint_telephone;
	}

	public String getMunicipio_id() {
		return municipio_id;
	}

	public void setMunicipio_id(String municipio_id) {
		this.municipio_id = municipio_id;
	}

	public String getMunicipio_title() {
		return municipio_title;
	}

	public void setMunicipio_title(String municipio_title) {
		this.municipio_title = municipio_title;
	}

	public String getStreet_address() {
		return street_address;
	}

	public void setStreet_address(String street_address) {
		this.street_address = street_address;
	}

	public String getPostal_code() {
		return postal_code;
	}

	public void setPostal_code(String postal_code) {
		this.postal_code = postal_code;
	}

	public String getPortal_id() {
		return portal_id;
	}

	public void setPortal_id(String portal_id) {
		this.portal_id = portal_id;
	}
	
	public String getTableName() {
		return tableName;
	}

	@Override
	public String toString() {
		return "Organization [ikey=" + ikey + ", id=" + id + ", title=" + title + ", URI=" + URI
				+ ", contactPoint_email=" + contactPoint_email + ", contactPoint_faxNumber=" + contactPoint_faxNumber
				+ ", contactPoint_title=" + contactPoint_title + ", contactPoint_telephone=" + contactPoint_telephone
				+ ", municipio_id=" + municipio_id + ", municipio_title=" + municipio_title + ", street_address="
				+ street_address + ", postal_code=" + postal_code + ", portal_id=" + portal_id + "]";
	}

	public static String toHeadCSVLine() {
		String result = "";
		
		result = "ikey"+ExtractorContratos.CSVSeparator+"id"+ExtractorContratos.CSVSeparator+"title"+ExtractorContratos.CSVSeparator+"URL"+ExtractorContratos.CSVSeparator+"contactPoint_email"+ExtractorContratos.CSVSeparator+"contactPoint_faxNumber"+ExtractorContratos.CSVSeparator+"contactPoint_title"+ExtractorContratos.CSVSeparator+"contactPoint_telephone"+ExtractorContratos.CSVSeparator+"municipio_id"+ExtractorContratos.CSVSeparator+"municipio_title"+ExtractorContratos.CSVSeparator+"postal_code"+ExtractorContratos.CSVSeparator+"street_address"+ExtractorContratos.CSVSeparator+"portal_id";
		
		return result;
	}
	
	public String toCSVLine() {
		String result = "";
		
		result = this.ikey+ExtractorContratos.CSVSeparator+this.id+ExtractorContratos.CSVSeparator+ExtractorContratos.CSVTextSeparator+this.title+ExtractorContratos.CSVTextSeparator+ExtractorContratos.CSVSeparator+this.URI+ExtractorContratos.CSVSeparator+this.contactPoint_email+ExtractorContratos.CSVSeparator+this.contactPoint_faxNumber+ExtractorContratos.CSVSeparator+this.contactPoint_title+ExtractorContratos.CSVSeparator+this.contactPoint_telephone+ExtractorContratos.CSVSeparator+this.municipio_id+ExtractorContratos.CSVSeparator+this.municipio_title+ExtractorContratos.CSVSeparator+this.postal_code+ExtractorContratos.CSVSeparator+this.street_address+ExtractorContratos.CSVSeparator+this.portal_id;
		
		return result;
	}
	
	public static String toFirstSQLLine() {
		String result = "";
		
		result = "INSERT INTO "+tableName+" (ikey, id, title, URL, contactPoint_email, contactPoint_faxNumber, contactPoint_telephone, contactPoint_title, municipio_id, municipio_title, street_address, postal_code, portal_id) VALUES";
		
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
		if(this.URI!=null) {
			result = result + ExtractorContratos.SQLTextSeparator+this.URI+ExtractorContratos.SQLTextSeparator+ExtractorContratos.SQLSeparator;
		}else {
			result = result + this.URI+ExtractorContratos.SQLSeparator;
		}
		if(this.contactPoint_email!=null) {
			result = result + ExtractorContratos.SQLTextSeparator+this.contactPoint_email+ExtractorContratos.SQLTextSeparator+ExtractorContratos.SQLSeparator;
		}else {
			result = result + this.contactPoint_email+ExtractorContratos.SQLSeparator;
		}
		if(this.contactPoint_faxNumber!=null) {
			result = result + ExtractorContratos.SQLTextSeparator+this.contactPoint_faxNumber+ExtractorContratos.SQLTextSeparator+ExtractorContratos.SQLSeparator;
		}else {
			result = result + this.contactPoint_faxNumber+ExtractorContratos.SQLSeparator;
		}
		if(this.contactPoint_telephone!=null) {
			result = result + ExtractorContratos.SQLTextSeparator+this.contactPoint_telephone+ExtractorContratos.SQLTextSeparator+ExtractorContratos.SQLSeparator;
		}else {
			result = result + this.contactPoint_telephone+ExtractorContratos.SQLSeparator;
		}
		if(this.contactPoint_title!=null) {
			result = result + ExtractorContratos.SQLTextSeparator+this.contactPoint_title+ExtractorContratos.SQLTextSeparator+ExtractorContratos.SQLSeparator;
		}else {
			result = result + this.contactPoint_title+ExtractorContratos.SQLSeparator;
		}
		if(this.municipio_id!=null) {
			result = result + ExtractorContratos.SQLTextSeparator+this.municipio_id+ExtractorContratos.SQLTextSeparator+ExtractorContratos.SQLSeparator;
		}else {
			result = result + this.municipio_id+ExtractorContratos.SQLSeparator;
		}
		if(this.municipio_title!=null) {
			result = result + ExtractorContratos.SQLTextSeparator+this.municipio_title+ExtractorContratos.SQLTextSeparator+ExtractorContratos.SQLSeparator;
		}else {
			result = result + this.municipio_title+ExtractorContratos.SQLSeparator;
		}
		if(this.street_address!=null) {
			result = result + ExtractorContratos.SQLTextSeparator+this.street_address+ExtractorContratos.SQLTextSeparator+ExtractorContratos.SQLSeparator;
		}else {
			result = result + this.street_address+ExtractorContratos.SQLSeparator;
		}
		if(this.postal_code!=null) {
			result = result + ExtractorContratos.SQLTextSeparator+this.postal_code+ExtractorContratos.SQLTextSeparator+ExtractorContratos.SQLSeparator;
		}else {
			result = result + this.postal_code+ExtractorContratos.SQLSeparator;
		}
		if(this.portal_id!=null) {
			result = result + ExtractorContratos.SQLTextSeparator+this.portal_id+ExtractorContratos.SQLTextSeparator;
		}else {
			result = result + this.portal_id;
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
		if (Util.validValue(URI))
		{
			updateSQL+=" URL = '"+URI+"',";
		}
		
		if (Util.validValue(contactPoint_email))
		{
			updateSQL+=" contactPoint_email = '"+contactPoint_email+"',";
		}
		
		if (Util.validValue(contactPoint_faxNumber))
		{
			updateSQL+=" contactPoint_faxNumber = '"+contactPoint_faxNumber+"',";
		}
		
		if (Util.validValue(contactPoint_title))
		{
			updateSQL+=" contactPoint_title = '"+contactPoint_title+"',";
		}
		
		if (Util.validValue(municipio_id))
		{
			updateSQL+=" municipio_id = '"+municipio_id+"',";
		}
		
		if (Util.validValue(municipio_title))
		{
			updateSQL+=" municipio_title = '"+municipio_title+"',";
		}
		
		if (Util.validValue(street_address))
		{
			updateSQL+=" street_address = '"+street_address+"',";
		}
		
		if (Util.validValue(postal_code))
		{
			updateSQL+=" postal_code = '"+postal_code+"',";
		}
		
		if (Util.validValue(portal_id))
		{
			updateSQL+=" portal_id = '"+portal_id+"',";
		}
		
		if (updateSQL.endsWith(","))
		{
			updateSQL=StringUtils.chop(updateSQL);
		}
		
		updateSQL+=" WHERE ikey like '"+ikey+"'";
		
		return updateSQL;		
	}
}
