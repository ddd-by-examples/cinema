package io.pillopl.cinema.reservation;

import io.pillopl.cinema.show.Seat;
import io.pillopl.cinema.show.Show;

import java.util.Set;

import static io.pillopl.cinema.reservation.ReservationResult.reservationCanBePossibleWith;
import static io.pillopl.cinema.reservation.ReservationResult.successfulReservation;

class ArtificialRuleWithRequiredSpecificSeat implements ShowReservationRule {

    private final Seat requiredSeat;

    ArtificialRuleWithRequiredSpecificSeat(Seat requiredSeat) {
        this.requiredSeat = requiredSeat;
    }

    @Override
    public ReservationResult checkReservation(Show availability, Seat seat) {
        if (seat.equals(requiredSeat)) {
            return successfulReservation();
        }
        return reservationCanBePossibleWith(Set.of(requiredSeat));
    }
}
