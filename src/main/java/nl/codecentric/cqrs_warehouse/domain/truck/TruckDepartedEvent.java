package nl.codecentric.cqrs_warehouse.domain.truck;

import lombok.Data;

import java.util.UUID;

@Data
public class TruckDepartedEvent {
    private final UUID truckId;
}
