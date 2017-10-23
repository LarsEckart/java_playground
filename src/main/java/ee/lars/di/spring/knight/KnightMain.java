package ee.lars.di.spring.knight;

import ee.lars.di.spring.knight.config.KnightConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class KnightMain {

    public static void main(String[] args) throws Exception {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(KnightConfig.class);

        Knight knight = context.getBean(Knight.class);
        knight.embarkOnQuest();

        context.close();
    }
}
