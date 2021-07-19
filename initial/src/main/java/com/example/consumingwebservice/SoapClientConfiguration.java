package com.example.consumingwebservice;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

import javax.xml.bind.Marshaller;
import java.util.Collections;


@Configuration
public class SoapClientConfiguration {

    @Bean
    public Jaxb2Marshaller marshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setPackagesToScan("com.example.consumingwebservice", "com.example.consumingwebservice.wsdl");
        marshaller.setMarshallerProperties(Collections.singletonMap(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE));
        return marshaller;
    }

    @Bean
    public SoapClient soapClient(Jaxb2Marshaller marshaller) {
        SoapClient client = new SoapClient();
        client.setDefaultUriCustom("http://desktop-4j91tvb.mshome.net:9300/p2pd/servlet/dispatch");
        client.setMarshallerCustom(marshaller);
        client.setUnmarshallerCustom(marshaller);
        return client;
    }
}
