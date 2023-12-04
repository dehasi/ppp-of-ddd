package ch16.hotel;

import java.util.UUID;

class Hotel {

    public UUID id;
    public HotelAvailability availability;
    public HotelRoomSummary rooms;

    Hotel(UUID id, HotelAvailability initialAvailability, HotelRoomSummary rooms) {
        enforceInvariants(rooms);
        this.id = id;
        this.availability = initialAvailability;
        this.rooms = rooms;
    }

    private static void enforceInvariants(HotelRoomSummary rooms) {
        if (rooms.singleRooms() < 1 &&
                rooms.doubleRooms() < 1 &&
                rooms.familyRooms() < 1)
            throw new HotelsMustHaveRooms();
    }
    // Other responsiblities: facilities, events, staff details

    static class HotelsMustHaveRooms extends RuntimeException {}
}
