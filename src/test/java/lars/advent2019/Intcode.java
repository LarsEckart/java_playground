package lars.advent2019;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class Intcode {

    @Test
    void example() {
        assertAll(
                () -> assertThat(opcode("1,0,0,0,99")).isEqualTo("2,0,0,0,99"),
                () -> assertThat(opcode("2,3,0,3,99")).isEqualTo("2,3,0,6,99"),
                () -> assertThat(opcode("2,4,4,5,99,0")).isEqualTo("2,4,4,5,99,9801"),
                () -> assertThat(opcode("1,9,10,3,2,3,11,0,99,30,40,50")).isEqualTo("3500,9,10,70,2,3,11,0,99,30,40,50"),
                () -> assertThat(opcode("12490719,12,2,2,1,1,2,3,1,3,4,3,1,5,0,3,2,13,1,60,1,19,6,62,1,23,6,64,1,13,27,69,2,13,31,345,1,5,35,346,2,39,13,1730,1,10,43,1734,2,13,47,8670,1,6,51,8672,2,55,13,43360,1,59,10,43364,1,63,10,43368,2,10,67,173472,1,6,71,173474,1,10,75,173478,1,79,9,173481,2,83,6,346962,2,87,9,1040886,1,5,91,1040887,1,6,95,1040889,1,99,9,1040892,2,10,103,4163568,1,107,6,4163570,2,9,111,12490710,1,5,115,12490711,1,10,119,12490715,1,2,123,12490717,1,127,6,0,99,2,14,0,0")).isEqualTo("12490719,12,2,2,1,1,2,3,1,3,4,3,1,5,0,3,2,13,1,60,1,19,6,62,1,23,6,64,1,13,27,69,2,13,31,345,1,5,35,346,2,39,13,1730,1,10,43,1734,2,13,47,8670,1,6,51,8672,2,55,13,43360,1,59,10,43364,1,63,10,43368,2,10,67,173472,1,6,71,173474,1,10,75,173478,1,79,9,173481,2,83,6,346962,2,87,9,1040886,1,5,91,1040887,1,6,95,1040889,1,99,9,1040892,2,10,103,4163568,1,107,6,4163570,2,9,111,12490710,1,5,115,12490711,1,10,119,12490715,1,2,123,12490717,1,127,6,0,99,2,14,0,0")
        );
    }

    private String opcode(String input) {
        String[] split = input.split(",");
        int i = 0;
        while (true) {
            String opCode = split[i];
            if (!(opCode.equals("1") || opCode.equals("2"))) break;
            doStuff(split, i);
            i = i + 4;
        }
        return String.join(",", split);
    }

    private void doStuff(String[] split, int i) {
        String operation = split[i];
        int first = Integer.parseInt(split[i+1]);
        int second = Integer.parseInt(split[i+2]);
        int pos = Integer.parseInt(split[i+3]);
        if (operation.equals("1")) {
            int sum = Integer.parseInt(split[first]) + Integer.parseInt(split[second]);
            split[pos] = String.valueOf(sum);
        }
        if (operation.equals("2")) {
            int sum = Integer.parseInt(split[first]) * Integer.parseInt(split[second]);
            split[pos] = String.valueOf(sum);
        }
    }
}
