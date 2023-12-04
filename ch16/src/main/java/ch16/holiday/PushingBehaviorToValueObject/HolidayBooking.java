package ch16.holiday.PushingBehaviorToValueObject;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

class HolidayBooking {

    String id;
    int travelerId;
    Stay stay;
    LocalDate booked;

    public HolidayBooking(int travelerId, Stay stay, LocalDate booked) {
        this.travelerId = travelerId;
        this.stay = stay;
        this.booked = booked;
        this.id = GenerateId(travelerId, stay.firstNight, stay.lastNight, booked);
    }

    private static String GenerateId(int travelerId, LocalDate firstNight, LocalDate lastNight, LocalDate booked) {
        return String.format(
                "%s-%s-%s-%s",
                travelerId, toIdFormat(firstNight), toIdFormat(lastNight), toIdFormat(booked));
    }

    private static String toIdFormat(LocalDate date) {
        return DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT).format(date);
    }
}
