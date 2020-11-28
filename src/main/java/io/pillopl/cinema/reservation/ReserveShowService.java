package io.pillopl.cinema.reservation;


import io.pillopl.cinema.show.SeatsCollection;
import io.pillopl.cinema.show.Show;
import io.pillopl.cinema.show.Shows;
import org.jooq.exception.DataChangedException;

import static io.pillopl.cinema.reservation.ReservationResult.failedReservation;
import static io.pillopl.cinema.reservation.ReservationResult.successfulReservation;

//TODO AVAILABILITY
//TODO jooq generation in multiple files
//TODO onTheLEft onTheRight - move to HallAvalability albo do Show (i wtedy polityki dzialaj na Show)
//TODO hall managmenet

class ReserveShowService {

    private final Shows shows;

    ReserveShowService(Shows shows) {
        this.shows = shows;
    }

    ReservationResult reserve(int showId, SeatsCollection seats) {
        return reserve(showId, seats, new NoAdditionalRule());
    }

    ReservationResult reserve(int showId, SeatsCollection seats, ShowReservationRule additionalRule) {
        Show show = shows.load(showId);
        ReservationResult result = new ShowReservationBuilder()
                .forShow(show)
                .seats(seats)
                .rule(additionalRule)
                .reserve();
        if (result.isSuccessful()) {
            return checkIfAllSeatsStillAvailableAndMarkAsUnavailable(showId, seats);
        }
        return result;
    }

    private ReservationResult checkIfAllSeatsStillAvailableAndMarkAsUnavailable(int showId, SeatsCollection seats) {
        try {
            boolean reserved = shows.checkIfAllSeatsStillAvailableAndMarkAsUnavailable(showId, seats);
            if (!reserved) {
                return failedReservation();
            }
        } catch (DataChangedException e) {
            return failedReservation();
        }
        return successfulReservation();
    }

}
