package org.example;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;

public class MySax extends DefaultHandler {
    ArrayList<Flight> schedule = new ArrayList<Flight>();
    Flight flight = new Flight();

    Price price = new Price();

    Time time = new Time();
    String thisElement;
    String thisTime;


    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        thisElement = qName;
        if(qName.equals("departure") || qName.equals("arrival")){
            thisTime = qName;
        }
//        System.out.println(thisElement);
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if(qName.equals("price")) {
            flight.setPrice(price);
            price = new Price();
        }
        if(qName.equals("time")) {
            flight.setTime(time);
            time = new Time();
        }
        if(qName.equals("flight")) {
            schedule.add(flight);
            flight = new Flight();
        }
    }

    @Override
    public void characters(char ch[], int start, int length) throws SAXException {
        try{
            switch (thisElement) {
                case "destination" -> {
                    String s = new String(ch, start, length).trim();
                    if (!s.isEmpty()) {
                        flight.setDestination(s);
                    }
                    break;
                }
                case "distance" -> {
                    String s = new String(ch, start, length);
                    if (isNumeric(s)) flight.setDistance(Integer.parseInt(s));
                    break;
                }
                case "number" -> {
                    String s = new String(ch, start, length);
                    if (isNumeric(s)) flight.setNumber(Integer.parseInt(s));
                    break;
                }
                case "adult" -> {
                    String s = new String(ch, start, length);
                    if (isNumeric(s)) price.setAdult(Integer.parseInt(s));
                    break;
                }
                case "child" -> {
                    String s = new String(ch, start, length);
                    if (isNumeric(s)) price.setChild(Integer.parseInt(s));
                    break;
                }
                case "hours" -> {
                    String s = new String(ch, start, length);
                    if (isNumeric(s)) {
                        if(thisTime.equals("departure")){
                            time.setDeparture(Integer.parseInt(s), "hours");
                        } else {
                            time.setArrival(Integer.parseInt(s), "hours");
                        }
                    }
                    break;
                }
                case "minutes" -> {
                    String s = new String(ch, start, length);
                    if (isNumeric(s)) {
                        if(thisTime.equals("departure")){
                            time.setDeparture(Integer.parseInt(s), "minutes");
                        } else {
                            time.setArrival(Integer.parseInt(s), "minutes");
                        }
                    }
                    break;
                }
                default -> {
//                System.out.println("End Element :" + new String(ch, start, length) + " " + thisElement);
                }
            }
        } catch(NumberFormatException ex) {
            System.out.println(ex);
        }
    }

    private static boolean isNumeric(String str){
        return str != null && str.matches("[0-9.]+");
    }

    public void getSchedule() {
        for (Flight item: schedule) {
            System.out.println(item+"\n");
        }
    }

}
