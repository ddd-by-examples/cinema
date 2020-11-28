package io.pillopl.cinema.show;


import java.util.Arrays;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import static java.lang.String.valueOf;
import static java.util.stream.Collectors.toList;

public class SeatsCollection {

    public static SeatsCollection seats(String... seats) {
        Set<Seat> wantedSeats = Arrays.stream(seats)
                .map(request -> new Seat(request.charAt(0), Integer.parseInt(request.substring(1))))
                .collect(Collectors.toSet());
        return new SeatsCollection(wantedSeats);
    }

    private final Set<Seat> seats;

    SeatsCollection(Set<Seat> seatRequests) {
        this.seats = seatRequests;
    }

    Collection<String> seatsToString() {
        return seats
                .stream()
                .map(seat -> valueOf(seat.row) + seat.number)
                .collect(toList());
    }

    public Set<Seat> all() {
        return seats;
    }

    int count() {
        return seats.size();
    }

    public boolean contains(Seat seat) {
        return seats.contains(seat);
    }
}

