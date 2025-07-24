package com.gridnine.testing;

/**
 * Правило 2: в полете не должно быть сегментов, у которых дата прилета раньше даты отлета
 */
public class Rule2 implements Rule {
    @Override
    public boolean check(Flight flight) {
        long cnt = flight
                .getSegments()
                .stream()
                .filter(segment -> segment.getArrivalDate().isBefore(segment.getDepartureDate()))
                .count();

        return !(cnt > 0);
    }
}
