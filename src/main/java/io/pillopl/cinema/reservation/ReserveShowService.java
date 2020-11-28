package io.pillopl.cinema.reservation;


import io.pillopl.cinema.show.SeatsCollection;
import io.pillopl.cinema.show.Show;
import io.pillopl.cinema.show.Shows;
import org.jooq.exception.DataChangedException;

import static io.pillopl.cinema.reservation.ReservationResult.failedReservation;
import static io.pillopl.cinema.reservation.ReservationResult.successfulReservation;

class ReserveShowService {

    private final Shows shows;
    private final ReserveShowDomainService reserveShowDomainService;

    ReserveShowService(Shows shows, ReserveShowDomainService reserveShowDomainService) {
        this.shows = shows;
        this.reserveShowDomainService = reserveShowDomainService;
    }

    ReservationResult reserve(int showId, SeatsCollection seats) {
        return reserve(showId, seats, new NoAdditionalRule());
    }

    ReservationResult reserve(int showId, SeatsCollection seats, ShowReservationRule additionalRule) {
        Show show = shows.load(showId);
        ReservationResult result = reserveShowDomainService.canReserve(show, seats, additionalRule);
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
