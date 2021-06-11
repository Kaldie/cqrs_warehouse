package nl.codecentric.cqrs_warehouse.domain.article;

import java.time.Instant;
import java.util.UUID;

import lombok.Data;

@Data
public class ContainerUnloadedEvent {
    private final UUID containerId;
    private final Instant expirationDate;
    private final String location;
}
