package semantic.sync.util;

public class Configuration {

	private String catalogURL;
	private String user;
	private String host;
	private String password;
	private String eldaURI;
	private String virtuosoPort;
	private String virtuosoUser;
	private String virtuosoPassword;
	private String virtuosoPathToIsql;
	private String formatFile;
	private String graphUri;
	
	
	public String getCatalogURL() {
		return catalogURL;
	}
	public void setCatalogURL(String catalogURL) {
		this.catalogURL = catalogURL;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEldaURI() {
		return eldaURI;
	}
	public void setEldaURI(String eldaURI) {
		this.eldaURI = eldaURI;
	}
	public String getVirtuosoPort() {
		return virtuosoPort;
	}
	public void setVirtuosoPort(String virtuosoPort) {
		this.virtuosoPort = virtuosoPort;
	}
	public String getVirtuosoUser() {
		return virtuosoUser;
	}
	public void setVirtuosoUser(String virtuosoUser) {
		this.virtuosoUser = virtuosoUser;
	}
	public String getVirtuosoPassword() {
		return virtuosoPassword;
	}
	public void setVirtuosoPassword(String virtuosoPassword) {
		this.virtuosoPassword = virtuosoPassword;
	}
	public String getVirtuosoPathToIsql() {
		return virtuosoPathToIsql;
	}
	public void setVirtuosoPathToIsql(String virtuosoPathToIsql) {
		this.virtuosoPathToIsql = virtuosoPathToIsql;
	}
	public String getFormatFile() {
		return formatFile;
	}
	public void setFormatFile(String formatFile) {
		this.formatFile = formatFile;
	}
	public String getGraphUri() {
		return graphUri;
	}
	public void setGraphUri(String graphUri) {
		this.graphUri = graphUri;
	}
	
	
}
