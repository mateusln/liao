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
                    
                    if(c=='/'){
                        lexema+=c;
                        estadoAtual=3;
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
                    if(c=='*')
                        estadoAtual=4;
                    else{
                        estadoAtual=estadoFinal;
                        devolve=true;
                    }
                    break;
                    
                case 4:
                    while(c!='*'){
                        c=(char) leitor.read();
                    }
                    estadoAtual=5;
                    break;
                    
                case 5:
                    if(c=='/'){
                        estadoAtual=0;
                        lexema="";
                    }
                    else if( c=='*'){
                     //   while(c=='*')
                       //     c=(char) leitor.read();
                        estadoAtual=5;
                        
                    }else
                        estadoAtual=4;
                        
                    break;
                            
            }
            
        }
        
        
        //#teste
        System.out.println("lexema "+lexema);
        lexema="";
    }
    
}
