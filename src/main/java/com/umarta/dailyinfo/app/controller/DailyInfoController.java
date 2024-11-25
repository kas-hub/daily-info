package com.umarta.dailyinfo.app.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.umarta.dailyinfo.app.exception.ParseException;
import com.umarta.dailyinfo.app.model.ValCurs;
import com.umarta.dailyinfo.app.model.ValuteData;
import com.umarta.dailyinfo.app.service.DailyInfoService;
import com.umarta.dailyinfo.app.service.DailyInfoSoapService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@Slf4j
@RestController
@RequestMapping(path = "/api/v1/dailyinfo")
public class DailyInfoController implements DailyInfoApi {

    @Autowired
    protected DailyInfoService dailyInfoService;
    @Autowired
    protected DailyInfoSoapService dailyInfoSoapService;

    @Override
    public ResponseEntity<?> getCursOnDate(LocalDate date) {
        log.info("getCursOnDate, date:{}", date);
        ValCurs valCurs = dailyInfoService.getCursOnDate(date);

        try {
            String jsonValCurs = new ObjectMapper().writeValueAsString(valCurs);
            return ResponseEntity.ok()
                    .body(jsonValCurs);
        } catch (JsonProcessingException exception) {
            log.error("Error during creation of JSON", exception);
            throw new ParseException("Ошибка обработки данных из ЦБ");
        }
    }

    @Override
    public ResponseEntity<?> getCursOnDateSoap(LocalDate date) {
        log.info("getCursOnDateSoap, date:{}", date);
        ValuteData valuteData = dailyInfoSoapService.getCursOnDate(date);

        try {
            String jsonValuteData = new ObjectMapper().writeValueAsString(valuteData);
            return ResponseEntity.ok()
                    .body(jsonValuteData);
        } catch (JsonProcessingException exception) {
            log.error("Error during creation of JSON", exception);
            throw new ParseException("Ошибка обработки данных из ЦБ");
        }
    }
}
