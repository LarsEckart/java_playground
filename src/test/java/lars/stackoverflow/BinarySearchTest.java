package lars.stackoverflow;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class BinarySearchTest {

  private BinarySearch<Integer> integerBinarySearch;

  @BeforeEach
  void setUp() {
    integerBinarySearch = new BinarySearch<>();
  }

  @Test
  void returns_index_of_searched_element_when_searched_element_is_in_the_beginning_of_array() {
    // given
    Integer[] numbers = new Integer[] {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
    int numberToSearch = 1;

    // when
    int indexOfSearchedNumber =
        integerBinarySearch.search(numbers, numberToSearch, 0, numbers.length - 1);

    // then
    assertThat(indexOfSearchedNumber).isEqualTo(0);
  }

  @Test
  void returns_index_of_searched_element_when_searched_element_is_in_the_end_of_array() {
    // given
    Integer[] numbers = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
    int numberToSearch = 10;

    // when
    int indexOfSearchedNumber =
        integerBinarySearch.search(numbers, numberToSearch, 0, numbers.length - 1);

    // then
    assertThat(indexOfSearchedNumber).isEqualTo(9);
  }

  @Test
  void returns_index_of_searched_element_when_searched_element_is_in_the_middle_of_array() {
    // given
    Integer[] numbers = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11};
    int numberToSearch = 6;

    // when
    int indexOfSearchedNumber =
        integerBinarySearch.search(numbers, numberToSearch, 0, numbers.length - 1);

    // then
    assertThat(indexOfSearchedNumber).isEqualTo(5);
  }

  @Disabled
  @Test
  void returns_minus_one_when_element_is_not_in_array() {
    // given
    Integer[] numbers = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
    int numberToSearch = 42;

    // when
    int indexOfSearchedNumber =
        integerBinarySearch.search(numbers, numberToSearch, 0, numbers.length - 1);

    // then
    assertThat(indexOfSearchedNumber).isEqualTo(-1);
  }
}
