package com.bibliotrack.Views;

import com.bibliotrack.entities.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class LoginPage {
    Scanner sc = new Scanner(System.in);
    List<User> Users = new ArrayList<>();

    public boolean Login(int id, String password){
        for(User user: Users){
            if(id == user.getId() && password == user.getPassword()){
                return true;
            }
            else {
                return false;
            }

        }
        return false;
    }
}
