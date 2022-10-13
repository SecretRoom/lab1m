package org.example.lab1N;

public class HM {
    int hours;
    int minutes;

    public void setHours(int hours) {
        this.hours = hours;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }

    @Override
    public String toString() {
        return  "\n\t\t\thours = " + hours +
                "\n\t\t\tminutes = " + minutes ;
    }
}
