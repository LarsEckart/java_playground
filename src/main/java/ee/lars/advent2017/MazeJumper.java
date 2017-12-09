package ee.lars.advent2017;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class MazeJumper {

  private final List<Integer> maze;
  private int position;
  private int jumps;

  public MazeJumper(String maze) {
    position = 0;
    jumps = 0;
    Pattern pattern = Pattern.compile("\\R");
    this.maze = pattern.splitAsStream(maze)
        .mapToInt(Integer::parseInt)
        .boxed()
        .collect(Collectors.toList());
  }

  public void jump() {
    if (hasEscaped()) {
      throw new UnsupportedOperationException("Cannot jump anymore, already escaped");
    }
    jumps++;
    Integer currentValue = maze.get(position);
    Integer newValue;
    if (currentValue >= 3) {
      newValue = currentValue - 1;
    } else {
      newValue = currentValue + 1;
    }
    maze.set(position, newValue);
    position += currentValue;
  }

  public int getPosition() {
    return position;
  }

  public int getMazeSize() {
    return maze.size();
  }

  public boolean hasEscaped() {
    return position < 0 || position >= maze.size();
  }

  public int getJumps() {
    return jumps;
  }
}
