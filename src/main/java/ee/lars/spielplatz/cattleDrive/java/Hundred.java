package ee.lars.spielplatz.cattleDrive.java;

public class Hundred {

    public static void main(String[] args) {
        if (args.length > 0) {
            final String output = args[0] + " ";
            for (int i = 0; i < 100; i++) {
                System.out.print(output);
            }

            int wordLengthPlusSpace = args[0].toCharArray().length + 1;
            int numberOfWordsPerLine = 80 / wordLengthPlusSpace;

            for (int i = 0; i < 100; i++) {
                if (i % numberOfWordsPerLine == 0) {
                    System.out.println();
                    System.out.print(output);
                } else {
                    System.out.print(output);
                }
            }
        }
    }
}
