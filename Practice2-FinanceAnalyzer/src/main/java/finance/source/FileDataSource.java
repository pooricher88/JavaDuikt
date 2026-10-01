package finance.source;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/** Читання рядків з локального файлу. */
@Getter
@AllArgsConstructor
public class FileDataSource implements DataSource {
    private final String path;

    @Override
    public List<String> readLines() throws IOException {
        return Files.readAllLines(Path.of(path), StandardCharsets.UTF_8);
    }
}
