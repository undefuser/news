package ru.cifrak.news.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.cifrak.news.entity.TypeEntity;
import ru.cifrak.news.exception.TypeColorAlreadyUsesException;
import ru.cifrak.news.exception.TypeNameAlreadyExistException;
import ru.cifrak.news.exception.TypeNotFoundException;
import ru.cifrak.news.service.TypeService;

@RestController
@RequestMapping("/type")
public class TypeController {
    @Autowired
    private TypeService typeService;

    @PostMapping("/add")
    public ResponseEntity<String> addType(@RequestBody TypeEntity type) {
        try {
            typeService.createType(type);
            return ResponseEntity.ok().body("Тип успешно добавлен в базу данных.");
        } catch (TypeNameAlreadyExistException | TypeColorAlreadyUsesException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Произошла непредвиденная ошибка при добавлении типа!");
        }
    }

    @GetMapping("/find")
    public ResponseEntity<?> findType(@RequestParam String fieldType, @RequestParam String field) {
        try {
            switch (fieldType) {
                case "id" -> {
                    return ResponseEntity.ok(typeService.readTypeById(Long.valueOf(field)));
                }
                case "name" -> {
                    return ResponseEntity.ok(typeService.readTypeByName(field));
                }
                case "color" -> {
                    return ResponseEntity.ok(typeService.readTypeByColor(field));
                }
                default -> {
                    return ResponseEntity.badRequest().body("Неправильно задан тип поля для поиска!");
                }
            }
        } catch (TypeNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Произошла непредвиденная ошибка при поиске типа!");
        }
    }

    @PatchMapping("/update")
    public ResponseEntity<?> updateType(@RequestParam String fieldType, @RequestParam String oldFieldValue,
                                                                        @RequestParam String newFieldValue) {
        try {
            switch (fieldType) {
                case "name" -> {
                    return ResponseEntity.ok(typeService.updateTypeByName(oldFieldValue, newFieldValue));
                }
                case "color" -> {
                    return ResponseEntity.ok(typeService.updateTypeByColor(oldFieldValue, newFieldValue));
                }
                default -> {
                    return ResponseEntity.badRequest().body("Неправильно задан тип поля для обновления!");
                }
            }
        } catch (TypeNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Произошла непредвиденная ошибка при обновлении типа!");
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteType(@RequestParam String fieldType, @RequestParam String field) {
        try {
            TypeEntity returnType;
            switch (fieldType) {
                case "id" -> returnType = typeService.deleteTypeById(Long.valueOf(field));
                case "name" -> returnType = typeService.deleteTypeByName(field);
                case "color" -> returnType = typeService.deleteTypeByColor(field);
                default -> {
                    return ResponseEntity.badRequest().body("Неправильно задан тип поля для удаления!");
                }
            }
            return ResponseEntity.ok(returnType);
        } catch (TypeNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Произошла непредвиденная ошибка при удалении типа!");
        }
    }
}
