package ee.lars.spring.sequence;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class SequenceMain {

    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(SequenceConfig.class);

        final SequenceGenerator sequenceGenerator = (SequenceGenerator) context.getBean("sequenceGenerator");

        System.out.println(sequenceGenerator.getSequence());
        System.out.println(sequenceGenerator.getSequence());
        System.out.println(sequenceGenerator.getSequence());
        System.out.println(sequenceGenerator.getSequence());
    }

}
