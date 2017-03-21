package info.gupton.twitter.model;

import org.springframework.social.twitter.api.*;

/**
 * Created by Brandon on 3/16/2017.
 */
public class RichTweet {
    private Tweet tweet;
    private String richTweet;

    public RichTweet(Tweet tweet) {
        this.tweet = tweet;
        this.richTweet = createRichContent();
    }

    public Tweet getTweet() {
        return tweet;
    }

    public String getRichTweet() {
        return richTweet;
    }

    private String createRichContent() {
        Entities entities = tweet.getEntities();
        String richText = tweet.getText();
        richText = embedUrls(entities, richText);
        richText = embedUserMentions(entities, richText);
        richText = embedMedia(entities, richText);
        richText = embedHashtags(entities, richText);

        return richText;
    }

    private String embedUrls(Entities entities, String richText) {
        for (UrlEntity url : entities.getUrls()) {
            StringBuffer sb = new StringBuffer("<a href=\"");
            sb.append(url.getUrl());
            sb.append("\">");
            sb.append(url.getDisplayUrl());
            sb.append("</a>");

            richText = richText.replace(url.getUrl(), sb.toString());
        }

        return richText;
    }

    private String embedUserMentions(Entities entities, String richText) {
        for (MentionEntity mention : entities.getMentions()) {
            StringBuffer sb = new StringBuffer("<a href=\"https://twitter.com/");
            sb.append(mention.getScreenName());
            sb.append("\">@");
            sb.append(mention.getScreenName());
            sb.append("</a>");

            richText = richText.replace("@" + mention.getScreenName(), sb.toString());
        }

        return richText;
    }

    private String embedHashtags(Entities entities, String richText) {
        for (HashTagEntity tag : entities.getHashTags()) {
            StringBuffer sb = new StringBuffer("<a href=\"https://twitter.com/hashtag/");
            sb.append(tag.getText());
            sb.append("?src=hash\">#");
            sb.append(tag.getText());
            sb.append("</a>");

            richText = richText.replace("#" + tag.getText(), sb.toString());
        }

        return richText;
    }

    private String embedMedia(Entities entities, String richText) {
        for (MediaEntity media : entities.getMedia()) {
            StringBuffer sb = new StringBuffer("<br/><img class=\"img-responsive\" src=\"");
            sb.append(media.getMediaUrl());
            sb.append("\"/><br/>");

            richText = richText.replace(media.getUrl(), sb.toString());
        }

        return richText;
    }
}
