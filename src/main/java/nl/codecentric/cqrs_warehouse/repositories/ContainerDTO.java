package nl.codecentric.cqrs_warehouse.repositories;

import lombok.Builder;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.time.Instant;

@Document
@Builder(toBuilder = true)
public class ContainerDTO {
    @MongoId
    String containerId;
    Instant expirationDate;
    boolean isReserved;
    String location;
}
