package com.redhat.demo.kudos.service;

import io.quarkus.runtime.StartupEvent;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Named;

@ApplicationScoped
public class KudosRepositoryProducer {

    private final static Logger log = LoggerFactory.getLogger(KudosRepositoryProducer.class);

    @Inject
    @ConfigProperty(name = "kudos.repository")
    String kudosRepositoryProducerName;

    @Inject
    JpaKudosRepoImpl jpaKudosRepo;

    @Inject
    FileSystemKudosRepoImpl fileSystemKudosRepo;

    private KudosRepository _repository;

    void startup(@Observes StartupEvent event) {
        switch (kudosRepositoryProducerName) {
            case "jpa":
                this._repository = jpaKudosRepo;
                break;
            case "file":
                this._repository = fileSystemKudosRepo;
                break;
            default:
                throw new RuntimeException("Kudos repository implementation is not configured (kudos.repository)");
        }
    }

    @ApplicationScoped
    @Named("producedRepo")
    KudosRepository produceRepository() {
        return _repository;
    }

}
