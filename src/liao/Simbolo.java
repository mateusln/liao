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

/**
 *
 * @author anacr
 */
public class Simbolo {
    private String lexema;
    private byte token;
    
    public Simbolo() {
    }
    
    public Simbolo(byte token, String lexema) {
        this.token = token;
        this.lexema = lexema;
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
