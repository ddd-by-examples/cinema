package io.pillopl.cinema.reservation;

import io.pillopl.cinema.availability.Hall;

import java.util.Set;

import static io.pillopl.cinema.reservation.ReservationResult.reservationCanBePossibleWith;
import static io.pillopl.cinema.reservation.ReservationResult.successfulReservation;
import static java.util.stream.Collectors.*;
import static java.util.stream.Stream.concat;

class DontLeaveSingleEmptySeat implements ReservationPolicy {

    private final NoSingleEmptySeatOnTheLeft noSingleEmptySeatOnTheLeft = new NoSingleEmptySeatOnTheLeft();
    private final NoSingleEmptySeatOnTheRight noSingleEmptySeatOnTheRight = new NoSingleEmptySeatOnTheRight();

    @Override
    public ReservationResult check(Hall hall, SeatRequest wantedSeats) {
        Set<SeatRequest> neededToTheLeft = noSingleEmptySeatOnTheLeft.check(hall, wantedSeats).getSeatsRequiredToReserve();
        Set<SeatRequest> neededToTheRight = noSingleEmptySeatOnTheRight.check(hall, wantedSeats).getSeatsRequiredToReserve();
        Set<SeatRequest> additionalNeededSeats = concat(neededToTheLeft.stream(), neededToTheRight.stream()).collect(toSet());
        return ReservationResult.reservationCanBePossibleWith(additionalNeededSeats);
    }

}

class NoSingleEmptySeatOnTheLeft implements ReservationPolicy {

    @Override
    public ReservationResult check(Hall hall, SeatRequest wantedSeat) {
        SeatRequest oneToTheLeft = wantedSeat.onTheLeft();
        SeatRequest twoToTheLeft = oneToTheLeft.onTheLeft();
        if (hall.isSeatAvailable(oneToTheLeft.row, oneToTheLeft.seat) && !hall.isSeatAvailable(twoToTheLeft.row, twoToTheLeft.seat)) {
            return reservationCanBePossibleWith(oneToTheLeft);
        }
        return successfulReservation();
    }
}

class NoSingleEmptySeatOnTheRight implements ReservationPolicy {

    @Override
    public ReservationResult check(Hall hall, SeatRequest wantedSeat) {
        SeatRequest oneToTheRight = wantedSeat.onTheRight();
        SeatRequest twoToTheRight = oneToTheRight.onTheRight();
        if (hall.isSeatAvailable(oneToTheRight.row, oneToTheRight.seat) && !hall.isSeatAvailable(twoToTheRight.row, twoToTheRight.seat)) {
            return reservationCanBePossibleWith(oneToTheRight);
        }
        return successfulReservation();
    }
}
