package io.pillopl.cinema.reservation;

import io.pillopl.cinema.availability.Hall;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.Map.of;
import static org.assertj.core.api.Assertions.assertThat;


class ReservationWithoutSingleFreeSeatLeftRuleTest {

    @Test
    void reservationThatLeavesOneSingleFreeSeatShouldNotBePossible() {
        //given
        Hall batman = new Hall(of(
                'A', "X--X----",
                'B', "--------",
                'C', "XXXXXXXX",
                'D', "XXXX--XX"));

        //expect
        assertThat(reservationOf(batman, "A2", "D5").isSuccessful()).isFalse();
        assertThat(reservationOf(batman, "A2", "D5").getSeatsRequiredToReserve()).containsExactly(new SeatRequest('A', 3), new SeatRequest('D', 6));

        assertThat(reservationOf(batman, "A3").isSuccessful()).isFalse();
        assertThat(reservationOf(batman, "A3").getSeatsRequiredToReserve()).containsExactly(new SeatRequest('A', 2));

        assertThat(reservationOf(batman, "A2").isSuccessful()).isFalse();
        assertThat(reservationOf(batman, "A2").getSeatsRequiredToReserve()).containsExactly(new SeatRequest('A', 3));

    }

    @Test
    void reservationThatDoesNotLeaveOneSingleFreeSeatShouldBePossible() {
        //given
        Hall batman = new Hall(of(
                'A', "X--X----",
                'B', "--------",
                'C', "XXXXXXXX",
                'D', "XXXX--XX",
                'E', "--------"));

        //expect
        assertThat(reservationOf(batman, "A2", "A3").isSuccessful()).isTrue();
        assertThat(reservationOf(batman, "B1", "B2").isSuccessful()).isTrue();
        assertThat(reservationOf(batman, "B3", "B4", "B7", "B8").isSuccessful()).isTrue();
        assertThat(reservationOf(batman, "E8").isSuccessful()).isTrue();
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
        return new ReservationRequest(seats(seats), batman).reserve(new DontLeaveSingleEmptySeat());
    }

    Set<SeatRequest> seats(String ... requests) {
        return Arrays.stream(requests)
                .map(request -> new SeatRequest(request.charAt(0), Integer.parseInt(request.substring(1))))
                .collect(Collectors.toSet());
    }

}