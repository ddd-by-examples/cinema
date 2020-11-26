package io.pillopl.cinema.reservation;

import io.pillopl.cinema.availability.Hall;

import java.util.*;
import java.util.stream.Collectors;


import static io.pillopl.cinema.reservation.ReservationResult.reservationCanBePossibleWith;

interface ReservationPolicy {

    ReservationResult check(Hall hall, SeatRequest wantedSeats);

    default ReservationResult check(Hall hall, Set<SeatRequest> wantedSeats) {
        Set<SeatRequest> additionalSeats = wantedSeats
                .stream()
                .map(seat -> check(hall, seat))
                .map(ReservationResult::getSeatsRequiredToReserve)
                .flatMap(Collection::stream)
                .filter(seatRequest -> !wantedSeats.contains(seatRequest))
                .collect(Collectors.toSet());

        return reservationCanBePossibleWith(additionalSeats);
    }

}

