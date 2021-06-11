package nl.codecentric.cqrs_warehouse.domain.container;

import java.util.UUID;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import lombok.Data;

@Data
public class ContainerClaimCommand {
    
    @TargetAggregateIdentifier
    private UUID articleId;
    private UUID containerId;
    private UUID shipmentId;
}
