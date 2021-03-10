package com.example.bookshopapp.security;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "tokens")
@Getter @Setter @NoArgsConstructor
public class TokenDao {

    public TokenDao(String token, BookstoreUser user) {
        this.token = token;
        this.user = user;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String token;
    private Date revokedAt;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private BookstoreUser user;

}
