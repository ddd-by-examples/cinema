package io.pillopl.cinema.reservation;

import io.pillopl.cinema.show.Seat;
import io.pillopl.cinema.show.SeatsCollection;
import io.pillopl.cinema.show.Show;

import java.util.*;
import java.util.stream.Collectors;


import static io.pillopl.cinema.reservation.ReservationResult.reservationCanBePossibleWith;

interface ShowReservationRule {

    ReservationResult checkSeat(Show show, Seat seat);

    default ReservationResult checkSeats(Show availability, SeatsCollection wantedSeats) {
        Set<Seat> additionalSeats = wantedSeats
                .all()
                .stream()
                .map(seat -> checkSeat(availability, seat))
                .map(ReservationResult::getSeatsRequiredToReserve)
                .flatMap(Collection::stream)
                .filter(seatRequest -> !wantedSeats.contains(seatRequest))
                .collect(Collectors.toSet());
        return reservationCanBePossibleWith(additionalSeats);
    }

}

