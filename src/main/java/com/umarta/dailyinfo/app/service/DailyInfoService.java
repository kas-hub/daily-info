package com.umarta.dailyinfo.app.service;

import com.umarta.dailyinfo.app.config.CbrConfig;
import com.umarta.dailyinfo.app.exception.DailyInfoException;
import com.umarta.dailyinfo.app.exception.ParseException;
import com.umarta.dailyinfo.app.model.ValCurs;
import com.umarta.dailyinfo.app.xml.ValCursUnmarshaller;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Slf4j
@Service("app_DailyInfoService")
public class DailyInfoService {

    private static final String DATE_FORMAT = "dd/MM/yyyy";
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(DATE_FORMAT);

    @Autowired
    protected CbrConfig cbrConfig;

    public ValCurs getCursOnDate(@Nullable LocalDate localDate) {
        String url;
        if (localDate == null) {
            url = cbrConfig.getUrl();
        } else {
            url = String.format("%s?date_req=%s", cbrConfig.getUrl(), DATE_FORMATTER.format(localDate));
        }
        String cursOnDateAsXml = getCursOnDateAsXml(url);
        return parseXml(cursOnDateAsXml);
    }

    private String getCursOnDateAsXml(String url) {
        try {
            log.info("URL execute request: {}", url);
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .build();

            HttpResponse<String> response =
                    client.send(request, HttpResponse.BodyHandlers.ofString());
            return response.body();
        } catch (Exception exception) {
            log.error("Request by URL error: {}", url, exception);
            throw new DailyInfoException("Данные из ЦБ не получены");
        }
    }

    private ValCurs parseXml(String xmlAsString) {
        try {
            return ValCursUnmarshaller.unmarshallValCurs(xmlAsString);
        } catch (JAXBException exception) {
            log.error("Error during creation of XML", exception);
            throw new ParseException("Ошибка обработки данных из ЦБ");
        }
    }
}
