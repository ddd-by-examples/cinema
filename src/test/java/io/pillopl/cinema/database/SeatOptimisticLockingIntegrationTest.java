package io.pillopl.cinema.database;

import io.pillopl.cinema.database.tables.records.ShowSeatRecord;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.conf.Settings;
import org.jooq.exception.DataChangedException;
import org.jooq.impl.DSL;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Random;

import static io.pillopl.cinema.database.tables.ShowSeat.SHOW_SEAT;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@SpringBootTest
public class SeatOptimisticLockingIntegrationTest {

    @Autowired
    DataSource dataSource;

    DSLContext database;

    final Long SEAT_ID = new Random().nextLong();
    final int SHOW_ID = new Random().nextInt();


    @BeforeEach
    public void setup() throws SQLException {
        database = DSL.using(dataSource.getConnection(), SQLDialect.H2,
                new Settings().withExecuteWithOptimisticLocking(true));
    }

    @Test
    void onlyOnePersonCanModifySeatAtGivenTime() {
        //given
        thereIsSeatWithId(SEAT_ID);

        //when
        ShowSeatRecord showSeat = someoneModifiesSeat(SEAT_ID);
        //and
        ShowSeatRecord showSeat2 = someoneElseModifiesTheSameSeat(SEAT_ID);
        //and
        showSeat2.store();

        //expect
        assertThatExceptionOfType(DataChangedException.class).isThrownBy(showSeat::store);
    }


    void thereIsSeatWithId(Long ID) {
        database.insertInto(SHOW_SEAT, SHOW_SEAT.ID, SHOW_SEAT.SHOW_ID, SHOW_SEAT.SEAT_ROW, SHOW_SEAT.SEAT_NUMBER, SHOW_SEAT.AVAILABILITY, SHOW_SEAT.VERSION).values(ID, SHOW_ID, "A", "A2", "AVAILABLE", 0).execute();
    }

    ShowSeatRecord someoneModifiesSeat(Long id) {
        ShowSeatRecord showSeatRecord = database.fetchOne(SHOW_SEAT, SHOW_SEAT.ID.eq(id));
        showSeatRecord.setAvailability("NOT_AVAILABLE");
        return showSeatRecord;
    }

    ShowSeatRecord someoneElseModifiesTheSameSeat(Long id) {
        ShowSeatRecord showSeatRecord = database.fetchOne(SHOW_SEAT, SHOW_SEAT.ID.eq(id));
        showSeatRecord.setAvailability("AVAILABLE");
        return showSeatRecord;
    }

}
