package org.ciudadesAbiertas.madrid.model.dynamic;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "task")
public class TaskD implements java.io.Serializable {
	
	private static final long serialVersionUID = 4638636109770410737L;
		
	public static final String FINALIZADA="terminado";
	public static final String ERROR="error";
	public static final String RUNNING="en ejecución";

	private String id;
	private String status;
	private String query;
	private String mode;
	private Date start;
	private Date finish;
	private String detail;

	public TaskD() {
		super();
		this.id = "";
		this.status = "";
		this.query = "";
		this.start = null;
		this.finish = null;
		this.detail = "";
	}
	
	

	public TaskD(String id, String status, String query, Date start, Date finish) {
		super();
		this.id = id;
		this.status = status;
		this.query = query;
		this.start = start;
		this.finish = finish;
	}
	
	public TaskD(TaskD TaskD) {
		super();
		this.id = TaskD.id;
		this.status = TaskD.status;
		this.query = TaskD.query;
		this.start = TaskD.start;
		this.finish = TaskD.finish;
		this.detail=TaskD.detail;
	}
	


	@Id
	@Column(name = "id", unique = true, nullable = false, length = 20)
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "status", nullable = false, length = 20)
	public String getStatus() {
		return this.status;
	}
	

	public void setStatus(String status) {
		this.status = status;
	}
	
	@Column(name = "query", nullable = false, length = 20)
	public String  getQuery() {
		return this.query;
	}

	public void setQuery(String query) {
		this.query = query;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "start", length = 19)
	public Date getStart()
	{
		return this.start;
	}

	public void setStart(Date start)
	{
		this.start = start;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "finish", length = 19)
	public Date getFinish()
	{
		return this.finish;
	}

	public void setFinish(Date finish)
	{
		this.finish = finish;
	}
	
	@Column(name = "mode", nullable = false, length = 20)
	public String getMode() {
		return this.mode;
	}
	

	public void setMode(String mode) {
		this.mode = mode;
	}


	
	
	@Column(name = "detail", nullable = true, length = 4000)
	public String getDetail() {
		return detail;
	}



	public void setDetail(String detail) {
		this.detail = detail;
	}



	@Override
	public String toString() {
	    return "TaskD [id=" + id + ", status=" + status + ", query=" + query + ", mode=" + mode + ", start=" + start + ", finish=" + finish + "]";
	}

	


	
	
	
}
