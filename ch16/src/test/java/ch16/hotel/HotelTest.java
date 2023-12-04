package ch16.hotel;

import ch16.hotel.Hotel.HotelsMustHaveRooms;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class HotelTest {
    @Test void hotels_must_have_rooms_else_they_are_not_hotels() {
        var id = UUID.randomUUID();
        var availability = new HotelAvailability(null, null, null);

        var rooms = new HotelRoomSummary(0, 0, 0);

        assertThatThrownBy(() ->
                new Hotel(id, availability, rooms)
        ).isInstanceOf(HotelsMustHaveRooms.class);
    }
}