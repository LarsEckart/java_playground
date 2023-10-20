package lars.katas;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class LeapYear {

  @Test
  void test_normal_leap_year() {
    assertThat(isLeapYear(1996)).isTrue();
  }

  @Test
  void test_normal_common_year() {
    assertThat(isLeapYear(2001)).isFalse();
  }

  @Test
  void test_atypical_common_year() {
    assertThat(isLeapYear(1900)).isFalse();
  }

  @Test
  void test_atypical_leap_year() {
    assertThat(isLeapYear(2000)).isTrue();
  }

  private boolean isLeapYear(int year) {
    return isTypicalLeapYear(year) && !isAtypicalCommonYear(year);
  }

  private boolean isTypicalLeapYear(int year) {
    return year % 4 == 0;
  }

  private boolean isAtypicalCommonYear(int year) {
    return year % 100 == 0 && !(year % 400 == 0);
  }
}
