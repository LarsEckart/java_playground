package lars.katas;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class PerfectNumberFinder_should {

    private static Integer[] PERFECT_NUMBERS = {6, 28, 496, 8128, 33550336};

    @Test
    void find_perfect_numbers() {
        for (int i : PERFECT_NUMBERS) {
            assertThat(PerfectNumberFinder.isPerfect(i)).isTrue();
        }
    }

    @Test
    void reject_non_perfect_numbers() {
        List<Integer> perfectNumbers = Arrays.asList(PERFECT_NUMBERS);
        for (int i = 2; i < 10000; i++) {
            if (perfectNumbers.contains(i)) {
                assertThat(PerfectNumberFinder.isPerfect(i)).isTrue();
            } else {
                assertThat(PerfectNumberFinder.isPerfect(i)).isFalse();
            }
        }
    }

    @Test
    void is_factor() {
        assertThat(Classifier1.isFactor(1, 10)).isTrue();
    }

    @Test
    void factor_for() {
        int[] expected = new int[] {1};
        assertThat(Classifier1.factorsFor(1)).isEqualTo(expected);
    }

    private static class Classifier1 {

        public static boolean isFactor(int factor, int number) {
            return number % factor == 0;
        }

        public static int[] factorsFor(int number) {
            return new int[] {number};
        }
    }

    @Test
    void is_1_a_factor_of_10() {
        Classifier2 c = new Classifier2(10);
        assertThat(c.isFactor(1)).isTrue();
    }

    @Test
    void is_5_a_factor_of_25() {
        Classifier2 c = new Classifier2(25);
        assertThat(c.isFactor(5)).isTrue();
    }

    @Test
    void is_3_not_a_factor_of_70() {
        Classifier2 c = new Classifier2(70);
        assertThat(c.isFactor(3)).isFalse();
    }

    @Test
    void factors_for_6() {
        int[] expected = new int[] {1, 2, 3, 6};
        Classifier2 c = new Classifier2(6);
        assertThat(c.getFactors()).isEqualTo(expected);
    }

    private class Classifier2 {

        private final int number;

        public Classifier2(int number) {
            this.number = number;
        }

        public boolean isFactor(int factor) {
            return number % factor == 0;
        }

        public int[] getFactors() {
            List<Integer> factors = new ArrayList<>();
            factors.add(1);
            for (int i = 2; i < number; i++) {
                if (isFactor(i)) {
                    factors.add(i);
                }
            }
            factors.add(number);
            int i = 0;
            int[] result = new int[factors.size()];
            for (Integer f : factors) {
                result[i++] = f;
            }
            return result;
        }
    }

    @Test
    void add_factors() {
        Classifier3 c = new Classifier3(6);
        c.addFactor(2);
        c.addFactor(3);
        Set<Integer> expected = new HashSet<>(Arrays.asList(1, 2, 3, 6));
        assertThat(c.getFactors()).isEqualTo(expected);
    }

    private class Classifier3 {

        private final int number;
        private Set<Integer> factors;

        public Classifier3(int number) {
            this.number = number;
            this.factors = new HashSet<>();
            this.factors.add(1);
            this.factors.add(number);
        }

        public void addFactor(int factor) {
            factors.add(factor);
        }

        public Set<Integer> getFactors() {
            return factors;
        }
    }

    @Test
    void calculate_factors_for_6() {
        Set<Integer> expected = expectedSetWith(1, 2, 3, 6);
        Classifier4 c = new Classifier4(6);
        c.calculateFactors();
        assertThat(c.getFactors()).isEqualTo(expected);
    }

    private Set<Integer> expectedSetWith(Integer... numbers) {
        return new HashSet<>(Arrays.asList(numbers));
    }

    private class Classifier4 {

        private final int number;
        private Set<Integer> factors;

        public Classifier4(int number) {
            this.number = number;
            this.factors = new HashSet<>();
            this.factors.add(1);
            this.factors.add(number);
        }

        public void addFactor(int factor) {
            factors.add(factor);
            factors.add(number / factor);
        }

        public Set<Integer> getFactors() {
            return factors;
        }

        public boolean isFactor(int factor) {
            return number % factor == 0;
        }

        public void calculateFactors() {
            for (int i = 2; i < Math.sqrt(number); i++) {
                if (isFactor(i)) {
                    addFactor(i);
                }
            }
        }

        public int sumOfFactors() {
            return factors.stream().mapToInt(i -> i).sum();
        }

        public boolean isPerfect() {
            calculateFactors();
            return sumOfFactors() - number == number;
        }
    }

    @Test
    void sum_of_factors() {
        Classifier4 c = new Classifier4(20);
        c.calculateFactors();
        int expected = 1 + 20 + 2 + 10 + 5 + 4;
        assertThat(c.sumOfFactors()).isEqualTo(expected);
    }

    @Test
    void perfection() {
        for (int i : PERFECT_NUMBERS) {
            Classifier4 c = new Classifier4(i);
            assertThat(c.isPerfect()).isTrue();
        }
    }
}
