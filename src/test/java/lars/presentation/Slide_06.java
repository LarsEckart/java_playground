package lars.presentation;

import org.junit.jupiter.api.Test;

import java.util.Optional;

public class Slide_06 {

    @Test
    void optional() {
        Optional<String> middleName = findMiddleName();

        System.out.println("middle name: " + middleName.get());

        System.out.println("middle name: " + middleName.orElseThrow());

        middleName.ifPresent(m -> System.out.println("middle name: " + m));
    }

    private Optional<String> findMiddleName() {
        return Optional.empty();
    }

    interface Search {

        Optional<String> inMemory(long id);

        Optional<String> onDisk(long id);

        Optional<String> remotely(long id);

        default Optional<String> anywhere(long id) {
            return inMemory(id)
                    .or(() -> onDisk(id))
                    .or(() -> remotely(id));
        }
    }

    @Test
    void if_present_or_else() {
        Optional<String> name = Optional.empty();

        name.ifPresentOrElse(
                System.out::println,
                () -> System.out.println("No middle name")
        );
    }
}
