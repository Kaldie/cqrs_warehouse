package nl.codecentric.cqrs_warehouse.controllers;

import nl.codecentric.cqrs_warehouse.domain.shipment.CreateShipmentCommand;
import nl.codecentric.cqrs_warehouse.domain.shipment.InitialiseShipmentCommand;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController()
public class ShipmentController {

    @Autowired
    CommandGateway commandGateway;

    @PostMapping(path = "/shipments/create")
    public UUID createShipment(@RequestBody CreateShipmentCommand command) {
        commandGateway.sendAndWait(command);
        return command.getShipmentId();
    }

    @PostMapping(path = "/shipments/initialise")
    public UUID initialiseShipment(@RequestBody InitialiseShipmentCommand command) {
        commandGateway.sendAndWait(command);
        return command.getShipmentId();
    }





}
