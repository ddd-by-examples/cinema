package io.pillopl.cinema.reservation;

import io.pillopl.cinema.show.SeatsCollection;
import io.pillopl.cinema.show.Show;

import static io.pillopl.cinema.reservation.ReservationResult.failedReservation;

class ShowReservationBuilder {

    private SeatsCollection wantedSeats;
    private Show show;
    private ShowReservationRule policy = new NoAdditionalRule();

    ShowReservationBuilder forShow(Show show) {
        this.show = show;
        return this;
    }

    ShowReservationBuilder seats(SeatsCollection wantedSeats) {
        this.wantedSeats = wantedSeats;
        return this;
    }

    ShowReservationBuilder rule(ShowReservationRule policy) {
        this.policy = policy;
        return this;
    }

    ReservationResult reserve() {
        if (!areAllSeatsAvailable()) {
            return failedReservation();
        }
        return policy.checkReservation(show, wantedSeats);
    }

    private boolean areAllSeatsAvailable() {
        return wantedSeats
                .all()
                .stream()
                .allMatch(seat -> show.isSeatAvailable(seat));
    }

}


