package person;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CSVPersonUtils {
    private CSVPersonUtils() {}

    private static String escapeQuotes(String data) {
        if (!data.contains(",")) {
            return data;
        }

        return "\"" + data + "\"";
    }

    private static String[] convertToStringArray(Person person) {
        return new String[] {
                person.name(),
                String.valueOf(person.age()),
                String.valueOf(person.sex())
        };
    }

    private static String convertToCSV(String[] data) {
        return Stream.of(data)
                .map(CSVPersonUtils::escapeQuotes)
                .collect(Collectors.joining(", "));
    }

    public static List<String> convertToCSV(List<Person> people) {
        return people.stream()
                .map(CSVPersonUtils::convertToStringArray)
                .map(CSVPersonUtils::convertToCSV)
                .toList();
    }
}
