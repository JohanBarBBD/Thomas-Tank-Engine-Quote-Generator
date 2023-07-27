package com.example.tteapi.model;

import jakarta.persistence.*;

@Entity
@Table(name = "Quotes")
public class Quote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "quoteText")
    private String quoteText;

    @Column(name = "quoteEp")
    private Integer quoteEp;

    @Column(name = "quoteSeason")
    private Integer quoteSeason;

    @Column(name = "CharacterID")
    private Long characterID;

    public Quote() {}  

    public Quote(String quoteText, Integer quoteEp, Integer quoteSeason, long characterID) {
        this.quoteText = quoteText;
        this.quoteEp = quoteEp;
        this.quoteSeason = quoteSeason;
        this.characterID = characterID;
    }

    public long getId() {
        return id;
    }

    public String getQuoteText() {
        return quoteText;
    }

    public void setQuoteText(String quoteText) {
        this.quoteText = quoteText;
    }

    public Integer getQuoteEp() {
        return quoteEp;
    }

    public void setQuoteEp(Integer quoteEp) {
        this.quoteEp = quoteEp;
    }

    public Integer getQuoteSeason() {
        return quoteSeason;
    }

    public void setQuoteSeason(Integer quoteSeason) {
        this.quoteSeason = quoteSeason;
    }

    public long getCharacterID() {
        return characterID;
    }

    public void setCharacterID(long characterID) {
        this.characterID = characterID;
    }
}
