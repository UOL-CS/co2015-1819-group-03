package groupthree.gitruler.domain;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;


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

  /**
   * Returns a URL for the README.md file of the exercise repository.
   */
  public URL getReadmeUrl() {
    URL url = null;
    try {
      url = new URL(repository + "/raw/master/README.md");
    } catch (MalformedURLException e) {
      e.printStackTrace();
    }

    return url;
  }


  /**
   * Retrieves and appends markdown to a string, adding a new line
   * after each parsed line. Returns appended string.
   */
  public String getReadmeContents(URL url) {
    StringBuilder contents = new StringBuilder();
    try (BufferedReader reader = new BufferedReader(
            new InputStreamReader(url.openStream(), "UTF-8"))) {
      for (String line; (line = reader.readLine()) != null; ) {
        contents.append(line + "\n");
      }
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }

    return contents.toString();
  }

  /**
   * Parses the retrieved markdown string and produces HTML.
   */
  public String renderMarkdown(String contents) {
    Parser parser = Parser.builder().build();
    Node document = parser.parse(contents);
    HtmlRenderer renderer = HtmlRenderer.builder().softbreak("\n").build();

    return renderer.render(document);
  }
}
