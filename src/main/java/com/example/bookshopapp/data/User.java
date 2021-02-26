package com.example.bookshopapp.data;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "users")
@Getter @Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Double balance;
    private String hash;
    private Date regTime;
    private String name;

    @OneToMany(mappedBy = "user")
    private List<Review> reviews;
}
