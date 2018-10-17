package com.example.currency;

import com.example.currency.client.CurrencyWebServiceClient;
import com.example.currency.client.CurrencyWebServiceClientSOAP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

@Configuration
public class ServiceConfiguration {

    @Autowired
    private Environment env;

    @Bean
    public Jaxb2Marshaller marshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setContextPath("com.example.currency.client.wsdl");
        return marshaller;
    }

    @Bean
    public CurrencyWebServiceClient soapConnector(Jaxb2Marshaller marshaller) {
        CurrencyWebServiceClientSOAP client = new CurrencyWebServiceClientSOAP();

        client.setDefaultUri(env.getProperty("soap.service.url"));
        client.setMarshaller(marshaller);
        client.setUnmarshaller(marshaller);
        return client;
    }
}
