package ru.cifrak.news.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.cifrak.news.entity.NewsEntity;
import ru.cifrak.news.exception.*;
import ru.cifrak.news.model.News;
import ru.cifrak.news.service.NewsService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/news")
public class NewsController {
    @Autowired
    private NewsService newsService;

    @PostMapping("/add")
    public ResponseEntity<String> addNews(@RequestBody NewsEntity note, @RequestParam Long typeId) {
        try {
            newsService.createNote(note, typeId);
            return ResponseEntity.ok().body("Новость успешно добавлена в базу данных.");
        } catch (NewsNameAlreadyExistException | NewsDescriptionAlreadyUsesException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Произошла непредвиденная ошибка при добавлении новости!");
        }
    }

    @GetMapping("/find")
    public ResponseEntity<?> findNews(@RequestParam String fieldType, @RequestParam String field) {
        try {
            switch (fieldType) {
                case "id" -> {
                    return ResponseEntity.ok(News.toModel(newsService.readNewsById(Long.valueOf(field))));
                }
                case "headline" -> {
                    return ResponseEntity.ok(News.toModel(newsService.readNewsByHeadline(field)));
                }
                case "description" -> {
                    return ResponseEntity.ok(News.toModel(newsService.readNewsByDescription(field)));
                }
                case "newsType" -> {
                    List<NewsEntity> newsEntities = newsService.readAllNewsByType(field);
                    List<News> news = new ArrayList<>();
                    for (NewsEntity note : newsEntities) {
                        news.add(News.toModel(note));
                    }

                    return ResponseEntity.ok(news);
                }
                default -> {
                    return ResponseEntity.badRequest().body("Неправильно задан тип поля для поиска!");
                }
            }
        } catch (NewsNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Произошла непредвиденная ошибка при поиске типа!");
        }
    }

    @PatchMapping("/update")
    public ResponseEntity<?> updateNews(@RequestParam String fieldType, @RequestParam String oldFieldValue,
                                        @RequestParam String newFieldValue) {
        try {
            switch (fieldType) {
                case "headline" -> {
                    return ResponseEntity.ok(News.toModel(newsService.updateNewsByHeadline(oldFieldValue, newFieldValue)));
                }
                case "description" -> {
                    return ResponseEntity.ok(News.toModel(newsService.updateNewsByDescription(oldFieldValue, newFieldValue)));
                }
                case "newsType" -> {
                    return ResponseEntity.ok(News.toModel(newsService.updateNewsByType(oldFieldValue, newFieldValue)));
                }
                default -> {
                    return ResponseEntity.badRequest().body("Неправильно задан тип поля для обновления!");
                }
            }
        } catch (NewsNotFoundException | NewsNameAlreadyExistException | NewsDescriptionAlreadyUsesException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Произошла непредвиденная ошибка при обновлении новости!");
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteType(@RequestParam String fieldType, @RequestParam String field) {
        try {
            NewsEntity returnNews;
            switch (fieldType) {
                case "id" -> returnNews = newsService.deleteNewsById(Long.valueOf(field));
                case "name" -> returnNews = newsService.deleteNewsByHeadline(field);
                case "color" -> returnNews = newsService.deleteNewsByDescription(field);
                case "type" -> {
                    List<NewsEntity> newsEntities = newsService.deleteAllNewsByType(field);
                    List<News> news = new ArrayList<>();
                    for (NewsEntity note : newsEntities) {
                        news.add(News.toModel(note));
                    }

                    return ResponseEntity.ok(news);
                }
                default -> {
                    return ResponseEntity.badRequest().body("Неправильно задан тип поля для удаления!");
                }
            }
            return ResponseEntity.ok(returnNews);
        } catch (TypeNotFoundException | NewsNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Произошла непредвиденная ошибка при удалении новости!");
        }
    }
}
