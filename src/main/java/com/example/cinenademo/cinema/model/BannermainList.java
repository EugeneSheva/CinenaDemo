package com.example.cinenademo.cinema.model;

import java.util.ArrayList;
import java.util.List;

public class BannermainList {

    private List<Bannermain> bannermainlist = new ArrayList<>();


    public void addBannMain(Bannermain bannermain) {
        this.bannermainlist.add(bannermain);
    }

    public int size() {
        return this.bannermainlist.size();
    }

    public List<Bannermain> getBannermainlist() {
        return bannermainlist;
    }

    public void setBannermainlist(List<Bannermain> bannermainlist) {
        this.bannermainlist = bannermainlist;
    }

    @Override
    public String toString() {
        return "BannermainList " + bannermainlist.toString() +
                '.';
    }
}
