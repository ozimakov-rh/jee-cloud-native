package com.redhat.demo.achievement.service;

import com.redhat.demo.achievement.entity.Achievement;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;
import java.util.stream.Stream;

@ApplicationScoped
public class JpaAchievementsRepoImpl implements AchievementRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Stream<Achievement> stream() {
        return this.list().stream();
    }

    @Override
    public List<Achievement> list() {
        Query query = em.createQuery("select a from Achievement a order by a.creationDate desc", Achievement.class);
        return query.getResultList();
    }

    @Override
    public void add(Achievement achievement) {
        em.persist(achievement);
    }

    @Override
    public void deleteById(Long id) {
        Query query = em.createQuery("delete from Achievement where id=" + id);
        query.executeUpdate();
    }

//    @Override
//    public void add(Kudos kudos) {
//        em.persist(kudos);
//    }
//
//    @Override
//    public Stream<Kudos> stream() {
//        return this.list().stream();
//    }
//
//    @Override
//    public List<Kudos> list() {
//        Query query = em.createQuery("from Kudos k order by k.creationDate desc", Kudos.class);
//        return query.getResultList();
//    }
//
//    @Override
//    public void deleteById(Long id) {
//        Query query = em.createQuery("delete from Kudos k where k.id=" + id);
//        query.executeUpdate();
//    }
}
