package lars.quinngil;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class FizzBuzz_03_IsolateTheirCode {

    class FizzBuzz {

        private String input;

        public FizzBuzz(String input) {
            this.input = input;
        }

        public String result() {
            JsonObject jsonObject = (JsonObject) JsonParser.parseString(input);
            int input = jsonObject.get("number").getAsInt();

            if (IsFizzBuzz(input)) {
                return "FizzBuzz";
            }
            if (IsFizz(input)) {
                return "Fizz";
            }
            if (IsBuzz(input)) {
                return "Buzz";
            }
            return String.valueOf(input);
        }

        private boolean IsFizzBuzz(int input) {
            return input % 15 == 0;
        }

        private boolean IsFizz(int input) {
            return input % 3 == 0;
        }

        private boolean IsBuzz(int input) {
            return input % 5 == 0;
        }
    }
}