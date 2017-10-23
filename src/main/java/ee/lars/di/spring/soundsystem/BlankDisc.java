package ee.lars.di.spring.soundsystem;

public class BlankDisc implements CompactDisc {

    private final String title;
    private final String artist;

    public BlankDisc(String title, String artist) {
        this.title = title;
        this.artist = artist;
    }

    @Override
    public void play() {
        System.out.println("Playing " + this.title + " by " + this.artist);
    }
}
