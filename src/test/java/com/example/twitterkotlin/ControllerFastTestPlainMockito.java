package com.example.twitterkotlin;

import com.example.twitterkotlin.models.WordItem;
import com.google.gson.Gson;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
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
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class ControllerFastTestPlainMockito {

    @Mock
    private TwitterService twitterService;

    @InjectMocks
    private  TwitterController twitterController;

    @Before
    public void setUp() throws Exception {
        this.twitterController = new TwitterController();
        initMocks(this);
    }


    private List<WordItem> words;
    private String expectedJson;

    @Before
    public void init() {
        this.words = Arrays.asList(new WordItem("#bieber", new Integer(1)));
        this.expectedJson = new Gson().toJson(words);

    }


    @Test
    public void mockingTheServiceLayer() throws Exception {
        when(twitterService.handleRequest(Mockito.anyString())).thenReturn(words);

        List<WordItem> result = twitterController.getTwitterTag("bieber");

        System.out.println(result);
        String json = new Gson().toJson(result);
        JSONAssert.assertEquals(expectedJson, json, false);

        verify(twitterService, times(1)).handleRequest(Mockito.anyString());
    }

}



