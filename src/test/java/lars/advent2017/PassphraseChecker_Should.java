package lars.advent2017;

import java.util.ArrayList;
import java.util.List;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(JUnitParamsRunner.class)
public class PassphraseChecker_Should {

  private PassphraseChecker passphraseChecker;

  @Before
  public void setUp() throws Exception {
    passphraseChecker = new PassphraseChecker();
  }

  @Parameters({
    "aa bb cc dd, true",
    "aa bb cc dd aa, false",
    "aa bb cc dd aaa, true",
  })
  @Test
  public void accept_passphrases_with_no_duplicates(String passphrase, boolean isAccepted)
      throws Exception {
    assertThat(passphraseChecker.isAcceptedNoDuplicates(passphrase)).isEqualTo(isAccepted);
  }

  @Parameters({
    "lars, sral, true",
    "lars, oskar, false",
    "lars, oskar, false",
  })
  @Test
  public void accept_when_two_words_are_not_anagram(String first, String second, boolean isAnagram)
      throws Exception {
    assertThat(passphraseChecker.isAnagram(first, second)).isEqualTo(isAnagram);
  }

  @Parameters({
    "abcde fghij, true",
    "abcde fghij abcd, true",
    "a ab abc abd abf abj, true",
    "iiii oiii ooii oooi oooo, true",
    "oiii ioii iioi iiio, false",
    "abcde xyz ecdab, false",
  })
  @Test
  public void accept_passphrase_when_no_anagram(String passphrase, boolean isAccepted)
      throws Exception {
    assertThat(passphraseChecker.isValidNoAnagrams(passphrase)).isEqualTo(isAccepted);
  }

  @Test
  public void count_valid_passphrases_without_duplicate() throws Exception {
    // given
    List<String> passphrases = new ArrayList<>();
    passphrases.add("aa bb cc dd");
    passphrases.add("aa bb cc dd ee");

    // when
    long sum = passphraseChecker.countValidPassphrases(passphrases);
    // then
    assertThat(sum).isEqualTo(2);
  }

  @Test
  public void count_valid_passphrases_without_anagram() throws Exception {
    // given
    List<String> passphrases = new ArrayList<>();
    passphrases.add("abcde fghij");
    passphrases.add("abcde xyz ecdab");

    // when
    long sum = passphraseChecker.countValidPassphrasesNoAnagram(passphrases);

    // then
    assertThat(sum).isEqualTo(1);
  }
}
