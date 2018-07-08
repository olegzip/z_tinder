package entity;

public class User {
  private int id;
  private String name;
  private String photo;
  private Boolean liked;
  private String password;

  public User(int id, String name, String photo) {
    this.id = id;
    this.name = name;
    this.photo = photo;
  }

  public User(int id, String name, String photo, Boolean liked) {
    this.id = id;
    this.name = name;
    this.photo = photo;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getPhoto() {
    return photo;
  }

  public void setPhoto(String photo) {
    this.photo = photo;
  }

  public Boolean getLiked() {
    return liked;
  }

  public void setLiked(Boolean liked) {
    this.liked = liked;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }
}