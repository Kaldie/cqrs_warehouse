package nl.codecentric.cqrs_warehouse.domain.truck;

import java.util.List;
import java.util.UUID;

import lombok.Data;

@Data
public class TruckAggergate {
    private final UUID id;
    private final String customerName;
    private final String location;
    private final UUID shipmentId;
    private final List<UUID> containers;
}
