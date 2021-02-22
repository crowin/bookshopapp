package com.example.bookshopapp.data;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity @Table(name = "genres") @Getter @Setter
public class Genre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer parentId;
    private String slug;
    private String nameEnLocale;
    private String nameRuLocale;

    @ManyToMany
    @JoinTable(name = "book2genre",
            joinColumns = { @JoinColumn(name = "genre_id") },
            inverseJoinColumns = { @JoinColumn(name = "book_id")}
    )
    @OrderBy()
    private List<Book> books;

    @Transient
    private List<Genre> children;
    @Transient
    private String currentName;
    @Transient
    private Integer totalBooksCount;
}
