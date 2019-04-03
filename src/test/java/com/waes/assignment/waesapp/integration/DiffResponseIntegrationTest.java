package com.waes.assignment.waesapp.integration;

import com.waes.assignment.waesapp.UUIDUtil;
import com.waes.assignment.waesapp.service.consts.MessageConsts;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Base64;
import java.util.UUID;

import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class DiffResponseIntegrationTest {

    @Autowired
    private MockMvc mockMvc;
    private static final String path = "/v1/diff/{id}";
    private static final String NOT_EQUAL_LENGTH = MessageConsts.NOT_EQUAL_LENGTH.getCode();
    private static final String NOT_EQUAL_CONTENT = MessageConsts.NOT_EQUAL_CONTENT.getCode();
    private static final String EQUAL = MessageConsts.EQUAL.getCode();

    @Test
    public void shouldReturnNoContentStatusWhenNotExistingIdPassed() throws Exception {
        mockMvc.perform(get("/v1/diff/{id}", UUIDUtil.getUUIDString())
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(status().isNoContent());
    }

    @Test
    public void shouldReturnOkAndLengthNotEqualWhenExistingIdPassed() throws Exception {
        //GIVEN
        String id = UUID.randomUUID().toString().replace("-", "");
        String leftpart = "Vladimir";
        String rightPart = "Ludmila";
        byte[] left =  Base64.getEncoder().encode(leftpart.getBytes("UTF-8"));
        byte[] right = Base64.getEncoder().encode(rightPart.getBytes("UTF-8"));
        mockMvc.perform(post(path +"/left", id)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .content(left)
                ).andExpect(status().isCreated());
        mockMvc.perform(post(path + "/right", id)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .content(right)
                ).andExpect(status().isCreated());
        //WHEN
        //THEN
        mockMvc.perform(get(path, id)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().string(containsString(NOT_EQUAL_LENGTH)))
                .andDo(print());

    }

    @Test
    public void shouldReturnOkAndContentNotEqualWhenExistingIdPassed() throws Exception {
        //GIVEN
        String id = UUID.randomUUID().toString().replace("-", "");
        String leftpart = "Vladimir goes home";
        String rightPart = "Ludmila goes house";
        byte[] left =  Base64.getEncoder().encode(leftpart.getBytes("UTF-8"));
        byte[] right = Base64.getEncoder().encode(rightPart.getBytes("UTF-8"));
        mockMvc.perform(post(path +"/left", id)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .content(left)
        ).andExpect(status().isCreated());
        mockMvc.perform(post(path + "/right", id)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .content(right)
        ).andExpect(status().isCreated());
        //WHEN
        //THEN
        mockMvc.perform(get(path, id)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().string(containsString(NOT_EQUAL_CONTENT)));

    }

    @Test
    public void shouldReturnOkAndEqualWhenExistingIdPassed() throws Exception {
        //GIVEN
        String id = UUID.randomUUID().toString().replace("-", "");
        String leftpart = "Vladimir is at home";
        String rightPart = leftpart;
        byte[] left =  Base64.getEncoder().encode(leftpart.getBytes("UTF-8"));
        byte[] right = Base64.getEncoder().encode(rightPart.getBytes("UTF-8"));
        mockMvc.perform(post(path +"/left", id)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .content(left)
        ).andExpect(status().isCreated());
        mockMvc.perform(post(path + "/right", id)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .content(right)
        ).andExpect(status().isCreated());
        //WHEN
        //THEN
        mockMvc.perform(get(path, id)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().string(containsString(EQUAL)));
    }

    @Test
    public void shouldReturnNoWhenAbsentIdPassed() throws Exception {
        //GIVEN
        String id = UUIDUtil.getUUIDString();
        String absentId = UUIDUtil.getUUIDString();
        String leftpart = "Vladimir";
        String rightPart = "Ludmila";
        byte[] left =  Base64.getEncoder().encode(leftpart.getBytes("UTF-8"));
        byte[] right = Base64.getEncoder().encode(rightPart.getBytes("UTF-8"));
        mockMvc.perform(post(path +"/left", id)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .content(left)
        ).andExpect(status().isCreated());
        mockMvc.perform(post(path + "/right", id)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .content(right)
        ).andExpect(status().isCreated());
        //WHEN
        //THEN
        mockMvc.perform(get(path, absentId)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(status().isNoContent());

    }



}
