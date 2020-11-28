package io.pillopl.cinema.reservation;

import io.pillopl.cinema.show.SeatsCollection;
import io.pillopl.cinema.show.Show;

import static io.pillopl.cinema.reservation.ReservationResult.failedReservation;

class ReserveShowDomainService {

    ReservationResult reserve(Show show, SeatsCollection wantedSeats) {
        return reserve(show, wantedSeats, new NoAdditionalRule());
    }

    ReservationResult reserve(Show show, SeatsCollection wantedSeats, ShowReservationRule additionalRule) {
        if (!show.areThoseSeatsAvailable(wantedSeats)) {
            return failedReservation();
        }
        return additionalRule.checkSeats(show, wantedSeats);
    }


}


