package com.arber.events.datamodel;

import com.arber.datamodel.EventMetadata;
import com.arber.datamodel.League;

import java.util.Set;

public record EventMetadataUpdateEvent(League theLeague, Set<EventMetadata> theEventMetadata) implements SportsEvent {}
