package org.example;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;

public class Main {
    public static void main(String[] args) {
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();

            MySax handler = new MySax();

            File file = new File("./data/data.xml");
            saxParser.parse(file, handler);
            handler.getSchedule();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    private static List<String> getFlights(Document doc, XPath xpath) {
//        List<String> list = new ArrayList<>();
//        try {
//            //создаем объект XPathExpression
//            XPathExpression xPathExpression = xpath.compile(
//                    "/schedule/flight[distance<5000]/number/text()"
//            );
//            // получаем список всех тегов, которые отвечают условию
//            NodeList nodes = (NodeList) xPathExpression.evaluate(doc, XPathConstants.NODESET);
//            // проходим по списку и получаем значение с помощью getNodeValue()
//            for (int i = 0; i < nodes.getLength(); i++)
//                list.add(nodes.item(i).getNodeValue());
//        } catch (XPathExpressionException e) {
//            e.printStackTrace();
//        }
//        return list;
//    }
}

