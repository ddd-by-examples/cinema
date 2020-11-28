package io.pillopl.cinema.availability;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static java.util.Map.of;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertEquals;

class SeatsAvailabilityPrintingTest {

    @Test
    void shouldPrintSeatsAvailabilityInHallWithNoSeats() {
        //given
        Map<Character, String> NO_SEATS = of();

        //expect
        assertEquals(NO_SEATS, new SeatsAvailability(NO_SEATS).print());
    }

    @Test
    void shouldPrintSeatsAvailabilityWhenThereIsNoAvailability() {
        //given
        Map<Character, String> NONE_AVAILABLE = of(
                'A', "XXXXXXXX",
                'B', "XXXXXXXX",
                'C', "XXXXXXXX",
                'D', "XXXXXXXX");
        //expect
        assertEquals(NONE_AVAILABLE, new SeatsAvailability(NONE_AVAILABLE).print());
    }

    @Test
    void shouldPrintSeatsAvailabilityWhenEverySeatIsAvailable() {
        //given
        Map<Character, String> ALL_AVAILABLE = of(
                'A', "--------",
                'B', "--------",
                'C', "--------",
                'D', "--------");
        //expect
        assertEquals(ALL_AVAILABLE, new SeatsAvailability(ALL_AVAILABLE).print());
    }

    @Test
    void shouldPrintSeatsAvailabilityInPartiallyUnavailableHall() {
        //given
        Map<Character, String> PARTIALLY_UNAVAILABLE = of(
                'A', "X-------",
                'B', "--------",
                'C', "XXXXXXXX",
                'D', "XXXX--XX");
        //expect
        assertEquals(PARTIALLY_UNAVAILABLE, new SeatsAvailability(PARTIALLY_UNAVAILABLE).print());
    }

    @Test
    void cannotCreateAvailabilityWhenInputIsNotUnderstood() {
        assertThatExceptionOfType(IllegalStateException.class)
                .isThrownBy(() -> new SeatsAvailability(of('A', "X-----xyzsda")));
    }


}