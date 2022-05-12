package com.example.rf_project;

import com.google.firebase.firestore.auth.User;

public class UserInfo {

    private String name;
    private String workField;
    private String id;
    private String hours;

    public UserInfo(String n, String w, String _id, String h){
        name = n;
        workField = w;
        id = _id;
        hours = h;
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWorkField() {
        return workField;
    }

    public  String getId(){return id;}

    public String getHours(){return hours;}

    public void setWorkField(String workField) {
        this.workField = workField;
    }


    @Override
    public String toString() {
        return name;
    }
}
