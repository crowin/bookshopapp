package com.example.bookshopapp.security;

import com.example.bookshopapp.data.Review;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "users")
@Getter @Setter
public class BookstoreUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Double balance;
    private String hash;
    private Date regTime;
    private String name;
    private String email;
    private String phone;
    private String password;

    @OneToMany(mappedBy = "bookStoreUser")
    private List<Review> reviews;


    @OneToOne(mappedBy = "user")
    private TokenDao token;
}
