package com.redhat.demo.kudos.service;

import com.redhat.demo.kudos.entity.Kudos;
import com.redhat.demo.kudos.event.KudosCreatedEvent;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@ApplicationScoped
public class KudosService {

    @Inject
    @Named("producedRepo")
    KudosRepository kudosRepository;

    @Inject
    EventService eventService;

    public Kudos createKudos(String userFrom, String userTo, String description) {
        var kudos = createKudosInDatabase(userFrom, userTo, description);
        eventService.publishKudosCreatedEvent(kudos);
        return kudos;
    }

    @Transactional
    Kudos createKudosInDatabase(String userFrom, String userTo, String description) {
        Kudos kudos = Kudos.builder()
                .id(Math.abs((new Random()).nextLong()))
                .userFrom(userFrom)
                .userTo(userTo)
                .description(description)
                .creationDate(new Date())
                .build();
        kudosRepository.add(kudos);
        return kudos;
    }

    public List<Kudos> listKudos(String user) {
        return kudosRepository.stream()
                .filter(kudos -> kudos.getUserTo().equalsIgnoreCase(user) || kudos.getUserFrom().equalsIgnoreCase(user))
                .collect(Collectors.toList());
    }

    public List<Kudos> listAllKudos() {
        return kudosRepository.list();
    }

    @Transactional
    public void deleteKudos(Long id) {
        kudosRepository.deleteById(id);
    }
}
