package org.example;

import java.io.Serializable;

public class UserRegisteredEvent implements Serializable {
    private String name;
    private String email;

    public UserRegisteredEvent() {} // default constructor for Jackson

    public UserRegisteredEvent(String name, String email) {
        this.name = name;
        this.email = email;
    }

    // getters and setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    @Override
    public String toString() {
        return "UserRegisteredEvent{name='" + name + "', email='" + email + "'}";
    }
}
