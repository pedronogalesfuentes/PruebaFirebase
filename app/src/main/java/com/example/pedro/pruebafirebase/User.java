package com.example.pedro.pruebafirebase;

/**
 * Created by pedro on 09/01/2017.
 */

public class User {

        public String date_of_birth;
        public String full_name;

    public User() {
        // ...
        this.date_of_birth = "";
        this.full_name = "";
    };

        public User(String date_of_birth, String full_name) {
            // ...
            this.date_of_birth = date_of_birth;
            this.full_name = full_name;
        };

    public String getFull_name() {
        return full_name;
    }
}

