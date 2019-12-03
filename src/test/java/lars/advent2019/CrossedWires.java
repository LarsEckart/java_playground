package lars.advent2019;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CrossedWires {

    @Test
    void short_wire_going_right_from_start() {
        Grid grid = new Grid();

        grid.pathTo("R1");

        assertThat(grid.wireAt(Location.of(1, 0))).isTrue();
    }

    @Test
    void medium_wire_going_right_from_start() {
        Grid grid = new Grid();

        grid.pathTo("R3");

        assertThat(grid.wireAt(Location.of(3, 0))).isTrue();
    }

    static class Location {

        private final int x;
        private final int y;

        private Location(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public static Location of(int x, int y) {
            return new Location(x, y);
        }

        @Override public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Location location = (Location) o;
            return x == location.x &&
                    y == location.y;
        }

        @Override public int hashCode() {
            return Objects.hash(x, y);
        }

        Location right() {
            return Location.of(this.x + 1, y);
        }
    }

    static class Grid {

        private Location origin = Location.of(0, 0);
        private Location current = origin;

        private List<Location> wires = new ArrayList<>();

        boolean wireAt(Location of) {
            return wires.contains(of);
        }

        void pathTo(String pathCommand) {
            char[] chars = pathCommand.toCharArray();
            if (chars[0] == 'R') {
                for (int i = 1; i <= Character.getNumericValue(chars[1]); i++) {
                    current = current.right();
                    wires.add(current);
                }
            }
        }
    }
}
