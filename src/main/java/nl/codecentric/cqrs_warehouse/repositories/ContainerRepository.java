package nl.codecentric.cqrs_warehouse.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContainerRepository extends MongoRepository<ContainerDTO, String> {
    List<ContainerDTO> findByArticleId(String articleId);

    List<ContainerDTO> findByReservedFor(String reservedFor);
}
