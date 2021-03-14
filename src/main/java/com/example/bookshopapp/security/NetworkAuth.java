package com.example.bookshopapp.security;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity @Table(name = "oauth_authorization")
@Getter @Setter
public class NetworkAuth {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private BookstoreUser user;
    private String networkId;
    private String networkName;
}
