package hu.domparse.phro7r;

import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.io.File;

public class PHRO7RDomQuery {

    private static Document document;

    public static void main(String[] args) {
        try {
            // Dokumentum betöltése
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            document = builder.parse(new File("PHRO7R_XML.xml"));
            document.getDocumentElement().normalize();

            System.out.println("PHRO7R DOM Query - Lekérdezések");
            System.out.println("=".repeat(40));

            // 1. lekérdezés: Összes raktár neve
            System.out.println("\n1. Raktárak listája:");
            NodeList raktarak = document.getElementsByTagName("Raktar");
            for (int i = 0; i < raktarak.getLength(); i++) {
                Element r = (Element) raktarak.item(i);
                System.out.println("   - " + r.getElementsByTagName("Nev").item(0).getTextContent());
            }

            // 2. lekérdezés: Termék keresése ID alapján
            System.out.println("\n2. T001 termék adatai:");
            NodeList termekek = document.getElementsByTagName("Termek");
            for (int i = 0; i < termekek.getLength(); i++) {
                Element t = (Element) termekek.item(i);
                if (t.getAttribute("Termekid").equals("T001")) {
                    System.out.println("   Név: " + t.getElementsByTagName("Nev").item(0).getTextContent());
                    System.out.println("   Ár: " + t.getElementsByTagName("Egysegar").item(0).getTextContent() + " Ft");
                }
            }

            // 3. lekérdezés: 3000 Ft feletti termékek
            System.out.println("\n3. Drága termékek (>3000 Ft):");
            for (int i = 0; i < termekek.getLength(); i++) {
                Element t = (Element) termekek.item(i);
                int ar = Integer.parseInt(t.getElementsByTagName("Egysegar").item(0).getTextContent());
                if (ar > 3000) {
                    System.out.println("   - " + t.getElementsByTagName("Nev").item(0).getTextContent() + " (" + ar + " Ft)");
                }
            }

            // 4. lekérdezés: Budapesti raktárak
            System.out.println("\n4. Budapesti raktárak:");
            for (int i = 0; i < raktarak.getLength(); i++) {
                Element r = (Element) raktarak.item(i);
                Element cim = (Element) r.getElementsByTagName("Cim").item(0);
                if (cim.getElementsByTagName("Varos").item(0).getTextContent().equals("Budapest")) {
                    System.out.println("   - " + r.getElementsByTagName("Nev").item(0).getTextContent());
                }
            }

            // 5. lekérdezés: Raktárvezetők
            System.out.println("\n5. Raktárvezetők:");
            NodeList dolgozok = document.getElementsByTagName("Dolgozo");
            for (int i = 0; i < dolgozok.getLength(); i++) {
                Element d = (Element) dolgozok.item(i);
                if (d.getElementsByTagName("Beosztas").item(0).getTextContent().equals("Raktárvezető")) {
                    System.out.println("   - " + d.getElementsByTagName("Nev").item(0).getTextContent());
                }
            }

            // 6. lekérdezés: Készlet összesítés
            System.out.println("\n6. Készlet raktáranként:");
            NodeList kapcsolatok = document.getElementsByTagName("RaktarTermek");
            for (int i = 0; i < raktarak.getLength(); i++) {
                Element r = (Element) raktarak.item(i);
                String rid = r.getAttribute("Raktarid");
                int ossz = 0;
                for (int j = 0; j < kapcsolatok.getLength(); j++) {
                    Element k = (Element) kapcsolatok.item(j);
                    if (k.getAttribute("r_rb").equals(rid)) {
                        ossz += Integer.parseInt(k.getElementsByTagName("KeszletDb").item(0).getTextContent());
                    }
                }
                System.out.println("   " + r.getElementsByTagName("Nev").item(0).getTextContent() + ": " + ossz + " db");
            }

            // 7. lekérdezés: Szállító cégek
            System.out.println("\n7. Szállító cégek:");
            NodeList szallitok = document.getElementsByTagName("SzallitoCeg");
            for (int i = 0; i < szallitok.getLength(); i++) {
                Element s = (Element) szallitok.item(i);
                System.out.println("   - " + s.getElementsByTagName("Nev").item(0).getTextContent() + 
                    " [" + s.getAttribute("sz_k") + "]");
            }

            // 8. lekérdezés: Termék keresése szóra
            System.out.println("\n8. 'csapágy' szót tartalmazó termékek:");
            for (int i = 0; i < termekek.getLength(); i++) {
                Element t = (Element) termekek.item(i);
                String nev = t.getElementsByTagName("Nev").item(0).getTextContent();
                if (nev.toLowerCase().contains("csapágy")) {
                    System.out.println("   - " + nev);
                }
            }

            System.out.println("\n" + "=".repeat(40));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
