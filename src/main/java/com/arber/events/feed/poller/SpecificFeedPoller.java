package com.arber.events.feed.poller;

import com.arber.api.SportsApiClient;
import com.arber.cache.MetadataCache;
import com.arber.datamodel.OddsMetadata;
import com.arber.datamodel.OddsMetadataId;
import com.arber.events.eventbus.EventBus;
import com.arber.events.feed.config.SpecificFeedServiceConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ConcurrentMap;

public class SpecificFeedPoller implements FeedPoller {
    private final Logger theLogger = LoggerFactory.getLogger(SpecificFeedPoller.class);
    private final SportsApiClient theSportsApiClient;
    private final EventBus theEventBus;
    private final MetadataCache theMetadataCache;
    private final ConcurrentMap<OddsMetadataId, OddsMetadata> theOddsMetadataCache;
    private final SpecificFeedServiceConfig theConfig;

    public SpecificFeedPoller(SportsApiClient aSportsApiClient,
                               EventBus anEventBus,
                               MetadataCache aMetadataCache,
                               ConcurrentMap<OddsMetadataId, OddsMetadata> anOddsMetadataCache,
                               SpecificFeedServiceConfig aConfig) {
        this.theSportsApiClient = aSportsApiClient;
        this.theEventBus = anEventBus;
        this.theMetadataCache = aMetadataCache;
        this.theOddsMetadataCache = anOddsMetadataCache;
        this.theConfig = aConfig;
    }

    // TODO: Implement function
    @Override
    public void pollMetadata() {
        throw new RuntimeException("Not implemented yet");
    }

    // TODO: Implement function
    @Override
    public void pollOdds() {
        throw new RuntimeException("Not implemented yet");
    }
}
