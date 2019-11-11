
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class Class1 {
    static public void main(String[] arg) {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = null;
        Document doc = null;
        Document doc2 = null;
        {
            try {
                db = dbf.newDocumentBuilder();
                doc = db.parse(new File("file.xml"));
                doc2 = db.parse(new File("file1.xml"));
                NodeList nodes = doc.getElementsByTagName("geodata");
                for (int i = 0; i < nodes.getLength(); i++) {
                    Node node = nodes.item(i);
                    doc.renameNode(node, null, "persondata");
                }

                NodeList ndListFirstFile = doc.getElementsByTagName("person");

                Node nodeSalary = doc.importNode(doc2.getElementsByTagName("salary").item(0), true);
                Node nodePension = doc.importNode(doc2.getElementsByTagName("pension").item(0), true);
                Node nodeSalary1 = doc.importNode(doc2.getElementsByTagName("salary").item(1), true);
                Node nodePension1 = doc.importNode(doc2.getElementsByTagName("pension").item(1), true);

                ndListFirstFile.item(0).appendChild(nodeSalary);
                ndListFirstFile.item(0).appendChild(nodePension);
                ndListFirstFile.item(1).appendChild(nodeSalary1);
                ndListFirstFile.item(1).appendChild(nodePension1);

                TransformerFactory tFactory = TransformerFactory.newInstance();
                Transformer transformer = tFactory.newTransformer();
                transformer.setOutputProperty(OutputKeys.INDENT, "yes");

                DOMSource source = new DOMSource(doc);
                StreamResult result = new StreamResult(new StringWriter());
                transformer.transform(source, result);

                Writer output = null;
                output = new BufferedWriter(new FileWriter("persondata.xml"));
                String xmlOutput = result.getWriter().toString();

                output.write(xmlOutput);

                output.close();
                System.out.println("XML Merged");

            } catch (ParserConfigurationException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            } catch (SAXException e2) {
                // TODO Auto-generated catch block
                e2.printStackTrace();
            } catch (IOException e3) {
                // TODO Auto-generated catch block
                e3.printStackTrace();
            } catch (TransformerException e4) {
                // TODO Auto-generated catch block
                e4.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
}