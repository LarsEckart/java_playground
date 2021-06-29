package lars.katas;

import static org.junit.jupiter.api.Assertions.*;

import org.approvaltests.Approvals;
import org.junit.jupiter.api.Test;

class SongTest {

  @Test
  void characterization() {
    Approvals.verify(new Song().lyrics());
  }
}
