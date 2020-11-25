package io.pillopl.cinema.availability;



import java.util.*;
import java.util.Map.Entry;

import static java.util.stream.Collectors.toMap;

public class Show {

    private final Map<Character, Row> rows;

    public Show(Map<Character, String> availability) {
        this.rows = availability
                .entrySet()
                .stream()
                .collect(toMap(Entry::getKey, entry -> Row.of(entry.getKey(), entry.getValue())));
    }

    public boolean isRequestedSeatAvailable(Character row, int seatNumber) {
        if (rowDoesNotExists(row)) {
            return false;
        }
        return rows.get(row).hasAvailabilityAt(seatNumber);
    }

    private boolean rowDoesNotExists(Character row) {
        return !rows.containsKey(row);
    }


}

