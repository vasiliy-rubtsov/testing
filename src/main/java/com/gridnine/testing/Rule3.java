package com.gridnine.testing;

import java.time.Duration;
import java.time.LocalDateTime;

/**
 * Правило 3: суммарное время ожидания между сегментами не должно превышать delta часов
 */
public class Rule3 implements Rule {
    private final long delta;    // максимальное время ожидания в часах
    private long deltaFact;  // фактическое время ожидания в часах
    private boolean isFirstSegment; // признак "это первый сегмент"
    private LocalDateTime previousArrivalDate; // время предыдущего прилета

    public Rule3(long delta) {
        this.delta = delta;
        this.deltaFact = 0L;
        this.isFirstSegment = true;
        this.previousArrivalDate = null;
    }

    @Override
    public boolean check(Flight flight) {
        if (flight.getSegments().size() < 2) {
            // Есил это прямой перелет, то задержки нет по определению
            return true;
        }

        this.isFirstSegment = true;
        this.deltaFact = 0L;
        flight.getSegments().forEach(segment -> {
            if (this.isFirstSegment) {
                // Если это первый сегмент перелета, то просто фиксируем его время прилета
                this.isFirstSegment = false;
                this.previousArrivalDate = segment.getArrivalDate();
            } else {
                // Если это второй и последующие сегменты перелета,
                // то накапливаем разницу между временем отправления и временем предыдущего прилета
                this.deltaFact += Duration.between(this.previousArrivalDate, segment.getDepartureDate()).toHours();
                this.previousArrivalDate = segment.getArrivalDate(); // фиксируем новое время прилета
            }
        });

        return this.deltaFact < this.delta;
    }
}
