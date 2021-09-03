package nl.codecentric.cqrs_warehouse.projections;

import nl.codecentric.cqrs_warehouse.domain.container.*;
import nl.codecentric.cqrs_warehouse.repositories.ContainerDTO;
import nl.codecentric.cqrs_warehouse.repositories.ContainerRepository;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@ProcessingGroup("ContainerProjection")
public class ContainerProjection {

    @Autowired
    ContainerRepository containerRepository;

    @EventHandler
    public void on(ContainerUnloadedEvent event) {
        containerRepository.save(ContainerDTO.builder()
                .containerId(event.getContainerId().toString())
                .articleId(event.getArticleId().toString())
                .expirationDate(event.getExpirationDate())
                .isReserved(false)
                .articleName(event.getArticleName())
                .location(event.getLocation())
                .build());
    }

    @EventHandler
    public void on(ContainerMovedEvent event) {
        Optional<ContainerDTO> container = containerRepository.findById(event.getContainerId().toString());

        if (container.isPresent()) {
            ContainerDTO containerDTO = container.get().toBuilder().location(event.getLocation()).build();
            containerRepository.save(containerDTO);
        }
    }

    @EventHandler
    public void on(ContainerLoadedEvent event) {
        Optional<ContainerDTO> container = containerRepository.findById(event.getContainerId().toString());

        if (container.isPresent()) {
            ContainerDTO containerDTO = container.get().toBuilder().location("In truck with id: " + event.getTruckId()).build();
            containerRepository.save(containerDTO);
        }
    }

    @EventHandler
    public void on(ContainerClaimedEvent event) {
        Optional<ContainerDTO> container = containerRepository.findById(event.getContainerId().toString());

        if (container.isPresent()) {
            ContainerDTO containerDTO = container.get().toBuilder().isReserved(true).build();
            containerRepository.save(containerDTO);
        }
    }

    @EventHandler
    public void on(ContainerUnclaimedEvent event) {
        Optional<ContainerDTO> container = containerRepository.findById(event.getContainerId().toString());

        if (container.isPresent()) {
            ContainerDTO containerDTO = container.get().toBuilder().isReserved(false).build();
            containerRepository.save(containerDTO);
        }
    }

    @EventHandler
    public void on(ContainerExpiredEvent event) {
        containerRepository.deleteById(event.getContainerId().toString());
    }

    @QueryHandler
    public List<ContainerDTO> on(FetchAllContainersByArticleQuery query) {
        return containerRepository.findByArticleId(query.getArticleId());
    }

    @QueryHandler
    public List<ContainerDTO> on(FetchAllContainersQuery query) {
        return containerRepository.findAll();
    }

    @QueryHandler
    public Optional<ContainerDTO> on(FetchContainerByIdQuery query) {
        return containerRepository.findById(query.getContainerId());
    }
}
