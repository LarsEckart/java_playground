package ee.lars.di.spring.knight.config;

import ee.lars.di.spring.knight.BraveKnight;
import ee.lars.di.spring.knight.Knight;
import ee.lars.di.spring.knight.Quest;
import ee.lars.di.spring.knight.SlayDragonQuest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KnightConfig {

    @Bean
    public Knight knight() {
        return new BraveKnight(quest());
    }

    @Bean
    public Quest quest() {
        return new SlayDragonQuest(System.out);
    }

}
