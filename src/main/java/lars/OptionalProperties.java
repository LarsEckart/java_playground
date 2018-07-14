package lars;

import java.util.Map;
import java.util.Optional;

public class OptionalProperties {

    private Map<String, String> properties;

    public Optional<String> getProperty(String propertyName) {
        return Optional.ofNullable(properties.get(propertyName));
    }

    public <T> Optional<T> getProperty(String propertyName, ThrowingFunction<String, ? extends T, ? extends Exception> supplier) {
        return this.getProperty(propertyName).map(val -> {
            try {
                return supplier.apply(val);
            } catch (Exception e) {
                System.err.println("Invalid property transform, will default." + e.getMessage());
                return null;
            }
        });
    }

    public static void main(String[] args) {
        final OptionalProperties config = new OptionalProperties();
        int size = config.getProperty("cache.limit", Integer::parseInt).orElse(500);
        boolean enabled = config.getProperty("cache.enabled", Boolean::getBoolean).orElse(true);
    }

    @FunctionalInterface
    interface ThrowingFunction<T, R, E extends Throwable> {

        R apply(T arg) throws E;
    }
}
