package com.project;

import java.util.Objects;

enum Role {
    USER,
    ADMIN
}

public class User {
    private String userName;
    private String login;
    private String email;
    private String password;
    private Role role;

    public User(String inf) throws Exception {
        String[] words = inf.split(";");
        if (words.length < 4) {
            throw new Exception();
        }
        userName = words[0];
        login = words[1];
        email = words[2];
        password = words[3];
        if (words.length == 4) {
            role = Role.USER;
        } else {
            role = Role.valueOf(words[4].toUpperCase());
        }
    }

    public User() {
        userName = "";
        login = "";
        email = "";
        password = "";
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(userName, user.userName) &&
                Objects.equals(login, user.login) &&
                Objects.equals(email, user.email) &&
                Objects.equals(password, user.password) &&
                role == user.role;
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

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
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
