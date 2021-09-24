package nl.codecentric.cqrs_warehouse.projections;

import nl.codecentric.cqrs_warehouse.domain.article.ArticleCreatedEvent;
import nl.codecentric.cqrs_warehouse.domain.article.FetchAllArticlesQuery;
import nl.codecentric.cqrs_warehouse.repositories.ArticleDTO;
import org.axonframework.config.ProcessingGroup;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@ProcessingGroup("ArticleProjection")
public class ArticleProjection {

    public void on(ArticleCreatedEvent event) {
    }

    public List<ArticleDTO> on(FetchAllArticlesQuery query) {
        return null;
    }

}
