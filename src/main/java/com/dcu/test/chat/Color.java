package com.dcu.test.chat;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Color {

    @Id
    private String username;
    private String color;

    public Color() {}

    public Color(String username, String color) {
        this.username = username;
        this.color = color;
    }

    public String getUsername() {
        return username;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
