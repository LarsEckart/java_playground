package ee.lars.advent2017;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class MazeJumper_Should {

  @Test
  public void jump_according_to_current_positions_value() throws Exception {
    // given
    String maze = "0\n3\n0\n1\n-3";
    MazeJumper mazeJumper = new MazeJumper(maze);

    // when
    mazeJumper.jump();

    // then
    assertThat(mazeJumper.getPosition()).isEqualTo(0);
  }

  @Test
  public void jump_until_out_of_maze() throws Exception {
    // given
    String maze = "0\n3\n0\n1\n-3";
    MazeJumper mazeJumper = new MazeJumper(maze);
    int jumps = 0;

    // when
    while (mazeJumper.getPosition() >= 0 && mazeJumper.getPosition() < mazeJumper.getMazeSize()) {
      mazeJumper.jump();
      jumps++;
    }

    // then
    assertThat(jumps).isEqualTo(10);
  }
}
