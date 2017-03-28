/**
 * PUC Minas - ICEI
 * Curso de Ciencia da Computacao
 * Disciplina: Compiladores (1-2017)
 * Trabalho Pratico
 * Liao - Compilador para a Linguagem Imperativa Simplificada 'L'
 * Parte 1 - Analisador Lexico e Analisador Sinttico
 * @author Ana Cristina Pereira Teixeira
 * @author Jordan Lyon Ramos Rodrigues Duarte
 * @author Mateus Loures do Nascimento
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
                
                
                case 0: //estado inicial
                    if(c==' ' )
                        estadoAtual=0; // ignora espaço
                    
                    char [] cadeiadeChar= {'(',')',',','+','-','*',';'};
                    if (Arrays.toString(cadeiadeChar).contains(""+c)){// compara se o caractere esta na cadeia de char
                        lexema+=c;
                        estadoAtual=estadoFinal;
                    }else if(c=='<' || c== '>' || c== '=' ){
                        lexema+=c;
                        estadoAtual=1;
                    }else if(c=='!'){
                        lexema+=c;
                        estadoAtual=2;
                    }else if(c=='/'){
                        lexema+=c;
                        estadoAtual=3;
                    }else if(isLetra(c)){
                        
                        lexema+=c;
                        estadoAtual=7;
                    }else if(c=='_'){
                        lexema+=c;
                        estadoAtual=6;
                    }
                        
                    
                    
                   break;
                   
                case 1: // leu < > , =
                    
                    if(c=='='){
                        lexema+=c;
                        estadoAtual=estadoFinal;
                    }
                    else{
                        devolve=true;
                        estadoAtual=estadoFinal;
                    }
                    
                    break;
                    
                case 2: // leu !
                    if(c== '='){
                        lexema+=c;
                        estadoAtual=estadoFinal;
                    }
                    
                    break;
                
                case 3: // leu /
                    if(c=='*')
                        estadoAtual=4;
                    else{
                        estadoAtual=estadoFinal;
                        devolve=true;
                    }
                    break;
                    
                case 4: //leu '/' e '*', começa comentario
                    while(c!='*'){
                        c=(char) leitor.read();
                    }
                    estadoAtual=5;
                    break;
                    
                case 5: // leu * depois de ter lido /* pode fechar o cometario ou nao
                    if(c=='/'){ //fecha comentario e volta para estado inicial
                        estadoAtual=0;
                        lexema="";
                    }
                    else if( c=='*'){ // le * indefinidamente se !='*' volta para estado 4
                    
                        estadoAtual=5;
                        
                    }else
                        estadoAtual=4;
                        
                    break;
                    
                case 6: // leu id começando com '_'
                    if(c=='_'){
                        lexema+=c;
                        estadoAtual=6;
                    }else if(isDigito(c) || isLetra(c)){ // muda para e7 se n tiver mais '_' no id
                        lexema+=c;
                        estadoAtual=7;
                    }
                        
                        
                    break;
                    
                case 7: // leu primeiro caracater do id ou leu '_'
                    
                    
                    if(isDigito(c) || isLetra(c) || c=='_'){
                        lexema+=c;
                        estadoAtual=7;                        
                    }else{
                    //fazer pesquisaTS
                        devolve=true;
                        estadoAtual=estadoFinal;
                    }
                        
                    
                    break;
                    
                case 8:
                    //fazer
                    break;
                            
            }
            
        }
        
        
        //#teste
        System.out.println("lexema "+lexema);
        lexema="";
        
        // criar uma classe (pode ser com o nome Simbolo ou RegistroLexico) onde recebe o lexema para criar o objeto dessa classe ver tp1.doc n1 e n2
    }
    
    public static boolean isLetra(char c){
		boolean isLetra = false;
		if((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z'))
			isLetra = true;
		return isLetra;
	} 
    
    public static boolean isDigito(char c){
		boolean isDigito = false;
		if(c >= '0' && c <= '9')
			isDigito = true;
		return isDigito;
	}
}
