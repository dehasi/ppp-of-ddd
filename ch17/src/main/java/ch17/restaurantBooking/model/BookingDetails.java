package ch17.restaurantBooking.model;

import java.time.LocalDate;

// value object
public record BookingDetails(LocalDate when, int numberOfDiners) {}
