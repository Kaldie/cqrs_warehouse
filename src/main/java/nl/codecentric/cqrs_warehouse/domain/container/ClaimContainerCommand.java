package nl.codecentric.cqrs_warehouse.domain.container;

import java.util.UUID;

import lombok.Value;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import lombok.Data;

@Value
public class ClaimContainerCommand {
    @TargetAggregateIdentifier
    UUID articleId;
    UUID shipmentId;
}
