package lars.cracking.chap1;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;

public class UrlifyTest {

  private Urlify urlify;

  @Before
  public void initialize() throws Exception {
    this.urlify = new Urlify();
  }

  @Test
  public void should_replace_space_with_percent_20() throws Exception {
    // when
    final String url = this.urlify.urlify("Mr John Smith", 13);

    // then
    assertThat(url).isEqualTo("Mr%20John%20Smith");
  }
}
