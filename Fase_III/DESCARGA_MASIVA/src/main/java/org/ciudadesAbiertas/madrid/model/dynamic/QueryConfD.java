package org.ciudadesAbiertas.madrid.model.dynamic;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "query_conf")
public class QueryConfD implements java.io.Serializable
{

    private static final long serialVersionUID = -7037886048691023962L;
    
    public static final String MODE_AUTO="auto";
    public static final String MODE_MANUAL="manual";

    private String id;
    private String path;
    private String mode;
    private String cron;
    private Boolean zip;
    private long items;
    private Boolean overwrite;
    
    private String minute;
    private String hour;
    private String dayW;
    private String dayM;
    private String month;
    
    public QueryConfD()
    {
      super();     
    }
    
    public QueryConfD(QueryConfD copia) {
	super();
	this.id = copia.id;
	this.path = copia.path;
	this.mode = copia.mode;
	this.cron = copia.cron;
	this.zip = copia.zip;
	this.items = copia.items;
	this.overwrite = copia.overwrite;
	this.minute = copia.minute;
	this.hour = copia.hour;
	this.dayW = copia.dayW;
	this.dayM = copia.dayM;
	this.month = copia.month;
    }




    @Id
    @Column(name = "id", unique = true, nullable = false, length = 20)
    public String getId()
    {
	return this.id;
    }

    public void setId(String id)
    {
	this.id = id;
    }

    @Column(name = "path", nullable = false, length = 200)
    public String getPath()
    {
	return path;
    }

    public void setPath(String path)
    {
	this.path = path;
    }

    @Column(name = "mode", nullable = false, length = 50)
    public String getMode()
    {
	return mode;
    }

    public void setMode(String mode)
    {
	this.mode = mode;
    }

    @Column(name = "cron", nullable = true, length = 50)
    public String getCron()
    {
	return cron;
    }

    public void setCron(String cron)
    {
	this.cron = cron;
    }

    @Column(name = "zip")
    public Boolean getZip()
    {
	return zip;
    }

    public void setZip(Boolean zip)
    {
	this.zip = zip;
    }

    @Column(name = "items")
    public long getItems()
    {
	return items;
    }

    public void setItems(long items)
    {
	this.items = items;
    }

    @Column(name = "overwrite")
    public Boolean getOverwrite()
    {
      return overwrite;
    }

    public void setOverwrite(Boolean overwrite)
    {
      this.overwrite = overwrite;
    }

    @Column(name = "minute", length = 50)
    public String getMinute() {
        return minute;
    }

    public void setMinute(String minute) {
        this.minute = minute;
    }

    @Column(name = "hour", length = 50)
    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    @Column(name = "day_week", length = 50)
    public String getDayW() {
        return dayW;
    }

    public void setDayW(String dayW) {
        this.dayW = dayW;
    }

    @Column(name = "day_month", length = 50)
    public String getDayM() {
        return dayM;
    }

    public void setDayM(String dayM) {
        this.dayM = dayM;
    }

    @Column(name = "month", length = 50)
    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    @Override
    public String toString() {
	return "QueryConfD [id=" + id + ", path=" + path + ", mode=" + mode + ", cron=" + cron + ", zip=" + zip + ", items=" + items + ", overwrite=" + overwrite + ", minute=" + minute + ", hour=" + hour + ", dayW=" + dayW + ", dayM="
		+ dayM + ", month=" + month + "]";
    }

    
    
    
}
