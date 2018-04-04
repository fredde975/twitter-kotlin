package com.example.twitterkotlin;

import com.example.twitterkotlin.exceptions.TagInputException;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.assertj.core.api.Assertions.assertThat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TwitterKotlinApplicationTests {

 /*   @MockBean
    private TwitterController twitterController;
*/
    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;


    @Test
    public void contextLoads() {
    }

    @Test (expected = TagInputException.class)
    public void shouldThrowTagInputException() throws Exception{
        throw new TagInputException("A kotlin exception");
    }

    @Test
    public void shouldTestTheInput() throws Exception{
        //assertThat(twitterController ).isNotNull();

        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/sortedTweets?twitterTag=bieber",
                String.class)).contains("#");
    }

}
