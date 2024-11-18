package com.umarta.dailyinfo.app.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@Schema(description = "Ошибка API")
public class ApiErrorDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 8214524316983337616L;

    @Schema(description = "Текст ошибки")
    private String message;
}