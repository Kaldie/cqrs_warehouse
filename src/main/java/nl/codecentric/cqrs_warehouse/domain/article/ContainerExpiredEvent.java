package nl.codecentric.cqrs_warehouse.domain.article;

import java.util.UUID;

import lombok.Data;

@Data
public class ContainerExpiredEvent {
    private final UUID containerId;
}
