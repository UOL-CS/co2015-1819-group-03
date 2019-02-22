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

  @Column(name = "description")
  private String description;
  
  @Column(name = "icon")
  private String icon;
  
  @Column(name = "point")
  private int point;

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
  
  public String getIcon() {
    return icon;
  }

  public void setIcon(String icon) {
    this.icon = icon;
  }

  public int getPoint() {
    return point;
  }

  public void setPoint(int point) {
    this.point = point;
  }
  
}
