package org.example.lab1L;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MyDOM {
    private static final ArrayList<Album> albums = new ArrayList<>();

    public static void getData() throws ParserConfigurationException, SAXException, IOException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(new File("data/dataL.xml"));
        NodeList albumElements = document.getDocumentElement().getElementsByTagName("album");
        for (int i = 0; i < albumElements.getLength(); i++) {
            NamedNodeMap attributes = albumElements.item(i).getAttributes();
            Album album = new Album();

            album.setYear(Integer.parseInt(attributes.getNamedItem("year").getNodeValue()));
            album.setTitle(attributes.getNamedItem("title").getNodeValue());
            album.setGroup(attributes.getNamedItem("group").getNodeValue());
            album.setStudio(attributes.getNamedItem("studio").getNodeValue());
            albums.add(album);
        }
    }

    public static void findGroup(int year, String studio) throws IOException, TransformerException, ParserConfigurationException {
        File file = new File("data/newDataL.xml");

        if(file.exists()){
            file.delete();
        }
        file.createNewFile();
        DocumentBuilder newDocumentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document doc = newDocumentBuilder.newDocument();
        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        Element root = doc.createElement("albums");
        doc.appendChild(root);
        albums.stream().filter(album -> year == album.year && studio.equals(album.studio)).forEach(album -> {
            Element albumNew = doc.createElement("album");
            albumNew.setAttribute("year", String.valueOf(album.year));
            albumNew.setAttribute("group", album.group);
            albumNew.setAttribute("title", album.title);
            albumNew.setAttribute("studio", album.studio);
            root.appendChild(albumNew);
        });
        Source source = new DOMSource(doc);
        Result result = new StreamResult(file);
        transformer.transform(source, result);
    }
}
