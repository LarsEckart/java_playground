package lars;

import java.util.Comparator;
import java.util.Optional;
import org.junit.jupiter.api.ClassDescriptor;
import org.junit.jupiter.api.ClassOrderer;
import org.junit.jupiter.api.ClassOrdererContext;
import org.junit.jupiter.api.Tag;

public class SlowTestLastClassOrder implements ClassOrderer {

  private final Comparator<ClassDescriptor> comparator =
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
  public void orderClasses(ClassOrdererContext context) {
    context.getClassDescriptors().sort(comparator);
  }
}
