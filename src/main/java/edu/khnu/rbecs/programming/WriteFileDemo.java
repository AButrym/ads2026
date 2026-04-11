package edu.khnu.rbecs.programming;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.stream.Stream;

public class WriteFileDemo {
    static void main() throws IOException {
        // OutputStream (binary) <--Charset-- Writer (text)
        // InputStream (binary) --Charset--> Reader (text)
        String filename1 = "hello.txt";
        String filename2 = "hello2.txt";
        try (Stream<String> lines = Files.lines(
                Path.of(filename1))) {
            Files.write(Path.of(filename2),
                    lines
                            .peek(line -> System.out.println(">> " + line))
                            .takeWhile(line -> !line.equals("line3"))
                            .map(String::toUpperCase)
                            .toList(),
                    StandardOpenOption.APPEND
            );
        }
    }
}
