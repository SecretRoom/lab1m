package org.example;

public class Flight {
    String destination;
    int distance;
    int number;
    Price price;
    Time time;

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public void setPrice(Price price) {
        this.price = price;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public int getDistance() {
        return distance;
    }

    public int getNumber() {
        return number;
    }

    public Price getPrice() {
        return price;
    }

    public String getDestination() {
        return destination;
    }

    public Time getTime() {
        return time;
    }

    @Override
    public String toString() {
        return "Flight: " +
                "\n\tdestination = " + destination +
                "\n\tdistance = " + distance +
                "\n\tnumber = " + number +
                "\n\t" + price +
                "\n\t" + time ;
    }
}
