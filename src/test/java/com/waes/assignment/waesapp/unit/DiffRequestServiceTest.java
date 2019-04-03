package com.waes.assignment.waesapp.unit;

import com.waes.assignment.waesapp.UUIDUtil;
import com.waes.assignment.waesapp.model.Attachment;
import com.waes.assignment.waesapp.repo.AttachmentRepo;
import com.waes.assignment.waesapp.repo.DiffRepo;
import com.waes.assignment.waesapp.service.DiffRequestService;
import com.waes.assignment.waesapp.service.exception.DiffException;
import com.waes.assignment.waesapp.utils.Base64Util;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Base64;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DiffRequestServiceTest {


    @Mock
    private DiffRepo diffRepo;
    @Mock
    private AttachmentRepo attachmentRepo;
    @Mock
    private Base64Util base64Util;
    @InjectMocks
    private DiffRequestService diffRequestService;

    @Test
    public void shouldDecodeWhenLeftPartIsInvoked() throws DiffException{
        //GIVEN
        String sample = "xxx";
        String correlationId = UUIDUtil.getUUIDString();
        byte[] bytes = Base64.getEncoder().encode(sample.getBytes());
        Attachment attachment = new Attachment(sample.length(), sample.getBytes());

        //WHEN
        when(base64Util.decode(bytes)).thenReturn(sample.getBytes());
        when(diffRepo.findDiffEntityByCorrelationId(correlationId)).thenReturn(Optional.empty());
        when(attachmentRepo.save(attachment)).thenReturn(attachment);
        diffRequestService.saveLeftPart(correlationId, bytes );
        //THEN
        verify(base64Util).decode(bytes);
        verify(diffRepo).findDiffEntityByCorrelationId(correlationId);
        verify(attachmentRepo).save(attachment);
    }

    @Test
    public void shouldDecodeWhenRightPartIsInvoked() throws DiffException{
        //GIVEN
        String sample = "xxxy";
        String correlationId = UUIDUtil.getUUIDString();
        byte[] bytes = Base64.getEncoder().encode(sample.getBytes());
        Attachment attachment = new Attachment(sample.length(), sample.getBytes());

        //WHEN
        when(base64Util.decode(bytes)).thenReturn(sample.getBytes());
        when(diffRepo.findDiffEntityByCorrelationId(correlationId)).thenReturn(Optional.empty());
        when(attachmentRepo.save(attachment)).thenReturn(attachment);
        diffRequestService.saveRightPart(correlationId, bytes );
        //THEN
        verify(base64Util).decode(bytes);
        verify(diffRepo).findDiffEntityByCorrelationId(correlationId);
        verify(attachmentRepo).save(attachment);
    }


}
