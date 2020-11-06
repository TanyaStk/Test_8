package com.project;

import java.util.Objects;

public class User {
    private String userName;
    private String login;
    private String email;
    private String password;
    private String role;

    public User(String inf) throws Exception {
        String[] words = inf.split(";");
        if (words.length < 5) {
            throw new Exception();
        }
        userName = words[0];
        login = words[1];
        email = words[2];
        password = words[3];
        role = words[4];
    }

    public User() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(login, user.login);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userName, login, email, password, role);
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return userName + ';' +
                login + ';' +
                email + ';' +
                password + ';' +
                role + ';';
    }
}
