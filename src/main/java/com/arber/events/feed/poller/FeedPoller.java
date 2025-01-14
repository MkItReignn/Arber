package com.arber.events.feed.poller;

public interface FeedPoller {
    void pollMetadata();
    void pollOdds();
}
