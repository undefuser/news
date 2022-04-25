package ru.cifrak.news.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.cifrak.news.entity.NewsEntity;
import ru.cifrak.news.entity.TypeEntity;
import ru.cifrak.news.exception.*;
import ru.cifrak.news.repository.NewsRepository;
import ru.cifrak.news.repository.TypeRepository;

import java.util.List;
import java.util.Optional;

@Service
public class NewsService {
    @Autowired
    private TypeService typeService;

    @Autowired
    private NewsRepository newsRepository;
    @Autowired
    private TypeRepository typeRepository;

    public void createNote(NewsEntity note, Long typeId) throws NewsNameAlreadyExistException,
            NewsDescriptionAlreadyUsesException, TypeNotFoundException {
        if (newsRepository.findByHeadline(note.getHeadline()).isPresent()) {
            throw new NewsNameAlreadyExistException("Новость с указанным именем уже существует!");
        }

        if (newsRepository.findByDescription(note.getDescription()).isPresent()) {
            throw new NewsDescriptionAlreadyUsesException("Указанное описание уже используется в другой новости!");
        }

        Optional<TypeEntity> typeOptional = typeRepository.findById(typeId);
        if (typeOptional.isEmpty()) {
            throw new TypeNotFoundException("!");
        }
        note.setNewsType(typeOptional.get());

        newsRepository.save(note);
    }

    public NewsEntity readNewsById(Long id) throws NewsNotFoundException {
        Optional<NewsEntity> newsOptional = newsRepository.findById(id);
        if (newsOptional.isEmpty()) {
            throw new NewsNotFoundException("с указанным id!");
        }

        return newsOptional.get();
    }

    public NewsEntity readNewsByHeadline(String headline) throws NewsNotFoundException {
        Optional<NewsEntity> newsOptional = newsRepository.findByHeadline(headline);
        if (newsOptional.isEmpty()) {
            throw new NewsNotFoundException("с указанным заголовком!");
        }

        return newsOptional.get();
    }

    public NewsEntity readNewsByDescription(String description) throws NewsNotFoundException {
        Optional<NewsEntity> newsOptional = newsRepository.findByDescription(description);
        if (newsOptional.isEmpty()) {
            throw new NewsNotFoundException("с указанным заголовком!");
        }

        return newsOptional.get();
    }

    public List<NewsEntity> readAllNewsByType(String typeName) throws NewsNotFoundException, TypeNotFoundException {
        Optional<TypeEntity> typeOptional = typeRepository.findByName(typeName);
        if (typeOptional.isEmpty()) {
            throw new TypeNotFoundException("с указанным именем!");
        }
        List<NewsEntity> news = newsRepository.findAllByNewsType(typeOptional.get());
        if (news.isEmpty()) {
            throw new NewsNotFoundException("с указанным типом!");
        }

        return news;
    }

    public List<NewsEntity> showAllNews() throws NewsListIsEmptyException {
        List<NewsEntity> allNews = newsRepository.findAll();
        if (allNews.isEmpty()) {
            throw new NewsListIsEmptyException();
        }

        return allNews;
    }

    public NewsEntity updateNewsByHeadline(String oldHeadline, String newHeadline) throws NewsNotFoundException, NewsNameAlreadyExistException {
        if (newsRepository.findByHeadline(newHeadline).isPresent()) {
            throw new NewsNameAlreadyExistException("Новость с указанным именем уже существует!");
        }

        NewsEntity news = readNewsByHeadline(oldHeadline);
        news.setHeadline(newHeadline);
        return newsRepository.save(news);
    }

    public NewsEntity updateNewsByDescription(String oldDescription, String newDescription) throws NewsNotFoundException, NewsDescriptionAlreadyUsesException {
        if (newsRepository.findByDescription(newDescription).isPresent()) {
            throw new NewsDescriptionAlreadyUsesException("Указанное описание уже используется в другой новости!");
        }

        NewsEntity news = readNewsByDescription(oldDescription);
        news.setDescription(newDescription);
        return newsRepository.save(news);
    }

    public NewsEntity updateNewsByType(String headline, String newTypeName)
            throws NewsNotFoundException, TypeNotFoundException {
        NewsEntity news = readNewsByHeadline(headline);
        TypeEntity newType = typeService.readTypeByName(newTypeName);
        news.setNewsType(newType);
        return newsRepository.save(news);
    }

    public NewsEntity deleteNewsById(Long id) throws NewsNotFoundException {
        NewsEntity news = readNewsById(id);
        newsRepository.deleteById(id);
        return news;
    }

    public NewsEntity deleteNewsByHeadline(String headline) throws NewsNotFoundException {
        NewsEntity news = readNewsByHeadline(headline);
        newsRepository.delete(news);
        return news;
    }

    public NewsEntity deleteNewsByDescription(String description) throws NewsNotFoundException {
        NewsEntity news = readNewsByDescription(description);
        newsRepository.delete(news);
        return news;
    }

    public List<NewsEntity> deleteAllNewsByType(String typeName) throws NewsNotFoundException, TypeNotFoundException {
        List<NewsEntity> news = readAllNewsByType(typeName);
        newsRepository.deleteAll(news);
        return news;
    }
}
