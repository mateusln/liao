/**
 * PUC Minas - ICEI
 * Curso de Ciencia da Computacao
 * Disciplina: Compiladores (1-2017)
 * Trabalho Pratico
 * Liao - Compilador para a Linguagem Imperativa Simplificada 'L'
 * Parte 1 - Analisador Lexico e Analisador Sintatico
 * @author Ana Cristina Pereira Teixeira
 * @author Jordan Lyon Ramos Rodrigues Duarte
 * @author Mateus Loures do Nascimento
 */
package liao;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 *
 * @author mateus
 */
public class Liao {

    
    public static void main(String[] args) throws FileNotFoundException, IOException {
        // TODO code application logic here
            
        String fonte= args[0]; //arquivo fonte 
        
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
            AnalisadorLexico.analisar(leitor);
        
        
        //System.out.println(leitor.readLine());
        
 
    }
}
