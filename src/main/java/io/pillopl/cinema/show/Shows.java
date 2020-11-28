package io.pillopl.cinema.show;

import io.pillopl.cinema.availability.HallAvailability;
import org.jooq.exception.DataChangedException;

import java.util.Map;


public class Shows {

    private final ShowRepository showRepository;

    public Shows(ShowRepository showRepository) {
        this.showRepository = showRepository;
    }

    public Show create(int showId, Map<Character, String> hall) {
        return showRepository.createShow(showId, new HallAvailability(hall));
    }

    public Show load(int showId) {
        return showRepository.load(showId);
    }

    public boolean checkIfAllSeatsStillAvailableAndMarkAsUnavailable(int showId, SeatsCollection toReserve) {
        try {
           showRepository.optimisticallyLockingMarkAllSeatsAsUnavailable(showId, toReserve);
        } catch (DataChangedException e) {
            return false;
        }
        return true;
    }

}
