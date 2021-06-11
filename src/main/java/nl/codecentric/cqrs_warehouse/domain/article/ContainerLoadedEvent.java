package nl.codecentric.cqrs_warehouse.domain.article;

import java.time.Instant;
import java.util.UUID;

import lombok.Data;

@Data
public class ContainerLoadedEvent {
    private final UUID containerId;
}
