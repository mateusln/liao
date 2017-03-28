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
import java.io.IOException;
import java.util.Arrays;

/**
 *
 * @author mateus
 */
public class AnalisadorLexico {
    public static RegistroLexico registroLexico;
    public static Tab_Simbolos tabela;
    private static boolean FDA = false;
    static int numLinha = 1;
    static String  lexema = "";
    char c;
    
    public static final void analisar (BufferedReader leitor) throws IOException {
        int estadoAtual = 0;
        final int estadoFinal = 12;
        char c = ' ';
        boolean devolve = false;
        
        while ( estadoAtual != estadoFinal ) {
            
            if ( !devolve ) { // pega o proximo char se nao for para devolver o anterior
                c = (char)leitor.read();
            }
            
            devolve = false;
                
            if ( c ==(char) -1 ) {//#teste
                estadoAtual = estadoFinal;
                return;
            }
            
            switch ( estadoAtual ) {
                case 0: // estado inicial
                    if(c == ' ' )
                        estadoAtual = 0; // ignora espaço
                    
                    char [] cadeiadeChar = {'(',')',',','+','-','*',';'};
                    
                    if (Arrays.toString(cadeiadeChar).contains(""+c)) {
                        // compara se o caractere esta na cadeia de char
                        lexema+=c;
                        estadoAtual = estadoFinal;
                    } else if ( c == '<' || c == '>' || c == '=' ) {
                        lexema+=c;
                        estadoAtual = 1;
                    } else if ( c == '!' ) {
                        lexema+=c;
                        estadoAtual = 2;
                    } else if ( c == '/') {
                        lexema+=c;
                        estadoAtual = 3;
                    } else if ( isLetra(c) ) { 
                        lexema+=c;
                        estadoAtual = 7;
                    } else if ( c == '_' ) {
                        lexema+=c;
                        estadoAtual = 6;
                    } else if ( c == '\'' ) {
                        lexema+=c;
                        estadoAtual = 16;
                    } else if (c == '0') {
                        lexema+=c;
                        estadoAtual = 8;
                    } else if ( isDigito(c) && c != '0' ) {
                        System.out.println("entrei");
                        lexema+=c;
                        estadoAtual = 11;
                    }
                    
                    
                    break;
                
                case 1: // leu < > ou =
                    if( c == '=' ) {
                        lexema+=c;
                        estadoAtual = estadoFinal;
                    }
                    else {
                        devolve = true;
                        estadoAtual = estadoFinal;
                    }
                    
                    break;
                    
                case 2: // leu !
                    if ( c == '=' ) {
                        lexema+=c;
                        estadoAtual=estadoFinal;
                    }
                    
                    break;
                
                case 3: // leu /
                    if ( c == '*' ) {
                        estadoAtual = 4;
                    }
                    else {
                        devolve = true;
                        estadoAtual = estadoFinal;
                    }
                    break;
                    
                case 4: //leu '/' e '*', começa comentario
                    while (c!='*') {
                        c = (char) leitor.read();
                    }
                    estadoAtual = 5;
                    break;
                    
                case 5: // leu * depois de ter lido /* pode fechar o cometario ou nao
                    if ( c == '/' ) { //fecha comentario e volta para estado inicial
                        estadoAtual = 0;
                        lexema = "";
                    }
                    else if ( c == '*' ) { // le * indefinidamente se !='*' volta para estado 4
                        estadoAtual = 5;
                        
                    } else {
                        estadoAtual = 4;
                    }
                    break;
                    
                case 6: // leu id começando com '_'
                    if ( c == '_' ) {
                        lexema+=c;
                        estadoAtual = 6;
                    } else if ( isDigito(c) || isLetra(c) ) { // muda para e7 se n tiver mais '_' no id
                        lexema+=c;
                        estadoAtual = 7;
                    }else{
                        
                        System.out.println("ERRO id apenas com _");
                        lexema="";
                        return  ;
                    }
                    break;
                    
                case 7: // leu primeiro caracater do id ou leu '_'
                    if ( isDigito(c) || isLetra(c) || c == '_' ) {
                        lexema+=c;
                        estadoAtual = 7;                        
                    } else {
                        //fazer pesquisaTS
                        
                        devolve = true;
                        estadoAtual = estadoFinal;
                    }
                        
                    
                    break;

                    
                case 16:
                    char [] cadeia = {'(',')',',','+','-','*',';','_','.',':','[',']','{','}','"','/','|','?','!','>', '<', '='};
                    if ( c == '\'' ) { // fim string
                        lexema+=c;
                        estadoAtual = estadoFinal;
                    } else if( (int)c == 9 ) { // espaço vazio
                        estadoAtual = estadoFinal;
                    } else if( (int)c == 10 ) { // \n
                        numLinha++;
                        estadoAtual = estadoFinal;
                    } else if( (int)c == 13 ) { // \r
                        estadoAtual = estadoFinal;
                    } else if( (int)c == 65535 ) { // FDA
                        System.out.println( numLinha + ":fim de arquivo n�o esperado.");
                        System.exit(0);
                    }
                    // dentro da String, todos caracteres validos
                    //char [] cadeiadeChar = {'(',')',',','+','-','*',';'};
                    else if ( isLetra(c) || isDigito(c) || c == ' ' || (int)c == 92 || (int)c == 39 ||
                            Arrays.toString(cadeia).contains(""+c)) {
                        lexema+=c;
                    } else {
                        System.out.println(numLinha + ":caractere invalido.");
                        System.exit(0);
                    }
                    
                    break;

                case 8: // número 
                    if ( c == 'h' ) {
                        lexema+=c;
                        estadoAtual = 9;
                    } else if( isDigito(c) ) {
                        lexema+=c;
                        estadoAtual = 11;
                    } else {
                        // (c != h) && (c != digito)

                        devolve = true;
                        estadoAtual = estadoFinal;
                    }

                    break;

                case 9:
                    if ( isHexadecimal(c) ) {
                        lexema+=c;
                        estadoAtual = 10;
                    }

                    break;

                case 10:
                    if ( isHexadecimal(c) ) {
                        lexema+=c;
                        estadoAtual = estadoFinal;
                    }

                    break;

                case 11:
                    if ( isDigito(c) ){
                        lexema+=c;
                        estadoAtual = 11;
                    } else {
                        // c != Digito

                        devolve = true;
                        estadoAtual=estadoFinal;
                    }

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

    public static boolean isHexadecimal(char c){
        boolean isHexadecimal = false;
        if ( (c >= 0 && c <= 9) || ( c >= 'A' && c <= 'F' ))
            isHexadecimal = true;
        return isHexadecimal;
    }
}
