# Spring Boot Twitter STOMP

## Summary

This software was developed under the following general requirements:

1. Build a Twitter feed reader that shows the last 10 tweets from a specific user timeline
1. Update the list of tweets each minute, showing the 10 most recent tweets
1. Allow the user to filter the current list of tweets if the string is present anywhere in the content of a tweet
1. Display the following details:
  * User name
  * User screen name (@screen_name)
  * user profile image
  * tweet content
  * number of times message was retweeted

### Restrictions
#### Front end
* Only 3rd party libraries allowed:
  * Backbone.js
  * Underscore.js
  * jQuery

#### Back end
* Server should be runnable as a standalone process

## Implementation and Methodology
Since the primary focus for this challenge was for Java development, the back end technology was implemented using Spring Boot.  This allows the code to be contained within a single Maven project, and compiles to an executable jar using embedded Tomcat.

The application communicates with the Twitter API using the [application-only authentication](https://dev.twitter.com/oauth/application-only) method.  In order to run the application, an application key and secret must be provided.  This can be done in multiple ways:
* The _api.key_ and _api.secret_ can be defined in __app.properties__ within the project directly.  This should be followed by executing __mvn clean install__ and calling __java -jar <`application`>.jar__.
* If running the application from IntelliJ, the _api.key_ and _api.secret_ can be defined in the Run Configuration, as override parameters. (A similar method likely exists for Eclipse.)
* The parameters can be defined when executing the application by calling __java -jar -Dapi.key=<`api key`> -Dapi.secret=<`api secret`> <`application`>.jar__

The front end was implemented using a Twitter bootstrap for the design and jQuery for the development.

### Stomp Implementation
Although it violates the front end restriction about 3rd party libraries, the final design was implemented using STOMP protocol.  This was intended as a way to experiment with Stomp and websockets, and is not intended as a valid solution to the original challenge.  However, it likely would be preferable, since it eliminates the need for client-side polling.
