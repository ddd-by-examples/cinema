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
}

