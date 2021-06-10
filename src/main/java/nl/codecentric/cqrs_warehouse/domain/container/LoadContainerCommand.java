package nl.codecentric.cqrs_warehouse.domain.container;

import java.util.UUID;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import lombok.Data;

@Data
public class LoadContainerCommand {
    @TargetAggregateIdentifier
    private final UUID containerId;
    private final String to;
}
