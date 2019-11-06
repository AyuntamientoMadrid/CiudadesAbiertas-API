package org.ciudadesabiertas.util;

import java.util.HashMap;
import java.util.Map;

public class ResponseH
{
	private StringBuffer response;
	private Map<String,String> headers;
	private int status;
	
	public ResponseH()
	{
		super();
		this.response = new StringBuffer();
		this.headers = new HashMap<String,String>();
		this.status=0;
	}

	
	
	public StringBuffer getResponse()
	{
		return response;
	}
	public void setResponse(StringBuffer response)
	{
		this.response=new StringBuffer();
		this.response.append(response);
	
	}
	public Map<String, String> getHeaders()
	{
		return headers;
	}
	public void setHeaders(Map<String, String> headers)
	{
		this.headers = headers;
	}



	public int getStatus()
	{
		return status;
	}



	public void setStatus(int status)
	{
		this.status = status;
	}
	
	
	
}
