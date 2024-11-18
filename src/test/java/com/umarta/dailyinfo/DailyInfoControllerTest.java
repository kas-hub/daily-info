package com.umarta.dailyinfo;

import com.umarta.dailyinfo.app.service.DailyInfoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;

public class DailyInfoControllerTest {

  /*  @Autowired
    private WebTestClient webTestClient;*/

    //private final ArmProperties armProperties;
    private final DailyInfoService dailyInfoService;

    @Autowired
    public DailyInfoControllerTest(DailyInfoService dailyInfoService) {
        this.dailyInfoService = dailyInfoService;
    }

    @Test
    void getCurrencyRateTest() throws Exception {
        String date = "2024-11-14";

        dailyInfoService.getCursOnDate(LocalDate.now());

        //when
        /* var result = webTestClient
                .get().uri(String.format("/api/v1/dailyinfo/%s", date))
                .accept(MediaType.APPLICATION_JSON)
                .exchange();
               .expectStatus().isOk()
                .returnResult(String.class)
                .getResponseBody()
                .blockLast();*/

    }
}
