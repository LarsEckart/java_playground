package ee.lars.di.spring.sequence;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SequenceGenerator {

    private PrefixGenerator prefixGenerator;
    private String prefix;
    private String suffix;
    private int initial;
    private int counter;

    public SequenceGenerator() {
    }

    public SequenceGenerator(String prefix, String suffix, int initial) {
        this.prefix = prefix;
        this.suffix = suffix;
        this.initial = initial;
    }

    @Autowired
    public SequenceGenerator(PrefixGenerator prefix, String suffix, int initial) {
        System.out.println("sg constructor");
        this.prefixGenerator = prefix;
        this.suffix = suffix;
        this.initial = initial;
    }

    public synchronized String getSequence() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(prefixGenerator.getPrefix());
        buffer.append(" " + initial + counter++ + " ");
        buffer.append(suffix);
        return buffer.toString();
    }
}