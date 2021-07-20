package com.example.consumingwebservice;

import com.example.consumingwebservice.wsdl.*;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;

import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;
import java.util.List;


public class SoapClient extends WebServiceGatewaySupport {

    public Object getObject() {

        return null;
    }

    public LogonResponseType login (String nameSpace, String userName, String userPassword) {
        final CredentialElementType nameSpaceElement = new CredentialElementType();
        ValueElementType nameSpaceValue = new ValueElementType();
        nameSpaceValue.setActualValue(nameSpace);
        nameSpaceElement.setName("CAMNamespace");
        nameSpaceElement.setValue(nameSpaceValue);

        final CredentialElementType userNameElement = new CredentialElementType();
        ValueElementType userNameValue = new ValueElementType();
        userNameValue.setActualValue(userName);
        userNameElement.setName("CAMUsername");
        userNameElement.setValue(userNameValue);

        final CredentialElementType passWordElement = new CredentialElementType();
        ValueElementType passWordValue = new ValueElementType();
        passWordValue.setActualValue(userPassword);
        passWordElement.setName("CAMPassword");
        passWordElement.setValue(passWordValue);

        final CredentialType credentialType = new CredentialType();
        final List<CredentialElementType> credentialElements = credentialType.getCredentialElements();
        credentialElements.add(nameSpaceElement);
        credentialElements.add(userNameElement);
        credentialElements.add(passWordElement);

        final LogonRequestType logonRequest = new LogonRequestType();
        logonRequest.setCredentials(credentialType);
        final LogonResponseType response = (LogonResponseType) getWebServiceTemplate().marshalSendAndReceive(getDefaultUri() + "/auth/logon", new ObjectFactory().createLogonRequest(logonRequest));
        return response;
    }

}
