package io.pillopl.cinema.availability;


import java.util.List;
import static java.util.stream.Collectors.toList;

class Row {

    static Row of(String seats) {
        return new Row(seats
                .chars()
                .mapToObj(character -> Seat.of((char) character))
                .collect(toList()));
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

}


