package domphro7r1105;

import org.w3c.dom.*;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;

class DOMModify {

    public static void main(String[] args) {
        try {
            File xmlFile = new File("PHRO7Rhallgato.xml");

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(xmlFile);
            document.getDocumentElement().normalize();

            NodeList studentList = document.getElementsByTagName("hallgato");

            for (int i = 0; i < studentList.getLength(); i++) {
                Node node = studentList.item(i);

                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element studentElement = (Element) node;

                    String idValue = studentElement.getAttribute("id");
                    if ("01".equals(idValue)) {

                        Element firstNameElement =
                                (Element) studentElement.getElementsByTagName("keresztnev").item(0);
                        firstNameElement.setTextContent("Ádám");

                        Element lastNameElement =
                                (Element) studentElement.getElementsByTagName("vezeteknev").item(0);
                        lastNameElement.setTextContent("Kovács");

                        break;
                    }
                }
            }

            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer = tf.newTransformer();

            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");

            DOMSource source = new DOMSource(document);
            StreamResult consoleResult = new StreamResult(System.out);

            transformer.transform(source, consoleResult);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
