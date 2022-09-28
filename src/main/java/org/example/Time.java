package org.example;

public class Time {
    HM departure = new HM();
    HM arrival = new HM();

    public void setArrival(int number, String type) {
        if(type.equals("hours")){
            arrival.setHours(number);
        } else{
            arrival.setMinutes(number);
        }
    }

    public void setDeparture(int number, String type) {
        if(type.equals("hours")){
            departure.setHours(number);
        } else{
            departure.setMinutes(number);
        }
    }

    @Override
    public String toString() {
        return "Time: " +
                "\n\t\tdeparture: " + departure +
                "\n\t\tarrival: " + arrival;
    }
}
