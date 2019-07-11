package lars.quinngil;

public class FizzBuzz_02_IfOnlyAsGuardClause {

    class FizzBuzz {

        private final int input;

        public FizzBuzz(int input) {
            this.input = input;
        }

        public String result() {
            if (input % 15 == 0) {
                return "FizzBuzz";
            }
            if (input % 3 == 0) {
                return "Fizz";
            }
            if (input % 5 == 0) {
                return "Buzz";
            }

            return String.valueOf(input);
        }
    }
}
