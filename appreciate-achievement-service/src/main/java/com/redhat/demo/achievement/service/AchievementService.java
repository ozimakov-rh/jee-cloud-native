package com.redhat.demo.achievement.service;

import com.redhat.demo.achievement.entity.Achievement;
import com.redhat.demo.common.Kudos;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

@ApplicationScoped
public class AchievementService {

    private final static Logger log = LoggerFactory.getLogger(AchievementService.class);

    @RestClient
    KudosService kudosService;

    @Inject
    AchievementRepository achievementRepository;

    public List<Achievement> listAchievements(String user) {
        log.debug("Retrieving the achievements list for user = {}", user);
        var achList = achievementRepository.stream().filter(
                achievement -> achievement.getOwner().equalsIgnoreCase(user)
        ).collect(Collectors.toList());
        log.debug("Achievements for {} retrieved: {}", user, achList);
        return achList;
    }

    @Transactional
    public List<Achievement> refreshAchievements(String user) {
        log.debug("Refreshing achievements for user {}", user);
        var kudosList = kudosService.listKudos(user);

        log.debug("Kudos for {} are: {}", user, kudosList);

        var ownKudos = kudosList.stream()
                .filter(k -> k.getUserTo().equalsIgnoreCase(user))
                .collect(Collectors.toList());


        // first kudos
        if (ownKudos.size() > 0) {
            grantAchievement(user, Achievements.FIRST_KUDOS);
        }

        // 5 kudos
        if (ownKudos.size() > 4) {
            grantAchievement(user, Achievements.FIVE_KUDOS);
        }

        // 10 kudos
        if (ownKudos.size() > 9) {
            grantAchievement(user, Achievements.TEN_KUDOS);
        }

        // sent at least one kudo
        if (kudosList.stream().anyMatch(k -> k.getUserFrom().equalsIgnoreCase(user))) {
            grantAchievement(user, Achievements.SENT_A_KUDOS);
        }

        return listAchievements(user);
    }

    @Transactional
    public Achievement grantAchievement(String user, String achievementType) {
        log.debug("Granting achievement for {}: {}", user, achievementType);

        Optional<Achievement> achievementOptional = achievementRepository.stream()
                .filter(achievement -> achievement.getOwner().equalsIgnoreCase(user))
                .filter(achievement -> achievement.getType().equals(achievementType))
                .findAny();

        if (!achievementOptional.isPresent()) {
            Achievement achievement = Achievement.builder()
                    .id(Math.abs((new Random()).nextLong()))
                    .type(achievementType)
                    .creationDate(new Date())
                    .owner(user)
                    .build();
            achievementRepository.add(achievement);
            return achievement;
        } else {
            return achievementOptional.get();
        }
    }

    public List<Achievement> listAllAchievements() {
        var achList = achievementRepository.list();
        log.debug("All achievements retrieved: {}", achList);
        return achList;
    }

    @Transactional
    public void deleteAchievement(Long id) {
        log.debug("Deleting achievements with id={}", id);
        achievementRepository.deleteById(id);
    }

}
