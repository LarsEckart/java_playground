package lars.logging;

import static org.assertj.core.api.Assertions.assertThat;

import ch.qos.logback.classic.spi.ILoggingEvent;
import com.innoq.junit.jupiter.logging.Logging;
import com.innoq.junit.jupiter.logging.LoggingEvents;
import lars.hexagon.taxes.HexagonalExample;
import org.junit.jupiter.api.Test;

@Logging
class WorkflowTest {

  @Test
  void test(LoggingEvents events) {
    HexagonalExample.main(new String[] {});

    assertThat(events.isEmpty()).isFalse();
    assertThat(events.all())
        .extracting(ILoggingEvent::getMessage)
        .containsExactly("the tax is 15.0");
  }
}
