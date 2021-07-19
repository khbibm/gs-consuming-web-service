package com.example.consumingwebservice;

import com.example.consumingwebservice.wsdl.*;

import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;
import java.lang.String;
import java.util.Collections;


public class SoapClient extends WebServiceGatewaySupportCustom {

    public BiBusHeader login(String namespace, String username, String password) {
        ResponseAndHeader responseAndHeader = getWebServiceTemplateForHeaders().loginRequest(namespace, username, password);
        return responseAndHeader.getHeader();
    }

    public QueryReply getQueryResponse(BiBusHeader biBusHeader, String searchPath, PropEnumArray properties, SortArray sortBy, QueryOptions options) {
        final QueryRequest request = new QueryRequest();
        request.setSearch(searchPath);
        request.setProperties(properties);
        request.setSortBy(sortBy);
        request.setOptions(options);

        final JAXBElement<QueryRequest> requestElement = new JAXBElement<>(new QName("http://developer.cognos.com/schemas/bibus/3/", "queryRequest"), QueryRequest.class, request);
        // marshalling problem with request - no @XmlRootElement
        final QueryReply response = (QueryReply) getWebServiceTemplate().marshalSendAndReceive(requestElement, new SoapRequestHeaderModifier(biBusHeader, getMarshaller()));
        return response;
    }
}
