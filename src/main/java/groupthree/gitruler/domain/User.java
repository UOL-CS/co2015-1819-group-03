package groupthree.gitruler.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import org.jasypt.util.text.BasicTextEncryptor;

@Entity(name = "user")
public class User {

  @Id
  @Column(name = "id", unique = true, nullable = false)
  private int id;

  @Column(name = "token", unique = true, nullable = false)
  private String token;

  @OneToMany(mappedBy = "user")
  private List<Attempt> attempts;

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

  public List<Attempt> getAttempts() {
    return attempts;
  }

  public void setAttempts(List<Attempt> attempts) {
    this.attempts = attempts;
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
