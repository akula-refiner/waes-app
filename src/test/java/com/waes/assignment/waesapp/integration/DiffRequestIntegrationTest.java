package com.waes.assignment.waesapp.integration;

import com.waes.assignment.waesapp.UUIDUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Base64;

import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class DiffRequestIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void shouldGetCreatedStatusWhenSendEncodedByteArrayToSaveLeftPart() throws Exception{
        String bla = "ALibek";
        byte[] binArray =  Base64.getEncoder().encode(bla.getBytes("UTF-8"));
        mockMvc.perform(post("/v1/diff/{id}/left", UUIDUtil.getUUIDString())
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .content(binArray))
                .andExpect(status().isCreated())
                .andExpect(content().string(containsString("")));
    }

    @Test
    public void shouldGetCreatedStatusWhenSendEncodedByteArrayToSaveRightPart() throws Exception{
        String bla = "Arnito";
        byte[] binArray =  Base64.getEncoder().encode(bla.getBytes("UTF-8"));
        mockMvc.perform(post("/v1/diff/{id}/right", UUIDUtil.getUUIDString())
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .content(binArray))
                .andExpect(status().isCreated())
                .andExpect(content().string(containsString("")));
    }
}
