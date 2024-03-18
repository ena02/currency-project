package com.ena.bank.currency.service;

import com.ena.bank.currency.client.HttpCurrentDateRateClient;
import com.ena.bank.currency.schema.CurrencyGlobal;
import com.ena.bank.currency.schema.CurrencyType;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@Service
public class XMLService {

    private final Cache<LocalDate, Map<String, BigDecimal>> cache;
    private final Logger logger = LoggerFactory.getLogger(XMLService.class);
    private HttpCurrentDateRateClient client;

    public XMLService(HttpCurrentDateRateClient client) {
        this.cache = CacheBuilder.newBuilder().build();
        this.client = client;
    }

    /*public BigDecimal requestByCurrencyCode(String code) {
        try {
            return (BigDecimal) cache.get(LocalDate.now(), this::callAllByCurrentDate);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
    }*/

    /*private Map<String, BigDecimal> callAllByCurrentDate() throws IOException, InterruptedException {
        var xml = client.requestByDate(LocalDate.now());
        ValCurs response = unmarshall(xml);
        return response.getVa;
    }*/

    /*private ValCurs unmarshall(String xml) {
        try {
            JAXBContext context = JAXBContext.newInstance(ValCurs.class);
            return (ValCurs) context.createUnmarshaller().unmarshal(reader);
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }
    }*/

    public CurrencyGlobal parseCurrency(LocalDate date) {
        CurrencyGlobal currencyGlobal = null;

        try {

            String dateString = date.toString();
            String[] Arr = dateString.split("-");
            dateString = String.join(".", Arr[2], Arr[1], Arr[0]);
            String URL = "https://nationalbank.kz/rss/get_rates.cfm?fdate="+dateString;

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
}
