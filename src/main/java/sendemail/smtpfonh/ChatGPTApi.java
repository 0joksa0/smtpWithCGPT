/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sendemail.smtpfonh;


import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;
import org.json.JSONObject;
/**
 *
 * @author aleksandar
 */
public class ChatGPTApi {
    
    static private ChatGPTApi instance;
    private String url;
    private String token;
    private String model;
    private HttpsURLConnection connection;
    private String body;
    
    
    private ChatGPTApi(){
        url = "https://api.openai.com/v1/chat/completions";
        token = "your_apikey";
        model = "gpt-4";
                
    }
    
    public static ChatGPTApi getInstance(){
        if (instance == null) {
            instance = new ChatGPTApi();
        }
        return instance;
    }
    
    
    public void createConnection() throws Exception{
    }
    
    public void setPrompt(String prompt){
            body = "{\"model\": \"" + model + "\", \"messages\": [{\"role\": \"user\", \"content\": \"" + prompt + "\"}]}";
    }
    
    public String sendPrompt() throws Exception{
        URL obj = new URL(url);
        connection = (HttpsURLConnection) obj.openConnection();
        connection.setRequestMethod("POST");
           connection.setRequestProperty("Authorization", "Bearer " + token);
           connection.setRequestProperty("Content-Type", "application/json");
           connection.setDoOutput(true);
    
           OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
           writer.write(body);
           writer.flush();
           writer.close();
           
           //response from chatGPT
           
           BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
           String line;

           StringBuffer response = new StringBuffer();

           while ((line = br.readLine()) != null) {
               response.append(line);
           }
           br.close();
//           System.out.println(response);
           JSONObject jo = new JSONObject(response.toString());
           response = null;
           String parsedJSON = jo.getJSONArray("choices").getJSONObject(0).getJSONObject("message").getString("content");
//           System.out.println(parsedJSON);
           return parsedJSON;
    }

    private String textFromJSON(StringBuffer response) {
        int start = response.indexOf("content")+ 11;

       int end = response.indexOf("\"", start);

       return response.substring(start, end);    
    }
    
    
}
