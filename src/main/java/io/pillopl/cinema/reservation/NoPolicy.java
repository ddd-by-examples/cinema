package io.pillopl.cinema.reservation;

import io.pillopl.cinema.availability.Hall;

import static io.pillopl.cinema.reservation.ReservationResult.successfulReservation;

class NoPolicy implements ReservationPolicy {

    @Override
    public ReservationResult check(Hall hall, SeatRequest wantedSeat) {
        return successfulReservation();
    }
}
