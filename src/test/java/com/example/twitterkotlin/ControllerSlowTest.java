package com.example.twitterkotlin;

import com.example.twitterkotlin.models.WordItem;
import com.google.gson.Gson;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(value = TwitterController.class, secure = false)
public class ControllerSlowTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TwitterService service;

    private List<WordItem> words;
    private String expectedJson;

    @Before
    public void init() {
        this.words = Arrays.asList(new WordItem("#bieber", new Integer(1)));
        this.expectedJson = new Gson().toJson(words);
    }


    @Test
    public void mockingTheServiceLayer() throws Exception {
        when(service.handleRequest(Mockito.anyString())).thenReturn(words);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
                "/sortedTweets?twitterTag=bieber").accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        System.out.println(result.getResponse());

        JSONAssert.assertEquals(expectedJson, result.getResponse()
                .getContentAsString(), false);

        verify(service, times(1)).handleRequest(Mockito.anyString());
    }

    @Test
    public void integrationTestingWithTheServiceLayer() throws Exception {
        when(service.handleRequest("#bieber")).thenReturn(words);

        this.mockMvc.perform(get("/sortedTweets?twitterTag=bieber"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("#bieber")));
    }

    @Test
    public void shouldVerifyBadInput() throws Exception {
        String input = "";
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
                "/sortedTweets?twitterTag=" + input).accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        assertThat(result.getResponse().getStatus()).isEqualTo(422);

        System.out.println(result.getResponse());
    }


}



