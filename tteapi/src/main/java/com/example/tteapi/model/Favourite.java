package com.example.tteapi.model;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "Favourites")
public class Favourite {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "QuoteID")
    private long quoteID;

    @Column(name = "UserID")
    private long userID;

    @Column(name = "dateFavourited")
    private Date dateFavourited;

    public Favourite() {}

    public Favourite(long quoteID, long userID, Date dateFavourited) {
        this.quoteID = quoteID;
        this.userID = userID;
        this.dateFavourited = dateFavourited;
    }

    public long getID() {
        return id;
    }

    public long getQuoteID() {
        return quoteID;
    }

    public void setQuoteID(long quoteID) {
        this.quoteID = quoteID;
    }

    public long getUserID() {
        return userID;
    }

    public void setUserID(long userID) {
        this.userID = userID;
    }

    public Date getDateFavourited() {
        return dateFavourited;
    }

    public void setDateFavourited(Date dateFavourited) {
        this.dateFavourited = dateFavourited;
    }
}
