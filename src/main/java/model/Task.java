package model;

public record Task(
        int id,
        String text,
        boolean done,
        java.time.LocalDate deadline
) {}
