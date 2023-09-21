package ru.asteises.lisa.util;

public enum Navigate {
    NEXT("NEXT >>>"),
    PREV("<<< PREV"),
    BOTH("BOTH");

    private final String text;

    Navigate(String text) {
        this.text = text;
    }

}
