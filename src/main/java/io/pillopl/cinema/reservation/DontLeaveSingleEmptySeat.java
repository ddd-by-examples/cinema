package io.pillopl.cinema.reservation;

import io.pillopl.cinema.show.Seat;
import io.pillopl.cinema.show.Show;

import java.util.Set;

import static io.pillopl.cinema.reservation.ReservationResult.reservationCanBePossibleWith;
import static io.pillopl.cinema.reservation.ReservationResult.successfulReservation;
import static java.util.stream.Collectors.*;
import static java.util.stream.Stream.concat;

class DontLeaveSingleEmptySeat implements ShowReservationRule {

    private final NoSingleEmptySeatOnTheLeft noSingleEmptySeatOnTheLeft = new NoSingleEmptySeatOnTheLeft();
    private final NoSingleEmptySeatOnTheRight noSingleEmptySeatOnTheRight = new NoSingleEmptySeatOnTheRight();

    @Override
    public ReservationResult checkReservation(Show show, Seat seat) {
        Set<Seat> neededToTheLeft = noSingleEmptySeatOnTheLeft.checkReservation(show, seat).getSeatsRequiredToReserve();
        Set<Seat> neededToTheRight = noSingleEmptySeatOnTheRight.checkReservation(show, seat).getSeatsRequiredToReserve();
        Set<Seat> additionalNeededSeats = concat(neededToTheLeft.stream(), neededToTheRight.stream()).collect(toSet());
        return reservationCanBePossibleWith(additionalNeededSeats);
    }

}

class NoSingleEmptySeatOnTheLeft implements ShowReservationRule {

    @Override
    public ReservationResult checkReservation(Show show, Seat seat) {
        Seat oneToTheLeft = seat.onTheLeft();
        Seat twoToTheLeft = oneToTheLeft.onTheLeft();
        if (show.isSeatAvailable(oneToTheLeft) && !show.isSeatAvailable(twoToTheLeft)) {
            return reservationCanBePossibleWith(oneToTheLeft);
        }
        return successfulReservation();
    }
}

class NoSingleEmptySeatOnTheRight implements ShowReservationRule {

    @Override
    public ReservationResult checkReservation(Show show, Seat seat) {
        Seat oneToTheRight = seat.onTheRight();
        Seat twoToTheRight = oneToTheRight.onTheRight();
        if (show.isSeatAvailable(oneToTheRight) && !show.isSeatAvailable(twoToTheRight)) {
            return reservationCanBePossibleWith(oneToTheRight);
        }
        return successfulReservation();
    }
}
