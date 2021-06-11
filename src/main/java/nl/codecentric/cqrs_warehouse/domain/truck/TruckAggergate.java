package nl.codecentric.cqrs_warehouse.domain.truck;

import java.util.List;
import java.util.UUID;

import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;

import lombok.Data;

@Data
@Aggregate
public class TruckAggergate {
    @AggregateIdentifier
    private final UUID id;
    private final String customerName;
    private final String location;
    private final UUID shipmentId;
    private final List<UUID> containers;

    
}
