package ee.lars.spielplatz.cattleDrive.java;

public class Say {

    public static void main(String[] args) {
        if (args.length > 0) {
            try {
                int numberToSpell = Integer.parseInt(args[0]);
                if (numberToSpell > 99 || numberToSpell < 0) {
                    throw new IllegalArgumentException("Number of out range (0-99).");
                }

            } catch (NumberFormatException exception) {
                System.out.println("input was not a number");
            } catch (IllegalArgumentException exception) {
                System.out.println(exception.getMessage());
            }
        }
    }
}
