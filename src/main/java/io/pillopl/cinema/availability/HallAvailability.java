package io.pillopl.cinema.availability;



import java.util.*;
import java.util.Map.Entry;

import static java.util.stream.Collectors.toMap;

public class HallAvailability {

    private final Map<Character, Row> seats;

    public HallAvailability(Map<Character, String> availability) {
        this.seats = availability
                .entrySet()
                .stream()
                .collect(toMap(Entry::getKey, entry -> Row.of(entry.getValue())));
    }

    public boolean isSeatAvailable(Character row, int seatNumber) {
        if (rowDoesNotExists(row)) {
            return false;
        }
        return seats.get(row).hasAvailabilityAt(seatNumber);
    }

    private boolean rowDoesNotExists(Character row) {
        return !seats.containsKey(row);
    }

    public Map<Character, String> print() {
        return seats
                .entrySet()
                .stream()
                .collect(toMap(Entry::getKey, entry -> entry.getValue().print()));
    }


}

