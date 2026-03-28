package edu.khnu.rbecs.programming;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Writer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class WriteFileDemo {
    static void main() throws IOException {
        // OutputStream (binary) <--Charset-- Writer (text)
        // InputStream (binary) --Charset--> Reader (text)
        String filename = "hello.txt";
        try (
                var fos = new FileOutputStream(filename, true);
                var fis = new FileOutputStream(filename);
        ) {
            byte[] bytes = {48, 49, 50, 51, (byte) 135};
            fos.write(bytes);
        }
    }
}
