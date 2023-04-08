package com.example.socksapp.models;

public enum Color {
    BLACK("Black"),
    WHITE("White"),
    GREY("Grey"),
    RED("Red"),
    BLUE("Blue");

    private final String text;

    Color(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
