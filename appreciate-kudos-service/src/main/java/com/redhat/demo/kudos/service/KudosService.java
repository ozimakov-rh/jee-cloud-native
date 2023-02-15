package com.redhat.demo.kudos.service;

import com.redhat.demo.common.Kudos;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    private final static Logger log = LoggerFactory.getLogger(KudosService.class);

    @Inject
    @Named("producedRepo")
    KudosRepository kudosRepository;

    @Inject
    EventService eventService;

    public Kudos createKudos(String userFrom, String userTo, String description) {
        log.debug("Creating a kudos from {} to {} with description: {}", userFrom, userTo, description);
        var kudos = createKudosInDatabase(userFrom, userTo, description);
        log.debug("Kudos created: {}", kudos);

        // notify other components about new kudos
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
        log.debug("Listing kudos for {}", user);
        var kudosList = kudosRepository.stream()
                .filter(kudos -> kudos.getUserTo().equalsIgnoreCase(user) || kudos.getUserFrom().equalsIgnoreCase(user))
                .collect(Collectors.toList());
        log.debug("List of kudos for {} retrieved: {}", user, kudosList);
        return kudosList;
    }

    public List<Kudos> listAllKudos() {
        var kudosList = kudosRepository.list();
        log.debug("Full list of kudos retrieved: {}", kudosList);
        return kudosList;
    }

    @Transactional
    public void deleteKudos(Long id) {
        log.debug("Deleting kudos with id={}", id);
        kudosRepository.deleteById(id);
    }
}
