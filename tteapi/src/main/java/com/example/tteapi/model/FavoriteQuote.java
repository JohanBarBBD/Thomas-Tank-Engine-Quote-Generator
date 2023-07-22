package com.example.tteapi.model;

import jakarta.persistence.*;
import java.sql.Date;

@Entity
@IdClass(FavoriteQuote.class)
@Table(name = "[favoritequote]")
public class FavoriteQuote {

    @ManyToOne(optional = false)
    @JoinColumn(name = "quote_id", insertable = false, updatable = false)
    @Id
    private Quote Quote;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    @Id
    private User User;

    @Column(name = "date_favorited")
    private Date DateFavorited;

    public FavoriteQuote(Quote Quote, User User, java.sql.Date dateFavourited) {
        this.Quote = Quote;
        this.User = User;
        this.DateFavorited = dateFavourited;
    }

    public Integer[] getID() {
        return new Integer[] {this.User.getId(),this.Quote.getId()};
    }

    public Quote getQuote() {
        return Quote;
    }

    public void setQuote(Quote Quote) {
        this.Quote = Quote;
    }

    public User getUser() {
        return this.User;
    }

    public void setUser(User User) {
        this.User = User;
    }

    public Date getDateFavourited() {
        return this.DateFavorited;
    }

    public void setDateFavourited(Date dateFavourited) {
        this.DateFavorited = dateFavourited;
    }
}
