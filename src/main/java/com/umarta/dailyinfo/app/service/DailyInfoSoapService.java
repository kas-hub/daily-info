package com.umarta.dailyinfo.app.service;

import com.umarta.dailyinfo.app.config.CbrConfig;
import com.umarta.dailyinfo.app.exception.DailyInfoException;
import com.umarta.dailyinfo.app.exception.ParseException;
import com.umarta.dailyinfo.app.model.ValuteCursOnDate;
import com.umarta.dailyinfo.app.model.ValuteData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service("app_DailyInfoSoapService")
public class DailyInfoSoapService {

    @Autowired
    protected CbrConfig cbrConfig;

    public ValuteData getCursOnDate(@Nullable LocalDate localDate) {
        if (localDate == null) {
            localDate = LocalDate.now();
        }

        String requestSoap = String.format(
                "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                        "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" +
                        "  <soap:Body>\n" +
                        "    <GetCursOnDateXML xmlns=\"http://web.cbr.ru/\">\n" +
                        "      <On_date>%s</On_date>\n" +
                        "    </GetCursOnDateXML>\n" +
                        "  </soap:Body>\n" +
                        "</soap:Envelope>",
                localDate);

        List<ValuteCursOnDate> valuteCursOnDateList = getResponseCbrSoap(requestSoap);
        ValuteData valuteData = new ValuteData();
        valuteData.setValuteCursOnDateList(valuteCursOnDateList);
        return valuteData;
    }

    private List<ValuteCursOnDate> getResponseCbrSoap(String soapRequest) {
        try {
            URL objUrl = new URL(cbrConfig.getUrlSoap());
            HttpURLConnection urlConnection = (HttpURLConnection) objUrl.openConnection();

            urlConnection.setRequestMethod("POST");
            urlConnection.setRequestProperty("Content-Type", "text/xml; charset=utf-8");
            urlConnection.setDoOutput(true);
            DataOutputStream dataOutputStream = new DataOutputStream(urlConnection.getOutputStream());
            dataOutputStream.writeBytes(soapRequest);
            dataOutputStream.flush();
            dataOutputStream.close();

            BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = reader.readLine()) != null) {
                response.append(inputLine);
            }
            reader.close();

            return parseXml(response.toString());
        } catch (Exception exception) {
            log.error("Error during request by SOAP:", exception);
            throw new DailyInfoException("Данные из ЦБ не получены");
        }
    }

    public static List<ValuteCursOnDate> parseXml(String xml) {
        List<ValuteCursOnDate> valuteCursOnDateList = new ArrayList<>();
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {
            dbf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
            DocumentBuilder db = dbf.newDocumentBuilder();

            try (StringReader reader = new StringReader(xml)) {
                Document doc = db.parse(new InputSource(reader));
                doc.getDocumentElement().normalize();

                NodeList list = doc.getElementsByTagName("ValuteCursOnDate");

                for (int valuteIdx = 0; valuteIdx < list.getLength(); valuteIdx++) {
                    Node node = list.item(valuteIdx);
                    if (node.getNodeType() == Node.ELEMENT_NODE) {
                        Element element = (Element) node;

                        ValuteCursOnDate valuteCursOnDate = ValuteCursOnDate.builder()
                                .vname(element.getElementsByTagName("Vname").item(0).getTextContent())
                                .vnom(element.getElementsByTagName("Vnom").item(0).getTextContent())
                                .vcurs(element.getElementsByTagName("Vcurs").item(0).getTextContent())
                                .vcode(element.getElementsByTagName("Vcode").item(0).getTextContent())
                                .vchCode(element.getElementsByTagName("VchCode").item(0).getTextContent())
                                .vunitRate(element.getElementsByTagName("VunitRate").item(0).getTextContent())
                                .build();
                        valuteCursOnDateList.add(valuteCursOnDate);
                    }
                }
            }
            return valuteCursOnDateList;
        } catch (Exception exception) {
            log.error("Error during creation of XML", exception);
            throw new ParseException("Ошибка обработки данных из ЦБ");
        }
    }
}
