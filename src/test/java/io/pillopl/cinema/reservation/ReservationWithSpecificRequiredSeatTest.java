package io.pillopl.cinema.reservation;

import io.pillopl.cinema.availability.Hall;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.Map.of;
import static org.assertj.core.api.Assertions.assertThat;


class ReservationWithSpecificRequiredSeatTest {

    public static final ArtificialPolicyWithRequiredSpecificSeat WITH_REQUIRED_A2_RULE = new ArtificialPolicyWithRequiredSpecificSeat(new SeatRequest('A', 2));

    @Test
    void reservationOfAvailableSeatsShouldBePossible() {
        //given
        Hall batman = new Hall(of(
                'A', "X-------",
                'B', "--------",
                'C', "XXXXXXXX",
                'D', "XXXX--XX"));

        //expect
        assertThat(new ReservationRequest(seats("A2"), batman).reserve(WITH_REQUIRED_A2_RULE).isSuccessful()).isTrue();
        assertThat(new ReservationRequest(seats("A1"), batman).reserve(WITH_REQUIRED_A2_RULE).isSuccessful()).isFalse();
        assertThat(new ReservationRequest(seats("A4"), batman).reserve(WITH_REQUIRED_A2_RULE).getSeatsRequiredToReserve()).containsExactly(new SeatRequest('A', 2));

    }

    Set<SeatRequest> seats(String... requests) {
        return Arrays.stream(requests)
                .map(request -> new SeatRequest(request.charAt(0), Integer.parseInt(request.substring(1))))
                .collect(Collectors.toSet());
    }

}