package com.waes.assignment.waesapp.service;

import com.waes.assignment.waesapp.service.consts.ExceptionConsts;
import com.waes.assignment.waesapp.service.exception.DiffException;
import com.waes.assignment.waesapp.utils.ExceptionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional(rollbackFor = DiffException.class)
public class DiffValidator {

    private static final String STRING_PATTERN = "^[0-9a-fA-F]{32}$";
    private static final Logger logger = LoggerFactory.getLogger(DiffValidator.class);

    public void validatePayload(String correlationId, byte[] base64EncodedPayload) throws DiffException {
        validateCorrelationId(correlationId);
        if(base64EncodedPayload == null || base64EncodedPayload.length < 1){
            ExceptionUtil.throwDiffException(ExceptionConsts.ABSENT_PAYLOAD, logger);
        }
    }

    public void validateCorrelationId(String correlationId) throws DiffException{
        if (correlationId == null || !correlationId.matches(STRING_PATTERN)) {
            ExceptionUtil.throwDiffException(ExceptionConsts.WRONG_CORRELATION_ID_FORMAT, logger);
        }
    }
}
