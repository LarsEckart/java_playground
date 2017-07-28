package ee.lars.spring.soundsystem;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CDPlayerConfig {

    @Bean
    public CompactDisc sgtPeppers() {
        return new SgtPeppers();
    }

    /*
    @Bean
    public MediaPlayer cdPlayer() {
        return new SpringCDPlayer(sgtPeppers());
    }
    */

    @Bean
    public MediaPlayer anotherCdPlayer(CompactDisc cd) {
        return new SpringCDPlayer(cd);
    }

}
