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
    public ReservationResult checkReservation(Show show, Seat fromSeat) {
        if (show.isSeatToTheLeftAvailable(fromSeat) && !show.isSeatTwoToTheLeftAvailable(fromSeat)) {
            return reservationCanBePossibleWith(show.toTheLeft(fromSeat));
        }
        return successfulReservation();
    }
}

class NoSingleEmptySeatOnTheRight implements ShowReservationRule {

    @Override
    public ReservationResult checkReservation(Show show, Seat fromSeat) {
        if (show.isSeatToTheRightAvailable(fromSeat) && !show.isSeatTwoToTheRightAvailable(fromSeat)) {
            return reservationCanBePossibleWith(show.toTheRight(fromSeat));
        }
        return successfulReservation();
    }
}
