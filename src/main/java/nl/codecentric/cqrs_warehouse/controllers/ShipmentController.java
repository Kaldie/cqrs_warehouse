package nl.codecentric.cqrs_warehouse.controllers;

import nl.codecentric.cqrs_warehouse.domain.article.CreateArticleCommand;
import nl.codecentric.cqrs_warehouse.domain.shipment.CreateShipmentCommand;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
public class ShipmentController {

    @Autowired
    CommandGateway commandGateway;

    @PostMapping(path = "/shipments/create")
    public ResponseEntity createShipment(CreateShipmentCommand command) {
        commandGateway.sendAndWait(command);
        return ResponseEntity.accepted().build();
    }

}
