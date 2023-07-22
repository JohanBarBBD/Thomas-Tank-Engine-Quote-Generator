package com.example.tteapi.model;

import jakarta.persistence.*;

@Entity
@Table(name = "[character]")
public class Character {

  @Id
  @Column(name = "character_id")
  private Integer characterId;

  @Column(name = "character_name")
  private String characterName;

  public Character(String characterName) {
    this.characterName = characterName;
  }

  public Integer getId() {
    return characterId;
  }

  public String getName() {
    return characterName;
  }

  public void setName(String characterName) {
    this.characterName = characterName;
  }
}
