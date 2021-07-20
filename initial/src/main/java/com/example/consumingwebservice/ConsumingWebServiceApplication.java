package com.example.consumingwebservice;


import com.example.consumingwebservice.wsdl.LogonResponseType;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ConsumingWebServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConsumingWebServiceApplication.class, args);
	}

	@Bean
	CommandLineRunner lookup(SoapClient soapClient) {
		return args -> {
			final LogonResponseType response = soapClient.login("CognosEx", "kelly", "CognosCognosCognos!1");
			System.out.println(response.getResponseCode());
		};
	}
}
