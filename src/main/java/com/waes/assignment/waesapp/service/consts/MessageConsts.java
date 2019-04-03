package com.waes.assignment.waesapp.service.consts;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public enum MessageConsts {

    EQUAL("equal_parts", "both parts are equal"),
    NOT_EQUAL_LENGTH("not_equal_length", "parts are not of equal length"),
    NOT_EQUAL_CONTENT("not_equal_content", "parts do not match");

    @Getter
    private String code;
    @Getter
    private String description;

}
