package io.pillopl.cinema.reservation;

import java.util.HashSet;
import java.util.Set;

class ReservationResult {

    private final boolean possible;

    private final Set<SeatRequest> requiredToComplete;

    static ReservationResult reservationCanBePossibleWith(SeatRequest seat) {
        return reservationCanBePossibleWith(Set.of(seat));
    }

    static ReservationResult reservationCanBePossibleWith(Set<SeatRequest> additionalSeats) {
        return new ReservationResult(true, additionalSeats);
    }

    static ReservationResult successfulReservation() {
        return new ReservationResult(true, new HashSet<>());
    }

    static ReservationResult failedReservation() {
        return new ReservationResult(false, new HashSet<>());
    }

    private ReservationResult(boolean possible, Set<SeatRequest> requiredToComplete) {
        this.possible = possible;
        this.requiredToComplete = requiredToComplete;
    }

    boolean isSuccessful() {
        return possible && requiredToComplete.isEmpty();
    }

    Set<SeatRequest> getSeatsRequiredToReserve() {
        return requiredToComplete;
    }
}
