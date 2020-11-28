package io.pillopl.cinema.reservation;

import io.pillopl.cinema.show.Seat;
import io.pillopl.cinema.show.Show;

import static io.pillopl.cinema.reservation.ReservationResult.successfulReservation;

class NoAdditionalRule implements ShowReservationRule {

    @Override
    public ReservationResult checkReservation(Show show, Seat seat) {
        return successfulReservation();
    }
}
