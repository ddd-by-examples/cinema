package io.pillopl.cinema.reservation;

import io.pillopl.cinema.show.Seat;
import io.pillopl.cinema.show.SeatsCollection;
import io.pillopl.cinema.show.Show;
import org.junit.jupiter.api.Test;


import static java.util.Map.of;
import static org.assertj.core.api.Assertions.assertThat;


class ReservationWithoutSingleFreeSeatLeftRuleTest {

    @Test
    void reservationThatLeavesOneSingleFreeSeatShouldNotBePossible() {
        //given
        Show batman = new Show(of(
                'A', "X--X----",
                'B', "--------",
                'C', "XXXXXXXX",
                'D', "XXXX--XX"));

        //expect
        assertThat(reservationFor(batman, "A2", "D5").isSuccessful()).isFalse();
        assertThat(reservationFor(batman, "A2", "D5").getSeatsRequiredToReserve()).containsExactly(new Seat('A', 3), new Seat('D', 6));

        assertThat(reservationFor(batman, "A3").isSuccessful()).isFalse();
        assertThat(reservationFor(batman, "A3").getSeatsRequiredToReserve()).containsExactly(new Seat('A', 2));

        assertThat(reservationFor(batman, "A2").isSuccessful()).isFalse();
        assertThat(reservationFor(batman, "A2").getSeatsRequiredToReserve()).containsExactly(new Seat('A', 3));

    }

    @Test
    void reservationThatDoesNotLeaveOneSingleFreeSeatShouldBePossible() {
        //given
        Show batman = new Show(of(
                'A', "X--X----",
                'B', "--------",
                'C', "XXXXXXXX",
                'D', "XXXX--XX",
                'E', "--------"));

        //expect
        assertThat(reservationFor(batman, "A2", "A3").isSuccessful()).isTrue();
        assertThat(reservationFor(batman, "B1", "B2").isSuccessful()).isTrue();
        assertThat(reservationFor(batman, "B3", "B4", "B7", "B8").isSuccessful()).isTrue();
        assertThat(reservationFor(batman, "E8").isSuccessful()).isTrue();
    }


    @Test
    void reservationOfNotAvailableSeatsShouldNotBePossible() {
        //given
        Show batman = new Show(of(
                'A', "X--X----",
                'B', "--------",
                'C', "XXXXXXXX",
                'D', "XXXX--XX",
                'E', "--------"));

        assertThat(reservationFor(batman, "A1").isSuccessful()).isFalse();
        assertThat(reservationFor(batman, "A1", "A2").isSuccessful()).isFalse();
        assertThat(reservationFor(batman, "B1", "B2", "B3", "C1").isSuccessful()).isFalse();
    }

    ReservationResult reservationFor(Show show, String... seats) {
        return new ShowReservationBuilder().forShow(show).seats(SeatsCollection.seats(seats)).rule(new DontLeaveSingleEmptySeat()).reserve();

    }



}