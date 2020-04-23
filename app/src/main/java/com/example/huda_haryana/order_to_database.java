package com.example.huda_haryana;

public class order_to_database {
        String Name, Details, Number;
        public order_to_database(){}

        public order_to_database(String name, String details, String number) {
            Name = name;
            Details = details;
            Number = number;
        }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getDetails() {
            return Details;
        }

        public void setDetails(String details) {
            Details = details;
        }

        public String getNumber() {
            return Number;
        }

        public void setNumber(String number) {
            Number = number;
        }
}
