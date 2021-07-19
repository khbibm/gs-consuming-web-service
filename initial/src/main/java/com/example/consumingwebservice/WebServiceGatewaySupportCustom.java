package com.example.consumingwebservice;

import org.springframework.oxm.Marshaller;
import org.springframework.oxm.Unmarshaller;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;

public abstract class WebServiceGatewaySupportCustom extends WebServiceGatewaySupport {

    private final WebServiceTemplateForHeaders webServiceTemplateForHeaders;

    public WebServiceGatewaySupportCustom() {
        this.webServiceTemplateForHeaders = new WebServiceTemplateForHeaders();
    }

    public WebServiceTemplateForHeaders getWebServiceTemplateForHeaders() {
        return webServiceTemplateForHeaders;
    }

    public void setDefaultUriCustom(String uri) {
        super.setDefaultUri(uri);
        this.webServiceTemplateForHeaders.setDefaultUri(uri);
    }

    public void setMarshallerCustom(Marshaller marshaller) {
        super.setMarshaller(marshaller);
        this.webServiceTemplateForHeaders.setMarshaller(marshaller);
    }

    public void setUnmarshallerCustom(Unmarshaller unmarshaller) {
        super.setUnmarshaller(unmarshaller);
        this.webServiceTemplateForHeaders.setUnmarshaller(unmarshaller);
    }
}
