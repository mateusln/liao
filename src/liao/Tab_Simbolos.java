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

import java.util.HashMap;

public class Tab_Simbolos {
    public static HashMap <String, Simbolo> token = new HashMap <>();
    public static int endereco_tab=0;
    
    private final byte CONST = 0;
    private final byte INTEGER = 1;
    private final byte BYTE = 2;
    private final byte STRING = 3;
    private final byte WHILE = 4;
    private final byte IF = 5;
    private final byte ELSE = 6;
    private final byte AND = 7;
    private final byte OR = 8;
    private final byte NOT = 9;
    private final byte ATRIBUICAO = 10; // =
    private final byte IGUAL = 11; // ==
    private final byte A_PARENT = 12; // (
    private final byte F_PARENT = 13; // )
    private final byte MAIOR = 14;
    private final byte MENOR = 15;
    private final byte DIFER = 16; // !=
    private final byte MAIORIGUAL = 17;
    private final byte MENORIGUAL = 18;
    private final byte VIRGULA = 19;
    private final byte SOMA = 20;
    private final byte SUBTRACAO = 21;
    private final byte ASTERISCO = 22;
    private final byte BARRA_DIV = 23; // /
    private final byte PONTO_VIRG = 24;
    private final byte BEGIN = 25;
    private final byte END = 26;
    private final byte THEN = 27;
    private final byte READLN = 28;
    private final byte MAIN = 29;
    private final byte WRITE = 30;
    private final byte WRITELN = 31;
    private final byte TRUE = 32;
    private final byte FALSE = 33;
    private final byte BOOLEAN = 34;
    private final byte IDENTIFICADOR = 35;
    private final byte VALORCONSTANTE = 36;
    
    public Tab_Simbolos() {
        token.put("const", new Simbolo (CONST,"const"));
	token.put("integer", new Simbolo (INTEGER,"integer"));
	token.put("byte", new Simbolo (BYTE,"byte"));
	token.put("string", new Simbolo (STRING,"string"));
	token.put("while", new Simbolo (WHILE,"while"));
	token.put("if", new Simbolo (IF,"if"));
	token.put("else", new Simbolo (ELSE,"else"));
	token.put("and", new Simbolo (AND,"and"));
	token.put("or", new Simbolo (OR,"or"));
	token.put("not", new Simbolo (NOT,"not"));
	token.put("=", new Simbolo (ATRIBUICAO,"="));
        token.put("==", new Simbolo (IGUAL,"=="));
        token.put("(", new Simbolo (A_PARENT, "("));
	token.put(")", new Simbolo (F_PARENT, ")"));
	token.put(">", new Simbolo (MAIOR,">"));
	token.put("<", new Simbolo (MENOR,"<"));
        token.put("!=", new Simbolo (DIFER,"!="));
	token.put(">=", new Simbolo (MAIORIGUAL,">="));
	token.put("<=", new Simbolo (MENORIGUAL,"<="));
	token.put(",", new Simbolo (VIRGULA,","));
	token.put("+", new Simbolo (SOMA,"+"));
        token.put("-", new Simbolo (SUBTRACAO,"-"));
        token.put("*", new Simbolo (ASTERISCO,"*"));
        token.put("/", new Simbolo(BARRA_DIV,"/"));
	token.put(";", new Simbolo (PONTO_VIRG,";"));
	token.put("begin", new Simbolo (BEGIN,"begin"));
	token.put("end", new Simbolo (END,"end"));
        token.put("then", new Simbolo (THEN,"then"));
	token.put("readln", new Simbolo (READLN,"readln"));
        token.put("main", new Simbolo (MAIN,"main"));
	token.put("write", new Simbolo (WRITE,"write")); 			
	token.put("writeln", new Simbolo (WRITELN,"writeln"));		
	token.put("true", new Simbolo (TRUE,"true"));
	token.put("false", new Simbolo (FALSE,"false"));
	token.put("boolean", new Simbolo (BOOLEAN,"boolean"));
    }
    
    // Metodo de Pesquisa
    public byte pesquisar (String lexema) {
        Simbolo s = token.get(lexema);
        //deve retornar o numero do endereco 
        return s.getNumToken();
    }
    
    // Metodo de Insercao
    public Simbolo inserir (String lexema) {
        Simbolo simbolo = new Simbolo(IDENTIFICADOR, lexema, endereco_tab++);
        simbolo.setClasse("");
        token.put(lexema, simbolo);
        return token.get(lexema);
    }
    
    public Simbolo inserirConstante(String lexema, String tipo){
        Simbolo simbolo = new Simbolo(VALORCONSTANTE, lexema, endereco_tab++, tipo);
        System.out.println("end"+endereco_tab);
        token.put(lexema, simbolo);
        return token.get(lexema);
    }
    
    // Metodo para Verificar se existe Lexema
    public boolean existe (String lexema) {
        return token.containsKey(lexema);
    }
    
    public Simbolo getSimbolo(String lexema){
        return token.get(lexema);
    }
    
}
