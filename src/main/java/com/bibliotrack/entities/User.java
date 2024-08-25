package com.bibliotrack.entities;

import com.bibliotrack.enums.Role;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {
    private int id;
    private String name;
    private String email;
    private String password;
    private Role role;

    public User(int id, String name, String email, String password, Role role) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public User() {
    }
}
