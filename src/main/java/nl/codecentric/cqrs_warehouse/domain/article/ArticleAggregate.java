package nl.codecentric.cqrs_warehouse.domain.article;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import nl.codecentric.cqrs_warehouse.domain.container.*;
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
@Aggregate
public class ArticleAggregate {
    @AggregateIdentifier
    private UUID id;
    private String name;

    // used also to check stock
    @AggregateMember(eventForwardingMode = ForwardMatchingInstances.class)
    private Map<UUID, Container> containers;

    @CommandHandler
    public ArticleAggregate(CreateArticleCommand command) {
        AggregateLifecycle.apply(new ArticleCreatedEvent(command.getId(), command.getName()));
    }

    @EventSourcingHandler
    private void on(ArticleCreatedEvent event) {
        this.id = event.getId();
        this.name = event.getName();
        this.containers = new HashMap<>();
    }

    @CommandHandler
    private void handle(UnloadContainerCommand command) {
        AggregateLifecycle.apply(new ContainerUnloadedEvent(command.getContainerId(), command.getArticleId(), command.getExpirationDate(),
                this.name, command.getLocation()));

    }

    @EventSourcingHandler
    private void on(ContainerUnloadedEvent event) {
        this.containers.put(event.getContainerId(),
                new Container(event.getContainerId(), event.getArticleId(), event.getArticleName(), event.getExpirationDate(), false, event.getLocation()));
    }

    @CommandHandler
    private void handle(LoadContainerCommand command) throws IllegalStateException {
        if (!containers.containsKey(command.getContainerId())) {
            throw new IllegalStateException("could not find the container within the article!");
        }
        AggregateLifecycle.apply(new ContainerLoadedEvent(command.getContainerId(), command.getTruckId()));
    }

    @EventSourcingHandler
    private void handle(ContainerLoadedEvent event) {
        containers.remove(event.getContainerId());
    }

    @CommandHandler
    private void handle(ExpireContainerCommand command) throws IllegalStateException {
        if (!containers.containsKey(command.getContainerId())) {
            throw new IllegalStateException("could not find the container within the article!");
        }
        AggregateLifecycle.apply(new ContainerExpiredEvent(command.getContainerId()));
    }

    @EventSourcingHandler
    private void handle(ContainerExpiredEvent event) {
        containers.remove(event.getContainerId());
    }

    @CommandHandler
    private void handle(ClaimContainerCommand command) {
        if (!hasAvailableContainers()) {
            AggregateLifecycle.apply(new ArticleOutOfStockEvent(this.id, command.getShipmentId()));
        } else {
            UUID containerId = findOldestAvailableContainer();
            AggregateLifecycle.apply(new ContainerClaimedEvent(command.getArticleId(), containerId, command.getShipmentId()));
        }
    }

    @CommandHandler
    private void handle(UnclaimContainerCommand command) {
        AggregateLifecycle.apply(new ContainerUnclaimedEvent(command.getArticleId(), command.getContainerId(), command.getShipmentId()));
    }

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

}
