package finance.source;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

/** Читання рядків за URL (наприклад, https://informer.com.ua/dut/java/pr2.csv). */
@Getter
@AllArgsConstructor
public class UrlDataSource implements DataSource {
    private final String url;

    @Override
    public List<String> readLines() throws IOException {
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(URI.create(url).toURL().openStream(), StandardCharsets.UTF_8))) {
            return br.lines().collect(Collectors.toList());
        }
    }
}
