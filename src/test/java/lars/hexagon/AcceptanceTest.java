package lars.hexagon;

import org.junit.jupiter.api.Test;

import static lars.MockitoExtension.stub;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class AcceptanceTest {

    // IRequestVerses = left side port
    // PoetryReader = hexagon
    @Test
    void should_give_verses_when_asked_for_poetry() {
        IRequestVerses poetryReader = new PoetryReader();

        String verses = poetryReader.giveMeSomePoetry();

        assertThat(verses).isEqualTo("Sie sagt, ich sei so oldschool, oldschool\n"
                + "Ich weiß nicht mal, was das heißt\n"
                + "Die Bärte werden länger, die Hosen enger\n"
                + "Doch ich trag' immer noch 501");
    }

    @Test
    void should_give_verses_when_asked_for_poetry_with_support_of_poetry_library() {
        IObtainPoetry poetryLibrary = stub(IObtainPoetry.class, p -> p.getMeAPoem(), "Ich bin dein 1999, deine Flucht zurück\n"
                + "Ich hol’ mein Batmobil und Babe, dann kommst du mit\n"
                + "Ich halte dir die Türen auf wie Gentleman\n"
                + "Ich kämpf' für dich wie Jackie Chan");
        IRequestVerses poetryReader = new PoetryReader(poetryLibrary);

        String verses = poetryReader.giveMeSomePoetry();

        assertThat(verses).isEqualTo("Ich bin dein 1999, deine Flucht zurück\n"
                + "Ich hol’ mein Batmobil und Babe, dann kommst du mit\n"
                + "Ich halte dir die Türen auf wie Gentleman\n"
                + "Ich kämpf' für dich wie Jackie Chan");
    }

    @Test
    void should_provide_verses_when_asked_for_poetry_with_support_of_console_adapter() {
        // init the right side adapter (i want to go outside hexagon)
        IObtainPoetry poetryLibrary = stub(IObtainPoetry.class, p -> p.getMeAPoem(), "Ich bin dein 1999, deine Flucht zurück\n"
                + "Ich hol’ mein Batmobil und Babe, dann kommst du mit\n"
                + "Ich halte dir die Türen auf wie Gentleman\n"
                + "Ich kämpf' für dich wie Jackie Chan");

        // init the hexagon
        IRequestVerses poetryReader = new PoetryReader(poetryLibrary);

        IWriteLines publicationStrategy = mock(IWriteLines.class);
        ConsoleAdapter consoleAdapter = new ConsoleAdapter(poetryReader, publicationStrategy);

        consoleAdapter.ask();

        // check that console.writeLine has been called
        verify(publicationStrategy).writeLines("Ich bin dein 1999, deine Flucht zurück\n"
                + "Ich hol’ mein Batmobil und Babe, dann kommst du mit\n"
                + "Ich halte dir die Türen auf wie Gentleman\n"
                + "Ich kämpf' für dich wie Jackie Chan");
    }

    @Test
    void should_give_verses_when_asked_for_poetry_with_the_support_of_a_file_adapter() {
        var fileAdapter = new PoetryLibraryFileAdapter("src/main/resources/lyrics.txt");

        var poetryReader = new PoetryReader(fileAdapter);

        String verses = poetryReader.giveMeSomePoetry();

        assertThat(verses).isEqualTo("Sie sagt, ich sei so oldschool, oldschool\n"
                + "Ich weiß nicht mal, was das heißt\n"
                + "Die Bärte werden länger, die Hosen enger\n"
                + "Doch ich trag' immer noch 501\n"
                + "\n"
                + "Blitzkrieg Bop, nie gehört\n"
                + "Sie kennt die Ramones nur von ihrem Shirt\n"
                + "Ich trag’ Snapback, zocke NES\n"
                + "Sie denkt, ein Gameboy sei eine App\n"
                + "Sie sagt, ich sei so oldschool, oldschool\n"
                + "Weißt du, was peinlich ist?\n"
                + "Digga, check ma' Insta, Insta\n"
                + "Ihr scheiß Köter hat mehr Likes als ich\n"
                + "\n"
                + "Sie sagt, ich sei so oldschool, oldschool\n"
                + "Ich weiß nicht mal, was das heißt\n"
                + "Die Bärte werden länger, die Hosen enger\n"
                + "Doch ich trag' immer noch 501\n"
                + "Sie sagt, ich sei so oldschool, oldschool\n"
                + "Ich weiß nicht mal, was das heißt\n"
                + "Die Bärte werden länger, die Hosen enger\n"
                + "Doch ich trag’ immer noch 501\n"
                + "\n"
                + "Was heißt hier „Oldschool“? Das klingt mir viel zu modern\n"
                + "Jurassic Dark, denn ich guck' immer noch fern\n"
                + "Alle pudern sich die Nase, keiner ist geschminkt\n"
                + "Wir schweben wie ein Hoverboard, wir beide sind verlinkt\n"
                + "Ich häng' ab mit der Lucky Strike Light auf der Lippe\n"
                + "Aufhör'n? Wozu? Ich seh' voll nice aus mit Kippe\n"
                + "Seit '99 immer noch die gleiche Clique\n"
                + "Das feiert sie, sie weiß, wie ich ticke\n"
                + "Und hört sie oldschool Mucke bei Spotify\n"
                + "Doch ich wette, sie weiß nicht\n"
                + "Was man macht mit Kassette und Bleistift\n"
                + "Nachrichten an sie schreib' ich auf Zettel in Schreibschrift\n"
                + "Und sie feiert es, denn\n"
                + "\n"
                + "Sie sagt, ich sei so oldschool, oldschool\n"
                + "Ich weiß nicht mal, was das heißt\n"
                + "Die Bärte werden länger, die Hosen enger\n"
                + "Doch ich trag' immer noch 501\n"
                + "Sie sagt, ich sei so oldschool, oldschool\n"
                + "Ich weiß nicht mal, was das heißt\n"
                + "Die Bärte werden länger, die Hosen enger\n"
                + "Doch ich trag’ immer noch 501\n"
                + "\n"
                + "Jeder da draußen rennt und sucht sich ständig was Neues\n"
                + "Doch glaubst du mir, wenn ich dir sag’, ich bin dir noch treu\n"
                + "Brauchst du wen zum Reden, nehm' ich mir für dich frei\n"
                + "Denn mit dir steht die Zeit still wie bei Max und Joy\n"
                + "Ich bin dein 1999, deine Flucht zurück\n"
                + "Ich hol’ mein Batmobil und Babe, dann kommst du mit\n"
                + "Ich halte dir die Türen auf wie Gentleman\n"
                + "Ich kämpf' für dich wie Jackie Chan\n"
                + "Ich steh' zu dir wie Metalfans, so wie Dr. Dre zu Eminem\n"
                + "Und während sich alles verändert\n"
                + "Sitz' ich immer noch im Clio hinterm Lenkrad\n"
                + "Tu es viel lieber mit Liebe als zu gangster\n"
                + "Wenn du genauso denkst, dann nimm die Hands up\n"
                + "\n"
                + "Sie sagt, ich sei so oldschool, oldschool\n"
                + "Ich weiß nicht mal, was das heißt\n"
                + "Die Bärte werden länger, die Hosen enger\n"
                + "Doch ich trag’ immer noch 501\n"
                + "Sie sagt, ich sei so oldschool, oldschool\n"
                + "Ich weiß nicht mal, was das heißt\n"
                + "Die Bärte werden länger, die Hosen enger\n"
                + "Doch ich trag' immer noch 501\uFEFF");
    }
}
