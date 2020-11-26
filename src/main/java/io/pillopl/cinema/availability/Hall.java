package io.pillopl.cinema.availability;



import java.util.*;
import java.util.Map.Entry;

import static java.util.stream.Collectors.toMap;

public class Hall {

    private final Map<Character, Row> rows;

    public Hall(Map<Character, String> availability) {
        this.rows = availability
                .entrySet()
                .stream()
                .collect(toMap(Entry::getKey, entry -> Row.of(entry.getValue())));
    }

    public boolean isSeatAvailable(Character row, int seatNumber) {
        if (rowDoesNotExists(row)) {
            return false;
        }
        return rows.get(row).hasAvailabilityAt(seatNumber);
    }

    private boolean rowDoesNotExists(Character row) {
        return !rows.containsKey(row);
    }


}

