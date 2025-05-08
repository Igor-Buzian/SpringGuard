package com.example.spring.exeption;

import java.util.Date;

public class InfoExeption {
    public int errorNum;
    public  String Name;
    public Date currentTime;

    public InfoExeption(int errorNum, String name) {
        this.errorNum = errorNum;
        Name = name;
        this.currentTime = new Date();
    }
}
