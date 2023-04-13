package elenaromanova.datastore;

import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

public class TimeExample {
    public static void main(String[] args) {
        LocalDate now = LocalDate.now();
        System.out.println(now.plus(3, ChronoUnit.YEARS));

        LocalDate newYear = LocalDate.of(2023, 1, 1);
        System.out.println(newYear.getDayOfWeek());

        newYear
            .datesUntil(LocalDate.now(), Period.ofWeeks(2))
            .forEach(System.out::println);
        List<LocalDate> leapYears = LocalDate.now()
                .datesUntil(LocalDate.now().plusYears(10), Period.ofYears(1))
                .filter(LocalDate::isLeapYear)
                .toList();
        System.out.println(leapYears);

        LocalTime nowTime = LocalTime.now();
        System.out.println(nowTime);

        LocalDateTime ldt1 = LocalDateTime.of(now, nowTime);
        System.out.println(ldt1.getMonth());

        System.out.println(Instant.now()); // IPAC time (seconds from 1.1.1970)
    }
}
