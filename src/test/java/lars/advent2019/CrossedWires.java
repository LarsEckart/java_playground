package lars.advent2019;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

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
    assertThat(grid.intersection(Location.of(6, 5))).isTrue();
  }

  @Test
  void intersection() {
    Grid grid = new Grid();

    grid.pathTo("R1,U1\nU1,R2");

    assertThat(grid.intersection(Location.of(1, 1))).isTrue();
  }

  @Test
  void not_intersection_when_self() {
    Grid grid = new Grid();

    grid.pathTo("R4,U1,L1,D2\nL1");

    assertThat(grid.intersection(Location.of(3, 0))).isFalse();
  }

  @Test
  void not_intersection_when_self_second() {
    Grid grid = new Grid();

    grid.pathTo("L1\nR4,U1,L1,D2");

    assertThat(grid.intersection(Location.of(3, 0))).isFalse();
  }

  @Test
  void closest_wire_intersection() {
    Grid grid = new Grid();

    grid.pathTo("R8,U5,L5,D3\nU7,R6,D4,L4");

    assertThat(grid.closestIntersectionDistance()).isEqualTo(6);
  }

  @Test
  void idea_why_it_might_be_wrong() {
    Grid grid = new Grid();

    grid.pathTo("R3,U3,L2,D4\nL1,U2,R3");

    assertThat(grid.closestIntersectionDistance()).isEqualTo(3);
  }

  @Test
  void more_example() {
    Grid grid = new Grid();

    grid.pathTo("R75,D30,R83,U83,L12,D49,R71,U7,L72\n" + "U62,R66,U55,R34,D71,R55,D58,R83");

    assertThat(grid.closestIntersectionDistance()).isEqualTo(159);
  }

  @Test
  void more_more_example() {
    Grid grid = new Grid();

    grid.pathTo(
        "R98,U47,R26,D63,R33,U87,L62,D20,R33,U53,R51\n" + "U98,R91,D20,R16,D67,R40,U7,R15,U6,R7");

    assertThat(grid.closestIntersectionDistance()).isEqualTo(135);
  }

  @Disabled("not needed right now")
  @Test
  void solution() {
    Grid grid = new Grid();

    grid.pathTo(
        "R1003,U476,L876,U147,R127,D10,R857,U199,R986,U311,R536,D930,R276,U589,L515,D163,L660,U69,R181,D596,L37,D359,R69,D50,L876,D867,L958,U201,R91,D127,R385,U646,L779,D309,L577,U535,R665,D669,L640,D50,L841,D32,R278,U302,L529,U679,R225,U697,R94,D205,L749,U110,L132,U664,R122,U476,R596,U399,R145,U995,R821,U80,L853,U461,L775,U57,R726,U299,L706,U500,R520,U608,L349,D636,L352,U617,R790,U947,L377,D995,R37,U445,L706,D133,R519,D194,L473,U330,L788,D599,L466,D100,L23,D68,R412,U566,R43,U333,L159,D18,L671,U135,R682,D222,R651,U138,R904,U546,R871,U264,R133,U19,R413,D235,R830,D376,R530,U18,L476,D120,L190,D252,R105,D874,L544,D705,R351,U527,L30,U283,L971,U199,L736,U36,R868,D297,L581,D888,L786,D865,R732,U394,L786,U838,L648,U434,L962,D862,R897,U116,L661,D848,L829,U930,L171,U959,R416,D855,L13,U941,R122,D678,R909,U536,R206,U39,L222,D501,L133,U360,R703,D928,R603,U793,L601,D935,R482,U444,L23,U331,L427,D349,L949,U147,L253,U757,R242,D307,R182,D371,L174,U518,L447,D851,R661,U432,R334,D240,R937,U625,L49,D105,R727,U504,L520,D126,R331,U176,L81,D168,L158,U774,L314,U623,R39,U743,R162,D646,R583,U523,R899,D419,L635,U958,R426,U482,L513,D624,L37,U669,L611,U167,L904,U163,L831,U222,L320,U561,R126,D7,L330,D313,R698,D473,R163,U527,R161,U823,L409,D734,L507,U277,L821,D341,R587,U902,R857,U386,R858,D522,R780,D754,L973,U1,R806,D439,R141,D621,R983,D546,R899,U566,L443,D147,R558,D820,R181,U351,R625,U60,R521,U225,R757,U673,L267,D624,L306,U531,L202,U854,L138,D725,R364,D813,L787,U183,R98,D899,R945,D363,L797\n"
            + "L993,D9,L41,D892,L493,D174,R20,D927,R263,D65,R476,D884,R60,D313,R175,U4,L957,U514,R821,U330,L973,U876,L856,D15,L988,U443,R205,D662,R753,U74,R270,D232,R56,D409,R2,U257,R198,U644,L435,U16,L914,D584,L909,D222,R919,U649,R77,U213,R949,D272,R893,U717,L939,U310,R637,D912,L347,D755,L895,D305,R460,D214,L826,D847,R680,U821,L688,U472,R721,U2,L755,D84,L716,U466,L833,U12,L410,D453,L462,D782,R59,U491,L235,D827,L924,U964,R443,D544,L904,D383,R259,D12,L538,D194,R945,U356,L85,D362,R672,D741,L556,U696,L994,U576,L201,D912,L282,D328,R322,D277,L269,U799,R150,U584,L479,U69,R313,U628,R114,D870,R660,D929,R964,U412,L790,U948,R949,D955,L555,U478,R967,D850,R569,D705,R30,U434,L948,U711,L507,D729,L256,U740,L60,D127,L95,U93,R260,D74,L267,D637,L658,U831,R882,D798,L173,U835,R960,D583,R411,U967,L515,U302,L456,D322,R963,U788,L516,U845,L131,U741,L246,D215,R233,U621,R420,D679,L8,D962,R514,U51,L891,U705,L699,U909,R408,D664,R324,U846,R503,U769,R32,D495,R154,U403,R145,U581,L708,D315,R556,U582,R363,U495,L722,U210,R718,U927,R994,D136,R744,U107,R316,D222,R796,U755,L69,D877,R661,D378,L215,D105,R333,D780,R335,D691,L263,U603,L582,U95,L140,D651,R414,D420,L497,U106,L470,D826,R706,D166,R500,D258,L225,U310,L866,U720,R247,D500,L340,U726,R296,U16,R227,U839,R537,U125,R700,U372,L310,D444,R214,D121,R151,U351,L767,D815,R537,U392,L595,U178,L961,D366,L216,U392,R645,U195,R231,D734,L441,D680,L226,D212,L142,U131,L427,D159,L538,D270,R553,D841,R115,U346,R673,D421,L403,D320,L296,U831,L655,U690,L105,U474,L687");

    assertThat(grid.closestIntersectionDistance()).isEqualTo(2050);
  }

  @Test
  void split_direction_command() {
    assertThat(Command.of("R1").direction()).isEqualTo('R');
    assertThat(Command.of("R1").distance()).isEqualTo(1);
    assertThat(Command.of("R14").direction()).isEqualTo('R');
    assertThat(Command.of("R14").distance()).isEqualTo(14);
    assertThat(Command.of("R144").distance()).isEqualTo(144);
  }

  static class Command {

    private final char direction;
    private final int distance;

    private Command(String command) {
      char[] chars = command.toCharArray();
      this.direction = chars[0];
      this.distance = Integer.parseInt(command.substring(1));
    }

    public static Command of(String command) {
      return new Command(command);
    }

    char direction() {
      return direction;
    }

    int distance() {
      return distance;
    }

    @Override
    public String toString() {
      return "Command{" + "direction=" + direction + ", distance=" + distance + '}';
    }
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

    int manhattan() {
      return Math.abs(this.x) + Math.abs(this.y);
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      Location location = (Location) o;
      return x == location.x && y == location.y;
    }

    @Override
    public int hashCode() {
      return Objects.hash(x, y);
    }

    @Override
    public String toString() {
      return "Location{" + "x=" + x + ", y=" + y + ", m=" + manhattan() + '}';
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

    private Set<Location> wires = new HashSet<>();
    private Set<Location> intersections = new HashSet<>();

    boolean wireAt(Location location) {
      return wires.contains(location);
    }

    void pathTo(String pathCommand) {
      String[] inputWires = pathCommand.split("\n");
      Location current = Location.of(0, 0);
      String[] directions = inputWires[0].split(",");
      for (String direction : directions) {
        Command command = Command.of(direction);
        if (command.direction() == 'R') {
          for (int i = 1; i <= command.distance(); i++) {
            current = current.right();
            wires.add(current);
          }
        }
        if (command.direction() == 'D') {
          for (int i = 1; i <= command.distance(); i++) {
            current = current.down();
            wires.add(current);
          }
        }
        if (command.direction() == 'L') {
          for (int i = 1; i <= command.distance(); i++) {
            current = current.left();
            wires.add(current);
          }
        }
        if (command.direction() == 'U') {
          for (int i = 1; i <= command.distance(); i++) {
            current = current.up();
            wires.add(current);
          }
        }
      }
      if (inputWires.length == 1) {
        return;
      }
      // if second wire intersects itself it also goes wrong :<
      current = Location.of(0, 0);
      directions = inputWires[1].split(",");
      for (String direction : directions) {
        Command command = Command.of(direction);
        if (command.direction() == 'R') {
          for (int i = 1; i <= command.distance(); i++) {
            current = current.right();
            if (wires.contains(current)) {
              intersections.add(current);
            }
          }
        }
        if (command.direction() == 'D') {
          for (int i = 1; i <= command.distance(); i++) {
            current = current.down();
            if (wires.contains(current)) {
              intersections.add(current);
            }
          }
        }
        if (command.direction() == 'L') {
          for (int i = 1; i <= command.distance(); i++) {
            current = current.left();
            if (wires.contains(current)) {
              intersections.add(current);
            }
          }
        }
        if (command.direction() == 'U') {
          for (int i = 1; i <= command.distance(); i++) {
            current = current.up();
            if (wires.contains(current)) {
              intersections.add(current);
            }
          }
        }
      }
    }

    boolean intersection(Location location) {
      return intersections.contains(location);
    }

    int closestIntersectionDistance() {
      return intersections.stream()
          .mapToInt(Location::manhattan)
          .min()
          .orElseThrow(() -> new IllegalStateException("no intersections"));
    }
  }
}
