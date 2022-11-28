package main;

import com.google.gson.Gson;
import preprocessing.preprocesamiento;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class main {
    private static preprocesamiento _preprocesamiento ;
    static HashMap<String, Integer> longDocumento = new HashMap<String, Integer>();
    static HashMap<String, docIDpeso > indiceInvertido = new HashMap<String, docIDpeso>();

    public static void main(String[] args) throws IOException{
        boolean bFinished = false;
        _preprocesamiento = new preprocesamiento();
        do{
            System.out.println("Search: ");
            Scanner out = new Scanner(System.in);
            String sConsulta = out.nextLine();
            //consulta preprocesada
            ArrayList<String> asPreprocesado =  _preprocesamiento.preprocessing(sConsulta);

            System.out.println("¿Quieres realizar otra consulta?S/N: ");
            Scanner out2 = new Scanner(System.in);
            String sRespuesta = out2.nextLine();
            if(sRespuesta.equals("N")) bFinished = true;

        }while(bFinished);
    }

    public void leerLongDocumento() throws IOException {
        String filePath = "C:\\Users\\condo\\Desktop\\ProjectRecInf\\RecInf\\Preprocesamiento\\src\\main\\longitudDocumento.txt";

        String line;
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        while ((line = reader.readLine()) != null)
        {
            String[] parts = line.split(" ", 2);
            if (parts.length >= 2)
            {
                longDocumento.put(parts[0], Integer.parseInt(parts[1]));
            }
        }
    }

    public void leerIndiceInvertido() throws FileNotFoundException {
        Gson gson = new Gson();
        BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\condo\\Desktop\\ProjectRecInf\\RecInf\\Preprocesamiento\\src\\main\\indiceInvertido.json"));
        indiceInvertido = gson.fromJson(br, HashMap.class);
    }
}
