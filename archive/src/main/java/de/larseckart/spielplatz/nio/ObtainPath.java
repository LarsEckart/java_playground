package de.larseckart.spielplatz.nio;

import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ObtainPath {

    public static void main(String[] args) {

        Path path = Paths.get("a", "b");
        System.out.println(path);

        path = Paths.get(FileSystems.getDefault().getSeparator() + "a", "b", "c");
        System.out.println(path);

        path = Paths.get("a", ":", "b");
        System.out.println(path);
    }
}