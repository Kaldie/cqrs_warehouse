package nl.codecentric.cqrs_warehouse.domain.article;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.modelling.command.AggregateMember;
import org.axonframework.modelling.command.ForwardMatchingInstances;
import org.axonframework.spring.stereotype.Aggregate;

import javassist.NotFoundException;
import lombok.Data;
import lombok.NoArgsConstructor;
import nl.codecentric.cqrs_warehouse.domain.container.Container;
import nl.codecentric.cqrs_warehouse.domain.container.MoveContainerCommand;

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
        this.name = event.getName();
        this.id = event.getId();
        this.containers = new HashMap<>();
    }

    @CommandHandler
    private void handle(UnloadContainerCommand command) {
        AggregateLifecycle.apply(new ContainerUnloadedEvent(command.getContainerId(), command.getExpirationData(),
                command.getLocation()));

    }

    @EventSourcingHandler
    private void on(ContainerUnloadedEvent event) {
        this.containers.put(
            event.getContainerId(), 
            new Container(event.getContainerId(), event.getExpirationDate(), false, event.getLocation()));
    }

    @CommandHandler
    private void handle(LoadContainerCommand command) throws NotFoundException {
        if (!containers.containsKey(command.getContainerId())) {
            throw new NotFoundException("could not find the container within the article!");
        }
        AggregateLifecycle.apply(new ContainerLoadedEvent(command.getContainerId()));
    }
    
    @EventSourcingHandler
    private void handle(ContainerLoadedEvent event) {
        containers.remove(event.getContainerId());
    }

    @CommandHandler
    private void handle(ExpireContainerCommand command) throws NotFoundException {
        if (!containers.containsKey(command.getContainerId())) {
            throw new NotFoundException("could not find the container within the article!");
        }
        AggregateLifecycle.apply(new ContainerExpiredEvent(command.getContainerId()));
    }
    
    @EventSourcingHandler
    private void handle(ContainerExpiredEvent event) {
        containers.remove(event.getContainerId());
    }


    
}
