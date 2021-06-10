package nl.codecentric.cqrs_warehouse.domain.container;

import java.time.Instant;
import java.util.UUID;

import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;

import lombok.Data;

@Aggregate
@Data
public class ContainerAggregate {
    
    @AggregateIdentifier
    private final UUID id;
    private final String contentName;
    private final Instant expirationDate;
    private final Boolean isReserved;
    private final String location;
}
