package io.pillopl.cinema.show;

import io.pillopl.cinema.availability.HallAvailability;


import java.util.Map;

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

    public boolean isSeatToTheLeftAvailable(Seat seat) {
        return isSeatAvailable(seat.onTheLeft());
    }

    public boolean isSeatToTheRightAvailable(Seat seat) {
        return isSeatAvailable(seat.onTheRight());
    }

    public boolean isSeatTwoToTheLeftAvailable(Seat seat) {
        return isSeatAvailable(seat.onTheLeft().onTheLeft());
    }

    public boolean isSeatTwoToTheRightAvailable(Seat seat) {
        return isSeatAvailable(seat.onTheRight().onTheRight());
    }

    public Seat toTheLeft(Seat fromSeat) {
        return fromSeat.onTheLeft();
    }

    public Seat toTheRight(Seat fromSeat) {
        return fromSeat.onTheRight();
    }

    public boolean areThoseSearsAvailable(SeatsCollection wantedSeats) {
        return wantedSeats
                .all()
                .stream()
                .allMatch(this::isSeatAvailable);
    }
}

