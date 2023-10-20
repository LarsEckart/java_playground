package lars.advent2017;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

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
  public void jump_until_has_escaped() throws Exception {
    // given
    String maze = "0\n3\n0\n1\n-3";
    MazeJumper mazeJumper = new MazeJumper(maze);

    // when
    while (!mazeJumper.hasEscaped()) {
      mazeJumper.jump();
    }

    // then
    assertThat(mazeJumper.getJumps()).isEqualTo(10);
  }
}
