package com.example.firebase_practise;

public class firebasedbhandler {
    String name,department,duration,uri;

    public firebasedbhandler(String name, String department, String duration, String uri) {
        this.name = name;
        this.department = department;
        this.duration = duration;
        this.uri = uri;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }
}
