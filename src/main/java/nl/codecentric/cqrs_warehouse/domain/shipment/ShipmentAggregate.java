package nl.codecentric.cqrs_warehouse.domain.shipment;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
public class ShipmentAggregate {
    private UUID shipmentId;
    private String customerName;
    private Integer volume;
    private UUID articleId;
    private String state;

}
