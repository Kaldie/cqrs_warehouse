package nl.codecentric.cqrs_warehouse.domain.article;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import lombok.extern.slf4j.Slf4j;
import nl.codecentric.cqrs_warehouse.domain.container.*;
import nl.codecentric.cqrs_warehouse.domain.shipment.InitialiseShipmentCommand;
import nl.codecentric.cqrs_warehouse.domain.shipment.ShipmentInitialisedEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.modelling.command.AggregateMember;
import org.axonframework.modelling.command.ForwardMatchingInstances;
import org.axonframework.spring.stereotype.Aggregate;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Slf4j
public class ArticleAggregate {
    private UUID id;
    private String name;

    private Map<UUID, Container> containers;

    private UUID findOldestAvailableContainer() {
        return this.containers.values().stream()
                .filter(container -> !container.getIsReserved())
                .min(Comparator.comparingLong(container -> container.getExpirationDate().getEpochSecond())).get().getContainerId();
    }

    private boolean hasAvailableContainers() {
        return !this.containers.isEmpty() &&
                this.containers.values().stream()
                        .anyMatch(container -> !container.getIsReserved() &&
                                container.getExpirationDate().isAfter(Instant.now().plus(5, ChronoUnit.DAYS)));
    }

    private boolean isContainerClaimed(UUID containerId) {
        return this.containers.containsKey(containerId) && this.containers.get(containerId).getIsReserved();
    }

}
