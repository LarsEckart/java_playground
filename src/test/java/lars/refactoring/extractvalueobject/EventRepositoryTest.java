package lars.refactoring.extractvalueobject;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class EventRepositoryTest {

    @Test
    void should_initialize_empty() {
        assertThat(repository.count()).isEqualTo(0);
    }

    @Test
    void should_add_event() {
        repository.add(EXPECTED_EVENT);

        assertThat(repository.count()).isEqualTo(1);
    }

    @Test
    void should_not_add_null_event() {
        assertThatThrownBy(
                () -> repository.add(null)
        ).isInstanceOf(NullPointerException.class);
    }

    @Test
    void should_find_event_by_name() {
        repository.add(EXPECTED_EVENT);
        repository.add(Event.of("anotherEvent", FROM_DATE, TO_DATE));

        Optional<Event> found = repository.findByName(EVENT_NAME);

        assertThat(found).containsSame(EXPECTED_EVENT);
    }

    @Test
    void should_not_remove_anything_on_null_date() {
        repository.add(EXPECTED_EVENT);

        assertThatThrownBy(
                () -> repository.removeOverlappingEvents(null)
        ).isInstanceOf(NullPointerException.class);

        assertThat(repository.count()).isEqualTo(1);
    }

    @Test
    void should_not_remove_non_overlapping_events() {
        repository.add(EXPECTED_EVENT);

        repository.removeOverlappingEvents(TO_DATE.plusDays(999));

        assertThat(repository.count()).isEqualTo(1);
    }

    @Test
    void should_remove_overlapping_events() {
        repository.add(EXPECTED_EVENT);

        repository.removeOverlappingEvents(FROM_DATE.plusDays(1));

        assertThat(repository.count()).isEqualTo(0);
    }

    @Test
    void should_remove_events_at_same_from_date() {
        repository.add(EXPECTED_EVENT);

        repository.removeOverlappingEvents(FROM_DATE);

        assertThat(repository.count()).isEqualTo(0);
    }

    @Test
    void should_remove_events_at_same_to_date() {
        repository.add(EXPECTED_EVENT);

        repository.removeOverlappingEvents(TO_DATE);

        assertThat(repository.count()).isEqualTo(0);
    }

    private final EventRepository repository = new EventRepository();
    private static final LocalDate FROM_DATE = LocalDate.of(2019, 11, 11);
    private static final LocalDate TO_DATE = LocalDate.of(2019, 11, 13);
    private static final String EVENT_NAME = "irrelevant";
    private static final Event EXPECTED_EVENT = Event.of(EVENT_NAME, FROM_DATE, TO_DATE);
}
