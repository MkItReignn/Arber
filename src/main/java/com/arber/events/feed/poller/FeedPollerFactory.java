package com.arber.events.feed.poller;

import com.arber.events.feed.config.FeedServiceConfig;
import com.arber.events.feed.config.SpecificFeedServiceConfig;
import com.arber.events.feed.config.UniversalFeedServiceConfig;
import com.arber.api.SportsApiClient;
import com.arber.events.eventbus.EventBus;
import com.arber.cache.MetadataCache;
import com.arber.datamodel.OddsMetadata;
import com.arber.datamodel.OddsMetadataId;

import java.util.concurrent.ConcurrentMap;

public class FeedPollerFactory {
    private final SportsApiClient theSportsApiClient;
    private final EventBus theEventBus;
    private final MetadataCache theMetadataCache;
    private final ConcurrentMap<OddsMetadataId, OddsMetadata> theOddsMetadataCache;

    public FeedPollerFactory(SportsApiClient aSportsApiClient,
                             EventBus anEventBus,
                             MetadataCache aMetadataCache,
                             ConcurrentMap<OddsMetadataId, OddsMetadata> anOddsMetadataCache) {
        this.theSportsApiClient = aSportsApiClient;
        this.theEventBus = anEventBus;
        this.theMetadataCache = aMetadataCache;
        this.theOddsMetadataCache = anOddsMetadataCache;
    }

    public FeedPoller createFeedPoller(FeedServiceConfig aConfig) {
        if (aConfig instanceof SpecificFeedServiceConfig) {
            return createSpecificFeedPoller((SpecificFeedServiceConfig) aConfig);
        } else if (aConfig instanceof UniversalFeedServiceConfig) {
            return createUniversalFeedPoller((UniversalFeedServiceConfig) aConfig);
        } else {
            throw new IllegalArgumentException("Unsupported FeedServiceConfig type: " + aConfig.getClass().getName());
        }
    }

    private FeedPoller createSpecificFeedPoller(SpecificFeedServiceConfig aSpecificConfig) {
        return new SpecificFeedPoller(
                theSportsApiClient,
                theEventBus,
                theMetadataCache,
                theOddsMetadataCache,
                aSpecificConfig
        );
    }

    private FeedPoller createUniversalFeedPoller(UniversalFeedServiceConfig aUniversalConfig) {
        return new UniversalFeedPoller(
                theSportsApiClient,
                theEventBus,
                theMetadataCache,
                theOddsMetadataCache,
                aUniversalConfig
        );
    }
}