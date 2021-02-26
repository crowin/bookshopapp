package com.example.bookshopapp.data;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "book_review")
@Getter @Setter
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    //private Integer bookId;
//    private Integer user_id;
    private Date time;
    private String text;
    private Integer score;

    @ManyToOne
    @JoinColumn(name = "book_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Book book;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", insertable = false, updatable = false)
    private User user;

    @OneToMany(mappedBy="review")
    private List<BookReviewLike> likes;

    public Integer getLikesSum() {
        return Math.toIntExact(likes.stream().filter(like -> like.getValue() == 1).count());
    }

    public Integer getDislikesSum() {
        return Math.toIntExact(likes.stream().filter(like -> like.getValue() == -1).count());
    }

    public String getFormattedTime() {
        return new SimpleDateFormat("dd.MM.yyyy HH:mm").format(this.time);
    }
}
