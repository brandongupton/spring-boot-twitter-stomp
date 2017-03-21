package info.gupton.twitter.controller;

import info.gupton.twitter.service.TwitterServiceSpringSocial;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Created by Brandon on 3/21/2017.
 */

@RunWith(MockitoJUnitRunner.class)
public class TwitterControllerTest {

    private MockMvc mvc;

    @Mock
    private TwitterServiceSpringSocial service;

    @Before
    public void setUp() throws Exception {
        mvc = MockMvcBuilders.standaloneSetup(new TwitterController(service)).build();
    }

    @Test
    public void testGetMainView() throws Exception {
        // Given
        Mockito.when(service.getTweets()).thenReturn(new ArrayList<>());

        // When
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/");

        // Then
        mvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("MainView"))
                .andExpect(model().attributeExists("tweets"));
    }
}
