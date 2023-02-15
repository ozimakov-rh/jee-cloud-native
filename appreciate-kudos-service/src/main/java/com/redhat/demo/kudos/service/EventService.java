package com.redhat.demo.kudos.service;

import com.redhat.demo.common.Kudos;
import com.redhat.demo.common.KudosCreatedEvent;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class EventService {

    private final static Logger log = LoggerFactory.getLogger(EventService.class);

    @Inject
    @Channel("kudos-out")
    Emitter<KudosCreatedEvent> emitter;

    public void publishKudosCreatedEvent(Kudos kudos) {
        var kudosEvent = new KudosCreatedEvent(kudos);
        log.debug("Publishing an event: {}", kudosEvent);
        emitter.send(kudosEvent);
    }

}
