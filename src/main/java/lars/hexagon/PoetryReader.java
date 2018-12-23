package lars.hexagon;

class PoetryReader implements IRequestVerses {

    private IObtainPoetry poetryLibrary;

    public PoetryReader() {
        this(new HardcodedPoetryLibrary());
    }

    public PoetryReader(IObtainPoetry poetryLibrary) {
        this.poetryLibrary = poetryLibrary;
    }

    @Override
    public String giveMeSomePoetry() {
        return poetryLibrary.getMeAPoem();
    }
}
