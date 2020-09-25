package com.henrichs.codenames;

public class Association {
    private String from;
    private String to;
    private double strength;

    public Association(String from, String to, double strength) {
        this.from = from;
        this.to = to;
        this.strength = strength;
    }
}
