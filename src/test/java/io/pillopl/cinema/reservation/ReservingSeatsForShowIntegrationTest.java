package io.pillopl.cinema.reservation;

import io.pillopl.cinema.show.Seat;
import io.pillopl.cinema.show.Shows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.SQLException;
import java.util.Map;
import java.util.Random;

import static io.pillopl.cinema.show.SeatsCollection.seats;
import static java.util.Map.of;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class ReservingSeatsForShowIntegrationTest {

    ReserveShowService reserveShowService;

    @Autowired
    Shows shows;

    @BeforeEach
    public void setup() throws SQLException {
        reserveShowService = new ReserveShowService(shows);
    }


    @Test
    void shouldReserveAllSeatsWhenAllAvailable() {
        //given
        int show = aShowWithAvailability(of(
                'A', "X-------",
                'B', "--------",
                'C', "XXXXXXXX",
                'D', "XXXX--XX"));

        //when
        ReservationResult result = reserveShowService.reserve(show, seats("B1", "B2"));

        //then
        assertThat(result.isSuccessful()).isTrue();
        assertEquals(of(
                'A', "X-------",
                'B', "XX------",
                'C', "XXXXXXXX",
                'D', "XXXX--XX"), shows.load(show).print());
    }

    @Test
    void shouldNotReserveAnythingWhenSomeSeatsAreNotAvailable() {
        //given
        int show = aShowWithAvailability(of(
                'A', "X-------",
                'B', "--------",
                'C', "XXXXXXXX",
                'D', "XXXX--XX"));

        //when
        ReservationResult result = reserveShowService.reserve(show, seats("A1", "A2"));

        //then
        assertThat(result.isSuccessful()).isFalse();
        assertEquals(of(
                'A', "X-------",
                'B', "--------",
                'C', "XXXXXXXX",
                'D', "XXXX--XX"), shows.load(show).print());
    }

    @Test
    void shouldTakeIntoAccountAdditionalRules() {
        //given
        int show = aShowWithAvailability(of(
                'A', "X--X----",
                'B', "--------",
                'C', "XXXXXXXX",
                'D', "XXXX--XX"));

        //when
        ReservationResult result = reserveShowService.reserve(show, seats("A2"), new DontLeaveSingleEmptySeat());

        //then
        assertThat(result.isSuccessful()).isFalse();
        assertThat(result.getSeatsRequiredToReserve()).containsExactly(new Seat('A', 3));
        assertEquals(of(
                'A', "X--X----",
                'B', "--------",
                'C', "XXXXXXXX",
                'D', "XXXX--XX"), shows.load(show).print());
    }

    int aShowWithAvailability(Map<Character, String> availability) {
        int showId = showId();
        shows.create(showId, availability);
        return showId;
    }

    int showId() {
        return new Random().nextInt();
    }


}
