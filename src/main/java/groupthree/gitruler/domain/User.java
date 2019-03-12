package groupthree.gitruler.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import org.jasypt.util.text.BasicTextEncryptor;

@Entity(name = "user")
public class User {

  @Id
  @Column(name = "id", unique = true, nullable = false)
  private int id;

  @Column(name = "token", unique = true, nullable = false)
  private String token;

  @ManyToMany(cascade = CascadeType.ALL)
  @JoinTable(name = "attempts", 
      joinColumns = { @JoinColumn(referencedColumnName = "id") }, 
      inverseJoinColumns = { @JoinColumn(referencedColumnName = "id") })
  private List<Exercise> exercise;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }
  
  /**
   * Method decrypts given token using the given password,
   * returning an unencrypted plain string.
   */
  public String decryptToken(String token, String password) {
    BasicTextEncryptor textEncryptor = new BasicTextEncryptor();
    textEncryptor.setPassword(password);
    return textEncryptor.decrypt(token);
  }
  
  /**
   * Method encrypts given token using the given password,
   * returning an encrypted string.
   */
  public String encryptToken(String token, String password) {
    BasicTextEncryptor textEncryptor = new BasicTextEncryptor();
    textEncryptor.setPassword(password);
    return textEncryptor.encrypt(token);
  }

}
