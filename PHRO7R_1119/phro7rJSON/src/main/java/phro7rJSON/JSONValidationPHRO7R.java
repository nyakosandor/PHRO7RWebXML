package phro7rJSON;

import com.networknt.schema.JsonSchema;
import com.networknt.schema.JsonSchemaFactory;
import com.networknt.schema.ValidationMessage;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Set;

public class JSONValidationPHRO7R {
    public static void main(String[] args) {
        System.out.println("JSON validalas");
        
        try {
            InputStream schemaStream = new FileInputStream("../phro7rSchema.json");
            JsonSchemaFactory schemaFactory = JsonSchemaFactory.getInstance();
            JsonSchema schema = schemaFactory.getSchema(schemaStream);
            
            InputStream jsonDataStream = new FileInputStream("../phro7r.json");
            JSONObject jsonData = new JSONObject(new JSONTokener(jsonDataStream));
            
            System.out.println("JSON betoltve");
            System.out.println("Schema betoltve");
            
            ObjectMapper mapper = new ObjectMapper();
            JsonNode jsonNode = mapper.readTree(jsonData.toString());
            
            Set<ValidationMessage> validationMessages = schema.validate(jsonNode);
            
            if (validationMessages.isEmpty()) {
                System.out.println("\nSikeres validalas");
                System.out.println("A JSON rendben van");
                
                validateJsonStructure(jsonData);
                
            } else {
                System.out.println("\nValidalas sikertelen");
                System.out.println("Hibak:");
                for (ValidationMessage message : validationMessages) {
                    System.out.println("  - " + message.getMessage());
                }
            }
            
        } catch (IOException e) {
            System.err.println("Hiba a fajlok olvasasakor:");
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Hiba:");
            e.printStackTrace();
        }
    }
    
    private static void validateJsonStructure(JSONObject jsonData) {
        System.out.println("\nStruktura ellenorzese:");
        
        try {
            if (jsonData.has("PHRO7R_orarend")) {
                System.out.println("Gyoker: PHRO7R_orarend");
                
                JSONObject orarend = jsonData.getJSONObject("PHRO7R_orarend");
                
                if (orarend.has("ora")) {
                    org.json.JSONArray orak = orarend.getJSONArray("ora");
                    System.out.println("Orak szama: " + orak.length());
                    
                    for (int i = 0; i < orak.length(); i++) {
                        JSONObject ora = orak.getJSONObject(i);
                        System.out.println("\nOra " + (i + 1) + ":");
                        
                        String[] requiredFields = {"targy", "idopont", "helyszin", "oktato", "szak"};
                        for (String field : requiredFields) {
                            if (ora.has(field)) {
                                System.out.println("  " + field + ": " + ora.get(field));
                            } else {
                                System.out.println("  Hianyzik: " + field);
                            }
                        }
                        
                        if (ora.has("idopont")) {
                            JSONObject idopont = ora.getJSONObject("idopont");
                            String[] timeFields = {"nap", "tol", "ig"};
                            for (String field : timeFields) {
                                if (idopont.has(field)) {
                                    System.out.println("    " + field + ": " + idopont.get(field));
                                } else {
                                    System.out.println("    Hianyzik: " + field);
                                }
                            }
                        }
                    }
                } else {
                    System.out.println("Hianyzik az 'ora' tomb");
                }
            } else {
                System.out.println("Hianyzik a 'PHRO7R_orarend'");
            }
            
        } catch (Exception e) {
            System.err.println("Hiba az ellenorzes soran:");
            e.printStackTrace();
        }
    }
}
