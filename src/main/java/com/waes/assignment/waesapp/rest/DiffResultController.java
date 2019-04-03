package com.waes.assignment.waesapp.rest;

import com.waes.assignment.waesapp.dto.ResultDTO;
import com.waes.assignment.waesapp.service.DiffResultService;
import com.waes.assignment.waesapp.service.DiffValidator;
import com.waes.assignment.waesapp.service.exception.DiffException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/***
 * Rest controller that provides a result on a end
 * point  <host>/v1/diff/<ID>
 * • The results shall provide the following info in JSON format
 * o If equal return that equal
 * o If not of equal size just return that not equal size
 * o If of same size provide insight in where the diffs are, actual diffs are not needed.
 * So mainly offsets + length in the data
 */
@RestController
@RequestMapping(value = "/v1/diff/{id}",
        consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
        produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Transactional(rollbackFor = DiffException.class)
public class DiffResultController {

    @Autowired
    private DiffResultService resultService;
    @Autowired
    private DiffValidator diffValidator;

    /***
     * Provides results depending on the content of passed left and right part of the Diff.
     * @param correlationId - 32 symbolic String in format "^[0-9a-fA-F]{32}$" without "-" <a href="https://en.wikipedia.org/wiki/Universally_unique_identifier">wiki</a>
     * @return the result whether left and right part are equal or differ and if differ at which place exactly.
     * @throws DiffException
     * @see {@link com.waes.assignment.waesapp.rest.DiffRequestController}
     */
    @GetMapping
    public ResponseEntity<ResultDTO> getResult(@PathVariable(name = "id") String correlationId) throws DiffException{
        diffValidator.validateCorrelationId(correlationId);
        ResultDTO resultDTO = resultService.computeResult(correlationId);
        ResponseEntity<ResultDTO> response = new ResponseEntity<>(resultDTO, HttpStatus.OK);
        return response;
    }

}
