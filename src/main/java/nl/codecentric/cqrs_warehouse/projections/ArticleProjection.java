package nl.codecentric.cqrs_warehouse.projections;

import org.axonframework.config.ProcessingGroup;
import org.springframework.stereotype.Component;

@Component
@ProcessingGroup("ArticleProjection")
public class ArticleProjection {
}
