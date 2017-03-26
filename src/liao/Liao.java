/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package liao;

import java.io.BufferedReader;
import java.io.EOFException;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

/**
 *
 * @author mateus
 */
public class Liao {

    
    public static void main(String[] args) throws FileNotFoundException, IOException {
        // TODO code application logic here
            
        String fonte= args[0]; //aruivo fonte 
        
        BufferedReader leitor;
	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));  
	//System.out.println("Digite o nome: ");
	//String fonte = br.readLine();
	if(fonte.endsWith(".l"))
            leitor = new BufferedReader(new FileReader(fonte));
        else
            leitor = new BufferedReader(new FileReader(fonte+=".l"));
        
        
        AnalisadorLexico analisadorLexico = new AnalisadorLexico();
        
        for(int i=0 ; i < 10 ; i++)
            analisadorLexico.analisar(leitor);
        
        
        //System.out.println(leitor.readLine());
        
 
    }
    
    
}
