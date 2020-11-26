package io.pillopl.cinema.reservation;

import io.pillopl.cinema.availability.Hall;

import java.util.Set;

import static io.pillopl.cinema.reservation.ReservationResult.reservationCanBePossibleWith;
import static io.pillopl.cinema.reservation.ReservationResult.successfulReservation;

class ArtificialPolicyWithRequiredSpecificSeat implements ReservationPolicy {

    final SeatRequest requiredSeat;

    ArtificialPolicyWithRequiredSpecificSeat(SeatRequest requiredSeat) {
        this.requiredSeat = requiredSeat;
    }

    @Override
    public ReservationResult check(Hall hall, SeatRequest wantedSeats) {
        if (wantedSeats.equals(requiredSeat)) {
            return successfulReservation();
        }
        return reservationCanBePossibleWith(Set.of(requiredSeat));
    }
}
