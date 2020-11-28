package io.pillopl.cinema.reservation;

import io.pillopl.cinema.show.Seat;

import java.util.HashSet;
import java.util.Set;

class ReservationResult {

    private final boolean possible;

    private final Set<Seat> requiredToComplete;

    static ReservationResult reservationCanBePossibleWith(Seat seat) {
        return reservationCanBePossibleWith(Set.of(seat));
    }

    static ReservationResult reservationCanBePossibleWith(Set<Seat> additionalSeats) {
        return new ReservationResult(true, additionalSeats);
    }

    static ReservationResult successfulReservation() {
        return new ReservationResult(true, new HashSet<>());
    }

    static ReservationResult failedReservation() {
        return new ReservationResult(false, new HashSet<>());
    }

    private ReservationResult(boolean possible, Set<Seat> requiredToComplete) {
        this.possible = possible;
        this.requiredToComplete = requiredToComplete;
    }

    boolean isSuccessful() {
        return possible && requiredToComplete.isEmpty();
    }

    Set<Seat> getSeatsRequiredToReserve() {
        return requiredToComplete;
    }
}
