package io.pillopl.cinema.show;

import io.pillopl.cinema.availability.Hall;


import java.util.Map;
import java.util.Optional;

public class Show {

    private final Hall hall;

    public Show(Map<Character, String> seatsAvailability) {
        this.hall = new Hall(seatsAvailability);
    }

    Show(Hall hall) {
        this.hall = hall;
    }

    public Map<Character, String> print() {
        return hall.print();
    }

    public boolean areThoseSeatsAvailable(SeatsCollection wantedSeats) {
        return wantedSeats
                .all()
                .stream()
                .allMatch(this::isSeatAvailable);
    }

    public Optional<Seat> singleSeatFreeToTheLeftFrom(Seat seat) {
        return hall
                .singleAvailabilityToTheRightFrom(seat.row, seat.number)
                .map(number -> new Seat(seat.row, number));
    }

    public Optional<Seat> singleSeatFreeToTheRightFrom(Seat seat) {
        return hall
                .singleAvailabilityToTheLeftFrom(seat.row, seat.number)
                .map(number -> new Seat(seat.row, number));
    }

    private boolean isSeatAvailable(Seat seat) {
        return hall.isSeatAvailable(seat.row, seat.number);
    }


}

