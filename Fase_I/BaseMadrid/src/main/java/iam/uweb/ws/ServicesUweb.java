/**
 * ServicesUweb.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package iam.uweb.ws;

public interface ServicesUweb extends java.rmi.Remote {
    public java.lang.String getUserData(iam.uweb.beans.UwebBean ub) throws java.rmi.RemoteException;
    public java.lang.String getServicesInfo() throws java.rmi.RemoteException;
    public java.lang.String getApplicationData(iam.uweb.beans.UwebBean ub) throws java.rmi.RemoteException;
    public java.lang.String getListApplicationData(iam.uweb.beans.UwebBean ub) throws java.rmi.RemoteException;
    public java.lang.String getApplicationsUserList(iam.uweb.beans.UwebBean ub) throws java.rmi.RemoteException;
    public java.lang.String getUsersProfileApplicationList(iam.uweb.beans.UwebBean ub) throws java.rmi.RemoteException;
    public java.lang.String getUsersApplicationList(iam.uweb.beans.UwebBean ub) throws java.rmi.RemoteException;
    public java.lang.String getErrorData(iam.uweb.beans.UwebBean ub) throws java.rmi.RemoteException;
    public java.lang.String getProcessData(iam.uweb.beans.UwebBean ub) throws java.rmi.RemoteException;
    public java.lang.String getProfileData(iam.uweb.beans.UwebBean ub) throws java.rmi.RemoteException;
    public java.lang.String getProfilesUserApplicationList(iam.uweb.beans.UwebBean ub) throws java.rmi.RemoteException;
    public java.lang.String getStatusUserData(iam.uweb.beans.UwebBean ub) throws java.rmi.RemoteException;
    public java.lang.String getUserDataByLogin(iam.uweb.beans.UwebBean uwebBean) throws java.rmi.RemoteException;
    public java.lang.String getUserDataByDni(iam.uweb.beans.UwebBean uwebBean) throws java.rmi.RemoteException;
    public java.lang.String getUserDataByNumPer(iam.uweb.beans.UwebBean uwebBean) throws java.rmi.RemoteException;
    public java.lang.String getProcessApplicationByUser(iam.uweb.beans.UwebBean uwebBean) throws java.rmi.RemoteException;
    public java.lang.String getChildsProcessByUser(iam.uweb.beans.UwebBean uwebBean) throws java.rmi.RemoteException;
    public java.lang.String getProcessStatusByUser(iam.uweb.beans.UwebBean uwebBean) throws java.rmi.RemoteException;
}
