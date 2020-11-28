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

    public Optional<Integer> singleAvailabilityToTheLeftFrom(Character row, int seatNumber) {
        int nextSeatToTheLeft = seatNumber - 1;
        if (isSeatAvailable(row, nextSeatToTheLeft) && !isSeatAvailable(row, nextSeatToTheLeft - 1)) {
            return Optional.of(nextSeatToTheLeft);
        }
        return Optional.empty();
    }

    public Optional<Integer> singleAvailabilityToTheRightFrom(Character row, int seatNumber) {
        int nextSeatToTheRight = seatNumber + 1;
        if (isSeatAvailable(row, nextSeatToTheRight) && !isSeatAvailable(row, nextSeatToTheRight + 1)) {
            return Optional.of(nextSeatToTheRight);
        }
        return Optional.empty();
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

