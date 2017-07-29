package ee.lars.spring.soundsystem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SpringCDPlayer implements MediaPlayer {

    private CompactDisc cd;

    @Autowired
    @Beatles
    public SpringCDPlayer(CompactDisc cd) {
        this.cd = cd;
    }

    @Override
    public void play() {
        this.cd.play();
    }
}
