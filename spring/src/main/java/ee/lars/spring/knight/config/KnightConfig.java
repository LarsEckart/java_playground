package ee.lars.spring.knight.config;

import ee.lars.spring.knight.BraveKnight;
import ee.lars.spring.knight.Knight;
import ee.lars.spring.knight.Quest;
import ee.lars.spring.knight.SlayDragonQuest;
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
