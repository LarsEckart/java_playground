package ee.lars.spring.soundsystem;

import javax.inject.Inject;
import javax.inject.Named;

/*
* Keeping this outcommented as autowiring ambiguity isn't explained yet.
@Named
public class Jsr330CDPlayer implements MediaPlayer {

    private final CompactDisc cd;

    @Inject
    public Jsr330CDPlayer(CompactDisc cd) {
        this.cd = cd;
    }

    @Override
    public void play() {
        this.cd.play();
    }
}
*/