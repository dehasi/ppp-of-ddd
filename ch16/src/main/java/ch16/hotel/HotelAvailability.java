package ch16.hotel;

import java.time.LocalDate;

import static zeliba.the.TheComparable.the;

class HotelAvailability {

    // These are private so encapsulated. Only Hotel.Availability is accessible from outside the Entity
    private final RoomAvailability singleRooms;
    private final RoomAvailability doubleRooms;
    private final RoomAvailability familyRooms;

    public HotelAvailability(RoomAvailability singleRooms, RoomAvailability doubleRooms, RoomAvailability FamilyRooms) {
        this.singleRooms = singleRooms;
        this.doubleRooms = doubleRooms;
        this.familyRooms = FamilyRooms;
    }

    public boolean HasSingleRoomAvailability(LocalDate start, LocalDate end) {
        return GetSingleRoomAvailability(start, end) != null;
    }

    public boolean HasDoubleRoomAvailability(LocalDate start, LocalDate end) {
        return GetDoubleRoomAvailability(start, end) != null;
    }

    public boolean HasFamilyRoomAvailability(LocalDate start, LocalDate end) {
        return GetFamilyRoomAvailability(start, end) != null;
    }

    public Money GetSingleRoomPriceFor(LocalDate start, LocalDate end) {
        return GetSingleRoomAvailability(start, end).pricePerNight;
    }

    public Money GetDoubleRoomPriceFor(LocalDate start, LocalDate end) {
        return GetDoubleRoomAvailability(start, end).pricePerNight;
    }

    public Money GetFamilyRoomPriceFor(LocalDate start, LocalDate end) {
        return GetFamilyRoomAvailability(start, end).pricePerNight;
    }

    private AvailableBookingSlot GetSingleRoomAvailability(LocalDate start, LocalDate end) {
        return singleRooms.dates.stream()
                .filter(dr ->
                        the(dr.start).isLessOrEqualsThan(start) && the(dr.end).isGreaterOrEqualsThan(end))
                .findFirst().get();
    }

    private AvailableBookingSlot GetDoubleRoomAvailability(LocalDate start, LocalDate end) {
        return doubleRooms.dates.stream()
                .filter(dr ->
                        the(dr.start).isLessOrEqualsThan(start) && the(dr.end).isGreaterOrEqualsThan(end))
                .findFirst().get();
    }

    private AvailableBookingSlot GetFamilyRoomAvailability(LocalDate start, LocalDate end) {
        return familyRooms.dates.stream()
                .filter(dr ->
                        the(dr.start).isLessOrEqualsThan(start) && the(dr.end).isGreaterOrEqualsThan(end))
                .findFirst().get();
    }
}
