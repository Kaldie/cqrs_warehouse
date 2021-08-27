package nl.codecentric.cqrs_warehouse.controllers;

import nl.codecentric.cqrs_warehouse.domain.article.CreateArticleCommand;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
public class ArticleController {

    @Autowired
    CommandGateway commandGateway;

    @PostMapping(path = "/articles/create")
    public ResponseEntity createArticle(CreateArticleCommand command) {
        commandGateway.sendAndWait(command);
        return ResponseEntity.accepted().build();
    }

}
