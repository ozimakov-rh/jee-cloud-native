package com.redhat.demo.kudos.service;

import com.redhat.demo.common.Kudos;
import com.redhat.demo.common.KudosCreatedEvent;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class EventService {

    @Inject
    @Channel("kudos-out")
    Emitter<KudosCreatedEvent> emitter;

    public void publishKudosCreatedEvent(Kudos kudos) {
        emitter.send(new KudosCreatedEvent(kudos));
    }

}
