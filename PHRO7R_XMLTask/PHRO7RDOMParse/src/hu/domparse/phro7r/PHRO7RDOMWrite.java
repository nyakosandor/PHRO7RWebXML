package hu.domparse.phro7r;

import org.w3c.dom.*;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;

public class PHRO7RDomWrite {

    public static void main(String[] args) {
        try {
            System.out.println("PHRO7R DOM Write - XML Létrehozás");
            System.out.println("=".repeat(40));

            // Új dokumentum létrehozása
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.newDocument();

            // Gyökérelem
            Element gyoker = document.createElement("PHRO7R_RaktarkezeloRendszer");
            gyoker.setAttribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
            gyoker.setAttribute("xsi:noNamespaceSchemaLocation", "PHRO7R_XMLSchema.xsd");
            document.appendChild(gyoker);

            // Beszállítók
            gyoker.appendChild(document.createComment(" BESZÁLLÍTÓK "));
            gyoker.appendChild(letrehozBeszallito(document, "Alfa Kft.", "11111111-1-11",
                    new String[]{"+36-1-111-1111", "alfa@email.hu"}));
            gyoker.appendChild(letrehozBeszallito(document, "Béta Zrt.", "22222222-2-22",
                    new String[]{"+36-20-222-2222"}));

            // Szállító cégek
            gyoker.appendChild(document.createComment(" SZÁLLÍTÓ CÉGEK "));
            gyoker.appendChild(letrehozSzallitoCeg(document, "SC001", "Gamma Fuvar", "33333333-3-33"));
            gyoker.appendChild(letrehozSzallitoCeg(document, "SC002", "Delta Log", "44444444-4-44"));

            // Dolgozók
            gyoker.appendChild(document.createComment(" DOLGOZÓK "));
            gyoker.appendChild(letrehozDolgozo(document, "D001", "Szabó Anna", "Raktárvezető",
                    "szabo@ceg.hu", "+36-20-555-1111"));
            gyoker.appendChild(letrehozDolgozo(document, "D002", "Tóth Béla", "Raktáros",
                    "toth@ceg.hu", "+36-30-555-2222"));

            // Raktárak
            gyoker.appendChild(document.createComment(" RAKTÁRAK "));
            gyoker.appendChild(letrehozRaktar(document, "R001", "SC001", "D001",
                    "Központi Raktár", "Főraktár", "1111", "Budapest", "Raktár utca", "1"));
            gyoker.appendChild(letrehozRaktar(document, "R002", "SC002", "D002",
                    "Vidéki Raktár", "Elosztó", "3000", "Hatvan", "Ipari út", "25"));

            // Termékek
            gyoker.appendChild(document.createComment(" TERMÉKEK "));
            gyoker.appendChild(letrehozTermek(document, "T001", "Szerszámkészlet", "15000", "400", "300", "150"));
            gyoker.appendChild(letrehozTermek(document, "T002", "Villanymotor", "85000", "500", "400", "350"));

            // Kapcsolatok
            gyoker.appendChild(document.createComment(" KÉSZLET "));
            gyoker.appendChild(letrehozKapcsolat(document, "R001", "T001", "100"));
            gyoker.appendChild(letrehozKapcsolat(document, "R001", "T002", "50"));
            gyoker.appendChild(letrehozKapcsolat(document, "R002", "T001", "75"));
            gyoker.appendChild(letrehozKapcsolat(document, "R002", "T002", "30"));

            // Mentés
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer = tf.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            transformer.transform(new DOMSource(document), new StreamResult(new File("PHRO7R_XML_Generated.xml")));

            System.out.println("Fájl létrehozva: PHRO7R_XML_Generated.xml");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Element letrehozBeszallito(Document doc, String cegnev, String adoszam, String[] elerhetosegek) {
        Element elem = doc.createElement("Beszallito");
        elem.appendChild(createTextElement(doc, "Cegnev", cegnev));
        elem.appendChild(createTextElement(doc, "Adoszam", adoszam));
        for (String e : elerhetosegek) {
            elem.appendChild(createTextElement(doc, "Elerhetoseg", e));
        }
        return elem;
    }

    private static Element letrehozSzallitoCeg(Document doc, String id, String nev, String adoszam) {
        Element elem = doc.createElement("SzallitoCeg");
        elem.setAttribute("sz_k", id);
        elem.appendChild(createTextElement(doc, "Nev", nev));
        elem.appendChild(createTextElement(doc, "Adoszam", adoszam));
        return elem;
    }

    private static Element letrehozDolgozo(Document doc, String id, String nev, String beosztas, String email, String tel) {
        Element elem = doc.createElement("Dolgozo");
        elem.setAttribute("Dolgozoid", id);
        elem.appendChild(createTextElement(doc, "Nev", nev));
        elem.appendChild(createTextElement(doc, "Beosztas", beosztas));
        elem.appendChild(createTextElement(doc, "Email", email));
        elem.appendChild(createTextElement(doc, "Telefonszam", tel));
        return elem;
    }

    private static Element letrehozRaktar(Document doc, String id, String szk, String drb,
                                          String nev, String tipus, String ir, String varos, String utca, String hsz) {
        Element elem = doc.createElement("Raktar");
        elem.setAttribute("Raktarid", id);
        elem.setAttribute("R_k", szk);
        elem.setAttribute("d_rb", drb);
        elem.appendChild(createTextElement(doc, "Nev", nev));

        Element cim = doc.createElement("Cim");
        cim.appendChild(createTextElement(doc, "Iranyitoszam", ir));
        cim.appendChild(createTextElement(doc, "Varos", varos));
        cim.appendChild(createTextElement(doc, "Utca", utca));
        cim.appendChild(createTextElement(doc, "Hazszam", hsz));
        elem.appendChild(cim);

        elem.appendChild(createTextElement(doc, "Tipus", tipus));
        return elem;
    }

    private static Element letrehozTermek(Document doc, String id, String nev, String ar, String h, String sz, String m) {
        Element elem = doc.createElement("Termek");
        elem.setAttribute("Termekid", id);
        elem.appendChild(createTextElement(doc, "Nev", nev));

        Element meret = doc.createElement("Meret");
        meret.appendChild(createTextElement(doc, "Hossz", h));
        meret.appendChild(createTextElement(doc, "Szelesseg", sz));
        meret.appendChild(createTextElement(doc, "Magassag", m));
        elem.appendChild(meret);

        elem.appendChild(createTextElement(doc, "Egysegar", ar));
        return elem;
    }

    private static Element letrehozKapcsolat(Document doc, String raktarId, String termekId, String keszlet) {
        Element elem = doc.createElement("RaktarTermek");
        elem.setAttribute("r_rb", raktarId);
        elem.setAttribute("t_rb", termekId);
        elem.appendChild(createTextElement(doc, "KeszletDb", keszlet));
        return elem;
    }

    private static Element createTextElement(Document doc, String nev, String ertek) {
        Element elem = doc.createElement(nev);
        elem.setTextContent(ertek);
        return elem;
    }
}
