package com.arber.events.datamodel;

import com.arber.datamodel.OddsMetadata;

public record OddsMetadataUpdateEvent(OddsMetadata theOdds) implements SportsEvent {}