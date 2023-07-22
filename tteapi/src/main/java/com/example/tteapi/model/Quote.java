package com.example.tteapi.model;

import jakarta.persistence.*;

@Entity
@Table(name = "[quote]")
public class Quote {

    @Id
    @Column(name = "quote_id")
    private Integer QuoteID;

    @Column(name = "quote_text")
    private String QuoteText;

    @Column(name = "quote_episode")
    private Integer QuoteEpisode;

    @Column(name = "quote_season")
    private Integer QuoteSeason;

    @ManyToOne
    @JoinColumn(name = "character_id", insertable = false, updatable = false)
    private Character Character;

    public Quote(String QuoteText, Integer QuoteEpisode, Integer QuoteSeason, Character Character) {
        this.QuoteText = QuoteText;
        this.QuoteEpisode = QuoteEpisode;
        this.QuoteSeason = QuoteSeason;
        this.Character = Character;
    }

    public Integer getId() {
        return QuoteID;
    }

    public String getQuoteText() {
        return QuoteText;
    }

    public void setQuoteText(String QuoteText) {
        this.QuoteText = QuoteText;
    }

    public Integer getQuoteEpisode() {
        return QuoteEpisode;
    }

    public void setQuoteEpisode(Integer QuoteEpisode) {
        this.QuoteEpisode = QuoteEpisode;
    }

    public Integer getQuoteSeason() {
        return QuoteSeason;
    }

    public void setQuoteSeason(Integer QuoteSeason) {
        this.QuoteSeason = QuoteSeason;
    }

    public Character getCharacter() {
        return Character;
    }

    public void setCharacter(Character Character) {
        this.Character = Character;
    }
}
