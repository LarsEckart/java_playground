package lars.spielplatz.tc;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ChatHistoryLineTest {

  private ChatHistoryLine chatHistoryLine;

  @BeforeEach
  void setUp() {
    chatHistoryLine =
        new ChatHistoryLine("Alice", "Hello", LocalDateTime.parse("2021-10-10T10:10:10"));
  }

  @Test
  void renderChatHistoryLine() {
    String expected = "[2021-10-10T10:10:10] Alice: Hello";
    assertEquals(expected, chatHistoryLine.renderChatHistoryLine());
  }
}
