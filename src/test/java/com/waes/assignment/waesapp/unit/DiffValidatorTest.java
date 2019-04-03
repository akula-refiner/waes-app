package com.waes.assignment.waesapp.unit;

import com.waes.assignment.waesapp.UUIDUtil;
import com.waes.assignment.waesapp.service.DiffValidator;
import com.waes.assignment.waesapp.service.consts.ExceptionConsts;
import com.waes.assignment.waesapp.service.exception.DiffException;
import com.waes.assignment.waesapp.utils.Base64Util;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Base64;

@RunWith(MockitoJUnitRunner.class)
public class DiffValidatorTest {

    @InjectMocks
    private DiffValidator diffValidator;
    private Base64Util base64Util;

    private static final String WRONG_COR_ID  = ExceptionConsts.WRONG_CORRELATION_ID_FORMAT.getCode()
            + " - " + ExceptionConsts.WRONG_CORRELATION_ID_FORMAT.getDescription();
    private static final String ABSENT_PAYLOAD  = ExceptionConsts.ABSENT_PAYLOAD.getCode()
            + " - " + ExceptionConsts.ABSENT_PAYLOAD.getDescription();


    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void init(){
        base64Util = new Base64Util();
    }

    @Test
    public void shouldNotThrowExceptionWhenValidCorrelationId() throws DiffException{
        diffValidator.validateCorrelationId(UUIDUtil.getUUIDString());
    }

    @Test
    public void shouldThrowDiffExceptionWhenInvalidCorIdPassed() throws DiffException{
        expectedException.expect(DiffException.class);
        expectedException.expectMessage(WRONG_COR_ID);
        diffValidator.validateCorrelationId("");
    }
    @Test
    public void shouldThrowDiffExceptionWhenNullPassed() throws DiffException{
        expectedException.expect(DiffException.class);
        expectedException.expectMessage(WRONG_COR_ID);
        diffValidator.validateCorrelationId(null);
    }

    @Test
    public void shouldThrowDiffExceptionWhenTooLongCorrIdPassed() throws DiffException{
        String tooLongCorId = UUIDUtil.getUUIDString() + UUIDUtil.getUUIDString();
        expectedException.expect(DiffException.class);
        expectedException.expectMessage(WRONG_COR_ID);
        diffValidator.validateCorrelationId(tooLongCorId);
    }

    @Test
    public void shouldThrowDiffExceptionWhenTooShortCorrIdPassed() throws DiffException{
        String tooShortCorId = UUIDUtil.getUUIDString();
        tooShortCorId = tooShortCorId.substring(0, tooShortCorId.length() -2);
        expectedException.expect(DiffException.class);
        expectedException.expectMessage(WRONG_COR_ID);
        diffValidator.validateCorrelationId(tooShortCorId);
    }

    @Test
    public void shouldBeOkWhenAcceptablePayloadPassed()throws DiffException{
        String inputString = "Donald Trumpito";
        byte[] input = Base64.getEncoder().encode(inputString.getBytes());
        diffValidator.validatePayload(UUIDUtil.getUUIDString(),input);
    }

    @Test
    public void shouldThrowDiffExceptionWhenPayloadIsEmpty()throws DiffException{
        String inputString = "";
        expectedException.expectMessage(ABSENT_PAYLOAD);
        expectedException.expect(DiffException.class);
        diffValidator.validatePayload(UUIDUtil.getUUIDString(), inputString.getBytes());
    }

    @Test
    public void shouldThrowDiffExceptionWhenPayloadIsNull()throws DiffException{
        expectedException.expectMessage(ABSENT_PAYLOAD);
        expectedException.expect(DiffException.class);
        diffValidator.validatePayload(UUIDUtil.getUUIDString(), null);
    }






}
