package io.pillopl.cinema.availability;

import org.junit.jupiter.api.Test;

import static java.util.Map.of;
import static org.assertj.core.api.Assertions.assertThat;

class ShowAvailabilityTest {

    static Show BATMAN = new Show(of(
            'A', "X-------",
            'B', "--------",
            'C', "XXXXXXXX",
            'D', "XXXX--XX"));

    @Test
    void shouldUnderstandTheAvailabilityOfSeats() {
        //expect
        assertThat(BATMAN.isRequestedSeatAvailable('A',1)).isFalse();
        assertThat(BATMAN.isRequestedSeatAvailable('A',2)).isTrue();
        assertThat(BATMAN.isRequestedSeatAvailable('A',3)).isTrue();
        assertThat(BATMAN.isRequestedSeatAvailable('A',4)).isTrue();
        assertThat(BATMAN.isRequestedSeatAvailable('A',5)).isTrue();
        assertThat(BATMAN.isRequestedSeatAvailable('A',6)).isTrue();
        assertThat(BATMAN.isRequestedSeatAvailable('A',7)).isTrue();
        assertThat(BATMAN.isRequestedSeatAvailable('A',8)).isTrue();

        assertThat(BATMAN.isRequestedSeatAvailable('B',1)).isTrue();
        assertThat(BATMAN.isRequestedSeatAvailable('B',2)).isTrue();
        assertThat(BATMAN.isRequestedSeatAvailable('B',3)).isTrue();
        assertThat(BATMAN.isRequestedSeatAvailable('B',4)).isTrue();
        assertThat(BATMAN.isRequestedSeatAvailable('B',5)).isTrue();
        assertThat(BATMAN.isRequestedSeatAvailable('B',6)).isTrue();
        assertThat(BATMAN.isRequestedSeatAvailable('B',7)).isTrue();
        assertThat(BATMAN.isRequestedSeatAvailable('B',8)).isTrue();

        assertThat(BATMAN.isRequestedSeatAvailable('C',1)).isFalse();
        assertThat(BATMAN.isRequestedSeatAvailable('C',2)).isFalse();
        assertThat(BATMAN.isRequestedSeatAvailable('C',3)).isFalse();
        assertThat(BATMAN.isRequestedSeatAvailable('C',4)).isFalse();
        assertThat(BATMAN.isRequestedSeatAvailable('C',5)).isFalse();
        assertThat(BATMAN.isRequestedSeatAvailable('C',6)).isFalse();
        assertThat(BATMAN.isRequestedSeatAvailable('C',7)).isFalse();
        assertThat(BATMAN.isRequestedSeatAvailable('C',8)).isFalse();

        assertThat(BATMAN.isRequestedSeatAvailable('D',1)).isFalse();
        assertThat(BATMAN.isRequestedSeatAvailable('D',2)).isFalse();
        assertThat(BATMAN.isRequestedSeatAvailable('D',3)).isFalse();
        assertThat(BATMAN.isRequestedSeatAvailable('D',4)).isFalse();
        assertThat(BATMAN.isRequestedSeatAvailable('D',5)).isTrue();
        assertThat(BATMAN.isRequestedSeatAvailable('D',6)).isTrue();
        assertThat(BATMAN.isRequestedSeatAvailable('D',7)).isFalse();
        assertThat(BATMAN.isRequestedSeatAvailable('D',8)).isFalse();
    }

    @Test
    void notExistingSeatsAreNotAvailable() {
        //expect
        assertThat(BATMAN.isRequestedSeatAvailable('A',0)).isFalse();
        assertThat(BATMAN.isRequestedSeatAvailable('D',-1)).isFalse();
        assertThat(BATMAN.isRequestedSeatAvailable('D',9)).isFalse();
        assertThat(BATMAN.isRequestedSeatAvailable('B',1000000)).isFalse();
        assertThat(BATMAN.isRequestedSeatAvailable('C',-1000000)).isFalse();
    }

}