package lars.advent2019;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

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

    @Test
    void medium_wire_going_down_from_start() {
        Grid grid = new Grid();

        grid.pathTo("D3");

        assertThat(grid.wireAt(Location.of(0, -3))).isTrue();
    }

    @Test
    void medium_wire_going_left_from_start() {
        Grid grid = new Grid();

        grid.pathTo("L3");

        assertThat(grid.wireAt(Location.of(-3, 0))).isTrue();
    }

    @Test
    void medium_wire_going_up_from_start() {
        Grid grid = new Grid();

        grid.pathTo("U3");

        assertThat(grid.wireAt(Location.of(0, 3))).isTrue();
    }

    @Test
    void medium_wire_going_up_then_right_from_start() {
        Grid grid = new Grid();

        grid.pathTo("U3,R3");

        assertThat(grid.wireAt(Location.of(3, 3))).isTrue();
    }

    @Test
    void example_wire() {
        Grid grid = new Grid();

        grid.pathTo("R8,U5,L5,D3");

        assertThat(grid.wireAt(Location.of(3, 2))).isTrue();
    }

    @Test
    void second_wire() {
        Grid grid = new Grid();

        grid.pathTo("R8,U5,L5,D3\nU7,R6,D4,L4");

        assertThat(grid.intersection(Location.of(3, 3))).isTrue();
    }

    @Test
    void closest_wire_intersection() {
        Grid grid = new Grid();

        grid.pathTo("R8,U5,L5,D3\nU7,R6,D4,L4");

        assertThat(grid.closestIntersectionDistance()).isEqualTo(6);
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

        int manhatten() {
            return Math.abs(this.x) + Math.abs(this.y);
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

        @Override public String toString() {
            return "Location{" +
                    "x=" + x +
                    ", y=" + y +
                    '}';
        }

        Location right() {
            return Location.of(this.x + 1, y);
        }

        Location down() {
            return Location.of(this.x, this.y - 1);
        }

        Location left() {
            return Location.of(this.x - 1, this.y);
        }

        Location up() {
            return Location.of(this.x, this.y + 1);
        }
    }

    static class Grid {

        private Location origin = Location.of(0, 0);
        private Location current = origin;

        private List<Location> wires = new ArrayList<>();
        private Set<Location> intersections = new LinkedHashSet<>();

        boolean wireAt(Location location) {
            return wires.contains(location);
        }

        void pathTo(String pathCommand) {
            String[] inputWires = pathCommand.split("\n");
            for (String wire : inputWires) {
                current = origin;
                String[] directions = wire.split(",");
                for (String direction : directions) {
                    char[] chars = direction.toCharArray();
                    if (chars[0] == 'R') {
                        for (int i = 1; i <= Character.getNumericValue(chars[1]); i++) {
                            current = current.right();
                            wires.add(current);
                        }
                    }
                    if (chars[0] == 'D') {
                        for (int i = 1; i <= Character.getNumericValue(chars[1]); i++) {
                            current = current.down();
                            wires.add(current);
                        }
                    }
                    if (chars[0] == 'L') {
                        for (int i = 1; i <= Character.getNumericValue(chars[1]); i++) {
                            current = current.left();
                            wires.add(current);
                        }
                    }
                    if (chars[0] == 'U') {
                        for (int i = 1; i <= Character.getNumericValue(chars[1]); i++) {
                            current = current.up();
                            wires.add(current);
                        }
                    }
                }
            }
            for (int i = 0; i < wires.size(); i++) {
                for (int j = i + 1; j < wires.size(); j++) {
                    if (wires.get(i).equals(wires.get(j))) {
                        intersections.add(wires.get(i));
                    }
                }
            }
        }

        boolean intersection(Location location) {
            return intersections.contains(location);
        }

        int closestIntersectionDistance() {
            return intersections.stream()
                    .mapToInt(Location::manhatten)
                    .min()
                    .orElseThrow(() -> new IllegalStateException("no intersections"));
        }

    }
}
