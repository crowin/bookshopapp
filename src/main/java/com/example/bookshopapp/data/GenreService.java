package com.example.bookshopapp.data;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;
import com.google.common.collect.Multimap;
import org.springframework.beans.factory.annotation.Autowired;
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

    public ListMultimap<String, GenreDto> getBooksCountByGenres(String locale) {
        ListMultimap<String, GenreDto> genresMap = ArrayListMultimap.create();
        Map<Integer, String> tempParentsMap = new HashMap<>();

        //parent genre will be at the top of list
        List<Genre> genreListDao = genreRepository.findAll().stream()
                .sorted(Comparator.comparing(g -> g.getParentId() != null))
                .collect(Collectors.toList());

        for(Genre genre: genreListDao) {
            String name = locale.equalsIgnoreCase("en") ? genre.getNameEnLocale() : genre.getNameRuLocale();
            if (genre.getParentId() == null) {
                tempParentsMap.put(genre.getId(), genre.getSlug());
            } else {
                GenreDto dto = new GenreDto().setId(genre.getId()).setName(name).setSlug(genre.getSlug())
                        .setCount(genre.getBooks().size());
                genresMap.put(tempParentsMap.get(genre.getParentId()), dto);
            }
        }

        return genresMap;
    }

    /*
    parent
    name,id

    child
    name, id
     */
}
