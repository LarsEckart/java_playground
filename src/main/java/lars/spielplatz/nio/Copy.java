package lars.spielplatz.nio;

import java.io.IOException;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

/**
 * http://www.javaworld.com/article/2882984/core-java/nio2-cookbook-part-1.html
 */
public class Copy {

    public static void main(String[] args) throws IOException {

        if (args.length != 2) {
            System.err.println("usage: java Copy source target");
            return;
        }

        Path source = Paths.get(args[0]);
        Path target = Paths.get(args[1]);

        try {
            Files.copy(source, target, StandardCopyOption.REPLACE_EXISTING);
        } catch (FileAlreadyExistsException faee) {
            System.err.printf("%s: file already exists%n", target);
        } catch (DirectoryNotEmptyException dnee) {
            System.err.printf("%s: not empty%n", target);
        } catch (IOException ioe) {
            System.err.printf("I/O error: %s%n", ioe.getMessage());
        }
    }
}
