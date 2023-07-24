package com.example.tteapi.model;

import jakarta.persistence.*;

@Entity
@Table(name = "Users")
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;

  @Column(name = "email")
  private String email;

  @Column(name = "name")
  private String name;

  public User() {}

  public User(String email, String name) {
    this.email = email;
    this.name = name;
  }

  public long getId() {
    return id;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Override
  public String toString() {
    return "Tutorial [id=" + id + ", email=" + email + ", name=" + name + "]";
  }
}