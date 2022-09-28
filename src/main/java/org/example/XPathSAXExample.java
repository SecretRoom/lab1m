package org.example;

import net.sf.saxon.Configuration;
import net.sf.saxon.lib.NamespaceConstant;
import net.sf.saxon.om.NodeInfo;
import net.sf.saxon.om.TreeInfo;
import net.sf.saxon.xpath.XPathFactoryImpl;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.sax.SAXSource;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;
import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Class XPathSAXExample - Parses the Inventory.xml file and uses
 * the JAXP XPath API to evaluate XPath expressions.
 */

public class XPathSAXExample {


    public static void main (String args[]) throws Exception {
        XPathSAXExample xpsexample = new XPathSAXExample();
        xpsexample.runApp("./data/data.xml");
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


        XPathExpression findSerialNos =
                xpExpression.compile(".//flight[distance=7000]/time/departure/hours/text()*10");

//                xpExpression.compile("//flight/hours");

        ArrayList<NodeInfo> resultNodeList = (ArrayList) findSerialNos.evaluate(treeInfo, XPathConstants.NODESET);
        Document newXmlDocument = DocumentBuilderFactory.newInstance()
                .newDocumentBuilder().newDocument();

        if (resultNodeList != null) {
            System.out.println(resultNodeList.size());
            for (NodeInfo cNode: resultNodeList) {
//                newXmlDocument.importNode(cNode.node,true); cNode.getTreeInfo().getUserData("flight")
                Class<? extends NodeInfo> name = cNode.getClass();
                System.out.println(cNode);

            }
//            for (int i = 0; i < 1; i++) {
//                NodeInfo cNode = (NodeInfo) resultNodeList.get(i);
//            }
        }
//        MySax handler = new MySax();
//        SAXParserFactory factory = SAXParserFactory.newInstance();
//        SAXParser saxParser = factory.newSAXParser();
//
//        saxParser.parse(resultNodeList, handler);
//        handler.getSchedule();
//

        // Finish when the user enters "."
        System.out.println("Finished.");
    }


    // Helper method to pretty up the output
    public static void outputSeparator() {
        System.out.println("=+=+=+=+=+=+=+=+");
    }

}
