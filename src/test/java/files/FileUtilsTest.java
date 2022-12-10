package files;

import org.assertj.core.util.Files;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class FileUtilsTest {
    static File workDir;

    @BeforeAll
    static void setUp() {
        workDir = Files.newTemporaryFolder();
        workDir.deleteOnExit();
    }

    @Test
    void shouldExist() {
        // given
        Path path = workDir.toPath();

        // when
        boolean exists = FileUtils.exists(path);

        // then
        assertThat(exists).isTrue();
    }

    @Test
    void shouldNotExist() {
        // given
        Path path = Path.of("not existing folder");

        // when
        boolean exists = FileUtils.exists(path);

        // then
        assertThat(exists).isFalse();
    }

    @Test
    void shouldMakeDirIfNotExists() {
        // given
        Path path = Path.of(workDir.getPath(), "output");
        path.toFile().deleteOnExit();

        // when
        FileUtils.makeDirIfNotExists(path);

        // then
        assertThat(path).exists().isDirectory();
    }

    @Test
    void shouldCreateFileIfNotExists() {
        // given
        Path path = Path.of(workDir.getPath(), "empty.txt");
        path.toFile().deleteOnExit();

        // when
        FileUtils.createFileIfNotExists(path);

        // then
        assertThat(path).exists().isRegularFile();
    }

    @Test
    void shouldWriteToFile() throws IOException {
        // given
        Path path = Path.of(workDir.getPath(), "notEmpty.txt");
        path.toFile().deleteOnExit();

        // when
        FileUtils.writeToFile(path.toFile(), List.of("test1", "test2"));

        // then
        assertThat(path).exists().isRegularFile().content().contains("test1", "test2");
    }

    @Test
    void shouldDownloadImage() {
        // given
        String url = "https://pbs.twimg.com/media/E4bu1cRXoAMRnXz.jpg";
        String name = "meme";

        // when
        Optional<File> file = FileUtils.downloadImage(url, workDir.getPath(), name);
        File image = file.orElseGet(() -> null);

        // then
        assertThat(file).isPresent().get().isInstanceOf(File.class);
        assertThat(image).exists().isFile().isNotEmpty().isReadable().hasFileName(name + ".jpg");
    }
}