package ee.lars.spring.soundsystem;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@Deutsch
public class NeLeiche implements CompactDisc {

    private String title = "Ne Leiche";
    private String artist = "SDP";

    @Override
    public void play() {
        System.out.println("Playing " + title + " by " + artist);
    }
}
