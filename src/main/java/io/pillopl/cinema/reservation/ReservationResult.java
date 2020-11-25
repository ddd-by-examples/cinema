package io.pillopl.cinema.reservation;

import java.util.HashSet;
import java.util.Set;

class ReservationResult {

    private final boolean possible;

    private final Set<SeatRequest> requiredToComplete;

    static ReservationResult reservationThatCanBePossibleWith(Set<SeatRequest> additionalSeats) {
        return new ReservationResult(true, additionalSeats);
    }

    static ReservationResult reservationThatIsSuccesful() {
        return new ReservationResult(true, new HashSet<>());
    }

    static ReservationResult reservationThatFailed() {
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
