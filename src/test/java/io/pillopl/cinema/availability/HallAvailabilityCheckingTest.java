package io.pillopl.cinema.availability;

import org.junit.jupiter.api.Test;

import static java.util.Map.of;
import static org.assertj.core.api.Assertions.assertThat;

class HallAvailabilityCheckingTest {

    static HallAvailability availability = new HallAvailability(of(
            'A', "X-------",
            'B', "--------",
            'C', "XXXXXXXX",
            'D', "XXXX--XX"));

    @Test
    void shouldUnderstandTheAvailabilityOfSeats() {
        //expect
        assertThat(availability.isSeatAvailable('A', 1)).isFalse();
        assertThat(availability.isSeatAvailable('A', 2)).isTrue();
        assertThat(availability.isSeatAvailable('A', 3)).isTrue();
        assertThat(availability.isSeatAvailable('A', 4)).isTrue();
        assertThat(availability.isSeatAvailable('A', 5)).isTrue();
        assertThat(availability.isSeatAvailable('A', 6)).isTrue();
        assertThat(availability.isSeatAvailable('A', 7)).isTrue();
        assertThat(availability.isSeatAvailable('A', 8)).isTrue();

        assertThat(availability.isSeatAvailable('B', 1)).isTrue();
        assertThat(availability.isSeatAvailable('B', 2)).isTrue();
        assertThat(availability.isSeatAvailable('B', 3)).isTrue();
        assertThat(availability.isSeatAvailable('B', 4)).isTrue();
        assertThat(availability.isSeatAvailable('B', 5)).isTrue();
        assertThat(availability.isSeatAvailable('B', 6)).isTrue();
        assertThat(availability.isSeatAvailable('B', 7)).isTrue();
        assertThat(availability.isSeatAvailable('B', 8)).isTrue();

        assertThat(availability.isSeatAvailable('C', 1)).isFalse();
        assertThat(availability.isSeatAvailable('C', 2)).isFalse();
        assertThat(availability.isSeatAvailable('C', 3)).isFalse();
        assertThat(availability.isSeatAvailable('C', 4)).isFalse();
        assertThat(availability.isSeatAvailable('C', 5)).isFalse();
        assertThat(availability.isSeatAvailable('C', 6)).isFalse();
        assertThat(availability.isSeatAvailable('C', 7)).isFalse();
        assertThat(availability.isSeatAvailable('C', 8)).isFalse();

        assertThat(availability.isSeatAvailable('D', 1)).isFalse();
        assertThat(availability.isSeatAvailable('D', 2)).isFalse();
        assertThat(availability.isSeatAvailable('D', 3)).isFalse();
        assertThat(availability.isSeatAvailable('D', 4)).isFalse();
        assertThat(availability.isSeatAvailable('D', 5)).isTrue();
        assertThat(availability.isSeatAvailable('D', 6)).isTrue();
        assertThat(availability.isSeatAvailable('D', 7)).isFalse();
        assertThat(availability.isSeatAvailable('D', 8)).isFalse();
    }

    @Test
    void notExistingSeatsAreNotAvailable() {
        //expect
        assertThat(availability.isSeatAvailable('A', 0)).isFalse();
        assertThat(availability.isSeatAvailable('D', -1)).isFalse();
        assertThat(availability.isSeatAvailable('D', 9)).isFalse();
        assertThat(availability.isSeatAvailable('B', 1000000)).isFalse();
        assertThat(availability.isSeatAvailable('C', -1000000)).isFalse();
    }



}