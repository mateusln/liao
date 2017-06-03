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
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;

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
    private final byte DIFERENCA = 16; // !=
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
    
    Simbolo registro;
    AnalisadorLexico automato;
    BufferedReader leitor;
    Memoria memoria;
    Buffer buffer;

    public AnalisadorSintatico(BufferedReader leitor) throws IOException {
        automato = new AnalisadorLexico();
        this.leitor=leitor;
        
        registro = automato.analisar(leitor);
        
        memoria = new Memoria();
        try {
            //fazer rotulo
            buffer = new Buffer();
        } catch (Exception ex) {
            Logger.getLogger(AnalisadorSintatico.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
        if( registro.getLexema() != "" ) {
            ProcS();
            if( registro.getLexema() != "EOF" ) {  
                System.out.println(automato.contaLinha+":token nao esperado [ "+registro.getLexema()+" ] ");
                System.exit(0);
            }else{
                buffer.criarArquivo();
                System.exit(0);
                
            }
        } else {
            System.out.println( /*Número da linha com erro + mensagem de erro */ );
            System.exit(0);
        }
    }

    public void CasaToken( byte tokenRecebido ) throws IOException {
        if( tokenRecebido != (byte)registro.getNumToken() ) {
            
            if(registro.getNumToken()==99){
                System.out.println(automato.contaLinha+":fim de arquivo nao esperado.");
                //System.out.println("Token recebido "+tokenRecebido+" token esperado "+(byte)registro.getNumToken() );
                System.exit(0);
            }
            
            System.out.println( automato.contaLinha+":token não esperado [ "+ registro.getLexema()+" ]" );
            System.exit(0);
        } else {
            //registro = anLex.automato( registro.getMarcado(), registro.getC() );
            
            registro=AnalisadorLexico.analisar(leitor);
        }
    }

    public void ProcS() throws IOException {
        
        Buffer.buffer.add("sseg SEGMENT STACK ;início seg. pilha");
	Buffer.buffer.add("byte 4000h DUP(?) ;dimensiona pilha");
	Buffer.buffer.add("sseg ENDS ;fim seg. pilha");
	Buffer.buffer.add("dseg SEGMENT PUBLIC ;início seg. dados");
	Buffer.buffer.add("byte 4000h DUP(?) ;temporários");
        
        
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
        String id_tipo="";
        
        
        if( registro.getNumToken() == CONST ) {
            CasaToken( CONST );
            String lexTemp=registro.getLexema();
            CasaToken( IDENTIFICADOR );
            CasaToken( ATRIBUICAO );
            
            String exp_Tipo=ProcExp();
            if(AnalisadorLexico.tabela.getSimbolo(lexTemp).getClasse()==""){ //olha se o ID já foi declarado
                AnalisadorLexico.tabela.getSimbolo(lexTemp).setClasse("const");
                AnalisadorLexico.tabela.getSimbolo(lexTemp).setTipo(exp_Tipo);
            }else{
                System.out.println(AnalisadorLexico.contaLinha+":identificador ja declarado ["+AnalisadorLexico.tabela.getSimbolo(lexTemp).getLexema()+"]");
                System.exit(0);
            }
            
            /*if( registro.getNumToken() == SOMA )
                CasaToken( SOMA );
            else if( registro.getNumToken() == SUBTRACAO )
                CasaToken( SUBTRACAO );
            
            if(registro.getNumToken() == VALORCONSTANTE)
                CasaToken(VALORCONSTANTE);
            else if(registro.getNumToken()== TRUE)
                CasaToken(TRUE);
            else
                CasaToken(FALSE);*/
                                
            CasaToken( PONTO_VIRG );
        } else {
            if( registro.getNumToken() == INTEGER ){
                CasaToken( INTEGER );
                id_tipo="tipo_inteiro";
            }else if( registro.getNumToken() == BOOLEAN ){
                CasaToken( BOOLEAN );
                id_tipo="tipo_logico";
            }else if( registro.getNumToken() == BYTE ){
                CasaToken( BYTE );
                id_tipo="tipo_byte";
            }else{ 
                CasaToken( STRING );
                id_tipo="tipo_string";
            }
            String lexTemp=registro.getLexema();
            CasaToken( IDENTIFICADOR );
            if(AnalisadorLexico.tabela.getSimbolo(lexTemp).getClasse()==""){
                AnalisadorLexico.tabela.getSimbolo(lexTemp).setClasse("var");
                AnalisadorLexico.tabela.getSimbolo(lexTemp).setTipo(id_tipo);
            }else{
                System.out.println(AnalisadorLexico.contaLinha+":identificador ja declarado ["+AnalisadorLexico.tabela.getSimbolo(lexTemp).getLexema()+"]");
                System.exit(0);
            }
            
            if( registro.getNumToken() == ATRIBUICAO ) {
                CasaToken( ATRIBUICAO );
                String Exp_tipo = "";
                Exp_tipo=ProcExp();
                if(id_tipo!=Exp_tipo){
                    if( !(id_tipo == "tipo_inteiro" && Exp_tipo=="tipo_byte")){
                        System.out.println(AnalisadorLexico.contaLinha+":identificador ja declarado ["+AnalisadorLexico.tabela.getSimbolo(lexTemp).getLexema()+"]");
                        System.exit(0);
                    }
                }
            }
            while( registro.getNumToken() == VIRGULA ) {
                CasaToken( VIRGULA );
                lexTemp=registro.getLexema();
                CasaToken( IDENTIFICADOR );
                
                if(AnalisadorLexico.tabela.getSimbolo(lexTemp).getClasse()==""){
                    AnalisadorLexico.tabela.getSimbolo(lexTemp).setClasse("var");
                    AnalisadorLexico.tabela.getSimbolo(lexTemp).setTipo(id_tipo);
                }else{
                    System.out.println(AnalisadorLexico.contaLinha+":identificador ja declarado ["+AnalisadorLexico.tabela.getSimbolo(lexTemp).getLexema()+"]");
                    System.exit(0);
                }
                
                if( registro.getNumToken() == ATRIBUICAO ){
                    CasaToken( ATRIBUICAO );
                    String exp_tipo=ProcExp();
                    if(id_tipo!=exp_tipo){
                    if( !(id_tipo == "tipo_inteiro" && exp_tipo=="tipo_byte")){
                        System.out.println(AnalisadorLexico.contaLinha+":tipos incompativeis");
                        System.exit(0);
                    }
                }
                }
            }
            CasaToken( PONTO_VIRG );
        }
    } // fim ProcD

    public String ProcExpS() throws IOException {
        String Exps_tipo="";
        boolean flagNegativo=false;
        String t_op="";
        
        if( registro.getNumToken() == SOMA )
            CasaToken( SOMA );
        else if( registro.getNumToken() == SUBTRACAO ){
            CasaToken( SUBTRACAO );
            flagNegativo=true;
        }
        
        String t_tipo=ProcT();
        
        if(flagNegativo && t_tipo=="tipo_byte"){
            t_tipo="tipo_inteiro";
            Exps_tipo=t_tipo;
        }else{
            Exps_tipo=t_tipo;
        }
        
        while (registro.getNumToken() == SOMA || registro.getNumToken() == SUBTRACAO || registro.getNumToken() == OR ) {
            if( registro.getNumToken() == SOMA ){
                CasaToken( SOMA );
                t_op="adicao";
            }else if( registro.getNumToken() == SUBTRACAO ){
                CasaToken( SUBTRACAO );
                t_op="subtracao";
            }else{
                CasaToken( OR );
                t_op="or";
            }
            String t2_tipo=ProcT();
            
            if(t_op=="adicao" || t_op=="subtracao"){
                if(t_tipo=="tipo_logico" || t2_tipo=="tipo_logico"){
                    System.out.println(AnalisadorLexico.contaLinha+":tipos incompativeis");
                    System.exit(0);
                }
                
                if(t_tipo=="tipo_byte" && t2_tipo=="tipo_byte")
                    Exps_tipo="tipo_byte";
                else
                    Exps_tipo="tipo_inteiro";
                
                
                if(t_op=="adicao"){
                    /*if( (t_tipo=="tipo_string" && t2_tipo!="tipo_string") || (t_tipo!="tipo_string" && t_tipo=="tipo_string") ){
                        System.out.println(AnalisadorLexico.contaLinha + ":tipos incompativeis");
                        System.exit(0);
                    }
                    Exps_tipo="tipo_string";*/
                    if( (t_tipo=="tipo_string" && t2_tipo=="tipo_string") ){
                        
                        Exps_tipo="tipo_string";
                        
                    }else if( (t_tipo=="tipo_string" && t2_tipo!="tipo_string") || (t_tipo!="tipo_string" && t_tipo=="tipo_string") ){
                        System.out.println(AnalisadorLexico.contaLinha + ":tipos incompativeis");
                        System.exit(0);
                    }
                    
                }else if(t_tipo=="tipo_string" || t2_tipo=="tipo_string"){
                    System.out.println(AnalisadorLexico.contaLinha + ":tipos incompativeis");
                    System.exit(0);
                }
                
                }else{//caso OR
                if(t_tipo!="tipo_logico" || t2_tipo!="tipo_logico"){
                    System.out.println(AnalisadorLexico.contaLinha + ":tipos incompativeis");
                    System.exit(0);
                }
                Exps_tipo="tipo_logico";
                
            }
                
        }
        
        return Exps_tipo;
    }// fim ProcExpS

    public String ProcExp() throws IOException {
        String expS_tipo;
        expS_tipo=ProcExpS();
        String logicosOperador="";
        
        if( registro.getNumToken() == IGUAL || registro.getNumToken() == DIFERENCA || registro.getNumToken() == MENOR || registro.getNumToken() == MAIOR || registro.getNumToken() == MENORIGUAL || registro.getNumToken() == MAIORIGUAL ){
            if( registro.getNumToken() == IGUAL ){
                CasaToken( IGUAL );
                logicosOperador="igualdade";
            }else if( registro.getNumToken() == DIFERENCA ){
                CasaToken( DIFERENCA );
                logicosOperador="diferenca";
            }else if( registro.getNumToken() == MENOR ){
                CasaToken( MENOR );
                logicosOperador="menor";
            }else if( registro.getNumToken() == MAIOR ){
                CasaToken( MAIOR );
                logicosOperador="maior";
            }else if( registro.getNumToken() == MENORIGUAL ){
                CasaToken( MENORIGUAL );
                logicosOperador="menorigual";
            }else{
                CasaToken( MAIORIGUAL );
                logicosOperador="maiorigual";
            }
            String expS2_tipo =ProcExpS();
            if(logicosOperador=="igualdade"){
                if (expS_tipo != expS2_tipo){
                    if( !(expS_tipo=="tipo_inteiro" || expS_tipo=="tipo_byte") ){
                        System.out.println(AnalisadorLexico.contaLinha + ":tipos incompativeis ");
                        System.exit(0);
                    }else if(!(expS2_tipo=="tipo_inteiro" || expS2_tipo=="tipo_byte") ){
                        System.out.println(AnalisadorLexico.contaLinha + ":tipos incompativeis");
                        System.exit(0);
                    }
                }
            }else if(logicosOperador!="diferenca"){ // caso seja <, >, <=, >=
                if(expS_tipo=="tipo_string" || expS2_tipo=="tipo_logico" || expS_tipo=="tipo_logico" || expS2_tipo=="tipo_string"){
                    System.out.println(expS_tipo+expS2_tipo );
                    
                    System.out.println(AnalisadorLexico.contaLinha + ":tipos incompativeis");
                    System.exit(0);
                }
            }else{ //caso logicoOp == igualdade
                if(expS_tipo=="tipo_string" || expS2_tipo=="tipo_string"){
                    System.out.println(AnalisadorLexico.contaLinha + ":tipos incompativeis");
                    System.exit(0);
                }
                
            }
            expS_tipo="tipo_logico"; //se ele chegou ate aqui a exp vai ser logica
        }
        return expS_tipo;
    }// fim ProcExp

    public String ProcF() throws IOException {
        String F_tipo="";
        
        if( registro.getNumToken() == A_PARENT ) {
            CasaToken( A_PARENT );
            F_tipo=ProcExp();
            CasaToken( F_PARENT );
        }
        else if( registro.getNumToken() == IDENTIFICADOR ){
            String lexTemp=registro.getLexema();
            CasaToken( IDENTIFICADOR );
            Simbolo simboloTemp=AnalisadorLexico.tabela.getSimbolo(lexTemp);//busca o lex na tabela de simbolos
            if(simboloTemp.getClasse()==null || simboloTemp.getClasse()==""){
                System.out.println(AnalisadorLexico.contaLinha+":identificador nao declarado ["+simboloTemp.getLexema()+"]");
                System.exit(0);
            }else
                F_tipo=simboloTemp.getTipo();
        }else if( registro.getNumToken() == VALORCONSTANTE ){
            String temp=registro.getLexema(); //armazena o lex antes de passar pelo CT
            CasaToken( VALORCONSTANTE );
            //regra 02
            //pega o tipo da tabela porque o programa so terá ele depois de passar pelo CT e inserir na tabela
            F_tipo=AnalisadorLexico.tabela.getSimbolo(temp).getTipo();
        
        }else if(registro.getNumToken() == TRUE){
            CasaToken(TRUE);
            F_tipo="tipo_logico";
        }else if(registro.getNumToken() == FALSE){
            CasaToken(FALSE);
            F_tipo="tipo_logico";
        }else{
            CasaToken( NOT );
            String f1_tipo=ProcF();
            if(f1_tipo=="tipo_logico")
                F_tipo=f1_tipo;
            else
                System.out.println(automato.contaLinha+":ERRO, tipos imcompativeis ");
        }
        
        return F_tipo;
    }//fim ProcF

    public String ProcT() throws IOException {
        String T_tipo="";
        
        T_tipo=ProcF();
        while( registro.getNumToken() == ASTERISCO || registro.getNumToken() == BARRA_DIV || registro.getNumToken() == AND ) {
            String t_op="";
            if( registro.getNumToken() == ASTERISCO ){
                CasaToken( ASTERISCO );
                t_op="multi";
            }else if( registro.getNumToken() == BARRA_DIV ){
                CasaToken( BARRA_DIV );
                t_op="div";
            }else{
                CasaToken( AND );
                t_op="and";
            }
            String f1_tipo=T_tipo;
            String f2_tipo=ProcF();
            if(t_op=="multi" || t_op=="div"){
                if(f1_tipo=="tipo_logico" || f1_tipo=="tipo_string" || f2_tipo=="tipo_logico" || f2_tipo=="tipo_string"){
                    System.out.println(AnalisadorLexico.contaLinha+":tipos incompativeis");
                    System.exit(0);
                }
                
                if(f1_tipo=="tipo_byte" && f2_tipo=="tipo_byte")
                    T_tipo="tipo_byte";
                else
                    T_tipo="tipo_inteiro";
            }else if(t_op=="and"){
                if(f1_tipo!="tipo_logico" || f2_tipo!="tipo_logico"){
                    System.out.println(AnalisadorLexico.contaLinha+":tipos incompativeis");
                    System.exit(0);
                }
                T_tipo="tipo_logico";
            }
            
        } 
        return T_tipo;
    }// fim ProcT

    public void ProcC() throws IOException {
        if( registro.getNumToken() == IDENTIFICADOR ){
            String lexTemp=registro.getLexema();
            Simbolo simboloID=AnalisadorLexico.tabela.getSimbolo(lexTemp); //pega o identificador na tabela
            if(simboloID.getClasse()=="" || simboloID.getClasse()==null){ 
                System.out.println(AnalisadorLexico.contaLinha+":identificador nao declarado ["+simboloID.getLexema()+"]");
                System.exit(0);
            }
            if(simboloID.getClasse()=="const"){
                System.out.println(AnalisadorLexico.contaLinha+":classe de identificador incompatível ["+simboloID.getLexema()+"]");
                System.exit(0);
            }
            
            CasaToken( IDENTIFICADOR );
            CasaToken( ATRIBUICAO );
            String exp_tipo=ProcExp();
            
            if(simboloID.getTipo()!=exp_tipo){
                if( !( simboloID.getTipo()=="tipo_inteiro" && exp_tipo=="tipo_byte") ){
                    System.out.println(AnalisadorLexico.contaLinha+":tipos incompativeis");
                    System.exit(0);
                }
            }
            CasaToken( PONTO_VIRG );
            
        } else if( registro.getNumToken() == WHILE ){
            CasaToken( WHILE );
            CasaToken( A_PARENT );
            String exp_Tipo=ProcExp();
            if(exp_Tipo!="tipo_logico"){
                System.out.println(AnalisadorLexico.contaLinha+":tipos incompativeis");
                System.exit(0);
            }
            CasaToken( F_PARENT );
            if( registro.getNumToken() == BEGIN ) {
                CasaToken( BEGIN );
                do {
                    ProcC();
                } while( registro.getNumToken() == IDENTIFICADOR || registro.getNumToken() == WHILE || registro.getNumToken() == IF || registro.getNumToken() == WRITE || registro.getNumToken() == WRITELN || registro.getNumToken() == READLN || registro.getNumToken() == PONTO_VIRG );
                CasaToken( END );
            } else
                ProcC();
        } else if( registro.getNumToken() == IF ){
            CasaToken( IF );
            CasaToken( A_PARENT );
            String exp_Tipo=ProcExp();
            if(exp_Tipo!="tipo_logico"){
                System.out.println(AnalisadorLexico.contaLinha+":tipos incompativeis");
                System.exit(0);
            }
            CasaToken( F_PARENT );
            CasaToken( THEN );
            if( registro.getNumToken() == BEGIN ){
                CasaToken( BEGIN );
                do {
                    ProcC();
                } while( registro.getNumToken() == IDENTIFICADOR || registro.getNumToken() == WHILE || registro.getNumToken() == IF || registro.getNumToken() == WRITE || registro.getNumToken() == WRITELN || registro.getNumToken() == READLN || registro.getNumToken() == PONTO_VIRG);
                CasaToken(END);
            } else
                ProcC();
            if( registro.getNumToken() == ELSE ) {
                CasaToken( ELSE );
                if( registro.getNumToken() == BEGIN ){
                    CasaToken(BEGIN);
                    do {
                        ProcC();
                    } while( registro.getNumToken() == IDENTIFICADOR || registro.getNumToken() == WHILE || registro.getNumToken() == IF || registro.getNumToken() == WRITE || registro.getNumToken() == WRITELN || registro.getNumToken() == READLN || registro.getNumToken() == PONTO_VIRG);
                    
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
            String exp_Tipo=ProcExp();
            if(exp_Tipo=="tipo_logico"){
                System.out.println(AnalisadorLexico.contaLinha+":tipos incompativeis");
                System.exit(0);
            }
            while( registro.getNumToken() == VIRGULA ) {
                CasaToken( VIRGULA );
                exp_Tipo=ProcExp();
                if(exp_Tipo=="tipo_logico"){
                    System.out.println(AnalisadorLexico.contaLinha+":tipos incompativeis");
                    System.exit(0);
                }
            }
            CasaToken( F_PARENT );
            CasaToken( PONTO_VIRG );
        } else if( registro.getNumToken() == READLN ) {
            CasaToken( READLN );
            CasaToken( A_PARENT );
            String lexTemp=registro.getLexema();
            CasaToken( IDENTIFICADOR );
            Simbolo simboloID=AnalisadorLexico.tabela.getSimbolo(lexTemp);
            if(simboloID.getTipo()=="tipo_logico"){
                System.out.println(AnalisadorLexico.contaLinha+":tipos incompativeis");
                System.exit(0);
            }
            
            CasaToken( F_PARENT );
            CasaToken( PONTO_VIRG );
        } else //Comando nulo 
            CasaToken( PONTO_VIRG );
    }// fim ProcC
}