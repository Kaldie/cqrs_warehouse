package nl.codecentric.cqrs_warehouse.domain.truck;

import lombok.Data;

import java.util.UUID;

@Data
public class TruckArrivedEvent {
    private final UUID truckId;
    private final UUID shipmentId;
}
