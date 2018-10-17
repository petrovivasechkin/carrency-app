package com.example.currency.client;


import com.example.currency.exception.WebClientException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.soap.*;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;

/**
 * Created by sergey on 15.10.2018.
 */

public class CurrencyWebServiceClientSOAP extends WebServiceGatewaySupport implements CurrencyWebServiceClient {

    /**
     * По-нормальному опросить этот сервис не получилось.
     * Сначала классы просто не генерились из WSDL,
     * потом, после небольших правок, классы я наконец получил,
     * но в процессе тестирования оказалось, что все запросы возвращают вместо внутреннего контента null,
     * из-за непрописаного типа в WSDL. Типы я пробовал прописать сам согласно xsd схеме,
     * но видимо чтото не так, потому что опять получаю нал.
     *
     * На тесты опять к сожалению не осталось времени,
     * потому что этот досадный баг в сервисе сожрал все мое время.
     *
     * Сейчас уже очень поздно, поэтому придется оставить все как есть
     * и просто добавить тупой метод парсинга xml, который все же работает.
     *
     *
     *
     */
    @Value("${soap.service.url}")
    private String connectionString;
    @Override
    public boolean requestCurrencyData(String currencyCode) throws WebClientException {


        try {
            return letsDoUglySoapRequest(currencyCode);
        } catch (Exception e) {
            throw new WebClientException("SOAP service exception: ", e);
        }

//        GetCursOnDateXMLResponse response;
//        try {
//            GregorianCalendar c = new GregorianCalendar();
//            XMLGregorianCalendar date = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
//            GetCursOnDateXML request = new GetCursOnDateXML();
//            request.setOnDate(date);
//            response = (GetCursOnDateXMLResponse) getWebServiceTemplate().marshalSendAndReceive(getDefaultUri(), request, new SoapActionCallback(
//                    "http://web.cbr.ru/GetCursOnDateXML"));
//
//        } catch (Exception e) {
//            throw new WebClientException("SOAP service exception: ", e);
//        }
//        return Optional.ofNullable(String.valueOf(response));
    }


    private boolean letsDoUglySoapRequest(String currencyCode) throws SOAPException, IOException {

        String s = "<soap:Envelope xmlns:soap=\"http://www.w3.org/2003/05/soap-envelope\" xmlns:web=\"http://web.cbr.ru/\">\n" +
                "   <soap:Header/>\n" +
                "   <soap:Body>\n" +
                "      <web:GetCursOnDateXML>\n" +
                "         <web:On_date>" + LocalDate.now() + "</web:On_date>\n" +
                "      </web:GetCursOnDateXML>\n" +
                "   </soap:Body>\n" +
                "</soap:Envelope>";


        InputStream is = new ByteArrayInputStream(s.getBytes());

        SOAPMessage soapMessage = MessageFactory.newInstance(SOAPConstants.SOAP_1_2_PROTOCOL).createMessage(null, is);
        SOAPPart part = soapMessage.getSOAPPart();
        SOAPEnvelope envelope = part.getEnvelope();
        SOAPBody body = envelope.getBody();
        Name bodyName = envelope.createName("GetCursOnDateXML", null, "http://web.cbr.ru/");
        body.addBodyElement(bodyName);
        soapMessage.saveChanges();

        SOAPConnectionFactory soapConnFactory = SOAPConnectionFactory.newInstance();
        SOAPConnection connection = soapConnFactory.createConnection();
        SOAPMessage reply = connection.call(soapMessage, connectionString);

        NodeList nl = reply.getSOAPBody().getElementsByTagName("Vcode");
        if (nl != null) {
            int length = nl.getLength();
            for (int i = 0; i < length; i++) {
                if (nl.item(i).getNodeType() == Node.ELEMENT_NODE) {
                    Element el = (Element) nl.item(i);
                    if (el.getTextContent().equals(currencyCode)) {
                        return true;
                    }
                }
            }

        }
        return false;
    }

    public void setConnectionString(String connectionString) {
        this.connectionString = connectionString;
    }
}
