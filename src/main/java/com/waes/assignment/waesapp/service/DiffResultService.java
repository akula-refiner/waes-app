package com.waes.assignment.waesapp.service;

import com.google.gson.Gson;
import com.waes.assignment.waesapp.dto.ResultDTO;
import com.waes.assignment.waesapp.model.Attachment;
import com.waes.assignment.waesapp.model.DiffEntity;
import com.waes.assignment.waesapp.repo.DiffRepo;
import com.waes.assignment.waesapp.service.consts.MessageConsts;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static java.lang.String.format;

@Service
@Transactional
public class DiffResultService {

    @Autowired
    private DiffRepo diffRepo;

    private static final Logger logger = LoggerFactory.getLogger(DiffResultService.class);

    public ResultDTO computeResult(String correlationId) {
        Optional<DiffEntity> optional = diffRepo.findDiffEntityByCorrelationId(correlationId);
        if (!optional.isPresent()) {
            logger.warn(format("no diff entity by id = %s was found", correlationId));
            throw new ResponseStatusException(HttpStatus.NO_CONTENT);
        }

        ResultDTO resultDTO = null;

        DiffEntity diffEntity = optional.get();
        Attachment leftPart = diffEntity.getLeftPart();
        Attachment rightPart = diffEntity.getRightPart();
        if (!Objects.equals(leftPart.getLength(), rightPart.getLength())) {
            resultDTO = new ResultDTO(MessageConsts.NOT_EQUAL_LENGTH.getCode(), MessageConsts.NOT_EQUAL_LENGTH.getDescription());
        } else {
            String leftString = new String(leftPart.getOnePart());
            String rightString = new String(rightPart.getOnePart());
            if (!Objects.equals(leftString, rightString)) {
                String diffLocation = getDifferingLocation(leftString, rightString);
                resultDTO = new ResultDTO(MessageConsts.NOT_EQUAL_CONTENT.getCode(), diffLocation);
            } else {
                resultDTO = new ResultDTO(MessageConsts.EQUAL.getCode(), MessageConsts.EQUAL.getDescription());
            }
        }
        return resultDTO;
    }

    private String getDifferingLocation(String leftPart, String rightPart) {
        int size = leftPart.length(); //left and right part are of the same length;
        int diffStart = 0;
        int diffEnd = -1;
        List<DiffLocation> locations = new ArrayList<>();

        for (int i = 0; i < size; i++) {
            if (leftPart.charAt(i) != rightPart.charAt(i)) {
                if (diffEnd == -1) {
                    diffStart = i;
                }
                diffEnd = i;
            }

            if (leftPart.charAt(i) == rightPart.charAt(i) || i == size - 1) {
                if (diffEnd > -1) {
                    locations.add(new DiffLocation(diffStart, i));
                    diffEnd = -1;
                }
            }
        }
        Gson gson = new Gson();
        return gson.toJson(locations);

    }

    @Data
    @AllArgsConstructor
    class DiffLocation {
        private int start;
        private int end;


    }
}
