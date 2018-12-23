package lars.hexagon;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

class PoetryLibraryFileAdapter implements IObtainPoetry {

    private final String path;

    public PoetryLibraryFileAdapter(String path) {
        this.path = path;
    }

    @Override
    public String getMeAPoem() {
        try {
            return String.join("\n", Files.readAllLines(Paths.get(path)));
        } catch (IOException e) {
            throw new RuntimeException("Expected to read file from " + path + " but failed.");
        }
    }
}
