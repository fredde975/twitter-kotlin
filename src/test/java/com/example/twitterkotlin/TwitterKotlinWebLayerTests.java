package com.example.twitterkotlin;

import com.example.twitterkotlin.exceptions.TagInputException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@WebMvcTest
public class TwitterKotlinWebLayerTests {

   @Autowired
   private MockMvc mockMvc;

   @Test
   public void shouldReturnDefaultMessage() throws Exception {
      this.mockMvc.perform(get("/sortedTweets?twitterTag=bieber")).andDo(print()).andExpect(status().isOk())
              .andExpect(content().string(containsString("#bieber")));
   }
}
