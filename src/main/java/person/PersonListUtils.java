package person;

import java.util.IntSummaryStatistics;
import java.util.List;

public class PersonListUtils {
    private PersonListUtils() {}

    public static List<Person> filterBySexAndAgeDiapason(List<Person> people, Sex sex, int ageFrom, int ageTo) {
        return people.stream()
                .filter(person -> person.sex() == sex &&
                        person.age() >= ageFrom &&
                        person.age() < ageTo
                )
                .toList();
    }

    public static double findAverageAge(List<Person> people, Sex sex) {
        IntSummaryStatistics stats = people.stream()
                .filter(person -> person.sex() == sex)
                .mapToInt(Person::age)
                .summaryStatistics();

        return stats.getAverage();
    }

    public static long countAbleBodiedPeople(List<Person> people) {
        return people.stream()
                .filter(person -> person.age() >= 18)
                .filter(person -> switch (person.sex()) {
                    case MAN -> person.age() < 60;
                    case WOMAN -> person.age() < 55;
                    default -> false;
                }
                ).count();
    }
}
