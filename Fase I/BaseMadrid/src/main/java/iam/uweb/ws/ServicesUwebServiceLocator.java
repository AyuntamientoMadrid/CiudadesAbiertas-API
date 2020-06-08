/**
 * ServicesUwebServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package iam.uweb.ws;

public class ServicesUwebServiceLocator extends org.apache.axis.client.Service implements iam.uweb.ws.ServicesUwebService {

    public ServicesUwebServiceLocator() {
    }


    public ServicesUwebServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public ServicesUwebServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for ServicesUweb
    private java.lang.String ServicesUweb_address = "http://desa1/WSUwebv2/services/ServicesUweb";

    public java.lang.String getServicesUwebAddress() {
        return ServicesUweb_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String ServicesUwebWSDDServiceName = "ServicesUweb";

    public java.lang.String getServicesUwebWSDDServiceName() {
        return ServicesUwebWSDDServiceName;
    }

    public void setServicesUwebWSDDServiceName(java.lang.String name) {
        ServicesUwebWSDDServiceName = name;
    }

    public iam.uweb.ws.ServicesUweb getServicesUweb() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(ServicesUweb_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getServicesUweb(endpoint);
    }

    public iam.uweb.ws.ServicesUweb getServicesUweb(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            iam.uweb.ws.ServicesUwebSoapBindingStub _stub = new iam.uweb.ws.ServicesUwebSoapBindingStub(portAddress, this);
            _stub.setPortName(getServicesUwebWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setServicesUwebEndpointAddress(java.lang.String address) {
        ServicesUweb_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (iam.uweb.ws.ServicesUweb.class.isAssignableFrom(serviceEndpointInterface)) {
                iam.uweb.ws.ServicesUwebSoapBindingStub _stub = new iam.uweb.ws.ServicesUwebSoapBindingStub(new java.net.URL(ServicesUweb_address), this);
                _stub.setPortName(getServicesUwebWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("ServicesUweb".equals(inputPortName)) {
            return getServicesUweb();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://ws.uweb.iam", "ServicesUwebService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://ws.uweb.iam", "ServicesUweb"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("ServicesUweb".equals(portName)) {
            setServicesUwebEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
