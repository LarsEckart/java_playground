package lars;

import java.util.Comparator;
import java.util.Optional;
import org.junit.jupiter.api.MethodDescriptor;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.MethodOrdererContext;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.parallel.ExecutionMode;

public class SlowTestLastMethodOrder implements MethodOrderer {

  private final Comparator<MethodDescriptor> comparator =
      (o1, o2) -> {
        Optional<Tag> annotation = o1.findAnnotation(Tag.class);
        boolean present = annotation.isPresent();
        if (present) {
          String value = annotation.get().value();
          return "slow".equalsIgnoreCase(value) ? 1 : -1;
        }
        return 0;
      };

  @Override
  public void orderMethods(MethodOrdererContext context) {
    context.getMethodDescriptors().sort(comparator);
  }

  @Override
  public Optional<ExecutionMode> getDefaultExecutionMode() {
    return MethodOrderer.super.getDefaultExecutionMode();
  }
}
