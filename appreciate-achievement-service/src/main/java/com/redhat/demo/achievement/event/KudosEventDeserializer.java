package com.redhat.demo.achievement.event;

import com.redhat.demo.common.KudosCreatedEvent;
import io.quarkus.kafka.client.serialization.ObjectMapperDeserializer;

public class KudosEventDeserializer extends ObjectMapperDeserializer<KudosCreatedEvent> {
    public KudosEventDeserializer() {
        super(KudosCreatedEvent.class);
    }
}
