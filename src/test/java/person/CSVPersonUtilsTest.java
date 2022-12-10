package person;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class CSVPersonUtilsTest {

    @Test
    void shouldConvertToCSV() {
        // given
        List<Person> people = List.of(
                new Person("Вася", 16, Sex.MAN),
                new Person("Петя", 23, Sex.MAN),
                new Person("Олена", 42, Sex.WOMAN),
                new Person( "Іван Іванович", 69, Sex.MAN)
        );
        List<String> correctResult = List.of(
                "Вася, 16, MAN",
                "Петя, 23, MAN",
                "Олена, 42, WOMAN",
                "Іван Іванович, 69, MAN"
        );

        // when
        List<String> converted = CSVPersonUtils.convertToCSV(people);

        // then
        assertThat(converted).isEqualTo(correctResult);
    }
}