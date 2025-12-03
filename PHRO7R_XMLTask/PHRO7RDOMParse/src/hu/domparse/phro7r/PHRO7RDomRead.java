package hu.domparse.phro7r;

import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;

public class PHRO7RDomRead {

    private static Document document;
    private static StringBuilder kimenet = new StringBuilder();

    public static void main(String[] args) {
        try {
            // Dokumentum betöltése
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            document = builder.parse(new File("PHRO7R_XML.xml"));
            document.getDocumentElement().normalize();

            kiir("=".repeat(50));
            kiir("PHRO7R DOM Read - XML Feldolgozás");
            kiir("=".repeat(50));
            kiir("Gyökérelem: " + document.getDocumentElement().getNodeName());
            kiir("");

            // Beszállítók
            kiir("--- BESZÁLLÍTÓK ---");
            NodeList beszallitoLista = document.getElementsByTagName("Beszallito");
            for (int i = 0; i < beszallitoLista.getLength(); i++) {
                if (beszallitoLista.item(i).getNodeType() == Node.ELEMENT_NODE) {
                    Element elem = (Element) beszallitoLista.item(i);
                    kiir((i+1) + ". Cégnév: " + getErtek(elem, "Cegnev"));
                    kiir("   Adószám: " + getErtek(elem, "Adoszam"));
                    NodeList elerhetosegek = elem.getElementsByTagName("Elerhetoseg");
                    for (int j = 0; j < elerhetosegek.getLength(); j++) {
                        kiir("   Elérhetőség: " + elerhetosegek.item(j).getTextContent());
                    }
                }
            }
            kiir("");

            // Szállító cégek
            kiir("--- SZÁLLÍTÓ CÉGEK ---");
            NodeList szallitoCegLista = document.getElementsByTagName("SzallitoCeg");
            for (int i = 0; i < szallitoCegLista.getLength(); i++) {
                if (szallitoCegLista.item(i).getNodeType() == Node.ELEMENT_NODE) {
                    Element elem = (Element) szallitoCegLista.item(i);
                    kiir((i+1) + ". ID: " + elem.getAttribute("sz_k"));
                    kiir("   Név: " + getErtek(elem, "Nev"));
                    kiir("   Adószám: " + getErtek(elem, "Adoszam"));
                }
            }
            kiir("");

            // Dolgozók
            kiir("--- DOLGOZÓK ---");
            NodeList dolgozoLista = document.getElementsByTagName("Dolgozo");
            for (int i = 0; i < dolgozoLista.getLength(); i++) {
                if (dolgozoLista.item(i).getNodeType() == Node.ELEMENT_NODE) {
                    Element elem = (Element) dolgozoLista.item(i);
                    kiir((i+1) + ". ID: " + elem.getAttribute("Dolgozoid"));
                    kiir("   Név: " + getErtek(elem, "Nev"));
                    kiir("   Beosztás: " + getErtek(elem, "Beosztas"));
                    kiir("   Email: " + getErtek(elem, "Email"));
                    kiir("   Telefon: " + getErtek(elem, "Telefonszam"));
                }
            }
            kiir("");

            // Raktárak
            kiir("--- RAKTÁRAK ---");
            NodeList raktarLista = document.getElementsByTagName("Raktar");
            for (int i = 0; i < raktarLista.getLength(); i++) {
                if (raktarLista.item(i).getNodeType() == Node.ELEMENT_NODE) {
                    Element elem = (Element) raktarLista.item(i);
                    kiir((i+1) + ". ID: " + elem.getAttribute("Raktarid"));
                    kiir("   Név: " + getErtek(elem, "Nev"));
                    kiir("   Típus: " + getErtek(elem, "Tipus"));
                    Element cim = (Element) elem.getElementsByTagName("Cim").item(0);
                    kiir("   Cím: " + getErtek(cim, "Iranyitoszam") + " " + 
                         getErtek(cim, "Varos") + ", " + getErtek(cim, "Utca") + " " + 
                         getErtek(cim, "Hazszam"));
                }
            }
            kiir("");

            // Termékek
            kiir("--- TERMÉKEK ---");
            NodeList termekLista = document.getElementsByTagName("Termek");
            for (int i = 0; i < termekLista.getLength(); i++) {
                if (termekLista.item(i).getNodeType() == Node.ELEMENT_NODE) {
                    Element elem = (Element) termekLista.item(i);
                    kiir((i+1) + ". ID: " + elem.getAttribute("Termekid"));
                    kiir("   Név: " + getErtek(elem, "Nev"));
                    kiir("   Ár: " + getErtek(elem, "Egysegar") + " Ft");
                    Element meret = (Element) elem.getElementsByTagName("Meret").item(0);
                    kiir("   Méret: " + getErtek(meret, "Hossz") + "x" + 
                         getErtek(meret, "Szelesseg") + "x" + getErtek(meret, "Magassag") + " mm");
                }
            }
            kiir("");

            // Raktár-Termék kapcsolatok
            kiir("--- KÉSZLET ---");
            NodeList kapcsolatLista = document.getElementsByTagName("RaktarTermek");
            for (int i = 0; i < kapcsolatLista.getLength(); i++) {
                if (kapcsolatLista.item(i).getNodeType() == Node.ELEMENT_NODE) {
                    Element elem = (Element) kapcsolatLista.item(i);
                    kiir((i+1) + ". Raktár: " + elem.getAttribute("r_rb") + 
                         " | Termék: " + elem.getAttribute("t_rb") + 
                         " | Készlet: " + getErtek(elem, "KeszletDb") + " db");
                }
            }

            kiir("");
            kiir("=".repeat(50));

            // Mentés fájlba
            mentesFajlba("PHRO7R_DomRead_Output.txt");
            System.out.println("Kimenet mentve: PHRO7R_DomRead_Output.txt");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Elem szöveges értékének lekérése
    private static String getErtek(Element szulo, String gyermekNev) {
        NodeList lista = szulo.getElementsByTagName(gyermekNev);
        return lista.getLength() > 0 ? lista.item(0).getTextContent() : "";
    }

    // Kiírás konzolra és pufferbe
    private static void kiir(String szoveg) {
        System.out.println(szoveg);
        kimenet.append(szoveg).append("\n");
    }

    // Mentés fájlba
    private static void mentesFajlba(String fajlnev) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(fajlnev))) {
            writer.print(kimenet.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
