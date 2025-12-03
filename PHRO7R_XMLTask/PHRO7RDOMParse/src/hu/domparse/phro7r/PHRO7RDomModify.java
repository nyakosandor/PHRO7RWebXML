package hu.domparse.phro7r;

import org.w3c.dom.*;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;

public class PHRO7RDomModify {

    private static Document document;

    public static void main(String[] args) {
        try {
            // Dokumentum betöltése
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            document = builder.parse(new File("PHRO7R_XML.xml"));
            document.getDocumentElement().normalize();

            System.out.println("PHRO7R DOM Modify - Módosítások");
            System.out.println("=".repeat(40));

            // 1. módosítás: Új termék hozzáadása
            System.out.println("\n1. Új termék hozzáadása:");
            ujTermek("T003", "Raklapmozgató", "125000", "1200", "800", "200");

            // 2. módosítás: Termék árának módosítása
            System.out.println("\n2. T001 ár módosítása:");
            termekArModositas("T001", "5500");

            // 3. módosítás: Raktár attribútum módosítása
            System.out.println("\n3. R001 felelős módosítása:");
            raktarAttributum("R001", "d_rb", "D002");

            // 4. módosítás: Kapcsolat törlése
            System.out.println("\n4. R002-T002 kapcsolat törlése:");
            kapcsolatTorles("R002", "T002");

            // 5. módosítás: Cím módosítása
            System.out.println("\n5. R002 cím módosítása:");
            cimModositas("R002", "2040", "Budaörs", "Logisztikai park", "8");

            // 6. módosítás: Új elérhetőség
            System.out.println("\n6. Új elérhetőség hozzáadása:");
            ujElerhetoseg("TechParts Kft.", "+36-70-999-8888");

            // 7. módosítás: Új dolgozó
            System.out.println("\n7. Új dolgozó hozzáadása:");
            ujDolgozo("D003", "Molnár Gábor", "Műszakvezető", "molnar@raktar.hu", "+36-70-123-4567");

            // 8. módosítás: Készlet módosítása
            System.out.println("\n8. Készlet módosítása:");
            keszletModositas("R001", "T001", "250");

            // Mentés
            System.out.println("\n" + "=".repeat(40));
            mentes("PHRO7R_XML_Modified.xml");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Új termék
    private static void ujTermek(String id, String nev, String ar, String h, String sz, String m) {
        Element gyoker = document.getDocumentElement();
        Element ujTermek = document.createElement("Termek");
        ujTermek.setAttribute("Termekid", id);

        ujTermek.appendChild(createElem("Nev", nev));

        Element meret = document.createElement("Meret");
        meret.appendChild(createElem("Hossz", h));
        meret.appendChild(createElem("Szelesseg", sz));
        meret.appendChild(createElem("Magassag", m));
        ujTermek.appendChild(meret);

        ujTermek.appendChild(createElem("Egysegar", ar));

        NodeList termekek = document.getElementsByTagName("Termek");
        Node utolso = termekek.item(termekek.getLength() - 1);
        gyoker.insertBefore(ujTermek, utolso.getNextSibling());

        System.out.println("   ✓ " + nev + " [" + id + "] hozzáadva");
    }

    // Ár módosítás
    private static void termekArModositas(String id, String ujAr) {
        NodeList termekek = document.getElementsByTagName("Termek");
        for (int i = 0; i < termekek.getLength(); i++) {
            Element t = (Element) termekek.item(i);
            if (t.getAttribute("Termekid").equals(id)) {
                Element ar = (Element) t.getElementsByTagName("Egysegar").item(0);
                String regi = ar.getTextContent();
                ar.setTextContent(ujAr);
                System.out.println("   ✓ " + regi + " -> " + ujAr + " Ft");
                return;
            }
        }
    }

    // Attribútum módosítás
    private static void raktarAttributum(String raktarId, String attr, String ertek) {
        NodeList raktarak = document.getElementsByTagName("Raktar");
        for (int i = 0; i < raktarak.getLength(); i++) {
            Element r = (Element) raktarak.item(i);
            if (r.getAttribute("Raktarid").equals(raktarId)) {
                String regi = r.getAttribute(attr);
                r.setAttribute(attr, ertek);
                System.out.println("   ✓ " + attr + ": " + regi + " -> " + ertek);
                return;
            }
        }
    }

    // Kapcsolat törlés
    private static void kapcsolatTorles(String raktarId, String termekId) {
        NodeList kapcsolatok = document.getElementsByTagName("RaktarTermek");
        for (int i = 0; i < kapcsolatok.getLength(); i++) {
            Element k = (Element) kapcsolatok.item(i);
            if (k.getAttribute("r_rb").equals(raktarId) && k.getAttribute("t_rb").equals(termekId)) {
                k.getParentNode().removeChild(k);
                System.out.println("   ✓ Törölve");
                return;
            }
        }
    }

    // Cím módosítás
    private static void cimModositas(String raktarId, String ir, String varos, String utca, String hsz) {
        NodeList raktarak = document.getElementsByTagName("Raktar");
        for (int i = 0; i < raktarak.getLength(); i++) {
            Element r = (Element) raktarak.item(i);
            if (r.getAttribute("Raktarid").equals(raktarId)) {
                Element cim = (Element) r.getElementsByTagName("Cim").item(0);
                cim.getElementsByTagName("Iranyitoszam").item(0).setTextContent(ir);
                cim.getElementsByTagName("Varos").item(0).setTextContent(varos);
                cim.getElementsByTagName("Utca").item(0).setTextContent(utca);
                cim.getElementsByTagName("Hazszam").item(0).setTextContent(hsz);
                System.out.println("   ✓ " + ir + " " + varos + ", " + utca + " " + hsz);
                return;
            }
        }
    }

    // Új elérhetőség
    private static void ujElerhetoseg(String cegnev, String elerhetoseg) {
        NodeList beszallitok = document.getElementsByTagName("Beszallito");
        for (int i = 0; i < beszallitok.getLength(); i++) {
            Element b = (Element) beszallitok.item(i);
            if (b.getElementsByTagName("Cegnev").item(0).getTextContent().equals(cegnev)) {
                b.appendChild(createElem("Elerhetoseg", elerhetoseg));
                System.out.println("   ✓ " + elerhetoseg + " hozzáadva");
                return;
            }
        }
    }

    // Új dolgozó
    private static void ujDolgozo(String id, String nev, String beosztas, String email, String tel) {
        Element gyoker = document.getDocumentElement();
        Element ujDolgozo = document.createElement("Dolgozo");
        ujDolgozo.setAttribute("Dolgozoid", id);
        ujDolgozo.appendChild(createElem("Nev", nev));
        ujDolgozo.appendChild(createElem("Beosztas", beosztas));
        ujDolgozo.appendChild(createElem("Email", email));
        ujDolgozo.appendChild(createElem("Telefonszam", tel));

        NodeList dolgozok = document.getElementsByTagName("Dolgozo");
        Node utolso = dolgozok.item(dolgozok.getLength() - 1);
        gyoker.insertBefore(ujDolgozo, utolso.getNextSibling());

        System.out.println("   ✓ " + nev + " [" + id + "] hozzáadva");
    }

    // Készlet módosítás
    private static void keszletModositas(String raktarId, String termekId, String ujKeszlet) {
        NodeList kapcsolatok = document.getElementsByTagName("RaktarTermek");
        for (int i = 0; i < kapcsolatok.getLength(); i++) {
            Element k = (Element) kapcsolatok.item(i);
            if (k.getAttribute("r_rb").equals(raktarId) && k.getAttribute("t_rb").equals(termekId)) {
                Element keszlet = (Element) k.getElementsByTagName("KeszletDb").item(0);
                String regi = keszlet.getTextContent();
                keszlet.setTextContent(ujKeszlet);
                System.out.println("   ✓ " + regi + " -> " + ujKeszlet + " db");
                return;
            }
        }
    }

    // Segéd: elem létrehozása
    private static Element createElem(String nev, String ertek) {
        Element elem = document.createElement(nev);
        elem.setTextContent(ertek);
        return elem;
    }

    // Mentés fájlba
    private static void mentes(String fajlnev) throws TransformerException {
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer transformer = tf.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        transformer.transform(new DOMSource(document), new StreamResult(new File(fajlnev)));
        System.out.println("Mentve: " + fajlnev);
    }
}
