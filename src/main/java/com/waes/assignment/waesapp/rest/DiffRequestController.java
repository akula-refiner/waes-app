package com.waes.assignment.waesapp.rest;

import com.waes.assignment.waesapp.service.DiffRequestService;
import com.waes.assignment.waesapp.service.DiffValidator;
import com.waes.assignment.waesapp.service.exception.DiffException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;


/***
 * Rest controller that provides 2 http endpoints that accepts JSON base64 encoded binary data on both
 * endpoints  <host>/v1/diff/<ID>/left and <host>/v1/diff/<ID>/right.
 * The provided data needs to be diff-ed and the results shall be available on a third end
 * point <host>/v1/diff/<ID>
 *
 */


@RestController
@RequestMapping(value = "/v1/diff/{id}/",
        consumes = MediaType.APPLICATION_OCTET_STREAM_VALUE)
@Transactional
public class DiffRequestController {

    @Autowired
    private DiffRequestService diffRequestService;
    @Autowired
    private DiffValidator diffValidator;
    private static final Logger logger = LoggerFactory.getLogger(DiffRequestController.class);

    /***
     * This service is used to save left part of the entity.
     * If Diff is already exists by {id} - left part is updated,
     * otherwise new Diff is created and left part is set.
     *
     * @param correlationId - 32 symbolic String in format "^[0-9a-fA-F]{32}$" without "-" <a href="https://en.wikipedia.org/wiki/Universally_unique_identifier">wiki</a>
     * @param leftBase64Encoded - byte array encoded into base64 passed as MediaType.APPLICATION_OCTET_STREAM_VALUE
     * @see <a href="https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/http/MediaType.html">MediaType</a>
     * @throws DiffException
     */
    @PostMapping(path = "left")
    @ResponseStatus(code = HttpStatus.CREATED)
    public void saveLeftPart(@PathVariable("id") String correlationId, @RequestBody byte[] leftBase64Encoded) throws DiffException {
        diffValidator.validatePayload(correlationId, leftBase64Encoded);

        diffRequestService.saveLeftPart(correlationId, leftBase64Encoded);
    }

    /***
     * This service is used to save right part of the entity.
     * If Diff is already exists by {id} - right part is updated,
     * otherwise new Diff is created and right part is set.
     *
     * @param correlationId - 32 symbolic String in format "^[0-9a-fA-F]{32}$" without "-" <a href="https://en.wikipedia.org/wiki/Universally_unique_identifier">wiki</a>
     * @param leftBase64Encoded - byte array encoded into base64 passed as MediaType.APPLICATION_OCTET_STREAM_VALUE
     * @see <a href="https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/http/MediaType.html">MediaType</a>
     * @throws DiffException
     */

    @PostMapping(path = "right")
    @ResponseStatus(code = HttpStatus.CREATED)
    public void saveRightPart(@PathVariable("id") String correlationid, @RequestBody byte[] rightBase64Encoded) throws DiffException{
        diffValidator.validatePayload(correlationid, rightBase64Encoded);

        diffRequestService.saveRightPart(correlationid, rightBase64Encoded);
    }
}
