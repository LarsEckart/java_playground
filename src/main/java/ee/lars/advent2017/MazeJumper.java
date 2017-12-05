package ee.lars.advent2017;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class MazeJumper {

  private final List<Integer> maze;
  private int position;

  public MazeJumper(String maze) {
    position = 0;
    this.maze = Arrays.stream(maze.split("\n"))
        .mapToInt(Integer::parseInt)
        .boxed()
        .collect(Collectors.toList());
  }

  public void jump() {
    Integer currentValue = maze.get(position);
    Integer newValue = currentValue + 1;
    maze.set(position, newValue);
    position += currentValue;
  }

  public int getPosition() {
    return position;
  }

  public int getMazeSize() {
    return maze.size();
  }
}
