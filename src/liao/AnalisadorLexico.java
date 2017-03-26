/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package liao;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Arrays;

/**
 *
 * @author mateus
 */
public class AnalisadorLexico {
    static String  lexema= "";
    char c;
    
    
    
    public static final void analisar( BufferedReader leitor) throws IOException {
        
        int estadoAtual=0;
        final int estadoFinal=12;
        char c=' ';
        boolean devolve=false;
        
        while(estadoAtual!= estadoFinal){
            
            if(!devolve){ // pega o proximo char se nao for para devolver o anterior
                c=(char)leitor.read();
            }
            
            devolve=false;
                
            if(c==(char) -1){//#teste
                estadoAtual=estadoFinal;
                return ;
            }
            
            switch(estadoAtual){
                
                
                case 0:
                    if(c==' ' )
                        break; // ignora espaço
                    
                    char [] cadeiadeChar= {'(',')',',','+','-','*',';'};
                    if (Arrays.toString(cadeiadeChar).contains(""+c)){// compara se o caractere esta na cadeia de char
                        lexema+=c;
                        estadoAtual=estadoFinal;
                    }
                    if(c=='<' || c== '>' || c== '=' ){
                        lexema+=c;
                        estadoAtual=1;
                    }
                    
                    if(c=='!'){
                        lexema+=c;
                        estadoAtual=2;
                    }
                        
                    
                    
                   break;
                   
                case 1:
                    
                    if(c=='='){
                        lexema+=c;
                        estadoAtual=estadoFinal;
                    }
                    else{
                        devolve=true;
                        estadoAtual=estadoFinal;
                    }
                    
                    break;
                    
                case 2:
                    if(c== '='){
                        lexema+=c;
                        estadoAtual=estadoFinal;
                    }
                    
                    break;
                
                case 3:
                    //fazer
                    break;
                    
                case 4:
                    //fazer
                    break;
                    
                case 5:
                    //fazer
                    break;
                            
            }
            
        }
        
        
        //#teste
        System.out.println("lexema "+lexema);
        lexema="";
    }
    
}
