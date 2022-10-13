package org.example.lab1N;

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

    public int getArrivalMinutes() {
        return this.arrival.hours*60+this.arrival.minutes;
    }

    public int getDepartureMinutes() {
        return this.departure.hours*60+this.departure.minutes;
    }

    @Override
    public String toString() {
        return "Time: " +
                "\n\t\tdeparture: " + departure +
                "\n\t\tarrival: " + arrival;
    }
}
