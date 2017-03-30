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
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class AnalisadorSintatico {
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
    private final byte CONSTANTE = 36;
    
    Simbolo registro;
    AnalisadorLexico automato;
    BufferedReader leitor;

    public AnalisadorSintatico(BufferedReader leitor) throws IOException {
        automato = new AnalisadorLexico();
        this.leitor=leitor;
        
        registro = automato.analisar(leitor);
        
        if( registro.getLexema() != "" ) {
            ProcS();
            if( registro.getLexema() != "" ) {
                System.out.println( /*Número da linha com erro + mensagem de erro */ );
                System.exit(0);
            }
        } else {
            System.out.println( /*Número da linha com erro + mensagem de erro */ );
            System.exit(0);
        }
    }

    public void CasaToken( byte tokenRecebido ) throws IOException {
        if( tokenRecebido != (byte)registro.getNumToken() ) {
            //System.out.println( registro.getCont()+":token não esperado." );
            System.exit(0);
        } else {
            //registro = anLex.automato( registro.getMarcado(), registro.getC() );
            registro=AnalisadorLexico.analisar(leitor);
        }
    }

    public void ProcS() throws IOException {
        do
            ProcD();
        while( registro.getNumToken() == CONST ||  registro.getNumToken() == INTEGER || registro.getNumToken() == BOOLEAN || registro.getNumToken() == BYTE || registro.getNumToken() == STRING );
        
        CasaToken( MAIN );
        
        do
            ProcC();
        while( registro.getNumToken() == IDENTIFICADOR || registro.getNumToken() == WHILE || registro.getNumToken() == IF || registro.getNumToken() == WRITE || registro.getNumToken() == WRITELN || registro.getNumToken() == READLN );
        
        CasaToken( END );
    }// fim ProcS

    public void ProcD() throws IOException {
        if( registro.getNumToken() == CONST ) {
            CasaToken( CONST );
            CasaToken( IDENTIFICADOR );
            CasaToken( ATRIBUICAO );
            ProcExp();
            CasaToken( PONTO_VIRG );
        } else {
            if( registro.getNumToken() == INTEGER )
                CasaToken( INTEGER );
            else if( registro.getNumToken() == BOOLEAN )
                CasaToken( BOOLEAN );
            else if( registro.getNumToken() == BYTE )
                CasaToken( BYTE );
            else if( registro.getNumToken() == STRING )
                CasaToken( STRING );

            CasaToken( IDENTIFICADOR );
            if( registro.getNumToken() == ATRIBUICAO ) {
                CasaToken( ATRIBUICAO );
                ProcExp();
            }
            while( registro.getNumToken() == WHILE ) {
                CasaToken( VIRGULA );
                CasaToken( IDENTIFICADOR );
                if( registro.getNumToken() == ATRIBUICAO ){
                    CasaToken( ATRIBUICAO );
                    ProcExp();
                }
            }
            CasaToken( PONTO_VIRG );
        }
    } // fim ProcD

    public void ProcExpS() throws IOException {
        if( registro.getNumToken() == SOMA )
            CasaToken( SOMA );
        else if( registro.getNumToken() == SUBTRACAO )
            CasaToken( SUBTRACAO );
        ProcT();
        do {
            if( registro.getNumToken() == SOMA )
                CasaToken( SOMA );
            else if( registro.getNumToken() == SUBTRACAO )
                CasaToken( SUBTRACAO );
            else
                CasaToken( OR );
            ProcT();
        } while (registro.getNumToken() == SOMA || registro.getNumToken() == SUBTRACAO || registro.getNumToken() == OR );
    }// fim ProcExpS

    public void ProcExp() throws IOException {
        ProcExpS();
        if( registro.getNumToken() == IGUAL || registro.getNumToken() == ATRIBUICAO || registro.getNumToken() == MENOR || registro.getNumToken() == MAIOR || registro.getNumToken() == MENORIGUAL || registro.getNumToken() == MAIORIGUAL ){
            if( registro.getNumToken() == IGUAL )
                CasaToken( IGUAL );
            else if( registro.getNumToken() == ATRIBUICAO )
                CasaToken( ATRIBUICAO );
            else if( registro.getNumToken() == MENOR )
                CasaToken( MENOR );
            else if( registro.getNumToken() == MAIOR )
                CasaToken( MAIOR );
            else if( registro.getNumToken() == MENORIGUAL )
                CasaToken( MENORIGUAL );
            else
                CasaToken( MAIORIGUAL );

            ProcExpS();
        }
    }// fim ProcExp

    public void ProcF() throws IOException {
        if( registro.getNumToken() == A_PARENT ) {
            CasaToken( A_PARENT );
            ProcExp();
            CasaToken( F_PARENT );
        }
        else if( registro.getNumToken() == IDENTIFICADOR )
            CasaToken( IDENTIFICADOR );
        else if( registro.getNumToken() == CONST )
            CasaToken( CONST );
        else {
            CasaToken( NOT );
            ProcF();
        }
    }//fim ProcF

    public void ProcT() throws IOException {
        ProcF();
        do {
            if( registro.getNumToken() == ASTERISCO )
                CasaToken( ASTERISCO );
            else if( registro.getNumToken() == BARRA_DIV )
                CasaToken( BARRA_DIV );
            else
                CasaToken( AND );

            ProcF();
        } while( registro.getNumToken() == ASTERISCO || registro.getNumToken() == BARRA_DIV || registro.getNumToken() == AND );
    }// fim ProcT

    public void ProcC() throws IOException {
        if( registro.getNumToken() == IDENTIFICADOR ){
            CasaToken( IDENTIFICADOR );
            CasaToken( ATRIBUICAO );
            ProcExp();
            CasaToken( PONTO_VIRG );
        } else if( registro.getNumToken() == WHILE ){
            CasaToken( WHILE );
            CasaToken( A_PARENT );
            ProcExp();
            CasaToken( F_PARENT );
            if( registro.getNumToken() == BEGIN ) {
                CasaToken( BEGIN );
                do {
                    ProcC();
                } while( registro.getNumToken() == IDENTIFICADOR || registro.getNumToken() == WHILE || registro.getNumToken() == IF || registro.getNumToken() == WRITE || registro.getNumToken() == WRITELN || registro.getNumToken() == READLN  );
                CasaToken( END );
            } else
                ProcC();
        } else if( registro.getNumToken() == IF ){
            CasaToken( IF );
            CasaToken( A_PARENT );
            ProcExp();
            CasaToken( F_PARENT );
            CasaToken( THEN );
            if( registro.getNumToken() == BEGIN ){
                do {
                    ProcC();
                } while( registro.getNumToken() == IDENTIFICADOR || registro.getNumToken() == WHILE || registro.getNumToken() == IF || registro.getNumToken() == WRITE || registro.getNumToken() == WRITELN || registro.getNumToken() == READLN );
            } else
                ProcC();
            if( registro.getNumToken() == ELSE ) {
                CasaToken( ELSE );
                if( registro.getNumToken() == BEGIN ){
                    do {
                        ProcC();
                    } while( registro.getNumToken() == IDENTIFICADOR || registro.getNumToken() == WHILE || registro.getNumToken() == IF || registro.getNumToken() == WRITE || registro.getNumToken() == WRITELN || registro.getNumToken() == READLN );
                    CasaToken( END );
                } else
                    ProcC();
            }
        } else if( registro.getNumToken() == WRITE || registro.getNumToken() == WRITELN ) {
            if( registro.getNumToken() == WRITE )
                CasaToken( WRITE );
            else
                CasaToken( WRITELN );
            CasaToken( A_PARENT );
            ProcExp();
            do {
                CasaToken( VIRGULA );
                ProcExp();
            } while( registro.getNumToken() == VIRGULA );
            CasaToken( F_PARENT );
            CasaToken( PONTO_VIRG );
        } else if( registro.getNumToken() == READLN ) {
            CasaToken( READLN );
            CasaToken( A_PARENT );
            CasaToken( IDENTIFICADOR );
            CasaToken( F_PARENT );
            CasaToken( PONTO_VIRG );
        } else //Comando nulo
            CasaToken( PONTO_VIRG );
    }// fim ProcC
}