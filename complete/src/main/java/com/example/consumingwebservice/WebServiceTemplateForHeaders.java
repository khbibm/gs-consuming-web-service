package com.example.consumingwebservice;

import com.example.consumingwebservice.wsdl.*;
import org.springframework.oxm.Marshaller;
import org.springframework.oxm.Unmarshaller;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.soap.SoapHeader;
import org.springframework.ws.soap.SoapHeaderElement;
import org.springframework.ws.soap.SoapMessage;
import org.springframework.ws.support.MarshallingUtils;

import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;
import java.lang.String;
import java.util.Iterator;
import java.util.List;

public class WebServiceTemplateForHeaders extends WebServiceTemplate {

    public ResponseAndHeader loginRequest(String namespace, String username, String password) {

        return marshalSendReceiveAndGetHeaders(null, getBiBusHeader(namespace, username, password));
    }

    public ResponseAndHeader marshalSendReceiveAndGetHeaders(final Object requestPayload, BiBusHeader biBusHeader) {
        return sendAndReceive(getDefaultUri(), request -> {
            final Marshaller marshaller = getMarshaller();
            if (marshaller == null) {
                throw new IllegalStateException("No marshaller registered. Check configuration of WebServiceTemplate.");
            }

            // set the bi bus header
            ((SoapMessage) request).setSoapAction("http://www.ibm.com/xmlns/prod/cognos/contentManagerService/202008/");
            final SoapHeader soapHeader = ((SoapMessage) request).getSoapHeader();
            final JAXBElement<BiBusHeader> biBusHeaderElement = new JAXBElement<>(new QName("http://developer.cognos.com/schemas/bibus/3/", "biBusHeader"), BiBusHeader.class, biBusHeader);

            marshaller.marshal(biBusHeaderElement, soapHeader.getResult());
            if (requestPayload != null) {
                MarshallingUtils.marshal(marshaller, requestPayload, request);
            }
        }, response -> {
            // get the bi bus header
            final SoapHeader header = ((SoapMessage) response).getSoapHeader();
            final Iterator<SoapHeaderElement> it = header.examineHeaderElements(
                    new QName("http://developer.cognos.com/schemas/bibus/3/", "biBusHeader"));

            final Unmarshaller unmarshaller = getUnmarshaller();
            if (unmarshaller == null) {
                throw new IllegalStateException("No unmarshaller registered. Check configuration of WebServiceTemplate.");
            }

            final BiBusHeader responseHeader = it.hasNext() ? (BiBusHeader) ((JAXBElement) unmarshaller.unmarshal(it.next().getSource())).getValue() : null;
            final Object responseBody =  MarshallingUtils.unmarshal(unmarshaller, response);
            return new ResponseAndHeader(responseBody, responseHeader);
        });
    }

    private BiBusHeader getBiBusHeader(String namespace, String username, String password) {
        final CAM cam = new CAM();
        cam.setAction("logonAs");
        final HdrSession header = new HdrSession();
        final FormFieldVarArray vars = new FormFieldVarArray();
        final List<Object> formFieldVarArrayList = vars.getAny();

        final FormFieldVar namespaceVar = new FormFieldVar();
        namespaceVar.setName("CAMNamespace");
        namespaceVar.setValue(namespace);
        namespaceVar.setFormat(FormatEnum.NOT_ENCRYPTED);
        formFieldVarArrayList.add(new JAXBElement<>(new QName("http://developer.cognos.com/schemas/bibus/3/", "formFieldVar"), FormFieldVar.class, namespaceVar));

        final FormFieldVar usernameVar = new FormFieldVar();
        usernameVar.setName("CAMUsername");
        usernameVar.setValue(username);
        usernameVar.setFormat(FormatEnum.NOT_ENCRYPTED);
        formFieldVarArrayList.add(new JAXBElement<>(new QName("http://developer.cognos.com/schemas/bibus/3/", "formFieldVar"), FormFieldVar.class, usernameVar));

        final FormFieldVar passwordVar = new FormFieldVar();
        passwordVar.setName("CAMPassword");
        passwordVar.setValue(password);
        passwordVar.setFormat(FormatEnum.NOT_ENCRYPTED);
        formFieldVarArrayList.add(new JAXBElement<>(new QName("http://developer.cognos.com/schemas/bibus/3/", "formFieldVar"), FormFieldVar.class, passwordVar));

        header.setFormFieldVars(vars);

        final BiBusHeader biBusHeader = new BiBusHeader();
        biBusHeader.setCAM(cam);
        biBusHeader.setHdrSession(header);
        return biBusHeader;
    }
}
