package ee.lars.spielplatz.java8;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

public class FileAsCharSequence implements CharSequence {

    private final int length;
    private final StringBuilder buffer = new StringBuilder();
    private final InputStream input;

    public FileAsCharSequence(File file) throws FileNotFoundException {
        if (file.length() > (long) Integer.MAX_VALUE) {
            throw new IllegalArgumentException("File is too long to handle as character sequence");
        }
        this.length = (int) file.length();
        this.input = new FileInputStream(file);
    }

    @Override
    public int length() {
        return length;
    }

    @Override
    public char charAt(int index) {
        ensureFilled(index + 1);
        return buffer.charAt(index);
    }

    @Override
    public CharSequence subSequence(int start, int end) {
        ensureFilled(end + 1);
        return buffer.subSequence(start, end);
    }

    private void ensureFilled(int index) {
        if (buffer.length() < index) {
            buffer.ensureCapacity(index);
            final byte[] bytes = new byte[index - buffer.length()];
            try {
                int length = input.read(bytes);
                if (length < bytes.length) {
                    throw new IllegalArgumentException("File ended unexpected");
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            try {
                buffer.append(new String(bytes, "utf-8"));
            } catch (UnsupportedEncodingException ignored) {
            }
        }
    }
}
