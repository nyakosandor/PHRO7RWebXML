package xpathphro7r;

import org.w3c.dom.*;
import javax.xml.parsers.*;
import javax.xml.xpath.*;
import java.io.File;
import org.xml.sax.SAXException;

public class xPathQueryPHRO7R {

    public static void main(String[] args) {
        try {
            File xmlFile = new File("studentPHRO7R.xml");

            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(xmlFile);
            document.getDocumentElement().normalize();


            XPath xPath = XPathFactory.newInstance().newXPath();

            String query1 = "/class/student";
            NodeList result1 = (NodeList) xPath.evaluate(query1, document, XPathConstants.NODESET);
            System.out.println("1) /class/student");
            printStudentNodeList(result1);

            String query2 = "/class/student[@id='02']";
            NodeList result2 = (NodeList) xPath.evaluate(query2, document, XPathConstants.NODESET);
            System.out.println("\n2) /class/student[@id='02']");
            printStudentNodeList(result2);

            String query3 = "//student";
            NodeList result3 = (NodeList) xPath.evaluate(query3, document, XPathConstants.NODESET);
            System.out.println("\n3) //student");
            printStudentNodeList(result3);

            String query4 = "/class/student[2]";
            NodeList result4 = (NodeList) xPath.evaluate(query4, document, XPathConstants.NODESET);
            System.out.println("\n4) /class/student[2]");
            printStudentNodeList(result4);

            String query5 = "/class/student[last()]";
            NodeList result5 = (NodeList) xPath.evaluate(query5, document, XPathConstants.NODESET);
            System.out.println("\n5) /class/student[last()]");
            printStudentNodeList(result5);

            String query6 = "/class/student[last()-1]";
            NodeList result6 = (NodeList) xPath.evaluate(query6, document, XPathConstants.NODESET);
            System.out.println("\n6) /class/student[last()-1]");
            printStudentNodeList(result6);

            String query7 = "/class/student[position()<=2]";
            NodeList result7 = (NodeList) xPath.evaluate(query7, document, XPathConstants.NODESET);
            System.out.println("\n7) /class/student[position()<=2]");
            printStudentNodeList(result7);

            String query8 = "/class/*";
            NodeList result8 = (NodeList) xPath.evaluate(query8, document, XPathConstants.NODESET);
            System.out.println("\n8) /class/*");
            printGenericNodeList(result8);

            String query9 = "//student[@*]";
            NodeList result9 = (NodeList) xPath.evaluate(query9, document, XPathConstants.NODESET);
            System.out.println("\n9) //student[@*]");
            printStudentNodeList(result9);

            String query10 = "//*";
            NodeList result10 = (NodeList) xPath.evaluate(query10, document, XPathConstants.NODESET);
            System.out.println("\n10) //*");
            printGenericNodeList(result10);

            String query11 = "/class/student[kor > 20]";
            NodeList result11 = (NodeList) xPath.evaluate(query11, document, XPathConstants.NODESET);
            System.out.println("\n11) /class/student[kor > 20]");
            printStudentNodeList(result11);

            String query12 = "/class/student/keresztnev | /class/student/vezeteknev";
            NodeList result12 = (NodeList) xPath.evaluate(query12, document, XPathConstants.NODESET);
            System.out.println("\n12) /class/student/keresztnev | /class/student/vezeteknev");
            for (int i = 0; i < result12.getLength(); i++) {
                Node node = result12.item(i);
                System.out.println(node.getNodeName() + ": " + node.getTextContent().trim());
            }

        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (java.io.IOException e) {
            e.printStackTrace();
        } catch (XPathExpressionException e) {
            e.printStackTrace();
        }
    }

    private static void printStudentNodeList(NodeList nodeList) {
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);

            if (node.getNodeType() == Node.ELEMENT_NODE && node.getNodeName().equals("student")) {
                Element element = (Element) node;

                System.out.println("Student ID   : " + element.getAttribute("id"));
                System.out.println("  First name : "
                        + element.getElementsByTagName("keresztnev").item(0).getTextContent());
                System.out.println("  Last name  : "
                        + element.getElementsByTagName("vezeteknev").item(0).getTextContent());
                System.out.println("  Nickname   : "
                        + element.getElementsByTagName("becenev").item(0).getTextContent());
                System.out.println("  Age        : "
                        + element.getElementsByTagName("kor").item(0).getTextContent());
                System.out.println();
            }
        }
    }

    private static void printGenericNodeList(NodeList nodeList) {
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;
                System.out.print("Element: " + element.getNodeName());

                if (element.hasAttributes()) {
                    System.out.print(" [");
                    NamedNodeMap attrs = element.getAttributes();
                    for (int j = 0; j < attrs.getLength(); j++) {
                        Node attr = attrs.item(j);
                        System.out.print(attr.getNodeName() + "=" + attr.getNodeValue());
                        if (j < attrs.getLength() - 1) {
                            System.out.print(", ");
                        }
                    }
                    System.out.print("]");
                }

                String text = element.getTextContent().trim();
                if (!text.isEmpty() && !text.contains("\n")) {
                    System.out.print(" -> \"" + text + "\"");
                }
                System.out.println();
            }
        }
    }
}
