package org.example;

import net.sf.saxon.Configuration;
import net.sf.saxon.lib.NamespaceConstant;
import net.sf.saxon.om.NodeInfo;
import net.sf.saxon.om.TreeInfo;
import net.sf.saxon.xpath.XPathFactoryImpl;
import org.example.lab1L.MyDOM;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.sax.SAXSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;
import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Class XPathSAXExample - Parses the Inventory.xml file and uses
 * the JAXP XPath API to evaluate XPath expressions.
 */

public class XPathSAXExample {


    public static void main (String args[]) throws Exception {
        XPathSAXExample xpsexample = new XPathSAXExample();
        xpsexample.runApp("./data/dataL.xml");
    }

    /**
     * Run the application
     */

    public void runApp(String filename) throws Exception {

        /////////////////////////////////////////////
        // The following initialization code is specific to Saxon
        // Please refer to SaxonHE documentation for details
        System.setProperty("javax.xml.xpath.XPathFactory:"+
                        NamespaceConstant.OBJECT_MODEL_SAXON,
                "net.sf.saxon.xpath.XPathFactoryImpl");

        XPathFactory xpFactory = XPathFactory.
                newInstance(NamespaceConstant.OBJECT_MODEL_SAXON);
        XPath xpExpression = xpFactory.newXPath();
        System.err.println("Loaded XPath Provider " + xpExpression.getClass().getName());

        // Build the source document.
        InputSource inputSrc = new InputSource(new File(filename).toURL().toString());
        SAXSource saxSrc = new SAXSource(inputSrc);
        Configuration config = ((XPathFactoryImpl) xpFactory).getConfiguration();
        TreeInfo treeInfo = config.buildDocumentTree (saxSrc);

        System.out.println("Enter year:");
        Scanner scanner = new Scanner(System.in);
        int year = scanner.nextInt();
        System.out.println("Enter studio:");
        String studio = scanner.next();
        scanner.close();

        XPathExpression findSerialNos =
                xpExpression.compile(".//album[@year="+year+" and @studio='"+studio+"']");

//                xpExpression.compile("//flight/hours");

        ArrayList<NodeInfo> resultNodeList = (ArrayList) findSerialNos.evaluate(treeInfo, XPathConstants.NODESET);
        Document newXmlDocument = DocumentBuilderFactory.newInstance()
                .newDocumentBuilder().newDocument();
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

        if (resultNodeList != null) {
            System.out.println(resultNodeList.size());
            for (NodeInfo cNode: resultNodeList) {
                Element albumNew = doc.createElement("album");
                albumNew.setAttribute("year", String.valueOf(cNode.getAttributeValue("", "year")));
                albumNew.setAttribute("group",cNode.getAttributeValue("","group"));
                albumNew.setAttribute("title", cNode.getAttributeValue("", "title"));
                albumNew.setAttribute("studio", cNode.getAttributeValue("", "studio"));
                root.appendChild(albumNew);
            }
        }
        Source source = new DOMSource(doc);
        Result result = new StreamResult(file);
        transformer.transform(source, result);
//

        // Finish when the user enters "."
        System.out.println("Finished.");
    }


    // Helper method to pretty up the output
    public static void outputSeparator() {
        System.out.println("=+=+=+=+=+=+=+=+");
    }

}
