package io.pillopl.cinema.availability;

import org.junit.jupiter.api.Test;

import static java.util.Map.of;
import static org.assertj.core.api.Assertions.assertThat;

class HallAvailabilityTest {

    static Hall BATMAN = new Hall(of(
            'A', "X-------",
            'B', "--------",
            'C', "XXXXXXXX",
            'D', "XXXX--XX"));

    @Test
    void shouldUnderstandTheAvailabilityOfSeats() {
        //expect
        assertThat(BATMAN.isSeatAvailable('A',1)).isFalse();
        assertThat(BATMAN.isSeatAvailable('A',2)).isTrue();
        assertThat(BATMAN.isSeatAvailable('A',3)).isTrue();
        assertThat(BATMAN.isSeatAvailable('A',4)).isTrue();
        assertThat(BATMAN.isSeatAvailable('A',5)).isTrue();
        assertThat(BATMAN.isSeatAvailable('A',6)).isTrue();
        assertThat(BATMAN.isSeatAvailable('A',7)).isTrue();
        assertThat(BATMAN.isSeatAvailable('A',8)).isTrue();

        assertThat(BATMAN.isSeatAvailable('B',1)).isTrue();
        assertThat(BATMAN.isSeatAvailable('B',2)).isTrue();
        assertThat(BATMAN.isSeatAvailable('B',3)).isTrue();
        assertThat(BATMAN.isSeatAvailable('B',4)).isTrue();
        assertThat(BATMAN.isSeatAvailable('B',5)).isTrue();
        assertThat(BATMAN.isSeatAvailable('B',6)).isTrue();
        assertThat(BATMAN.isSeatAvailable('B',7)).isTrue();
        assertThat(BATMAN.isSeatAvailable('B',8)).isTrue();

        assertThat(BATMAN.isSeatAvailable('C',1)).isFalse();
        assertThat(BATMAN.isSeatAvailable('C',2)).isFalse();
        assertThat(BATMAN.isSeatAvailable('C',3)).isFalse();
        assertThat(BATMAN.isSeatAvailable('C',4)).isFalse();
        assertThat(BATMAN.isSeatAvailable('C',5)).isFalse();
        assertThat(BATMAN.isSeatAvailable('C',6)).isFalse();
        assertThat(BATMAN.isSeatAvailable('C',7)).isFalse();
        assertThat(BATMAN.isSeatAvailable('C',8)).isFalse();

        assertThat(BATMAN.isSeatAvailable('D',1)).isFalse();
        assertThat(BATMAN.isSeatAvailable('D',2)).isFalse();
        assertThat(BATMAN.isSeatAvailable('D',3)).isFalse();
        assertThat(BATMAN.isSeatAvailable('D',4)).isFalse();
        assertThat(BATMAN.isSeatAvailable('D',5)).isTrue();
        assertThat(BATMAN.isSeatAvailable('D',6)).isTrue();
        assertThat(BATMAN.isSeatAvailable('D',7)).isFalse();
        assertThat(BATMAN.isSeatAvailable('D',8)).isFalse();
    }

    @Test
    void notExistingSeatsAreNotAvailable() {
        //expect
        assertThat(BATMAN.isSeatAvailable('A',0)).isFalse();
        assertThat(BATMAN.isSeatAvailable('D',-1)).isFalse();
        assertThat(BATMAN.isSeatAvailable('D',9)).isFalse();
        assertThat(BATMAN.isSeatAvailable('B',1000000)).isFalse();
        assertThat(BATMAN.isSeatAvailable('C',-1000000)).isFalse();
    }

}