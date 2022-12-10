package files;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

public class FileUtils {
    private FileUtils() {}

    public static boolean exists(Path path) {
        return Files.exists(path);
    }

    public static boolean makeDirIfNotExists(Path path) {
        if (exists(path)) {
            return true;
        }
        try {
            Files.createDirectory(path);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public static Optional<File> createFileIfNotExists(Path path) {
        if (exists(path)) {
            return Optional.of(path.toFile());
        }

        try {
            return Optional.of(Files.createFile(path).toFile());
        } catch (IOException e) {
            return Optional.empty();
        }
    }

    public static boolean writeToFile(File file, List<String> rows) {
        try {
            Files.write(file.toPath(), rows);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public static Optional<File> downloadImage(String source, String dir, String name) {
        URL url;
        try {
            url = new URL(source);
        } catch (MalformedURLException e) {
            return Optional.empty();
        }
        String extension = "";
        if (url.getPath().contains(".")) {
            extension = url.getPath().substring(url.getPath().lastIndexOf(".") + 1);
        }

        String filename = name + "." + extension;
        File file = Path.of(dir, filename).toFile();

        try (ReadableByteChannel readableByteChannel = Channels.newChannel(url.openStream());
             FileOutputStream fileOutputStream = new FileOutputStream(file)) {
            fileOutputStream.getChannel().transferFrom(readableByteChannel, 0, Long.MAX_VALUE);
            return Optional.of(file);

        } catch (IOException e) {
            return Optional.empty();
        }
    }
}
