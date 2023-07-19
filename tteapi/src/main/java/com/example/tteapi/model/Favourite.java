package com.example.tteapi.model;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "Favourites")
public class Favourite {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne
    @JoinColumn(name = "QuoteID", insertable = false, updatable = false)
    private Quote quote;

    @ManyToOne
    @JoinColumn(name = "UserID", insertable = false, updatable = false)
    private User user;

    @Column(name = "dateFavourited")
    private Date dateFavourited;

    public Favourite(Quote quote, User user, Date dateFavourited) {
        this.quote = quote;
        this.user = user;
        this.dateFavourited = dateFavourited;
    }

    public long getID() {
        return id;
    }

    public Quote getQuote() {
        return quote;
    }

    public void setQuote(Quote quote) {
        this.quote = quote;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getDateFavourited() {
        return dateFavourited;
    }

    public void setDateFavourited(Date dateFavourited) {
        this.dateFavourited = dateFavourited;
    }
}
