package org.masteukodeu.robocoop.model;

import java.util.Objects;

public class User {

    private final String id;
    private final String username;
    private final String email;
    private final String password;
    private final boolean admin;
    private final String name;
    private final String phone;
    private final boolean drivingLicense;

    public User(String id, String username, String email, String password, boolean admin, String name, String phone, boolean drivingLicense) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.admin = admin;
        this.name = name;
        this.phone = phone;
        this.drivingLicense = drivingLicense;
    }

    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public boolean isAdmin() {
        return admin;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public boolean hasDrivingLicense() {
        return drivingLicense;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
