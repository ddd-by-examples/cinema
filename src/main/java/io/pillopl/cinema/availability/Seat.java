package io.pillopl.cinema.availability;

class Seat {

    private final boolean available;

    Seat(boolean available) {
        this.available = available;
    }

    boolean isAvailable() {
        return available;
    }

}
