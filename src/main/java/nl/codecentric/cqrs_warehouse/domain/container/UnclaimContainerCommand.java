package nl.codecentric.cqrs_warehouse.domain.container;

import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.util.UUID;

@Data
public class UnclaimContainerCommand {
    
    @TargetAggregateIdentifier
    private final UUID articleId;
    private final UUID containerId;
    private final UUID shipmentId;
}
