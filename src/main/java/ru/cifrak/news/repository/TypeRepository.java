package ru.cifrak.news.repository;

import org.springframework.data.repository.CrudRepository;
import ru.cifrak.news.entity.TypeEntity;

import java.util.Optional;

public interface TypeRepository extends CrudRepository<TypeEntity, Long> {
    Optional<TypeEntity> findByName(String name);
    Optional<TypeEntity> findByColor(String color);
}
