package elenaromanova.datastore;

import java.time.*;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;
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

        // Calculating difference between dates and time
        Period diff = Period.between(newYear, now);
        System.out.printf("%d months, %d days %n", diff.getMonths(), diff.getDays());

        LocalTime ltd2 = LocalTime.of(14, 25);
        Duration timeDiff = Duration.between(ltd2, ldt1);
        System.out.printf("%d hours, %d minutes %n", timeDiff.toHours(), timeDiff.toMinutes());

        // Time Zones
        ZonedDateTime zn1 = ZonedDateTime.of(ldt1, ZoneId.of("+1"));
        System.out.println(zn1);

        // User time zone - GMT - 6;
        LocalDateTime dateFromUser = LocalDateTime.of(2023, 01, 25, 18, 37);
        ZonedDateTime zd = ZonedDateTime.of(dateFromUser, ZoneId.of("-6"));
        ZonedDateTime zd2 = zd.withZoneSameInstant(ZoneId.of("+0"));
        System.out.println(zd2);

        System.out.println(dateFromUser.with(TemporalAdjusters.next(DayOfWeek.FRIDAY))); // will return local day next friday
        System.out.println(dateFromUser.withMonth(3)); // creating a clone of date with another month
    }
}
