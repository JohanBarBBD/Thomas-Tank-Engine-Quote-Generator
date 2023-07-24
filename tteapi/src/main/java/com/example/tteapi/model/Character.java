package com.example.tteapi.model;

import jakarta.persistence.*;

@Entity
@Table(name = "Characters")
public class Character {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
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