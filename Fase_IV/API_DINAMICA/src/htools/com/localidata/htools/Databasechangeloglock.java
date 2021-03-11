package com.localidata.htools;
// Generated 19 ene. 2021 10:56:41 by Hibernate Tools 4.3.5.Final

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Databasechangeloglock generated by hbm2java
 */
@Entity
@Table(name = "DATABASECHANGELOGLOCK", catalog = "apiDinamica")
public class Databasechangeloglock implements java.io.Serializable {

private int id;
private boolean locked;
private Date lockgranted;
private String lockedby;

public Databasechangeloglock() {
}

public Databasechangeloglock(int id, boolean locked) {
  this.id = id;
  this.locked = locked;
}

public Databasechangeloglock(int id, boolean locked, Date lockgranted, String lockedby) {
  this.id = id;
  this.locked = locked;
  this.lockgranted = lockgranted;
  this.lockedby = lockedby;
}

@Id

@Column(name = "ID", unique = true, nullable = false)
public int getId() {
  return this.id;
}

public void setId(int id) {
  this.id = id;
}

@Column(name = "LOCKED", nullable = false)
public boolean isLocked() {
  return this.locked;
}

public void setLocked(boolean locked) {
  this.locked = locked;
}

@Temporal(TemporalType.TIMESTAMP)
@Column(name = "LOCKGRANTED", length = 19)
public Date getLockgranted() {
  return this.lockgranted;
}

public void setLockgranted(Date lockgranted) {
  this.lockgranted = lockgranted;
}

@Column(name = "LOCKEDBY")
public String getLockedby() {
  return this.lockedby;
}

public void setLockedby(String lockedby) {
  this.lockedby = lockedby;
}

}
