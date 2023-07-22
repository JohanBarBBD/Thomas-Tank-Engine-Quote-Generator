package com.example.tteapi.model;

import jakarta.persistence.*;

@Entity
@Table(name = "[user]")
public class User {

  @Id
  @Column(name = "[user_id]")
  private Integer UserID;

  @Column(name = "UserEmail")
  private String UserEmail;

  @Column(name = "UserName")
  private String UserName;

  public User() {

  }

  public User(String UserEmail, String UserName) {
    this.UserEmail = UserEmail;
    this.UserName = UserName;
  }

  public Integer getId() {
    return this.UserID;
  }

  public String getUserEmail() {
    return UserEmail;
  }

  public void setUserEmail(String UserEmail) {
    this.UserEmail = UserEmail;
  }

  public String getUserName() {
    return UserName;
  }

  public void setUserName(String UserName) {
    this.UserName = UserName;
  }

  // @Override
  // public String toString() {
  //   return "Tutorial [id=" + id + ", UserEmail=" + UserEmail + ", UserName=" + UserName + "]";
  // }
}
