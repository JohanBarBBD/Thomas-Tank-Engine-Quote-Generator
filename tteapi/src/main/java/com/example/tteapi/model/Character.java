package com.example.tteapi.model;

import jakarta.persistence.*;

@Entity
@Table(name = "characters")
public class Character {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)

  private long id;

  @Column(name = "name")
  private String name;

  public Character() {}

  public Character(String name) {
    this.name = name;
  }

  public long getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}