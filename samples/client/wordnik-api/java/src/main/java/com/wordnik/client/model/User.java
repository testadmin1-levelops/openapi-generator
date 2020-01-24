package com.wordnik.client.model;

public class User {
  private Long id = null;
  private String username = null;
  private Integer status = null;
  private String email = null;
  private String faceBookId = null;
  private String userName = null;
  private String displayName = null;
  private String password = null;
  public Long getId() {
    return id;
  }
  public void setId(Long id) {
    this.id = id;
  }

  public String getUsername() {
    return username;
  }
  public void setUsername(String username) {
    this.username = username;
  }

  public Integer getStatus() {
    return status;
  }
  public void setStatus(Integer status) {
    this.status = status;
  }

  public String getEmail() {
    return email;
  }
  public void setEmail(String email) {
    this.email = email;
  }

  public String getFaceBookId() {
    return faceBookId;
  }
  public void setFaceBookId(String faceBookId) {
    this.faceBookId = faceBookId;
  }

  public String getUserName() {
    return userName;
  }
  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getDisplayName() {
    return displayName;
  }
  public void setDisplayName(String displayName) {
    this.displayName = displayName;
  }

  public String getPassword() {
    return password;
  }
  public void setPassword(String password) {
    this.password = password;
  }

  @Override
  public String toString()  {
    StringBuilder sb = new StringBuilder();
    sb.append("class User {\n");
    sb.append("  id: ").append(id).append("\n");
    sb.append("  username: ").append(username).append("\n");
    sb.append("  status: ").append(status).append("\n");
    sb.append("  email: ").append(email).append("\n");
    sb.append("  faceBookId: ").append(faceBookId).append("\n");
    sb.append("  userName: ").append(userName).append("\n");
    sb.append("  displayName: ").append(displayName).append("\n");
    sb.append("  password: ").append(password).append("\n");
    sb.append("}\n");
    return sb.toString();
  }
}

