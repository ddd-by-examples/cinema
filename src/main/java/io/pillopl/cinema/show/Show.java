package io.pillopl.cinema.show;

import io.pillopl.cinema.availability.SeatsAvailability;


import java.util.Map;
import java.util.Optional;

public class Show {

    private final SeatsAvailability seatsAvailability;

    public Show(Map<Character, String> seatsAvailability) {
        this.seatsAvailability = new SeatsAvailability(seatsAvailability);
    }

    Show(SeatsAvailability seatsAvailability) {
        this.seatsAvailability = seatsAvailability;
    }

    public Map<Character, String> print() {
        return seatsAvailability.print();
    }

    public boolean areThoseSeatsAvailable(SeatsCollection wantedSeats) {
        return wantedSeats
                .all()
                .stream()
                .allMatch(this::isSeatAvailable);
    }

    public Optional<Seat> singleSeatFreeToTheLeftFrom(Seat seat) {
        return seatsAvailability
                .singleAvailabilityToTheRightFrom(seat.row, seat.number)
                .map(number -> new Seat(seat.row, number));
    }

    public Optional<Seat> singleSeatFreeToTheRightFrom(Seat seat) {
        return seatsAvailability
                .singleAvailabilityToTheLeftFrom(seat.row, seat.number)
                .map(number -> new Seat(seat.row, number));
    }

    private boolean isSeatAvailable(Seat seat) {
        return seatsAvailability.isSeatAvailable(seat.row, seat.number);
    }


}

