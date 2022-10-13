package org.example;

import org.example.lab1N.MySax;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.util.Scanner;

public class App 
{
    public static void main( String[] args )
    {
        lab1N();
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
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
