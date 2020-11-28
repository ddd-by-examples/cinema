package io.pillopl.cinema.reservation;

import io.pillopl.cinema.show.SeatsCollection;
import io.pillopl.cinema.show.Show;
import org.junit.jupiter.api.Test;


import static java.util.Map.of;
import static org.assertj.core.api.Assertions.assertThat;


class ReservationWithoutAdditionalRulesTest {

    @Test
    void reservationOfAvailableSeatsShouldBePossible() {
        //given
        Show batman = new Show(of(
                'A', "X-------",
                'B', "--------",
                'C', "XXXXXXXX",
                'D', "XXXX--XX"));


        assertThat(reservationFor(batman, "A2").isSuccessful()).isTrue();
        assertThat(reservationFor(batman, "A3").isSuccessful()).isTrue();
        assertThat(reservationFor(batman, "A4").isSuccessful()).isTrue();

        assertThat(reservationFor(batman, "A2","A3","A4","A5","A6","A7","A8","B2","B3","B4","B5","B6","B7","B8","D5").isSuccessful());
    }

    @Test
    void reservationOfNotAvailableSeatsShouldNotBePossible() {
        //given
        Show batman = new Show(of(
                'A', "X-------",
                'B', "--------",
                'C', "XXXXXXXX",
                'D', "XXXX--XX"));

        assertThat(reservationFor(batman, "A1").isSuccessful()).isFalse();
        assertThat(reservationFor(batman, "A1","A2").isSuccessful()).isFalse();
        assertThat(reservationFor(batman, "B1","B2","B3","C1").isSuccessful()).isFalse();
    }

    ReservationResult reservationFor(Show show, String ... seats) {
        return new ReserveShowDomainService().reserve(show, SeatsCollection.seats(seats));
    }



}