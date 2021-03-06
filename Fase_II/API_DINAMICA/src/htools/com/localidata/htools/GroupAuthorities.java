package com.localidata.htools;
// Generated 1 jul. 2020 14:16:07 by Hibernate Tools 4.3.5.Final

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

/**
 * GroupAuthorities generated by hbm2java
 */
@Entity
@Table(name = "group_authorities", catalog = "apiDinamica")
public class GroupAuthorities implements java.io.Serializable {

    private long groupId;
    private Groups groups;
    private String authority;

    public GroupAuthorities() {
    }

    public GroupAuthorities(Groups groups, String authority) {
	this.groups = groups;
	this.authority = authority;
    }

    @GenericGenerator(name = "generator", strategy = "foreign", parameters = @Parameter(name = "property", value = "groups"))
    @Id
    @GeneratedValue(generator = "generator")

    @Column(name = "group_id", unique = true, nullable = false)
    public long getGroupId() {
	return this.groupId;
    }

    public void setGroupId(long groupId) {
	this.groupId = groupId;
    }

    @OneToOne(fetch = FetchType.LAZY)
    @PrimaryKeyJoinColumn
    public Groups getGroups() {
	return this.groups;
    }

    public void setGroups(Groups groups) {
	this.groups = groups;
    }

    @Column(name = "authority", nullable = false, length = 50)
    public String getAuthority() {
	return this.authority;
    }

    public void setAuthority(String authority) {
	this.authority = authority;
    }

}
