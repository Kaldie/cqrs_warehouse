package nl.codecentric.cqrs_warehouse.repositories;

import lombok.Setter;
import lombok.Value;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Document
@Value
@Setter
public class ShipmentDTO {
    @MongoId
    String shipmentId;
    String customerName;
    Integer volume;
    String articleId;
    String state;
}
