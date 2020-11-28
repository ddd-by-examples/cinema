package io.pillopl.cinema.show;

import java.util.Objects;

public class Seat {

    final Character row;
    final int number;

    public Seat(Character row, int number) {
        this.row = row;
        this.number = number;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Seat that = (Seat) o;
        return number == that.number &&
                Objects.equals(row, that.row);
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, number);
    }
}
