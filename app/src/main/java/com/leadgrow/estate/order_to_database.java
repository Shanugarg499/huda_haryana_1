package com.leadgrow.estate;

import java.io.Serializable;

public class order_to_database implements Serializable {
        String Name, location, R_C, subtype, figures, Number, Email, address, event, time, date, key;
        boolean pin;
        public order_to_database(){}

    public order_to_database(String name, String location, String r_C, String subtype, String figures, String number, String email, String address, String event, String time, String date, String key, boolean pin) {
        Name = name;
        this.location = location;
        R_C = r_C;
        this.subtype = subtype;
        this.figures = figures;
        Number = number;
        Email = email;
        this.address = address;
        this.event = event;
        this.time = time;
        this.date = date;
        this.key = key;
        this.pin = pin;
    }

    public boolean isPin() {
        return pin;
    }

    public void setPin(boolean pin) {
        this.pin = pin;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getR_C() {
        return R_C;
    }

    public void setR_C(String r_C) {
        R_C = r_C;
    }

    public String getSubtype() {
        return subtype;
    }

    public void setSubtype(String subtype) {
        this.subtype = subtype;
    }

    public String getFigures() {
        return figures;
    }

    public void setFigures(String figures) {
        this.figures = figures;
    }

    public String getNumber() {
        return Number;
    }

    public void setNumber(String number) {
        Number = number;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
