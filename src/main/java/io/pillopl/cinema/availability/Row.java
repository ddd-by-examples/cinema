package io.pillopl.cinema.availability;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.stream.IntStream.range;

class Row {

    static final char SEAT_NOT_AVAILABLE = 'X';
    static final char SEAT_AVAILABLE = '-';

    static Row of(Character letter, String seats) {
        return new Row(range(1, seats.length() + 1)
                .mapToObj(number -> Seat.of(letter, number, availability(seats.charAt(number - 1))))
                .collect(Collectors.toList()));
    }

    private static boolean availability(Character availabilityMarker) {
        if (availabilityMarker.equals(SEAT_NOT_AVAILABLE)) {
            return false;
        }
        if (availabilityMarker.equals(SEAT_AVAILABLE)) {
            return true;
        }
        throw new IllegalStateException("Cannot understand availability from sign: " + availabilityMarker);
    }

    private final List<Seat> seats;

    private Row(List<Seat> seats) {
        this.seats = seats;
    }

    boolean hasAvailabilityAt(int seatNumber) {
        return seatAt(seatNumber).map(Seat::isAvailable).orElse(false);
    }

    private boolean seatDoesNotExists(int seatNumber) {
        return seatNumber > seats.size() || seatNumber < 1;
    }

    Optional<Seat> seatAt(int seatNumber) {
        if (seatDoesNotExists(seatNumber)) {
            return Optional.empty();
        } else {
            return Optional.of(seats.get(seatNumber - 1));
        }
    }
}


class Seat {

    static Seat of(Character row, int number, boolean availability) {
        if (number <= 0) {
            throw new IllegalStateException("Negative number of a seat: " + number);
        }
        if (row <= 0) {
            throw new IllegalStateException("Negative number of a row: " + number);
        }
        return new Seat(availability);
    }

    private final boolean available;

    private Seat(boolean available) {
        this.available = available;
    }

    boolean isAvailable() {
        return available;
    }
}
