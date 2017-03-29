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
 * @author mateus
 * 
 * O token reconhecido deverá ser representado por um registro único de escopo
 * global contendo campo para o número do token, lexema, endereço de inserção na
 * tabela (somente para identificadores e palavras reservadas) e tipo (somente
 * para o token constante). Observe que o registro léxico é diferente do
 * registro da tabela de símbolos.
 */
public class RegistroLexico {
    int numTok;
    String lex;
    Simbolo endereco; // ids
    String tipo; // constantes
    int linha;
    char c;
    //boolean marcado;
    
    public RegistroLexico (byte token, String lexema, char c, int cont) { // boolean marcado
        this.numTok = token;
        this.lex = lexema;
        this.c = c;
        this.linha = cont;
        //this.marcado = marcado;
    }
    
    public RegistroLexico (byte token, String lexema, Simbolo endereco, char c, int cont) { // boolean marcado
        this.numTok = token;
        this.lex = lexema;
        this.endereco = endereco;
        this.c = c;
        this.linha = cont;
        //this.marcado = marcado;
    }
    
    public int getNumToken() {
        return numTok;
    }
    
    public void setNumToken(int numToken) {
        this.numTok = numToken;
    }
    
    public String getLexema() {
        return lex;
    }
    
    public void setLexema(String lexema) {
        this.lex = lexema;
    }
    
    public Simbolo getEndereco() {
        return endereco;
    }
    
    public void setEndereco(Simbolo endereco) {
        this.endereco = endereco;
    }
    
    public String getTipo() {
        return tipo;
    }
    
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    
    public int getLinha() {
        return linha;
    }
    
    public void setLinha (int cont) {
        this.linha = cont;
    }
    
    public char getC() {
        return this.c;
    }

    /*
    public boolean getMarcado() {
        return this.marcado;
    }
    */
}
