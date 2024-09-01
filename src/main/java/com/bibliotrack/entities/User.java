package com.bibliotrack.entities;

import com.bibliotrack.enums.Role;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class User {
    private int id;
    private String name;
    private String email;
    private String password;
    private Role role;

    public User(String name, String email, String password,Role role) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;

    }
    public User() {
    }

}
