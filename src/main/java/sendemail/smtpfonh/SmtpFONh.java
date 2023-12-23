/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package sendemail.smtpfonh;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author aleksandar
 */
public class SmtpFONh {

    public static void main(String[] args) {

        List<String> mails = new ArrayList<>();

        try {
            mails = readMailsFromFile("/home/aleksandar/NetBeansProjects/smtpFONh/src/main/java/sendemail/smtpfonh/Mailovi.txt");
            ChatGPTApi chatGpt = ChatGPTApi.getInstance();
            chatGpt.createConnection();
            int i = 1;
            for (String mail : mails) {
                int indx = mail.indexOf('.');
                String surname = mail.substring(0, indx);
                System.out.println(mail + " -> " + surname + "no: " + i);
                String prompt = "Na Srpskom napisi. Par recenica maila zahvalnosti  za osobu cije ime je: " + surname + " i  koja je  odlicili da posalju prijavu za organizacioni tim predsotjeceg projekta FON hakaton. Dodaj da ce rezultate dobiti uskoro i molim te da napises na kraju Srdacan pozdrav Aleksandar Joksimovic. Osoba koja salje ovaj mail je muskog pola";
                chatGpt.setPrompt(prompt);
                String text = chatGpt.sendPrompt();
                text = text.concat("\n\nSent from my buddy chatgpt😂");
                System.out.println(text);
                SendMail sm = new SendMail("*******");
                sm.sendMail( mail, "Hvala na prijavi za FON hakaton", text);
                i++;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static List<String> readMailsFromFile(String pathname) throws FileNotFoundException {
        List<String> list = new ArrayList<>();

        File myObj = new File(pathname);
        Scanner myReader = new Scanner(myObj);
        while (myReader.hasNextLine()) {
            String data = myReader.nextLine();
//            System.out.println(data);
            list.add(data);
        }
        return list;
    }
}
