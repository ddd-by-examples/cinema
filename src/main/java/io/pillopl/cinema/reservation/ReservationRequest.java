package io.pillopl.cinema.reservation;

import io.pillopl.cinema.availability.Show;

import java.util.Objects;
import java.util.Set;

import static io.pillopl.cinema.reservation.ReservationResult.reservationThatFailed;

class ReservationRequest {

    private final Set<SeatRequest> wantedSeats;
    private final Show show;

    ReservationRequest(Set<SeatRequest> wantedSeats, Show show) {
        this.wantedSeats = wantedSeats;
        this.show = show;
    }

    ReservationResult reserve() {
        return reserve(new NoRule());
    }

    ReservationResult reserve(ReservationRule rule) {
        if (anySeatNotAvailable()) {
            return reservationThatFailed();
        }
        return rule.validate(show, wantedSeats);
    }

    private boolean anySeatNotAvailable() {
        return wantedSeats
                .stream()
                .anyMatch(seat -> !show.isRequestedSeatAvailable(seat.row, seat.seat));
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
