package io.pillopl.cinema.show;

import io.pillopl.cinema.availability.HallAvailability;


import java.util.Map;
import java.util.Optional;

public class Show {

    private final HallAvailability hallAvailability;

    public Show(Map<Character, String> seatsAvailability) {
        this.hallAvailability = new HallAvailability(seatsAvailability);
    }

    Show(HallAvailability hallAvailability) {
        this.hallAvailability = hallAvailability;
    }

    public Map<Character, String> print() {
        return hallAvailability.print();
    }

    public boolean isSeatAvailable(Seat seat) {
        return hallAvailability.isSeatAvailable(seat.row, seat.number);
    }

    public Optional<Seat> singleSeatFreeToTheLeftFrom(Seat seat) {
        return hallAvailability
                .singleAvailabilityToTheRightFrom(seat.row, seat.number)
                .map(number -> new Seat(seat.row, number));
    }

    public Optional<Seat> singleSeatFreeToTheRightFrom(Seat seat) {
        return hallAvailability
                .singleAvailabilityToTheLeftFrom(seat.row, seat.number)
                .map(number -> new Seat(seat.row, number));
    }

    public boolean areThoseSeatsAvailable(SeatsCollection wantedSeats) {
        return wantedSeats
                .all()
                .stream()
                .allMatch(this::isSeatAvailable);
    }
}

