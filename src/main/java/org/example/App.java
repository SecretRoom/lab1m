package org.example;

import org.example.lab1L.MyDOM;
import org.example.lab1N.MySax;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class App 
{
    public static void main( String[] args ) throws ParserConfigurationException, IOException, SAXException, TransformerException {
//        lab1N();
        lab1L();
    }

    public static void lab1N() {
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            System.out.println("Enter value:");
            Scanner scanner = new Scanner(System.in);
            int value = scanner.nextInt();
            MySax data = new MySax();

            File file = new File("./data/data.xml");
            saxParser.parse(file, data);
            data.filter(value);
            scanner.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void lab1L() throws ParserConfigurationException, IOException, SAXException, TransformerException {
        MyDOM.getData();
        System.out.println("Enter year:");
        Scanner scanner = new Scanner(System.in);
        int year = scanner.nextInt();
        System.out.println("Enter studio:");
        String studio = scanner.next();
        System.out.println(studio);
        MyDOM.findGroup(year, studio);
        scanner.close();
    }
}
