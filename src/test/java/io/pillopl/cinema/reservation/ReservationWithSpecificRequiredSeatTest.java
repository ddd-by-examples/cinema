package io.pillopl.cinema.reservation;

import io.pillopl.cinema.show.Seat;
import io.pillopl.cinema.show.SeatsCollection;
import io.pillopl.cinema.show.Show;
import org.junit.jupiter.api.Test;

import static java.util.Map.of;
import static org.assertj.core.api.Assertions.assertThat;


class ReservationWithSpecificRequiredSeatTest {

    static final ArtificialRuleWithRequiredSpecificSeat WITH_REQUIRED_A2_RULE = new ArtificialRuleWithRequiredSpecificSeat(new Seat('A', 2));

    @Test
    void reservationOfAvailableSeatsShouldBePossible() {
        //given
        Show batman = new Show(of(
                'A', "X-------",
                'B', "--------",
                'C', "XXXXXXXX",
                'D', "XXXX--XX"));

        //expect
        assertThat(reservationFor(batman, "A2").isSuccessful()).isTrue();
        assertThat(reservationFor(batman, "A3").isSuccessful()).isFalse();
        assertThat(reservationFor(batman, "A3").getSeatsRequiredToReserve()).containsExactly(new Seat('A', 2));
    }

    ReservationResult reservationFor(Show show, String seats) {
        return new ReserveShowDomainService().canReserve(show, SeatsCollection.seats(seats), WITH_REQUIRED_A2_RULE);
    }


}