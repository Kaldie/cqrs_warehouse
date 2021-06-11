package nl.codecentric.cqrs_warehouse.domain.container;

import java.util.UUID;

import org.axonframework.modelling.command.EntityId;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import lombok.Data;

@Data
public class MoveContainerCommand {
    @TargetAggregateIdentifier
    private final UUID articleId;
    private final UUID containerId;
    private final String location;
}
