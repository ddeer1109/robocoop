package org.masteukodeu.robocoop.model;

import java.math.BigDecimal;

public class Category {
    private final String id;
    private final String name;
    private final boolean hidden;
    private BigDecimal blockedPeriod;

    public Category(String id, String name, boolean hidden, BigDecimal blockedPeriod) {
        this.id = id;
        this.name = name;
        this.hidden = hidden;
        this.blockedPeriod = blockedPeriod;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public boolean isHidden() {
        return hidden;
    }

    public BigDecimal getBlockedPeriod() {
        return blockedPeriod;
    }
}
