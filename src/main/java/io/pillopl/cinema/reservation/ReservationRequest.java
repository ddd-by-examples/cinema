package io.pillopl.cinema.reservation;

import io.pillopl.cinema.availability.Hall;

import java.util.Objects;
import java.util.Set;

import static io.pillopl.cinema.reservation.ReservationResult.failedReservation;

class ReservationRequest {

    private final Set<SeatRequest> wantedSeats;
    private final Hall show;

    ReservationRequest(Set<SeatRequest> wantedSeats, Hall show) {
        this.wantedSeats = wantedSeats;
        this.show = show;
    }

    ReservationResult reserve() {
        return reserve(new NoPolicy());
    }

    ReservationResult reserve(ReservationPolicy rule) {
        if (anySeatNotAvailable()) {
            return failedReservation();
        }
        return rule.check(show, wantedSeats);
    }

    private boolean anySeatNotAvailable() {
        return wantedSeats
                .stream()
                .anyMatch(seat -> !show.isSeatAvailable(seat.row, seat.seat));
    }

}

class SeatRequest {
    final Character row;
    final int seat;

    SeatRequest(Character row, int number) {
        this.row = row;
        this.seat = number;
    }

    SeatRequest onTheLeft() {
        return new SeatRequest(row, seat - 1);
    }

    SeatRequest onTheRight() {
        return new SeatRequest(row, seat + 1);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final SeatRequest that = (SeatRequest) o;
        return seat == that.seat &&
                Objects.equals(row, that.row);
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, seat);
    }
}
