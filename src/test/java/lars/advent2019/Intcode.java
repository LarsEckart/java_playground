package lars.advent2019;

import java.util.function.BiFunction;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class Intcode {

    @Test
    void example() {
        assertAll(
                () -> assertThat(new IntCodeProgram("1,0,0,0,99").haltState()).isEqualTo("2,0,0,0,99"),
                () -> assertThat(new IntCodeProgram("1,0,0,0,99").haltState()).isEqualTo("2,0,0,0,99"),
                () -> assertThat(new IntCodeProgram("2,3,0,3,99").haltState()).isEqualTo("2,3,0,6,99"),
                () -> assertThat(new IntCodeProgram("2,4,4,5,99,0").haltState()).isEqualTo("2,4,4,5,99,9801"),
                () -> assertThat(new IntCodeProgram("1,9,10,3,2,3,11,0,99,30,40,50").haltState()).isEqualTo("3500,9,10,70,2,3,11,0,99,30,40,50")
        );
    }

    @Test
    void solution() {
        IntCodeProgram intCodeProgram = new IntCodeProgram(
                "1,12,2,3,1,1,2,3,1,3,4,3,1,5,0,3,2,13,1,19,1,19,6,23,1,23,6,27,1,13,27,31,2,13,31,35,1,5,35,39,2,39,13,43,1,10,43,47,2,13,47,51,1,6,51,55,2,55,13,59,1,59,10,63,1,63,10,67,2,10,67,71,1,6,71,75,1,10,75,79,1,79,9,83,2,83,6,87,2,87,9,91,1,5,91,95,1,6,95,99,1,99,9,103,2,10,103,107,1,107,6,111,2,9,111,115,1,5,115,119,1,10,119,123,1,2,123,127,1,127,6,0,99,2,14,0,0");

        assertThat(intCodeProgram.haltValueAt(0)).isEqualTo("12490719");
    }

    static class IntCodeProgram {

        private final String[] memory;
        private int instructionPointer;

        IntCodeProgram(String input) {
            this.memory = input.split(",");
            this.instructionPointer = 0;
        }

        String haltState() {
            run();
            return String.join(",", memory);
        }

        String haltValueAt(int i) {
            run();
            return memory[i];
        }

        private void run() {
            BiFunction<String, String, Integer> plus = (x, y) -> Integer.parseInt(x) + Integer.parseInt(y);
            BiFunction<String, String, Integer> mal = (x, y) -> Integer.parseInt(x) * Integer.parseInt(y);
            while (true) {
                String opCode = memory[instructionPointer];
                if (!(opCode.equals("1") || opCode.equals("2"))) {
                    break;
                }
                if (opCode.equals("1")) {
                    processOpcode(plus);
                } else {
                    processOpcode(mal);
                }
                instructionPointer = instructionPointer + 4;
            }
        }

        private void processOpcode(BiFunction<String, String, Integer> f) {
            String noun = memory[Integer.parseInt(memory[instructionPointer + 1])];
            String verb = memory[Integer.parseInt(memory[instructionPointer + 2])];
            int pos = Integer.parseInt(memory[instructionPointer + 3]);

            memory[pos] = String.valueOf(f.apply(noun, verb));
        }
    }
}
