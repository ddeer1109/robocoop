package coop.model;

import java.time.LocalDate;

public class Round {
    private final String id, name;
    private final LocalDate finalDate;

    public Round(String id, String name, LocalDate finalDate) {
        this.id = id;
        this.name = name;
        this.finalDate = finalDate;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalDate getFinalDate() {
        return finalDate;
    }
}
