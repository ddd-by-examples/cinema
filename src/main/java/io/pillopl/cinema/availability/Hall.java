package io.pillopl.cinema.availability;


import java.util.*;
import java.util.Map.Entry;

import static java.util.stream.Collectors.toMap;

public class Hall {

    private final Map<Character, Row> seats;

    public Hall(Map<Character, String> availability) {
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

    public Optional<Integer> singleAvailabilityToTheLeftFrom(Character row, int seatNumber) {
        if (isSeatAvailable(row, seatNumber - 1) && !isSeatAvailable(row, seatNumber - 1 - 1)) {
            return Optional.of(seatNumber - 1);
        }
        return Optional.empty();
    }

    public Optional<Integer> singleAvailabilityToTheRightFrom(Character row, int seatNumber) {
        if (isSeatAvailable(row, seatNumber + 1) && !isSeatAvailable(row, seatNumber + 1 + 1)) {
            return Optional.of(seatNumber + 1);
        }
        return Optional.empty();
    }

    public Map<Character, String> print() {
        return seats
                .entrySet()
                .stream()
                .collect(toMap(Entry::getKey, entry -> entry.getValue().print()));
    }

    private boolean rowDoesNotExists(Character row) {
        return !seats.containsKey(row);
    }


}

