package edu.khnu.rbecs.programming;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

public class LocalDateTimeDemo {
    static void main() {
        // legacy
        Date date1 = new Date();
        System.out.println(date1);
        System.out.println(date1.getTime());
        Instant instant = date1.toInstant();
        System.out.println(instant);

        // new recommended
        LocalDate date2 = LocalDate.now();
        System.out.println(date2);
        LocalDateTime time1 = LocalDateTime.now();
        System.out.println(time1);
        Locale ukrainian = Locale.of("uk", "UA");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(
                        "yyyy-MM-dd HH:mm:ss.n eeee EEEE LLLL")
                .withLocale(ukrainian);
        System.out.println(formatter.format(time1));
        System.out.println(time1.format(formatter));
    }
}
