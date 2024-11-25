package com.umarta.dailyinfo.app.controller;

import com.umarta.dailyinfo.app.dto.ApiErrorDTO;
import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

@Tag(name = "Сервис работы с ЦБ", description = "Предоставляет методы для работы с сервисами Центрального банка")
public interface DailyInfoApi {
    @Operation(
            summary = "Получение ежедневных курсов валют",
            description = "Обращение к сервису ЦБ для получения ежедневных курсов валют",
            externalDocs = @ExternalDocumentation(
                    description = "Ссылка на сервис ЦБ",
                    url = "https://cbr.ru/scripts/XML_daily.asp"
            )
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "JSON ежедневных курсов валют"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Некорректные параметры запроса",
                    content = @Content(schema = @Schema(implementation = ApiErrorDTO.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Данные из ЦБ не получены",
                    content = @Content(schema = @Schema(implementation = ApiErrorDTO.class))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Непредвиденная ошибка на стороне сервера",
                    content = @Content(schema = @Schema(implementation = ApiErrorDTO.class))
            )
    })
    @GetMapping(value = "valcurs")
    default ResponseEntity<?> getCursOnDate(
            @Parameter(description = "На дату (необязательный параметр, по умолч. текущая дата)")
            @DateTimeFormat(pattern = "dd-MM-yyyy") @RequestParam(value = "date", required = false) LocalDate date) {

        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

    @Operation(
            summary = "Получение ежедневных курсов валют (SOAP запрос)",
            description = "Обращение к сервису ЦБ для получения ежедневных курсов валют",
            externalDocs = @ExternalDocumentation(
                    description = "Ссылка на сервис ЦБ",
                    url = "https://www.cbr.ru/DailyInfoWebServ/DailyInfo.asmx?op=GetCursOnDate"
            )
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "JSON ежедневных курсов валют"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Некорректные параметры запроса",
                    content = @Content(schema = @Schema(implementation = ApiErrorDTO.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Данные из ЦБ не получены",
                    content = @Content(schema = @Schema(implementation = ApiErrorDTO.class))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Непредвиденная ошибка на стороне сервера",
                    content = @Content(schema = @Schema(implementation = ApiErrorDTO.class))
            )
    })
    @GetMapping(value = "valcursSoap")
    default ResponseEntity<?> getCursOnDateSoap(
            @Parameter(description = "На дату (необязательный параметр, по умолч. текущая дата)")
            @DateTimeFormat(pattern = "dd-MM-yyyy") @RequestParam(value = "date", required = false) LocalDate date) {

        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }
}
