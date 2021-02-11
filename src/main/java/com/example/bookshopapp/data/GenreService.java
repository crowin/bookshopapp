package com.example.bookshopapp.data;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;
import com.google.common.collect.Multimap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class GenreService {
    private GenreRepository genreRepository;

    @Autowired
    public GenreService(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    public List<Genre> getBooksCountByGenres(String locale) {
        List<Genre> genresList = genreRepository.findAll();
        List<Genre> copiedList = new ArrayList<>(genresList);

        for (Genre cGenre : copiedList) {
            int booksCount = 0;
            for(Genre genre: genresList) {
                if (genre.getCurrentName() == null) {
                    genre.setCurrentName(locale.equalsIgnoreCase("en") ? genre.getNameEnLocale() : genre.getNameRuLocale());
                }

                if (genre.getParentId() != null && genre.getParentId().equals(cGenre.getId())) {
                    booksCount += genre.getBooks().size();
                    if (cGenre.getChildren() == null) {
                        cGenre.setChildren(new ArrayList<>());
                    }
                    genre.setTotalBooksCount(genre.getBooks().size());
                    cGenre.getChildren().add(genre);
                    cGenre.setTotalBooksCount(booksCount);
                }
            }
        }
        copiedList.removeIf(elem -> elem.getParentId() != null);

        return copiedList;
    }

    public Genre getGenreWithBooksList(String slugName) {
        return genreRepository.findGenreBySlugEquals(slugName);
    }

    public Genre getGenreById(Integer id) {
        return genreRepository.findById(id).get();
    }
}
