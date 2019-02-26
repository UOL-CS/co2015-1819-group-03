package groupthree.gitruler.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name = "exercises")
public class Exercise {
  
  @Id
  @GeneratedValue(strategy = GenerationType.TABLE)
  @Column(name = "id", unique = true, nullable = false)
  private int id;
  
  @Column(name = "name", unique = true, nullable = false, length = 100)
  private String name;

  private String description;
  
  private String theme;
  
  private int point;
  
  @Column(name = "repository", unique = true, nullable = false)
  private String repository;

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

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }
  
  public String getTheme() {
    return theme;
  }

  public void setTheme(String theme) {
    this.theme = theme;
  }

  public int getPoint() {
    return point;
  }

  public void setPoint(int point) {
    this.point = point;
  }
  
  public String getRepository() {
    return repository;
  }

  public void setRepository(String repository) {
    this.repository = repository;
  }
  
}
