package info.gupton.twitter.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import info.gupton.twitter.model.RichTweet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Brandon on 3/8/2017.
 */
@Service
@PropertySource(value = "classpath:service.properties")
public class TwitterServiceSpringSocial {

    private static final Logger LOGGER = LoggerFactory.getLogger(TwitterServiceSpringSocial.class);

    @Value("${count:10}")
    private Integer count;

    @Value("${screenName:@salesforce}")
    private String screenName;

    private final SimpMessagingTemplate messagingTemplate;
    private final Twitter twitter;

    @Autowired
    public TwitterServiceSpringSocial(SimpMessagingTemplate messagingTemplate, Twitter twitter) {
        this.messagingTemplate = messagingTemplate;
        this.twitter = twitter;
    }

    public List<RichTweet> getTweets() {
        List<RichTweet> richTweets = new ArrayList<>();
        List<Tweet> tweets = twitter.timelineOperations().getUserTimeline(screenName, count);
        for (Tweet tweet : tweets) {
            richTweets.add(new RichTweet(tweet));
        }

        return richTweets;
    }

    @Scheduled(fixedDelay = 60000)
    public void updateTweets() {
        try {
            messagingTemplate.convertAndSend("/topic/update", new ObjectMapper().writeValueAsString(getTweets()));
        } catch (JsonProcessingException e) {
            LOGGER.error("Unable to convert tweets to json string!", e);
        }
    }
}
