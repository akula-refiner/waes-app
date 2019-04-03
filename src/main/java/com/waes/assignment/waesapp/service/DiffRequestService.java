package com.waes.assignment.waesapp.service;

import com.waes.assignment.waesapp.model.Attachment;
import com.waes.assignment.waesapp.model.DiffEntity;
import com.waes.assignment.waesapp.repo.AttachmentRepo;
import com.waes.assignment.waesapp.repo.DiffRepo;
import com.waes.assignment.waesapp.service.consts.ExceptionConsts;
import com.waes.assignment.waesapp.service.exception.DiffException;
import com.waes.assignment.waesapp.utils.Base64Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.Optional;

import static com.waes.assignment.waesapp.utils.ExceptionUtil.throwDiffException;


@Service
@Transactional(rollbackFor = DiffException.class)
public class DiffRequestService {

    @Autowired
    private DiffRepo diffRepo;
    @Autowired
    private AttachmentRepo attachmentRepo;
    @Autowired
    private Base64Util base64Util;
    private static final Logger logger = LoggerFactory.getLogger(DiffRequestService.class);

    public void saveLeftPart(String correlationId, byte[] leftBase64Encoded)  {
        byte[] left = base64Util.decode(leftBase64Encoded);
        DiffEntity diffEntity = prepareDiffEntity(correlationId);
        diffEntity.setLeftPart(createAttachment(left));
        diffRepo.save(diffEntity);
    }


    public void saveRightPart(String correlationId, byte[] rightBase64Encoded)  {
        byte[] right = base64Util.decode(rightBase64Encoded);
        DiffEntity diffEntity = prepareDiffEntity(correlationId);
        diffEntity.setRightPart(createAttachment(right));
        diffRepo.save(diffEntity);
    }

    private Attachment createAttachment(byte[] left) {
        Attachment attachment = new Attachment(new String(left).length(), left);
        attachmentRepo.save(attachment);
        return attachment;
    }

    private DiffEntity prepareDiffEntity(String correlationId) {
        Optional<DiffEntity> optional = diffRepo.findDiffEntityByCorrelationId(correlationId);
        DiffEntity diffEntity = getDiffEntity(optional);
        diffEntity.setCorrelationId(correlationId);
        return diffEntity;
    }


    private DiffEntity getDiffEntity(Optional<DiffEntity> optional) {
        DiffEntity diffEntity;
        if (optional.isPresent()) {
            diffEntity = optional.get();
        } else {
            diffEntity = new DiffEntity();
        }
        return diffEntity;
    }


}
