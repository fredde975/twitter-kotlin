package com.example.twitterkotlin;

import com.example.twitterkotlin.models.WordItem;
import com.google.gson.Gson;
import org.jetbrains.annotations.NotNull;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class TwitterServiceFastTest {

    @Mock
    private TwitterRepository twitterRepository;

    @InjectMocks
    private  TwitterService twitterService;

    @Before
    public void setUp() throws Exception {
        this.twitterService = new TwitterService();
        initMocks(this);
    }

    @Test
    public void shouldRetrieveASortedList() throws Exception{
        Map<String, WordItem> resultMap = createResultMap();

        when(twitterRepository.getTweets(Mockito.any(), Mockito.any())).thenReturn(resultMap);

        List<WordItem> result = twitterService.handleRequest("#bieber");
        System.out.println(new Gson().toJson(result));

    }

    @NotNull
    private Map<String, WordItem> createResultMap() {
        Map<String, WordItem> resultMap = new HashMap<>();
        WordItem bieber = new WordItem("#bieber", 1);
        WordItem metoo = new WordItem("#metoo", 3);
        WordItem sweden = new WordItem("#sweden", 2);
        resultMap.put("#bieber", bieber);
        resultMap.put("#metoo", metoo);
        resultMap.put("#sweden", sweden);
        return resultMap;
    }
}