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
    private String role = "";

    public User(int id, String name, String email, String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;

    }
    public User() {
    }

}
