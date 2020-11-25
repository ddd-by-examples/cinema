package io.pillopl.cinema.reservation;

import io.pillopl.cinema.availability.Show;

import java.util.*;
import java.util.stream.Collectors;


import static io.pillopl.cinema.reservation.ReservationResult.reservationThatCanBePossibleWith;
import static io.pillopl.cinema.reservation.ReservationResult.reservationThatIsSuccesful;

interface ReservationRule {

    ReservationResult validate(Show forShow, Set<SeatRequest> wantedSeats);
}

class NoRule implements ReservationRule {

    @Override
    public ReservationResult validate(Show forShow, Set<SeatRequest> wantedSeats) {
        return reservationThatIsSuccesful();
    }
}


class DontLeaveSingleFreeSeat implements ReservationRule {

    @Override
    public ReservationResult validate(Show forShow, Set<SeatRequest> wantedSeats) {
        return reservationThatCanBePossibleWith(additionalSeatsRequiredToGet(wantedSeats, forShow));
    }

    private Set<SeatRequest> additionalSeatsRequiredToGet(Set<SeatRequest> wantedSeats, Show forShow) {
        return wantedSeats
                .stream()
                .map(seat -> additionalSeatsRequiredToGet(seat, forShow))
                .flatMap(Collection::stream)
                .filter(seatRequest -> !wantedSeats.contains(seatRequest))
                .collect(Collectors.toSet());
    }

    private Set<SeatRequest> additionalSeatsRequiredToGet(SeatRequest seat, Show forShow) {
        Set<SeatRequest> additionalSeats = new HashSet<>();
        if (mustReserveOnTheLeft(seat, forShow)) {
            additionalSeats.add(seat.onTheLeft());
        }
        if (mustReserveOnTheRight(seat, forShow)) {
            additionalSeats.add(seat.onTheRight());
        }
        return additionalSeats;
    }

    private boolean mustReserveOnTheRight(SeatRequest seat, Show forShow) {
        SeatRequest oneToTheRight = seat.onTheRight();
        SeatRequest twoToTheRight = oneToTheRight.onTheRight();
        if (forShow.isRequestedSeatAvailable(oneToTheRight.row, oneToTheRight.seat) && !forShow.isRequestedSeatAvailable(twoToTheRight.row, twoToTheRight.seat)) {
            return true;
        }
        return false;
    }

    private boolean mustReserveOnTheLeft(SeatRequest seat, Show forShow) {
        SeatRequest oneToTheLeft = seat.onTheLeft();
        SeatRequest twoToTheLeft = oneToTheLeft.onTheLeft();
        if (forShow.isRequestedSeatAvailable(oneToTheLeft.row, oneToTheLeft.seat) && !forShow.isRequestedSeatAvailable(twoToTheLeft.row, twoToTheLeft.seat)) {
            return true;
        }
        return false;
    }
}

class ArtificialRuleWithRequiredSpecificSeat implements ReservationRule {

    final SeatRequest requiredSeat;

    ArtificialRuleWithRequiredSpecificSeat(SeatRequest requiredSeat) {
        this.requiredSeat = requiredSeat;
    }

    @Override
    public ReservationResult validate(Show forShow, Set<SeatRequest> wantedSeats) {
        if (wantedSeats.contains(requiredSeat)) {
            return reservationThatIsSuccesful();
        }
        return reservationThatCanBePossibleWith(Set.of(requiredSeat));
    }
}