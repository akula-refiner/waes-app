package com.waes.assignment.waesapp.utils;

import com.waes.assignment.waesapp.service.consts.ExceptionConsts;
import com.waes.assignment.waesapp.service.exception.DiffException;
import org.slf4j.Logger;

public final class ExceptionUtil {

    public static DiffException throwDiffException(ExceptionConsts exceptionConsts, Logger logger, Exception e) throws DiffException {
        String message = String.format("%s - %s ", exceptionConsts.getCode(), exceptionConsts.getDescription());
        logger.error(message, e);
        throw new DiffException(message);
    }

    public static DiffException throwDiffException(ExceptionConsts exceptionConsts, Logger logger) throws DiffException {
        String message = String.format("%s - %s ", exceptionConsts.getCode(), exceptionConsts.getDescription());
        logger.error(message);
        throw new DiffException(message);
    }


}
