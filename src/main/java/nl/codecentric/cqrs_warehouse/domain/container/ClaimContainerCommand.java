package nl.codecentric.cqrs_warehouse.domain.container;

import java.util.UUID;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import lombok.Data;

@Data
public class ClaimContainerCommand {
    
    @TargetAggregateIdentifier
    private final UUID articleId;
    private final UUID shipmentId;
}
