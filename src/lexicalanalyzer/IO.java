/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lexicalanalyzer;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Map;
public class IO {
    
    public  Map LoadKeywords(Map<String, String> keywords){
        try{
          FileInputStream fstream = new FileInputStream("Hashmap.txt");
          DataInputStream in = new DataInputStream(fstream);
          BufferedReader br = new BufferedReader(new InputStreamReader(in));
          String strLine;
          while ((strLine = br.readLine()) != null)   {
                String[] tokens = strLine.split(" ");
                keywords.put(tokens[0], tokens[1]);
            }
          in.close();
        }catch (Exception e){
            System.err.println("Error: " + e.getMessage());
        }
        keywords.put(" ", "Space");
        return keywords;
     }
    
    public  ArrayList ReadFile(String filename){
         ArrayList<String> code = new ArrayList<>();
        try{
          FileInputStream fstream = new FileInputStream(filename);
          DataInputStream in = new DataInputStream(fstream);
          BufferedReader br = new BufferedReader(new InputStreamReader(in));
          String strLine;
          while ((strLine = br.readLine()) != null)   {
               code.add(strLine);
            }
          in.close();
        }catch (Exception e){
            
        }
        return code;
     }
    
    
}
