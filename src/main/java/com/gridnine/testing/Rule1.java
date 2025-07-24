package com.gridnine.testing;

import java.time.LocalDateTime;

/**
 * Правило 1: в полете не должно быть сегментов, у к-рых дата вылета раньше текущей
 */
public class Rule1 implements Rule {
    private final LocalDateTime currentDateTime;

    public Rule1() {
        this.currentDateTime = LocalDateTime.now();
    }

    public Rule1(LocalDateTime currentDateTime) {
        this.currentDateTime = currentDateTime;
    }
    @Override
    public boolean check(Flight flight) {
        // Подсчитываем количество сегментов, у которых дата вылета раньше текущей
        long cnt = flight
                .getSegments()
                .stream()
                .filter(segment -> segment.getDepartureDate().isBefore(this.currentDateTime))
                .count();
        return !(cnt > 0);
    }
}
