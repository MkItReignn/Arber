package com.arber.events.feed;

import com.arber.events.feed.poller.FeedPoller;

import java.util.concurrent.*;

public class FeedService {
    private final ScheduledExecutorService theScheduler = Executors.newSingleThreadScheduledExecutor();
    private final ScheduleConfig theMetadataScheduleConfig;
    private final ScheduleConfig theOddsScheduleConfig;
    private final FeedPoller theFeedPoller;

    private FeedService(ScheduleConfig aMetadataScheduleConfig, ScheduleConfig anOddsScheduleConfig,
                        FeedPoller aFeedPoller) {
        theMetadataScheduleConfig = aMetadataScheduleConfig;
        theOddsScheduleConfig = anOddsScheduleConfig;
        theFeedPoller = aFeedPoller;
    }

    public void start() {
        theScheduler.scheduleAtFixedRate(
                theFeedPoller::pollMetadata,
                theMetadataScheduleConfig.theInitialDelay(),
                theMetadataScheduleConfig.thePeriod(),
                theMetadataScheduleConfig.theUnit());
        theScheduler.scheduleAtFixedRate(
                theFeedPoller::pollOdds,
                theOddsScheduleConfig.theInitialDelay(),
                theOddsScheduleConfig.thePeriod(),
                theOddsScheduleConfig.theUnit());
    }
}
