package ch16.holiday.HolidayBooking;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

class HolidayBooking {

    String id;
    int travelerId;
    LocalDate firstNight;
    LocalDate lastNight;
    LocalDate booked;

    HolidayBooking(int travelerId, LocalDate firstNight, LocalDate lastNight, LocalDate booked) {
        // validate

        this.travelerId = travelerId;
        this.firstNight = firstNight;
        this.lastNight = lastNight;
        this.booked = booked;
        this.id = GenerateId(travelerId, firstNight, lastNight, booked);
    }

    // This could be pulled out into a factory and passed in based on contextual needs
    private static String GenerateId(int travelerId, LocalDate firstNight, LocalDate lastNight, LocalDate booked) {
        return String.format(
                "%s-%s-%s-%s",
                travelerId, toIdFormat(firstNight), toIdFormat(lastNight), toIdFormat(booked)
        );
    }

    private static String toIdFormat(LocalDate date) {
        return DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT).format(date);
    }

    // ..
}
