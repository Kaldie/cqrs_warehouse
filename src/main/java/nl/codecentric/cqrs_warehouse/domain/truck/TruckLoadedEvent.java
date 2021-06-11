package nl.codecentric.cqrs_warehouse.domain.truck;

import java.util.UUID;

import lombok.Data;

@Data
public class TruckLoadedEvent {
    private final UUID truckId;
}
