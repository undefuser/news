package ru.cifrak.news.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.cifrak.news.entity.TypeEntity;
import ru.cifrak.news.exception.TypeColorAlreadyUsesException;
import ru.cifrak.news.exception.TypeNameAlreadyExistException;
import ru.cifrak.news.exception.TypeNotFoundException;
import ru.cifrak.news.repository.TypeRepository;

import java.util.Optional;

@Service
public class TypeService {
    @Autowired
    private TypeRepository typeRepository;

    public void createType(TypeEntity type) throws TypeNameAlreadyExistException,
                                                   TypeColorAlreadyUsesException {
        if (typeRepository.findByName(type.getName()).isPresent()) {
            throw new TypeNameAlreadyExistException("Тип с указанным именем уже существует!");
        }

        if (typeRepository.findByColor(type.getColor()).isPresent()) {
            throw new TypeColorAlreadyUsesException("Указанный цвет уже занят другим типом!");
        }

        typeRepository.save(type);
    }

    public TypeEntity readTypeById(Long id) throws TypeNotFoundException {
        Optional<TypeEntity> typeOptional = typeRepository.findById(id);
        if (typeOptional.isEmpty()) {
            throw new TypeNotFoundException("с указанным id!");
        }

        return typeOptional.get();
    }

    public TypeEntity readTypeByName(String name) throws TypeNotFoundException {
        Optional<TypeEntity> typeOptional = typeRepository.findByName(name);
        if (typeOptional.isEmpty()) {
            throw new TypeNotFoundException("с указанным именем!");
        }

        return typeOptional.get();
    }

    public TypeEntity readTypeByColor(String color) throws TypeNotFoundException {
        Optional<TypeEntity> typeOptional = typeRepository.findByColor(color);
        if (typeOptional.isEmpty()) {
            throw new TypeNotFoundException("с указанным цветом!");
        }

        return typeOptional.get();
    }

    public TypeEntity updateTypeByName(String oldName, String newName) throws TypeNotFoundException {
        TypeEntity type = readTypeByName(oldName);
        type.setName(newName);
        return typeRepository.save(type);
    }

    public TypeEntity updateTypeByColor(String oldColor, String newColor) throws TypeNotFoundException {
        TypeEntity type = readTypeByColor(oldColor);
        type.setColor(newColor);
        return typeRepository.save(type);
    }

    public TypeEntity deleteTypeById(Long id) throws TypeNotFoundException {
        TypeEntity type = readTypeById(id);
        typeRepository.deleteById(id);
        return type;
    }

    public TypeEntity deleteTypeByName(String name) throws TypeNotFoundException {
        TypeEntity type = readTypeByName(name);
        typeRepository.delete(type);
        return type;
    }

    public TypeEntity deleteTypeByColor(String color) throws TypeNotFoundException {
        TypeEntity type = readTypeByColor(color);
        typeRepository.delete(type);
        return type;
    }
}
