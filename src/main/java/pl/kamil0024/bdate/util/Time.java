package pl.kamil0024.bdate.util;

import lombok.Getter;

public enum Time {

    MS(1),
    SECOND(1000),
    MINUTE(60000),
    HOUR(3600000),
    DAY(86400000);

    @Getter
    private final long ms;

    Time(int ms) {
        this.ms = ms;
    }
}
