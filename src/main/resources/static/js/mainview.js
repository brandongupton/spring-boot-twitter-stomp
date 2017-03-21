var filter      = '';
var stompClient = null;

$(document).ready(function() {

	$('#filter-submit').on('click', function(e) {
		e.preventDefault();
		filter = $('#filter-text').val();
		filterTimeline();
	});

	$('#filter-reset').on('click', function() {
		filter = '';
		$('#filter-text').val(filter);
		filterTimeline();
	});

	var filterTimeline = function() {
		var filteredTweets = [];
		var timeline       = '';

		for ( var i = 0; i < tweets.length; i++ ) {
			if ( (filter.trim().length === 0) || (tweets[i].richTweet.toLowerCase().indexOf(filter.toLowerCase()) != -1 ) ) {
				filteredTweets.push(tweets[i]);
			}
		}
		if ( filteredTweets.length === 0 ) {
			timeline = '<div class="panel panel-warning"><div class="panel-heading">' +
				noresults_heading +
				'</div><div class="panel-body">' +
				noresults_body +
				'</div></div>';
		}
		for ( i = 0; i < filteredTweets.length; i++ ) {
			timeline += createTweetMarkup(filteredTweets[i]);
		}
		$('.timeline').html(timeline);
	};

	var connect = function() {
		var socket  = new SockJS('/connect');
		stompClient = Stomp.over(socket);
		stompClient.connect({}, function(frame) {
			console.log('Connected: ' + frame);
			stompClient.subscribe('/topic/update', function(data) {
				tweets = JSON.parse(data.body);
				filterTimeline();
			});
		});
        
        tweets = JSON.parse(tweets);
        filterTimeline();
	};

	var createTweetMarkup = function(tweet) {
		return '<div class="panel panel-default"><div class="panel-body"><div class="row header"><div class="col-sm-2 col-xs-4"><a href="' +
            (tweet.tweet.user.profileUrl  || "#") +
			'"><img class="profile-img img-rounded img-responsive" src="' +
			tweet.tweet.user.profileImageUrl +
			'"/></a></div><div class="col-sm-6 col-xs-4 user-info"><h4 class="user-name">' +
			tweet.tweet.user.name +
			'</h4><a class="user-handle" href="https://twitter.com/' +
			tweet.tweet.user.screenName +
			'">' +
			tweet.tweet.user.screenName +
			'</a></div><div class="col-sm-4 col-xs-4 right"><h4 class="retweet"><small>' +
			retweets_label +
			'</small><p/><span  class="retweet-count">' +
			tweet.tweet.retweetCount +
			'</span></h4></div></div><div class="row bottom"><div class="col-sm-12 status-content"><p>' +
            (tweet.richTweet || "No content") +
			'</p></div></div></div></div>';
	};

	connect();
});
