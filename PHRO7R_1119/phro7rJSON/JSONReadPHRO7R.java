package phro7rJSON;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;

public class JSONReadPHRO7R {
    public static void main(String[] args) {
        JSONParser parser = new JSONParser();
        
        try {
            Object obj = parser.parse(new FileReader("../orarendPHRO7R.json"));
            
            JSONObject jsonObject = (JSONObject) obj;
            
            JSONObject orarendObj = (JSONObject) jsonObject.get("PHRO7R_orarend");
            
            JSONArray orak = (JSONArray) orarendObj.get("ora");
            
            System.out.println("Orarend");
            
            for (int i = 0; i < orak.size(); i++) {
                JSONObject ora = (JSONObject) orak.get(i);
                
                System.out.println("\nOra " + (i + 1));
                System.out.println("Targy: " + ora.get("targy"));
                
                JSONObject idopont = (JSONObject) ora.get("idopont");
                System.out.println("Nap: " + idopont.get("nap"));
                System.out.println("Kezdes: " + idopont.get("tol"));
                System.out.println("Befejezes: " + idopont.get("ig"));
                
                System.out.println("Helyszin: " + ora.get("helyszin"));
                System.out.println("Oktato: " + ora.get("oktato"));
                System.out.println("Szak: " + ora.get("szak"));
            }
            
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }
}