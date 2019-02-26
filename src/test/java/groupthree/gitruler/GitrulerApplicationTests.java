package groupthree.gitruler;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import groupthree.gitruler.domain.Exercise;
import java.net.MalformedURLException;
import java.net.URL;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * This class contains the JUnit tests that test the application.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class GitrulerApplicationTests {

  @Test
  public void contextLoads() {
  }

  @Test
  public void assertsTrue() {
    assertTrue(true);
  }

  @Test
  public void testReadmeUrlReturnsCorrectLink() {
    Exercise ex = new Exercise();
    ex.setRepository("https://gitlab.com/gitlab-org/gitlab-ce");
    assertEquals("https://gitlab.com/gitlab-org/gitlab-ce/raw/master/README.md", ex.getReadmeUrl().toString());
  }

  @Test
  public void testReadMeRetrievedContentContainsWord() {
    Exercise ex = new Exercise();
    ex.setRepository("https://gitlab.com/gitlab-org/gitlab-ce");
    String contents = ex.getReadmeContents(ex.getReadmeUrl());
    System.out.println(contents.length());
    assertTrue(contents.contains("GitLab"));
  }

  @Test
  public void testReadMeRetrievedContentLength() {
    Exercise ex = new Exercise();
    ex.setRepository("https://gitlab.com/gitlab-org/gitlab-ce");
    String contents = ex.getReadmeContents(ex.getReadmeUrl());
    assertTrue(contents.length() == 5968);
  }

  @Test(expected = NullPointerException.class)
  public void testReadmeUrlThrowsNullPointerException() throws NullPointerException {
    Exercise ex = new Exercise();
    fail(ex.getReadmeUrl().toString());
  }

  @Test
  public void testRenderMarkdownProducesCorrectHtml() {
    StringBuilder contents = new StringBuilder();
    contents.append("# Foobar\n");
    contents.append("Foobar is a Python library for dealing with word pluralization.\n");
    contents.append("## Installation\n");
    contents.append("Use the package manager [pip](https://pip.pypa.io/en/stable/) to install foobar.\n");
    contents.append("```bash\n");
    contents.append("pip install foobar\n");
    contents.append("```\n");

    StringBuilder expectedOut = new StringBuilder();
    expectedOut.append("<h1>Foobar</h1>\n");
    expectedOut.append("<p>Foobar is a Python library for dealing with word pluralization.</p>\n");
    expectedOut.append("<h2>Installation</h2>\n");
    expectedOut.append(
        "<p>Use the package manager <a href=\"https://pip.pypa.io/en/stable/\">pip</a> to install foobar.</p>\n");
    expectedOut.append("<pre><code class=\"language-bash\">pip install foobar\n");
    expectedOut.append("</code></pre>\n");

    Exercise ex = new Exercise();
    String actualOut = ex.renderMarkdown(contents.toString());

    assertEquals(expectedOut.toString(), actualOut);

  }

  @Test
  public void testGetReadmeUrlNullReturnsNull() throws MalformedURLException {
    Exercise ex = new Exercise();
    ex.setRepository(null);
    assertEquals(null, ex.getReadmeUrl());
  }

  @Test
  public void testGetReadmeUrlEmptyReturnsNull() throws MalformedURLException {
    Exercise ex = new Exercise();
    ex.setRepository("notactualurl");
    assertEquals(null, ex.getReadmeUrl());
  }

  @Test
  public void testRenderMarkdownEmpty() {
    String contents = "";
    Exercise ex = new Exercise();

    String actualOut = ex.renderMarkdown(contents.toString());
    assertEquals("", actualOut);
  }

  @Test(expected = NullPointerException.class)
  public void testRenderMarkdownNull() {
    String contents = null;
    Exercise ex = new Exercise();

    @SuppressWarnings("null")
    String actualOut = ex.renderMarkdown(contents.toString());
    assertEquals(null, actualOut);
  }

  @Test(expected = MalformedURLException.class)
  public void testGetReadmeContentsUrlEmpty() throws MalformedURLException {
    Exercise ex = new Exercise();
    ex.getReadmeContents(new URL(""));
  }

  @Test(expected = MalformedURLException.class)
  public void testGetReadmeContentsUrlNull() throws MalformedURLException {
    Exercise ex = new Exercise();
    ex.getReadmeContents(new URL(null));
  }

}
