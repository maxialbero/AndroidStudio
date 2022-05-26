package com.classes.utility;

import com.classes.objects.Item;

import java.util.ArrayList;

public class DB {
    String url;
    String username;
    String password;

    // Connection connection; // the DB connection

    public DB(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    public void connect() {
        // Create connection
    }

    public ArrayList<Item> SELECT(String query) {
        ArrayList<Item> res = new ArrayList<>();

        // should be a prepared statement to avoid SQL injections
        // fill the ArrayList with the query result

        return res;
    }

    public void INSERT(String query) {
        // maybe also table name and other parameters are needed
        // execute the insert query
    }

    public void DELETE(int id) {
        // maybe also table name and other parameters are needed
        // delete the item by id
    }

    public void MODIFY(int id) {
        // maybe also table name, new value, fields to be modified and other parameters are needed
        // delete the item by id
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
