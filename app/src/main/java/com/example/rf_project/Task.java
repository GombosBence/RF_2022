package com.example.rf_project;

public class Task {
    String Description, Duration, Equipment, Requirement, Scheduled, Solved;

    public String getDescription() {
        return Description;
    }

    public String getDuration() {
        return Duration;
    }

    public String getEquipment() {
        return Equipment;
    }

    public String getRequirement() {
        return Requirement;
    }

    public String getScheduled() {
        return Scheduled;
    }

    public String getSolved() {
        return Solved;
    }
    public String toString(){
        return this.Description +", "+this.Equipment + ", Solved:" + this.Solved;
    }
}
