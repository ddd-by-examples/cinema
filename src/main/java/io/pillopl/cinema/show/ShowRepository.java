package io.pillopl.cinema.show;

import io.pillopl.cinema.availability.SeatsAvailability;
import io.pillopl.cinema.database.tables.records.ShowSeatRecord;
import org.jooq.DSLContext;
import org.jooq.Field;
import org.jooq.Result;
import org.jooq.SQLDialect;
import org.jooq.conf.Settings;
import org.jooq.exception.DataChangedException;
import org.jooq.impl.DSL;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Map;
import java.util.stream.IntStream;

import static io.pillopl.cinema.database.Sequences.SHOW_SEAT_SEQUENCE;
import static io.pillopl.cinema.database.tables.ShowSeat.SHOW_SEAT;
import static java.lang.String.valueOf;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toMap;

class ShowRepository {

    private final DSLContext database;

    ShowRepository(DataSource dataSource) throws SQLException {
        database = DSL.using(dataSource.getConnection(), SQLDialect.H2,
                new Settings().withExecuteWithOptimisticLocking(true));
    }

    Show createShow(int showId, SeatsAvailability seatsAvailability) {
        seatsAvailability
                .print()
                .forEach((row, seats) -> insertRow(showId, valueOf(row), seats));
        return new Show(seatsAvailability);
    }

    Show load(int showId) {
        Map<String, Result<ShowSeatRecord>> stringResultMap =
                database
                        .selectFrom(SHOW_SEAT)
                        .where(SHOW_SEAT.SHOW_ID.eq(showId))
                        .orderBy(SHOW_SEAT.SEAT_NUMBER)
                        .fetch()
                        .intoGroups(SHOW_SEAT.SEAT_ROW);
        return reconstructShow(stringResultMap);
    }

    void optimisticallyLockingMarkAllSeatsAsUnavailable(int showId, SeatsCollection toReserve) {
        database.transaction(configuration -> {
            int result = DSL.using(configuration)
                    .update(SHOW_SEAT)
                    .set(SHOW_SEAT.AVAILABILITY, "X")
                    .where(SHOW_SEAT.AVAILABILITY.eq("-"))
                    .and(SHOW_SEAT.SHOW_ID.eq(showId))
                    .and(SHOW_SEAT.SEAT_NUMBER.in(toReserve.seatsToString()))
                    .execute();
            if (result != toReserve.count()) {
                throw new DataChangedException("Some seats were reserved in the meantime");
            };
        });

    }

    private Show reconstructShow(Map<String, Result<ShowSeatRecord>> seats) {
        Map<Character, String> availability = seats
                .entrySet()
                .stream()
                .collect(toMap(e -> e.getKey().charAt(0), e -> e.getValue().stream().map(ShowSeatRecord::getAvailability).collect(joining())));
        return new Show(availability);
    }


    private void insertRow(int showId, String row, String rowAvailability) {
        IntStream.range(1, rowAvailability.length() + 1)
                .forEach(seatNumber -> insertSeat(showId, row, row + seatNumber, rowAvailability.charAt(seatNumber - 1)));
    }

    private void insertSeat(int showId, String row, String seatNumber, Character availability) {
        Field<Long> seq = SHOW_SEAT_SEQUENCE.nextval();
        Long nextVal = database.select(seq).fetchOne(seq);
        database.insertInto(
                SHOW_SEAT,
                SHOW_SEAT.ID,
                SHOW_SEAT.SHOW_ID,
                SHOW_SEAT.AVAILABILITY,
                SHOW_SEAT.SEAT_ROW,
                SHOW_SEAT.SEAT_NUMBER,
                SHOW_SEAT.VERSION)
                .values(
                        nextVal,
                        showId,
                        String.valueOf(availability),
                        row,
                        seatNumber,
                        0).
                execute();
    }

}
