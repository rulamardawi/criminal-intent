package com.jameskbride.criminalIntent;

import java.util.Date;
import java.util.UUID;

public class Crime {

    private UUID id;
    private String title;
    private Date discoveredOn;
    private boolean solved;

    public Crime() {
        id = UUID.randomUUID();
        discoveredOn = new Date();
    }

    public UUID getId() {
        return this.id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return this.title;
    }

    public Date getDiscoveredOn() {
        return discoveredOn;
    }

    public void setDiscoveredOn(Date discoveredOn) {
        this.discoveredOn = discoveredOn;
    }

    public boolean isSolved() {
        return solved;
    }

    public void setSolved(boolean solved) {
        this.solved = solved;
    }

    @Override
    public String toString() {
        return title;
    }
}
