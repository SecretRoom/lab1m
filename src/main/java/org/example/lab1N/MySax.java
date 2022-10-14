package org.example.lab1N;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.stream.Collectors;

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

    public void getSchedule(ArrayList<Flight> data) {
        for (Flight item: data) {
            System.out.println(item+"\n");
        }
    }

    public void filter(int value) throws IOException, SAXException, TransformerException, ParserConfigurationException {
        File file = new File("data/newData.xml");

        if(file.exists()){
            file.delete();
        }
        file.createNewFile();
        DocumentBuilder newDocumentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document doc = newDocumentBuilder.newDocument();
        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        Element root = doc.createElement("schedule");
        doc.appendChild(root);

        schedule.stream()
            .filter(flight1 -> flight1.distance < value)
            .sorted((f1, f2) -> {
                int time1 = f1.time.getArrivalMinutes() - f1.time.getDepartureMinutes();
                int time2 = f2.time.getArrivalMinutes() - f2.time.getDepartureMinutes();
                if(time1 == time2) return 0;
                return time1 < time2 ? 1 : -1;
            }).limit(3).forEach(flight1 -> {
                    Element flight = doc.createElement("flight");
                    Element destination = doc.createElement("destination");
                    destination.setTextContent(flight1.destination);
                    Element distance = doc.createElement("distance");
                    distance.setTextContent(String.valueOf(flight1.distance));
                    Element number = doc.createElement("number");
                    number.setTextContent(String.valueOf(flight1.number));
                    Element time = doc.createElement("time");
                    Element departure = doc.createElement("departure");
                    Element arrival = doc.createElement("arrival");
                    Element hours = doc.createElement("hours");
                    Element minutes = doc.createElement("minutes");
                    Element hours1 = doc.createElement("hours");
                    Element minutes1 = doc.createElement("minutes");
                    hours.setTextContent(String.valueOf(flight1.time.departure.hours));
                    minutes.setTextContent(String.valueOf(flight1.time.departure.minutes));
                    departure.appendChild(hours);
                    departure.appendChild(minutes);
                    time.appendChild(departure);
                    hours1.setTextContent(String.valueOf(flight1.time.arrival.hours));
                    minutes1.setTextContent(String.valueOf(flight1.time.arrival.minutes));
                    arrival.appendChild(hours1);
                    arrival.appendChild(minutes1);
                    time.appendChild(arrival);
                    Element price = doc.createElement("price");
                    Element adult = doc.createElement("adult");
                    adult.setTextContent(String.valueOf(flight1.price.adult));
                    Element child = doc.createElement("child");
                    child.setTextContent(String.valueOf(flight1.price.child));
                    price.appendChild(adult);
                    price.appendChild(child);
                    flight.appendChild(destination);
                    flight.appendChild(distance);
                    flight.appendChild(number);
                    flight.appendChild(price);
                    flight.appendChild(time);

                    root.appendChild(flight);
                });
        Source source = new DOMSource(doc);
        Result result = new StreamResult(file);
        transformer.transform(source, result);
    }
}
