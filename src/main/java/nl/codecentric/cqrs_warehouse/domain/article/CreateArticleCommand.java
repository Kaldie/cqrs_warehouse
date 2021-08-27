package nl.codecentric.cqrs_warehouse.domain.article;

import java.util.UUID;

import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
public class CreateArticleCommand {
    @TargetAggregateIdentifier
    private UUID id;
    private String name;
}
