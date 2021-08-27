package nl.codecentric.cqrs_warehouse.projections;

import nl.codecentric.cqrs_warehouse.domain.container.ContainerMovedEvent;
import nl.codecentric.cqrs_warehouse.domain.container.ContainerUnloadedEvent;
import nl.codecentric.cqrs_warehouse.repositories.ContainerDTO;
import nl.codecentric.cqrs_warehouse.repositories.ContainerRepository;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
                .expirationDate(event.getExpirationDate())
                .isReserved(false)
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
}
