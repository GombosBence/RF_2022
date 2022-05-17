package com.example.rf_project;

public class Task {
    String description, duration, equipment, requirement, scheduled, solved, id;

    public Task(String des, String du, String eq, String req, String sche, String solv, String _id){

        description = des;
        duration = du;
        equipment = eq;
        requirement = req;
        scheduled = sche;
        solved = solv;
        id = _id;

    }
    public Task() {}

    public String getDescription() {
        return description;
    }

    public String getDuration() {
        return duration;
    }

    public String getEquipment() {
        return equipment;
    }

    public String getRequirement() {
        return requirement;
    }

    public String getScheduled() {
        return scheduled;
    }

    public String getSolved() {
        return solved;
    }

    public String getId() {
        return id;
    }

}
