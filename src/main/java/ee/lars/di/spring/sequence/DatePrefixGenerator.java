package ee.lars.di.spring.sequence;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javax.annotation.PostConstruct;

@Component
public class DatePrefixGenerator implements PrefixGenerator {

    private final DateTimeFormatter formatter;

    public DatePrefixGenerator(String pattern) {
        System.out.println("dpg constructor");
        this.formatter = DateTimeFormatter.ofPattern(pattern);
    }

    @Override
    public String getPrefix() {
        return this.formatter.format(LocalDate.now());
    }

    @PostConstruct
    public void post() {
        System.out.println("dpg post");
    }
}
