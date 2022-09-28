package org.example;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;

public class App 
{
    public static void main( String[] args )
    {
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();

            MySax handler = new MySax();

            File file = new File("./data/data.xml");
            saxParser.parse(file, handler);
            handler.getSchedule();
        } catch (Exception e) {
            e.printStackTrace();
        }    }
}
