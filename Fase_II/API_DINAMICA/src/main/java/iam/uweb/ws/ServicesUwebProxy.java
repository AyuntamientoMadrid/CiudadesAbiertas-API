package iam.uweb.ws;



public class ServicesUwebProxy implements iam.uweb.ws.ServicesUweb {
  private String _endpoint = null;
  private iam.uweb.ws.ServicesUweb servicesUweb = null;
  
  public ServicesUwebProxy() {
    _initServicesUwebProxy();
  }
  
  public ServicesUwebProxy(String endpoint) {
    _endpoint = endpoint;
    _initServicesUwebProxy();
  }
  
  private void _initServicesUwebProxy() {
    try {
      servicesUweb = (new ServicesUwebServiceLocator()).getServicesUweb();
      if (servicesUweb != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)servicesUweb)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)servicesUweb)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (servicesUweb != null)
      ((javax.xml.rpc.Stub)servicesUweb)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public iam.uweb.ws.ServicesUweb getServicesUweb() {
    if (servicesUweb == null)
      _initServicesUwebProxy();
    return servicesUweb;
  }
  
  public java.lang.String getUserData(iam.uweb.beans.UwebBean ub) throws java.rmi.RemoteException{
    if (servicesUweb == null)
      _initServicesUwebProxy();
    return servicesUweb.getUserData(ub);
  }
  
  public java.lang.String getServicesInfo() throws java.rmi.RemoteException{
    if (servicesUweb == null)
      _initServicesUwebProxy();
    return servicesUweb.getServicesInfo();
  }
  
  public java.lang.String getApplicationData(iam.uweb.beans.UwebBean ub) throws java.rmi.RemoteException{
    if (servicesUweb == null)
      _initServicesUwebProxy();
    return servicesUweb.getApplicationData(ub);
  }
  
  public java.lang.String getListApplicationData(iam.uweb.beans.UwebBean ub) throws java.rmi.RemoteException{
    if (servicesUweb == null)
      _initServicesUwebProxy();
    return servicesUweb.getListApplicationData(ub);
  }
  
  public java.lang.String getApplicationsUserList(iam.uweb.beans.UwebBean ub) throws java.rmi.RemoteException{
    if (servicesUweb == null)
      _initServicesUwebProxy();
    return servicesUweb.getApplicationsUserList(ub);
  }
  
  public java.lang.String getUsersProfileApplicationList(iam.uweb.beans.UwebBean ub) throws java.rmi.RemoteException{
    if (servicesUweb == null)
      _initServicesUwebProxy();
    return servicesUweb.getUsersProfileApplicationList(ub);
  }
  
  public java.lang.String getUsersApplicationList(iam.uweb.beans.UwebBean ub) throws java.rmi.RemoteException{
    if (servicesUweb == null)
      _initServicesUwebProxy();
    return servicesUweb.getUsersApplicationList(ub);
  }
  
  public java.lang.String getErrorData(iam.uweb.beans.UwebBean ub) throws java.rmi.RemoteException{
    if (servicesUweb == null)
      _initServicesUwebProxy();
    return servicesUweb.getErrorData(ub);
  }
  
  public java.lang.String getProcessData(iam.uweb.beans.UwebBean ub) throws java.rmi.RemoteException{
    if (servicesUweb == null)
      _initServicesUwebProxy();
    return servicesUweb.getProcessData(ub);
  }
  
  public java.lang.String getProfileData(iam.uweb.beans.UwebBean ub) throws java.rmi.RemoteException{
    if (servicesUweb == null)
      _initServicesUwebProxy();
    return servicesUweb.getProfileData(ub);
  }
  
  public java.lang.String getProfilesUserApplicationList(iam.uweb.beans.UwebBean ub) throws java.rmi.RemoteException{
    if (servicesUweb == null)
      _initServicesUwebProxy();
    return servicesUweb.getProfilesUserApplicationList(ub);
  }
  
  public java.lang.String getStatusUserData(iam.uweb.beans.UwebBean ub) throws java.rmi.RemoteException{
    if (servicesUweb == null)
      _initServicesUwebProxy();
    return servicesUweb.getStatusUserData(ub);
  }
  
  public java.lang.String getUserDataByLogin(iam.uweb.beans.UwebBean uwebBean) throws java.rmi.RemoteException{
    if (servicesUweb == null)
      _initServicesUwebProxy();
    return servicesUweb.getUserDataByLogin(uwebBean);
  }
  
  public java.lang.String getUserDataByDni(iam.uweb.beans.UwebBean uwebBean) throws java.rmi.RemoteException{
    if (servicesUweb == null)
      _initServicesUwebProxy();
    return servicesUweb.getUserDataByDni(uwebBean);
  }
  
  public java.lang.String getUserDataByNumPer(iam.uweb.beans.UwebBean uwebBean) throws java.rmi.RemoteException{
    if (servicesUweb == null)
      _initServicesUwebProxy();
    return servicesUweb.getUserDataByNumPer(uwebBean);
  }
  
  public java.lang.String getProcessApplicationByUser(iam.uweb.beans.UwebBean uwebBean) throws java.rmi.RemoteException{
    if (servicesUweb == null)
      _initServicesUwebProxy();
    return servicesUweb.getProcessApplicationByUser(uwebBean);
  }
  
  public java.lang.String getChildsProcessByUser(iam.uweb.beans.UwebBean uwebBean) throws java.rmi.RemoteException{
    if (servicesUweb == null)
      _initServicesUwebProxy();
    return servicesUweb.getChildsProcessByUser(uwebBean);
  }
  
  public java.lang.String getProcessStatusByUser(iam.uweb.beans.UwebBean uwebBean) throws java.rmi.RemoteException{
    if (servicesUweb == null)
      _initServicesUwebProxy();
    return servicesUweb.getProcessStatusByUser(uwebBean);
  }
  
  
}