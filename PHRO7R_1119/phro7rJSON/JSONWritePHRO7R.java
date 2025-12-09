package neptunPHRO7RJSON;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class JSONWritePHRO7R {
    public static void main(String[] args) {
        JSONParser parser = new JSONParser();
        
        try {
            Object obj = parser.parse(new FileReader("../orarendPHRO7R.json"));
            
            JSONObject jsonObject = (JSONObject) obj;
            
            JSONObject orarendObj = (JSONObject) jsonObject.get("PHRO7R_orarend");
            
            JSONArray orak = (JSONArray) orarendObj.get("ora");
            
            System.out.println("Orarend feldolgozas");
            
            JSONObject newJsonObject = new JSONObject();
            JSONObject newOrarendObj = new JSONObject();
            JSONArray newOrak = new JSONArray();
            
            for (int i = 0; i < orak.size(); i++) {
                JSONObject ora = (JSONObject) orak.get(i);
                
                System.out.println("\nOra " + (i + 1));
                System.out.println("Targy: " + ora.get("targy"));
                
                JSONObject idopont = (JSONObject) ora.get("idopont");
                System.out.println("Idopont: " + idopont.get("nap") + " " + 
                                 idopont.get("tol") + "-" + idopont.get("ig"));
                
                System.out.println("Helyszin: " + ora.get("helyszin"));
                System.out.println("Oktato: " + ora.get("oktato"));
                System.out.println("Szak: " + ora.get("szak"));
                
                JSONObject newOra = new JSONObject();
                newOra.put("targy", ora.get("targy"));
                newOra.put("idopont", idopont);
                newOra.put("helyszin", ora.get("helyszin"));
                newOra.put("oktato", ora.get("oktato"));
                newOra.put("szak", ora.get("szak"));
                
                newOrak.add(newOra);
            }
            
            newOrarendObj.put("ora", newOrak);
            newJsonObject.put("PHRO7R_orarend_masolat", newOrarendObj);
            
            try (FileWriter file = new FileWriter("../orarendPHRO7R_1.json")) {
                file.write(newJsonObject.toJSONString());
                file.flush();
                System.out.println("\nJSON fajl kiirva: orarendPHRO7R_1.json");
            }
            
            System.out.println("\nJSON tartalom:");
            System.out.println(newJsonObject.toJSONString());
            
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }
}