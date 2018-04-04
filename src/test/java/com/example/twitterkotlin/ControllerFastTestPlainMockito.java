package com.example.twitterkotlin;

import com.example.twitterkotlin.models.WordItem;
import com.google.gson.Gson;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;


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



