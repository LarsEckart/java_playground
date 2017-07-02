package de.larseckart.spielplatz.nio;

import java.io.IOException;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Delete {

    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("usage: java Delete file-or-directory");
            return;
        }

        Path path = Paths.get(args[0]);
        try {
            Files.delete(path);
        } catch (NoSuchFileException nsfe) {
            System.err.printf("%s: no such file or directory%n", path);
        } catch (DirectoryNotEmptyException dnee) {
            System.err.printf("%s: not empty%n", path);
        } catch (IOException ioe) {
            System.err.printf("I/O error: %s%n", ioe.getMessage());
        }
    }
}
