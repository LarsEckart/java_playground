package ee.lars.spring.sequence;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
public class DatePrefixGenerator implements PrefixGenerator {

    private final DateTimeFormatter formatter;

    public DatePrefixGenerator(String pattern) {
        this.formatter = DateTimeFormatter.ofPattern(pattern);
    }

    public String getPrefix() {
        return this.formatter.format(LocalDate.now());
    }
}
