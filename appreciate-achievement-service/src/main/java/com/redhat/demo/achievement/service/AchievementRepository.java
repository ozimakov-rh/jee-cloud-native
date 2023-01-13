package com.redhat.demo.achievement.service;

import com.redhat.demo.achievement.entity.Achievement;

import java.util.List;
import java.util.stream.Stream;

public interface AchievementRepository {

    Stream<Achievement> stream();
    List<Achievement> list();

    void add(Achievement achievement);

    void deleteById(Long id);
}
