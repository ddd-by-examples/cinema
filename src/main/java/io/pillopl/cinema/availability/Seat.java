package io.pillopl.cinema.availability;

class Seat {

    static final char SEAT_NOT_AVAILABLE = 'X';
    static final char SEAT_AVAILABLE = '-';

    static Seat of(Character marker) {
        if (marker.equals(SEAT_NOT_AVAILABLE)) {
            return new Seat(false);
        }
        if (marker.equals(SEAT_AVAILABLE)) {
            return new Seat(true);
        }
        throw new IllegalStateException("Cannot understand availability from sign: " + marker);
    }

    private final boolean available;

    private Seat(boolean available) {
        this.available = available;
    }

    boolean isAvailable() {
        return available;
    }
}
