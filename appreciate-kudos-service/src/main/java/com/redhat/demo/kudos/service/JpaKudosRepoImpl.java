package com.redhat.demo.kudos.service;

import com.redhat.demo.common.Kudos;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;
import java.util.stream.Stream;

@ApplicationScoped
public class JpaKudosRepoImpl implements KudosRepository {

    @PersistenceContext
    EntityManager em;

    @Override
    public void add(Kudos kudos) {
        em.persist(kudos);
    }

    @Override
    public Stream<Kudos> stream() {
        return this.list().stream();
    }

    @Override
    public List<Kudos> list() {
        Query query = em.createQuery("select k from Kudos k order by k.creationDate desc", Kudos.class);
        return query.getResultList();
    }

    @Override
    public void deleteById(Long id) {
        Query query = em.createQuery("delete from Kudos k where k.id=" + id);
        query.executeUpdate();
    }
}
