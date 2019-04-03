package com.waes.assignment.waesapp.service.consts;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public enum ExceptionConsts {

    ABSENT_PAYLOAD("absent_payload", "payload of the diff is absent"),
    DECODING_EXCEPTION("decoding_exception", "exception occurred while decoding from base64"),
    WRONG_CORRELATION_ID_FORMAT("wrong_id_format", "wrong id format passed");


    @Getter
    private String code;
    @Getter
    private String description;


}
