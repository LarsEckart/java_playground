package ee.lars;

import java.util.LinkedHashMap;
import java.util.Map;

public class EfficientCounter {

    public void naiveApproach() {
        String s = "one two three two three three";
        String[] sArr = s.split(" ");
        Map<String, Integer> counter = new LinkedHashMap<>();

        for (String a : sArr) {
            if (counter.containsKey(a)) {
                int oldValue = counter.get(a);
                counter.put(a, oldValue + 1);
            } else {
                counter.put(a, 1);
            }
        }
    }

    public void improvedApproach() {
        String s = "one two three two three three";
        String[] sArr = s.split(" ");
        Map<String, MutableInteger> newCounter = new LinkedHashMap<>();

        for (String a : sArr) {
            if (newCounter.containsKey(a)) {
                MutableInteger oldValue = newCounter.get(a);
                oldValue.set(oldValue.get() + 1);
            } else {
                newCounter.put(a, new MutableInteger(1));
            }
        }
    }

    public void efficientApproach() {
        String s = "one two three two three three";
        String[] sArr = s.split(" ");
        Map<String, MutableInteger> efficientCounter = new LinkedHashMap<>();

        for (String a : sArr) {
            MutableInteger initValue = new MutableInteger(1);
            MutableInteger oldValue = efficientCounter.put(a, initValue);

            if (oldValue != null) {
                initValue.set(oldValue.get() + 1);
            }
        }
    }

    class MutableInteger {

        private int val;

        public MutableInteger(int val) {
            this.val = val;
        }

        public int get() {
            return this.val;
        }

        public void set(int val) {
            this.val = val;
        }

        @Override
        public String toString() {
            return Integer.toString(val);
        }
    }
}
