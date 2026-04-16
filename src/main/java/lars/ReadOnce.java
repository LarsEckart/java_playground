package lars;

import java.util.concurrent.atomic.AtomicBoolean;

public class ReadOnce {
  private final AtomicBoolean locked = new AtomicBoolean(false);
  private final String value;

  public ReadOnce(String value) {
    this.value = value;
  }

  @Override
  public String toString() {
    return "[redacted]";
  }

  public String unwrap() {
    if (!this.locked.compareAndSet(false, true)) {
      throw new IllegalStateException("already read");
    }
    return this.value;
  }
}
