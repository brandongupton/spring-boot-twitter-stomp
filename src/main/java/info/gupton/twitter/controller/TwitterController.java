package info.gupton.twitter.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import info.gupton.twitter.service.TwitterServiceSpringSocial;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class TwitterController {

    private final TwitterServiceSpringSocial twitterService;

    @Autowired
    public TwitterController(TwitterServiceSpringSocial twitterService) {
        this.twitterService = twitterService;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String getMainView(Model model) throws JsonProcessingException {
        model.addAttribute("tweets", new ObjectMapper().writeValueAsString(twitterService.getTweets()));
        return "MainView";
    }
}
