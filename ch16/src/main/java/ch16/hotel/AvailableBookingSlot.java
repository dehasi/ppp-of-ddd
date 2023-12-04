package ch16.hotel;

import java.time.LocalDate;

class AvailableBookingSlot {

    LocalDate start;
    LocalDate end;
    Money pricePerNight;
    int rooms;

     public AvailableBookingSlot(LocalDate start, LocalDate end)
     {
          this.start = start;
          this.end = end;
     }
}
