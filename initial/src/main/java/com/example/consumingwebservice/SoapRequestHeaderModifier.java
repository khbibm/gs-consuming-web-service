package com.example.consumingwebservice;

import com.example.consumingwebservice.wsdl.BiBusHeader;
import org.springframework.oxm.Marshaller;
import org.springframework.ws.WebServiceMessage;
import org.springframework.ws.client.core.WebServiceMessageCallback;
import org.springframework.ws.soap.SoapHeader;
import org.springframework.ws.soap.SoapMessage;
import org.springframework.ws.soap.saaj.SaajSoapMessage;

import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;
import java.io.IOException;

public class SoapRequestHeaderModifier implements WebServiceMessageCallback {
    private final BiBusHeader biBusHeader;
    private final Marshaller marshaller;


    public SoapRequestHeaderModifier(BiBusHeader biBusHeader, Marshaller marshaller) {
        this.biBusHeader = biBusHeader;
        this.marshaller = marshaller;
    }

    @Override
    public void doWithMessage(WebServiceMessage message) throws IOException {
        if (message instanceof SaajSoapMessage) {
            final SoapHeader soapHeader = ((SoapMessage) message).getSoapHeader();
            final JAXBElement<BiBusHeader> biBusHeaderElement = new JAXBElement<>(new QName("http://developer.cognos.com/schemas/bibus/3/", "biBusHeader"), BiBusHeader.class, biBusHeader);
            marshaller.marshal(biBusHeaderElement, soapHeader.getResult());
        }
    }
}
