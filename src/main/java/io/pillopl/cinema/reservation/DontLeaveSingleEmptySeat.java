package io.pillopl.cinema.reservation;

import io.pillopl.cinema.show.Seat;
import io.pillopl.cinema.show.Show;

import java.util.Optional;
import java.util.Set;

import static io.pillopl.cinema.reservation.ReservationResult.reservationCanBePossibleWith;
import static java.util.Set.of;
import static java.util.stream.Collectors.*;

class DontLeaveSingleEmptySeat implements ShowReservationRule {

    @Override
    public ReservationResult checkReservation(Show show, Seat requestedSeat) {
        Set<Seat> additionalNeededSeats =
                of(show.singleSeatFreeToTheLeftFrom(requestedSeat), show.singleSeatFreeToTheRightFrom(requestedSeat))
                .stream()
                .flatMap(Optional::stream)
                .collect(toSet());
        return reservationCanBePossibleWith(additionalNeededSeats);
    }

}

