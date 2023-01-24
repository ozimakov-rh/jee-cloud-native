package com.redhat.demo.kudos.event;

import com.redhat.demo.kudos.entity.Kudos;

import java.util.Date;

public record KudosCreatedEvent(Kudos kudos) {

}
