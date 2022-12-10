package files;

import person.CSVPersonUtils;
import person.Person;
import person.Sex;

import java.io.File;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

public class Main {
    private static final String DIRNAME = "output";
    private static final String FILENAME = "result.csv";

    public static void main(String[] args) {
        if (!FileUtils.makeDirIfNotExists(Path.of(DIRNAME))) {
            System.err.println("Can't create directory");
            return;
        }
        Path path = Path.of(DIRNAME, FILENAME);
        Optional<File> file = FileUtils.createFileIfNotExists(path);

        if (file.isEmpty()) {
            System.err.println("Can't create a file");
            return;
        }

        System.out.println("file.get() = " + file.get());

        List<Person> people = List.of(
                new Person("Вася", 16, Sex.MAN),
                new Person("Петя", 23, Sex.MAN),
                new Person("Олена", 42, Sex.WOMAN),
                new Person( "Іван Іванович", 69, Sex.MAN)
        );

        List<String> csv = CSVPersonUtils.convertToCSV(people);

        if (!FileUtils.writeToFile(file.get(), csv)) {
            System.err.println("Can't write to file");
            return;
        }

        String src = "https://pbs.twimg.com/media/E4bu1cRXoAMRnXz.jpg";
        Optional<File> image = FileUtils.downloadImage(src, DIRNAME, "meme");
        if (image.isEmpty()) {
            System.err.println("Can't download file");
        }
    }
}