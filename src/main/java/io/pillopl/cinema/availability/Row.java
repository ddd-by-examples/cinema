package io.pillopl.cinema.availability;


import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

class Row {

    private static final char SEAT_NOT_AVAILABLE = 'X';
    private static final char SEAT_AVAILABLE = '-';

    static Row of(String seats) {
        return new Row(seats
                .chars()
                .mapToObj(character -> seatOf((char) character))
                .collect(toList()));
    }

    static Seat seatOf(Character marker) {
        if (marker.equals(SEAT_NOT_AVAILABLE)) {
            return new Seat(false);
        }
        if (marker.equals(SEAT_AVAILABLE)) {
            return new Seat(true);
        }
        throw new IllegalStateException("Cannot understand availability from sign: " + marker);
    }

    private final List<Seat> seats;

    private Row(List<Seat> seats) {
        this.seats = seats;
    }

    boolean hasAvailabilityAt(int seatNumber) {
        if (seatDoesNotExists(seatNumber)) {
            return false;
        }
        return seats.get(seatNumber - 1).isAvailable();
    }

    private boolean seatDoesNotExists(int seatNumber) {
        return seatNumber > seats.size() || seatNumber < 1;
    }

    String print() {
        return seats.stream()
                .map(this::seatAvailabilityToMarker)
                .map(String::valueOf)
                .collect(Collectors.joining());
    }

    private Character seatAvailabilityToMarker(Seat seat) {
        if (seat.isAvailable()) {
            return SEAT_AVAILABLE;
        } else {
            return SEAT_NOT_AVAILABLE;
        }
    }
}


