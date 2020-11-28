package io.pillopl.cinema.availability;

import org.junit.jupiter.api.Test;

import static java.util.Map.of;
import static org.assertj.core.api.Assertions.assertThat;

class SeatsAvailabilityCheckingTest {

    static SeatsAvailability availability = new SeatsAvailability(of(
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

    @Test
    void shouldInformAboutSingleSeatAvailableBetweenTwoUnavailable() {
        //expect
        assertThat(availability.singleAvailabilityToTheLeftFrom('A', 3)).hasValue(2);
        assertThat(availability.singleAvailabilityToTheRightFrom('A', 7)).hasValue(8);
        assertThat(availability.singleAvailabilityToTheLeftFrom('B', 2)).hasValue(1);
        assertThat(availability.singleAvailabilityToTheRightFrom('D', 5)).hasValue(6);
        assertThat(availability.singleAvailabilityToTheLeftFrom('D', 6)).hasValue(5);
        assertThat(availability.singleAvailabilityToTheRightFrom('B', 7)).hasValue(8);

    }

    @Test
    void shouldNotInformAboutSingleSeatAvailableBetweenTwoUnavailable() {
        //expect
        assertThat(availability.singleAvailabilityToTheLeftFrom('A', 1)).isEmpty();
        assertThat(availability.singleAvailabilityToTheLeftFrom('A', 2)).isEmpty();
        assertThat(availability.singleAvailabilityToTheLeftFrom('A', 4)).isEmpty();
        assertThat(availability.singleAvailabilityToTheLeftFrom('A', 5)).isEmpty();
        assertThat(availability.singleAvailabilityToTheLeftFrom('A', 6)).isEmpty();
        assertThat(availability.singleAvailabilityToTheLeftFrom('A', 7)).isEmpty();
        assertThat(availability.singleAvailabilityToTheLeftFrom('A', 8)).isEmpty();

        assertThat(availability.singleAvailabilityToTheRightFrom('A', 1)).isEmpty();
        assertThat(availability.singleAvailabilityToTheRightFrom('A', 2)).isEmpty();
        assertThat(availability.singleAvailabilityToTheRightFrom('A', 4)).isEmpty();
        assertThat(availability.singleAvailabilityToTheRightFrom('A', 5)).isEmpty();
        assertThat(availability.singleAvailabilityToTheRightFrom('A', 6)).isEmpty();
        assertThat(availability.singleAvailabilityToTheRightFrom('A', 8)).isEmpty();

        assertThat(availability.singleAvailabilityToTheLeftFrom('B', 1)).isEmpty();
        assertThat(availability.singleAvailabilityToTheLeftFrom('B', 3)).isEmpty();
        assertThat(availability.singleAvailabilityToTheLeftFrom('B', 4)).isEmpty();
        assertThat(availability.singleAvailabilityToTheLeftFrom('B', 5)).isEmpty();
        assertThat(availability.singleAvailabilityToTheLeftFrom('B', 6)).isEmpty();
        assertThat(availability.singleAvailabilityToTheLeftFrom('B', 7)).isEmpty();
        assertThat(availability.singleAvailabilityToTheLeftFrom('B', 8)).isEmpty();

        assertThat(availability.singleAvailabilityToTheRightFrom('B', 1)).isEmpty();
        assertThat(availability.singleAvailabilityToTheRightFrom('B', 2)).isEmpty();
        assertThat(availability.singleAvailabilityToTheRightFrom('B', 3)).isEmpty();
        assertThat(availability.singleAvailabilityToTheRightFrom('B', 4)).isEmpty();
        assertThat(availability.singleAvailabilityToTheRightFrom('B', 5)).isEmpty();
        assertThat(availability.singleAvailabilityToTheRightFrom('B', 6)).isEmpty();
        assertThat(availability.singleAvailabilityToTheRightFrom('B', 8)).isEmpty();

        assertThat(availability.singleAvailabilityToTheLeftFrom('C', 1)).isEmpty();
        assertThat(availability.singleAvailabilityToTheLeftFrom('C', 2)).isEmpty();
        assertThat(availability.singleAvailabilityToTheLeftFrom('C', 3)).isEmpty();
        assertThat(availability.singleAvailabilityToTheLeftFrom('C', 4)).isEmpty();
        assertThat(availability.singleAvailabilityToTheLeftFrom('C', 5)).isEmpty();
        assertThat(availability.singleAvailabilityToTheLeftFrom('C', 6)).isEmpty();
        assertThat(availability.singleAvailabilityToTheLeftFrom('C', 7)).isEmpty();
        assertThat(availability.singleAvailabilityToTheLeftFrom('C', 8)).isEmpty();

        assertThat(availability.singleAvailabilityToTheLeftFrom('D', 1)).isEmpty();
        assertThat(availability.singleAvailabilityToTheLeftFrom('D', 2)).isEmpty();
        assertThat(availability.singleAvailabilityToTheLeftFrom('D', 3)).isEmpty();
        assertThat(availability.singleAvailabilityToTheLeftFrom('D', 4)).isEmpty();

        assertThat(availability.singleAvailabilityToTheLeftFrom('D', 7)).isEmpty();
        assertThat(availability.singleAvailabilityToTheLeftFrom('D', 8)).isEmpty();

        assertThat(availability.singleAvailabilityToTheRightFrom('C', 1)).isEmpty();
        assertThat(availability.singleAvailabilityToTheRightFrom('C', 2)).isEmpty();
        assertThat(availability.singleAvailabilityToTheRightFrom('C', 3)).isEmpty();
        assertThat(availability.singleAvailabilityToTheRightFrom('C', 4)).isEmpty();
        assertThat(availability.singleAvailabilityToTheRightFrom('C', 5)).isEmpty();
        assertThat(availability.singleAvailabilityToTheRightFrom('C', 6)).isEmpty();
        assertThat(availability.singleAvailabilityToTheRightFrom('C', 7)).isEmpty();
        assertThat(availability.singleAvailabilityToTheRightFrom('C', 8)).isEmpty();

        assertThat(availability.singleAvailabilityToTheRightFrom('D', 1)).isEmpty();
        assertThat(availability.singleAvailabilityToTheRightFrom('D', 2)).isEmpty();
        assertThat(availability.singleAvailabilityToTheRightFrom('D', 3)).isEmpty();
        assertThat(availability.singleAvailabilityToTheRightFrom('D', 4)).isEmpty();

        assertThat(availability.singleAvailabilityToTheRightFrom('D', 7)).isEmpty();
        assertThat(availability.singleAvailabilityToTheRightFrom('D', 8)).isEmpty();
    }


}