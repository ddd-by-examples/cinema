package io.pillopl.cinema.reservation;

import io.pillopl.cinema.availability.Hall;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.Map.of;
import static org.assertj.core.api.Assertions.assertThat;


class ReservationWithoutAdditionalRulesTest {

    @Test
    void reservationOfAvailableSeatsShouldBePossible() {
        //given
        Hall batman = new Hall(of(
                'A', "X-------",
                'B', "--------",
                'C', "XXXXXXXX",
                'D', "XXXX--XX"));


        assertThat(reservationOf(batman, "A2").isSuccessful()).isTrue();
        assertThat(reservationOf(batman, "A3").isSuccessful()).isTrue();
        assertThat(reservationOf(batman, "A4").isSuccessful()).isTrue();

        assertThat(reservationOf(batman, "A2","A3","A4","A5","A6","A7","A8","B2","B3","B4","B5","B6","B7","B8","D5").isSuccessful());
    }

    @Test
    void reservationOfNotAvailableSeatsShouldNotBePossible() {
        //given
        Hall batman = new Hall(of(
                'A', "X-------",
                'B', "--------",
                'C', "XXXXXXXX",
                'D', "XXXX--XX"));

        assertThat(reservationOf(batman, "A1").isSuccessful()).isFalse();
        assertThat(reservationOf(batman, "A1","A2").isSuccessful()).isFalse();
        assertThat(reservationOf(batman, "B1","B2","B3","C1").isSuccessful()).isFalse();
    }

    ReservationResult reservationOf(Hall batman, String ... seats) {
        return new ReservationRequest(seats(seats), batman).reserve();
    }

    Set<SeatRequest> seats(String ... requests) {
        return Arrays.stream(requests)
                .map(request -> new SeatRequest(request.charAt(0), Integer.parseInt(request.substring(1))))
                .collect(Collectors.toSet());
    }

}