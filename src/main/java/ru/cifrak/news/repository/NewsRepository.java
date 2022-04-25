package ru.cifrak.news.repository;

import org.springframework.data.repository.CrudRepository;
import ru.cifrak.news.entity.NewsEntity;
import ru.cifrak.news.entity.TypeEntity;

import java.util.List;
import java.util.Optional;

public interface NewsRepository extends CrudRepository<NewsEntity, Long> {
    Optional<NewsEntity> findByHeadline(String headline);
    Optional<NewsEntity> findByDescription(String description);
    List<NewsEntity> findAllByNewsType(TypeEntity newsType);
}
