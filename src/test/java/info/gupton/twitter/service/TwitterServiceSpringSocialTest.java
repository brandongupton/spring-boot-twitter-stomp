package info.gupton.twitter.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import info.gupton.twitter.model.RichTweet;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Answers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

/**
 * Created by Brandon on 3/21/2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class TwitterServiceSpringSocialTest {

    @Mock
    private SimpMessagingTemplate template;

    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    private Twitter twitter;

    private TwitterServiceSpringSocial service;

    @Before
    public void setup() {
        service = new TwitterServiceSpringSocial(template, twitter);
        ReflectionTestUtils.setField(service, "count", 10);
        ReflectionTestUtils.setField(service, "screenName", "salesforce");
    }

    @Test
    public void testGetTweets() throws Exception {
        // Given
        when(twitter.timelineOperations().getUserTimeline(anyString(), anyInt())).thenReturn(new ArrayList<>());

        // When
        List<RichTweet> richTweets = service.getTweets();

        // Then
        assertNotNull(richTweets);
    }

    @Test
    public void testUpdateTweets() throws Exception {
        // Given
        when(twitter.timelineOperations().getUserTimeline(anyString(), anyInt())).thenReturn(new ArrayList<>());

        // When
        service.updateTweets();

        // Then
        Mockito.verify(template).convertAndSend("/topic/update", new ObjectMapper().writeValueAsString(new ArrayList<RichTweet>()));
    }
}
