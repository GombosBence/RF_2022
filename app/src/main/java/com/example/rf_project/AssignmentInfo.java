package com.example.rf_project;

public class AssignmentInfo {

    private String taskId;
    private String workerId;
    private String assignmentState;
    private String workerName;
    private String description;
    private String equipmentName;
    private String assignmentId;
    private String duration;

    public AssignmentInfo(String tId, String wId, String assigState, String name, String desc, String eq, String assignId, String dur){
        taskId = tId;
        workerId = wId;
        assignmentState = assigState;
        workerName = name;
        description = desc;
        equipmentName = eq;
        assignmentId = assignId;
        duration = dur;

    }


    public String getAssignmentState() {
        return assignmentState;
    }

    public String getWorkerId() {
        return workerId;
    }

    public String getTaskId() {
        return taskId;
    }

    public String getWorkerName() {
        return workerName;
    }

    public String getDescription() {
        return description;
    }

    public String getEquipmentName() {
        return equipmentName;
    }

    public String getAssignmentId() {
        return assignmentId;
    }

    public String getDuration() {
        return duration;
    }
}
