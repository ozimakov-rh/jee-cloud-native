package com.redhat.demo.achievement.event;

import com.redhat.demo.achievement.service.AchievementService;
import com.redhat.demo.common.Kudos;
import com.redhat.demo.common.KudosCreatedEvent;
import org.eclipse.microprofile.faulttolerance.Retry;
import org.eclipse.microprofile.reactive.messaging.Incoming;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

@ApplicationScoped
public class KudosEventConsumer {

    @Inject
    AchievementService achievementService;

    @Incoming("kudos-in")
    @Retry(delay = 10, maxRetries = 5)
    @Transactional
    public void consume(KudosCreatedEvent kudosEvent) {
        achievementService.refreshAchievements(kudosEvent.kudos().getUserFrom());
        achievementService.refreshAchievements(kudosEvent.kudos().getUserTo());
    }

}
