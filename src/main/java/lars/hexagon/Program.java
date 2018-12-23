package lars.hexagon;

public class Program {

    public static void main(String[] args) {

        IObtainPoetry file = new PoetryLibraryFileAdapter("src/main/resources/lyrics.txt");
        PoetryReader poetryReader = new PoetryReader(file);
        ConsoleAdapter consoleAdapter = new ConsoleAdapter(poetryReader);

        consoleAdapter.ask();
    }
}
