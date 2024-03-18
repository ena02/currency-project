package com.ena.bank.currency.client;

import com.ena.bank.currency.schema.CurrencyGlobal;
import com.ena.bank.currency.schema.CurrencyType;
import com.ena.bank.currency.service.XMLService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Component
public class NbCurrentDateRateClient implements HttpCurrentDateRateClient {

    private static final String DATE_PATTERN = "dd.mm.yyyy";
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(DATE_PATTERN);

    private final Logger logger = LoggerFactory.getLogger(XMLService.class);

    @Override
    public String buildUrlRequest(LocalDate date) {

        String dateString = date.toString();
        String[] Arr = dateString.split("-");
        dateString = String.join(".", Arr[2], Arr[1], Arr[0]);
        String Url = "https://nationalbank.kz/rss/get_rates.cfm?fdate="+dateString;

        return Url;
    }


    public CurrencyGlobal parseCurrencyByDate(LocalDate date) {
        CurrencyGlobal currencyGlobal = null;

        try {

            String URL = buildUrlRequest(date);

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(URL);

            doc.getDocumentElement().normalize();



            currencyGlobal = new CurrencyGlobal(
                    doc.getElementsByTagName("title").item(0).getTextContent(),
                    doc.getElementsByTagName("link").item(0).getTextContent(),
                    doc.getElementsByTagName("description").item(0).getTextContent(),
                    doc.getElementsByTagName("copyright").item(0).getTextContent(),
                    doc.getElementsByTagName("date").item(0).getTextContent());

            NodeList nodeList = doc.getElementsByTagName("item");

            List<CurrencyType> currencyList = new ArrayList<>();

            for(int i = 0; i < nodeList.getLength(); i++) {

                Node node = nodeList.item(i);

                if(node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    CurrencyType currency = new CurrencyType(
                            element.getElementsByTagName("fullname").item(0).getTextContent(),
                            element.getElementsByTagName("title").item(0).getTextContent(),
                            Double.parseDouble(element.getElementsByTagName("description").item(0).getTextContent()),
                            Double.parseDouble(element.getElementsByTagName("quant").item(0).getTextContent()),
                            element.getElementsByTagName("index").item(0).getTextContent()
                    );

                    currencyList.add(currency);
                }
            }

            currencyGlobal.setCurrencyList(currencyList);

        } catch (ParserConfigurationException | IOException | SAXException e) {
            throw new RuntimeException(e);
        } catch (Exception ex) {
            logger.error(ex.getMessage());
        }

        return currencyGlobal;
    }

    /*private String buildUrlRequest(String baseUrl, LocalDate date) {
        return UriComponentsBuilder.fromHttpUrl(baseUrl)
                .queryParam("date_req", DATE_TIME_FORMATTER.format(date))
                .build().toUriString();
    }*/
}
