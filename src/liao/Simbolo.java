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

public class Simbolo {
    private String lexema;
    private byte token;
    private String classe;
    private String tipo;
    private int endereco;
    
    public Simbolo() {
    }
    
    public Simbolo(byte token, String lexema) {
        this.token = token;
        this.lexema = lexema;
    }
    
    public Simbolo(byte token, String lexema, int endereco) {
        this.token = token;
        this.lexema = lexema;
        this.endereco = endereco;
    }
    
    //part 3
    public Simbolo(byte token, String lexema, int endereco, String classe, String tipo) {
        this.token = token;
        this.lexema = lexema;
        this.tipo = tipo;
        this.classe = classe;
        this.endereco= endereco;
    }
    
    
    
    public String getLexema() {
        return lexema;
    }
    
    public byte getNumToken() {
        return token;
    }
    
    public void setLexema (String lex) {
        this.lexema = lex;
    }
    
    public void setToken (byte tok) {
        this.token = tok;
    }
}
