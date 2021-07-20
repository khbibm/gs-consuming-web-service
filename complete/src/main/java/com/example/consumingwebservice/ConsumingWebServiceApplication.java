package com.example.consumingwebservice;

import com.example.consumingwebservice.wsdl.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.lang.String;
import java.util.List;

@SpringBootApplication
public class ConsumingWebServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConsumingWebServiceApplication.class, args);
	}

	@Bean
	CommandLineRunner lookup(SoapClient soapClient) {
		return args -> {
			final BiBusHeader biBusHeader = soapClient.login("CognosEx", "kelly", "CognosCognosCognos!1");
			final String searchPath = "";
			final PropEnumArray properties = new PropEnumArray();
			properties.getAny().add(PropEnum.defaultName);
			properties.getAny().add(PropEnum.searchPath);
			properties.getAny().add(PropEnum.specification);
			final SortArray sort = new SortArray();
			final Sort sortBy = new Sort();
			sortBy.setOrder(OrderEnum.ASCENDING);
			sortBy.setPropName(PropEnum.defaultName.getValue());
			sort.getAny().add(sortBy);

			final QueryReply response = soapClient.getQueryResponse(biBusHeader, searchPath, properties, sort, new QueryOptions());
			List<Object> baseClasses = response.getQueryResult().getAny();
		};
	}

	private PropEnumArray getProperties() {
		final PropEnumArray properties = new PropEnumArray();
		final List<Object> propEnumArrayList = properties.getAny();

		propEnumArrayList.add("defaultName");

		return properties;
	}
}
