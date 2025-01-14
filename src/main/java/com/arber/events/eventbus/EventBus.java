package com.arber.events.eventbus;

import com.arber.events.datamodel.SportsEvent;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;

public class EventBus {
    private final List<Consumer<SportsEvent>> theSubscribers = new CopyOnWriteArrayList<>();

    public void subscribe(Consumer<SportsEvent> aSubscriber) {
        theSubscribers.add(aSubscriber);
    }

    public void unsubscribe(Consumer<SportsEvent> aSubscriber) {
        theSubscribers.remove(aSubscriber);
    }

    public void publish(SportsEvent anEvent) {
        for (Consumer<SportsEvent> mySubscriber : theSubscribers) {
            mySubscriber.accept(anEvent);
        }
    }
}

