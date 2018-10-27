package lars.java11;

import java.util.Optional;

public interface Search {
    Optional<Customer> inMemory(String id);
    Optional<Customer> onDisk(String id);
    Optional<Customer> remotely(String id);

    default Optional<Customer> anywhere(String id) {
        return inMemory(id)
            .or(() -> onDisk(id))
            .or(() -> remotely(id));
    }

    class Customer {

    }
}
