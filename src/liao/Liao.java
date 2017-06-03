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
        
        
	if(fonte.endsWith(".l"))
            leitor = new BufferedReader(new FileReader(fonte));
        else
            leitor = new BufferedReader(new FileReader(fonte+=".l"));
        
        AnalisadorSintatico analisadorSintatico = new AnalisadorSintatico(leitor);

        //#teste
        //AnalisadorLexico analisadorLexico = new AnalisadorLexico();
        
        //for(int i=0 ; i < 500 ; i++) //#teste
          //AnalisadorLexico.analisar(leitor);

    }
}
