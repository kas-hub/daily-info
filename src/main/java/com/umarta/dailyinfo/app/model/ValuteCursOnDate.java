package com.umarta.dailyinfo.app.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ValuteCursOnDate {

    private String vname;

    private String vnom;

    private String vcurs;

    private String vcode;

    private String vchCode;

    private String vunitRate;
}
