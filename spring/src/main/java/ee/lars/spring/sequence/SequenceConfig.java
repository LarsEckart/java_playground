package ee.lars.spring.sequence;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SequenceConfig {

    @Bean
    public SequenceGenerator sequenceGenerator(PrefixGenerator prefix) {
        return new SequenceGenerator(prefix, ">", 1000);
    }

    @Bean
    public PrefixGenerator prefixGenerator() {
        return new DatePrefixGenerator("yyyyMMdd");
    }

    @Bean
    public MyPostProcessor prefixGeneratorPostProcessor() {
        return new MyPostProcessor();
    }
}
