package com.example.tteapi.model;

import jakarta.persistence.*;

@Entity
@Table(name = "Quotes")
public class Quote {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "quoteText")
    private String quoteText;

    @Column(name = "quoteEp")
    private Integer quoteEp;

    @Column(name = "quoteSeason")
    private Integer quoteSeason;

    @ManyToOne
    @JoinColumn(name = "CharacterID", insertable = false, updatable = false)
    private Character character;

    public Quote(String quoteText, Integer quoteEp, Integer quoteSeason, Character character) {
        this.quoteText = quoteText;
        this.quoteEp = quoteEp;
        this.quoteSeason = quoteSeason;
        this.character = character;
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

    public Character getCharacter() {
        return character;
    }

    public void setCharacter(Character character) {
        this.character = character;
    }
}
