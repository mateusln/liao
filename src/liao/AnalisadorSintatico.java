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
    
    int f_end;
    int t_end;
    int expS_end;
    int exp_end;
    
    Rotulo rotulo;
    

    public AnalisadorSintatico(BufferedReader leitor) throws IOException {
        automato = new AnalisadorLexico();
        this.leitor=leitor;
        
        registro = automato.analisar(leitor);
        
        memoria = new Memoria();
        rotulo=new Rotulo();
        
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
    
    //#Geraçao de codigo
    
    void escreveBuffer(String frase){
        Buffer.buffer.add(frase);
    }
    
    public void alocarID(String tipo, String id){
        int endereco;
        switch(tipo){
            case "tipo_byte":
                
                endereco=memoria.alocarByte();
                escreveBuffer("byte ?   ; "+id+" ? byte mem="+endereco);
                AnalisadorLexico.tabela.getSimbolo(id).setEndereco(endereco);
                break;
            case "tipo_inteiro":
                escreveBuffer("sword ?  ; "+id+" ? inteiro");
                endereco=memoria.alocarInteiro();
                AnalisadorLexico.tabela.getSimbolo(id).setEndereco(endereco);
                break;
            case "tipo_logico":
                escreveBuffer("byte ?   ;" +id+" ? logico");
                endereco=memoria.alocarLogico();
                AnalisadorLexico.tabela.getSimbolo(id).setEndereco(endereco);
                break;
            case "tipo_string":
                escreveBuffer("byte  256 DUP (?)    ;"+id+"? String");
                endereco=memoria.alocarString();
                AnalisadorLexico.tabela.getSimbolo(id).setEndereco(endereco);
                break;
            
        }
    }
    
    public void alocarIDcomAtribuicao(String tipo, String id){
        int endereco;
        switch(tipo){
            case "tipo_byte":
                
                endereco=memoria.alocarByte();
                escreveBuffer("byte ?   ; "+id+" ? byte mem="+endereco);
                AnalisadorLexico.tabela.getSimbolo(id).setEndereco(endereco);
                break;
            case "tipo_inteiro":
                escreveBuffer("sword ?  ; "+id+" ? inteiro");
                endereco=memoria.alocarInteiro();
                AnalisadorLexico.tabela.getSimbolo(id).setEndereco(endereco);
                break;
            case "tipo_logico":
                escreveBuffer("byte ?   ;" +id+" ? logico");
                endereco=memoria.alocarLogico();
                AnalisadorLexico.tabela.getSimbolo(id).setEndereco(endereco);
                break;
            case "tipo_string":
                escreveBuffer("byte  256 DUP (?)    ;"+id+"? String");
                endereco=memoria.alocarString();
                AnalisadorLexico.tabela.getSimbolo(id).setEndereco(endereco);
                break;
            
        }
    }

    public void ProcS() throws IOException {
        
        Buffer.buffer.add("sseg SEGMENT STACK ;início seg. pilha");
	Buffer.buffer.add("byte 4000h DUP(?) ;dimensiona pilha");
	Buffer.buffer.add("sseg ENDS ;fim seg. pilha");
	Buffer.buffer.add("dseg SEGMENT PUBLIC ;início seg. dados");
	Buffer.buffer.add("byte 4000h DUP(?) ;temporários");
        memoria.alocarTemp();
        
        
        do
            ProcD();
        while( registro.getNumToken() == CONST ||  registro.getNumToken() == INTEGER || registro.getNumToken() == BOOLEAN || registro.getNumToken() == BYTE || registro.getNumToken() == STRING );
        
        Buffer.buffer.add("dseg ENDS ;fim seg. dados");
	Buffer.buffer.add("cseg SEGMENT PUBLIC ;início seg. código");
	Buffer.buffer.add("ASSUME CS:cseg, DS:dseg");
	Buffer.buffer.add("strt:");
	Buffer.buffer.add("mov ax, dseg");
	Buffer.buffer.add("mov ds, ax");
        
        CasaToken( MAIN );
        
        do
            ProcC();
        while( registro.getNumToken() == IDENTIFICADOR || registro.getNumToken() == WHILE || registro.getNumToken() == IF || registro.getNumToken() == WRITE || registro.getNumToken() == WRITELN || registro.getNumToken() == READLN );
        
        escreveBuffer("mov ah, 4Ch");
	escreveBuffer("int 21h");
	escreveBuffer("cseg ENDS ;fim seg. código");
	escreveBuffer("END strt ;fim programa");
        
        CasaToken( END );
    }// fim ProcS

    public void ProcD() throws IOException {
        String id_tipo="";
        boolean flagPositivo=false,flagNegativo=false;
        
        
        if( registro.getNumToken() == CONST ) {
            CasaToken( CONST );
            String lexTempID=registro.getLexema();
            CasaToken( IDENTIFICADOR );
            CasaToken( ATRIBUICAO );
            
            
            if( registro.getNumToken() == SOMA ){
                CasaToken( SOMA );
                flagPositivo=true;
            }else if( registro.getNumToken() == SUBTRACAO ){
                CasaToken( SUBTRACAO );
                flagNegativo=true;
            }
        
        //String t_tipo=ProcT() const; // CT const
        
        String tipoDoValorConstante="";
        
        if( registro.getNumToken() == IDENTIFICADOR ){
            String lexTemp=registro.getLexema();
            CasaToken( IDENTIFICADOR );
            Simbolo simboloTemp=AnalisadorLexico.tabela.getSimbolo(lexTemp);//busca o lex na tabela de simbolos
            f_end=simboloTemp.getEndereco();
            if(simboloTemp.getClasse()==null || simboloTemp.getClasse()==""){
                System.out.println(AnalisadorLexico.contaLinha+":identificador nao declarado ["+simboloTemp.getLexema()+"]");
                System.exit(0);
            }else
                tipoDoValorConstante=simboloTemp.getTipo();
        }else if( registro.getNumToken() == VALORCONSTANTE ){
            String LexemaConst=registro.getLexema(); //armazena o lex antes de passar pelo CT
            CasaToken( VALORCONSTANTE );
             // pega o enderco da constante e passa para t
            //regra 02
            //pega o tipo da tabela porque o programa so terá ele depois de passar pelo CT e inserir na tabela
            tipoDoValorConstante=AnalisadorLexico.tabela.getSimbolo(LexemaConst).getTipo();
            
            if(tipoDoValorConstante!="tipo_string"){
                f_end = memoria.alocarTemp(tipoDoValorConstante);
                escreveBuffer("mov ax, " + LexemaConst + " ; const " + LexemaConst);
                escreveBuffer("mov DS:[" + f_end + "], al");
            }
            
        }else if(registro.getNumToken() == TRUE){
            CasaToken(TRUE);
            tipoDoValorConstante="tipo_logico";
            f_end = memoria.alocarTemp(tipoDoValorConstante);
            escreveBuffer("mov al, 0ffh ; const true");
            escreveBuffer("mov DS:[" + f_end + "], al");
        }else{ //registro.getNumToken() == FALSE
            CasaToken(FALSE);
            tipoDoValorConstante="tipo_logico";
            f_end = memoria.alocarTemp(tipoDoValorConstante);
            escreveBuffer("mov al, 0h ; const false");
            escreveBuffer("mov DS:[" + f_end + "], al");
        }
        
        
        
        /*
        if(flagNegativo && t_tipo=="tipo_byte"){
            t_tipo="tipo_inteiro";
            Exps_tipo=t_tipo;
        }else{
            Exps_tipo=t_tipo;
        }*/
        
        
        Simbolo ID=AnalisadorLexico.tabela.getSimbolo(lexTempID);
        if(AnalisadorLexico.tabela.getSimbolo(lexTempID).getClasse()==""){ //olha se o ID já foi declarado
                AnalisadorLexico.tabela.getSimbolo(lexTempID).setClasse("const");
                AnalisadorLexico.tabela.getSimbolo(lexTempID).setTipo(tipoDoValorConstante);
                Buffer.buffer.add(";"+lexTempID);
                //fazer alocação para cada tipo
        }else{
                System.out.println(AnalisadorLexico.contaLinha+":identificador ja declarado ["+AnalisadorLexico.tabela.getSimbolo(lexTempID).getLexema()+"]");
                System.exit(0);
        }
        
        if(flagNegativo) {
            if (tipoDoValorConstante=="tipo_string"|| tipoDoValorConstante=="tipo_logico"){
                System.out.println(AnalisadorLexico.contaLinha + ":tipos incompativeis");
                System.exit(0);
            } else if (tipoDoValorConstante=="tipo_byte") {
                tipoDoValorConstante="tipo_inteiro";
                
            }
        } else if (flagPositivo) {
            if (tipoDoValorConstante=="tipo_string"|| tipoDoValorConstante=="tipo_logico"){
                System.out.println(AnalisadorLexico.contaLinha + ":tipos incompativeis");
                System.exit(0);
            }
        } else {
         AnalisadorLexico.tabela.getSimbolo(lexTempID).setTipo(tipoDoValorConstante);
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
            String lexTempID=registro.getLexema();
            CasaToken( IDENTIFICADOR );
            if(AnalisadorLexico.tabela.getSimbolo(lexTempID).getClasse()==""){
                AnalisadorLexico.tabela.getSimbolo(lexTempID).setClasse("var");
                AnalisadorLexico.tabela.getSimbolo(lexTempID).setTipo(id_tipo);
            }else{
                System.out.println(AnalisadorLexico.contaLinha+":identificador ja declarado ["+AnalisadorLexico.tabela.getSimbolo(lexTempID).getLexema()+"]");
                System.exit(0);
            }
            
            if( registro.getNumToken() == ATRIBUICAO ) {
                CasaToken( ATRIBUICAO );
                String Exp_tipo = "";
                Exp_tipo=ProcExp();
                if(id_tipo!=Exp_tipo){
                    if( !(id_tipo == "tipo_inteiro" && Exp_tipo=="tipo_byte")){
                        System.out.println(AnalisadorLexico.contaLinha+":identificador ja declarado ["+AnalisadorLexico.tabela.getSimbolo(lexTempID).getLexema()+"]");
                        System.exit(0);
                    }
                }
                alocarID(AnalisadorLexico.tabela.getSimbolo(lexTempID).getTipo(), lexTempID);
                escreveBuffer("mov al, DS:[" + exp_end + "]");
                escreveBuffer("mov DS:[" + AnalisadorLexico.tabela.getSimbolo(lexTempID).getEndereco() + "], ax");
                
            }else
                alocarID(AnalisadorLexico.tabela.getSimbolo(lexTempID).getTipo(), lexTempID);
            while( registro.getNumToken() == VIRGULA ) {
                CasaToken( VIRGULA );
                lexTempID=registro.getLexema();
                CasaToken( IDENTIFICADOR );
                
                if(AnalisadorLexico.tabela.getSimbolo(lexTempID).getClasse()==""){
                    AnalisadorLexico.tabela.getSimbolo(lexTempID).setClasse("var");
                    AnalisadorLexico.tabela.getSimbolo(lexTempID).setTipo(id_tipo);
                }else{
                    System.out.println(AnalisadorLexico.contaLinha+":identificador ja declarado ["+AnalisadorLexico.tabela.getSimbolo(lexTempID).getLexema()+"]");
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
        boolean flagNegativo=false, flagPositivo=false;
        String t_op="";
        
        if( registro.getNumToken() == SOMA ){
            CasaToken( SOMA );
            flagPositivo=true;
        }else if( registro.getNumToken() == SUBTRACAO ){
            CasaToken( SUBTRACAO );
            flagNegativo=true;
        }
        
        String t_tipo=ProcT();
        
        expS_end=t_end;
        
        /*
        if(flagNegativo && t_tipo=="tipo_byte"){
            t_tipo="tipo_inteiro";
            Exps_tipo=t_tipo;
        }else{
            Exps_tipo=t_tipo;
        }*/
  
        if(flagNegativo) {
            if (t_tipo=="tipo_string"|| t_tipo=="tipo_logico"){
                System.out.println(AnalisadorLexico.contaLinha + ":tipos incompativeis");
                System.exit(0);
            } else if (t_tipo=="tipo_byte") {
                t_tipo="tipo_inteiro";
                Exps_tipo=t_tipo;
            }
        } else if (flagPositivo) {
            if (t_tipo=="tipo_string"|| t_tipo=="tipo_logico"){
                System.out.println(AnalisadorLexico.contaLinha + ":tipos incompativeis");
                System.exit(0);
            }
        } else {
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
        exp_end=expS_end;
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
            f_end=exp_end;
            CasaToken( F_PARENT );
        }else if( registro.getNumToken() == IDENTIFICADOR ){
            String lexTemp=registro.getLexema();
            CasaToken( IDENTIFICADOR );
            Simbolo simboloTemp=AnalisadorLexico.tabela.getSimbolo(lexTemp);//busca o lex na tabela de simbolos
            f_end=simboloTemp.getEndereco();
            if(simboloTemp.getClasse()==null || simboloTemp.getClasse()==""){
                System.out.println(AnalisadorLexico.contaLinha+":identificador nao declarado ["+simboloTemp.getLexema()+"]");
                System.exit(0);
            }else
                F_tipo=simboloTemp.getTipo();
        }else if( registro.getNumToken() == VALORCONSTANTE ){
            String LexemaConst=registro.getLexema(); //armazena o lex antes de passar pelo CT
            CasaToken( VALORCONSTANTE );
             // pega o enderco da constante e passa para t
            //regra 02
            //pega o tipo da tabela porque o programa so terá ele depois de passar pelo CT e inserir na tabela
            F_tipo=AnalisadorLexico.tabela.getSimbolo(LexemaConst).getTipo();
            
            if(F_tipo!="tipo_string"){
                f_end = memoria.alocarTemp(F_tipo);
                escreveBuffer("mov ax, " + LexemaConst + " ; const " + LexemaConst);
                escreveBuffer("mov DS:[" + f_end + "], al");
            }
            
        }else if(registro.getNumToken() == TRUE){
            CasaToken(TRUE);
            F_tipo="tipo_logico";
            f_end = memoria.alocarTemp(F_tipo);
            escreveBuffer("mov al, 0ffh ; const true");
            escreveBuffer("mov DS:[" + f_end + "], al");
        }else if(registro.getNumToken() == FALSE){
            CasaToken(FALSE);
            F_tipo="tipo_logico";
            f_end = memoria.alocarTemp(F_tipo);
            escreveBuffer("mov al, 0h ; const false");
            escreveBuffer("mov DS:[" + f_end + "], al");
        }else{
            CasaToken( NOT );
            String f1_tipo=ProcF();
            if(f1_tipo=="tipo_logico")
                F_tipo=f1_tipo;
            else
                System.out.println(automato.contaLinha+":ERRO, tipos imcompativeis ");
            
            //f_end = procF
            escreveBuffer("mov al, DS:[" + f_end + "] ;");
	    escreveBuffer("not al");
            escreveBuffer("mov DS:[" + f_end + "], al");
        }
        
        return F_tipo;
    }//fim ProcF

    public String ProcT() throws IOException {
        String T_tipo="";
        
        T_tipo=ProcF();
        t_end=f_end;
        
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
                System.out.println(AnalisadorLexico.contaLinha+":classe de identificador incompativel ["+simboloID.getLexema()+"]");
                System.exit(0);
            }
            
            
            CasaToken( IDENTIFICADOR );
            CasaToken( ATRIBUICAO );
            String exp_tipo=ProcExp();
            //pegar exp_end
            escreveBuffer("mov al, DS:[" + exp_end + "]");
            
            if(exp_tipo.equals("tipo_byte") || exp_tipo.equals("tipo_logico")){
				escreveBuffer("mov DS:[" + simboloID.getEndereco() + "], al; armazena byte");
            }
            else
                escreveBuffer("mov DS:[" + simboloID.getEndereco() + "], ax;");
            
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
            
            String RotuloFalso = rotulo.novoRotulo();
            String RotuloFim = rotulo.novoRotulo();
            
            String exp_Tipo=ProcExp();
            if(exp_Tipo!="tipo_logico"){
                System.out.println(AnalisadorLexico.contaLinha+":tipos incompativeis");
                System.exit(0);
            }
            
            escreveBuffer("mov ax, DS:[" + exp_end + "]");
			
            escreveBuffer("cmp al, 0");
			
            escreveBuffer("je " + RotuloFalso+"; rotulo falso");
			
            
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
                escreveBuffer("jmp " + RotuloFim);
				
		escreveBuffer(RotuloFalso + ":");
                if( registro.getNumToken() == BEGIN ){
                    CasaToken(BEGIN);
                    do {
                        ProcC();
                    } while( registro.getNumToken() == IDENTIFICADOR || registro.getNumToken() == WHILE || registro.getNumToken() == IF || registro.getNumToken() == WRITE || registro.getNumToken() == WRITELN || registro.getNumToken() == READLN || registro.getNumToken() == PONTO_VIRG);
                    
                    CasaToken( END );
                } else
                    ProcC();
                
                escreveBuffer(RotuloFim + ":");
            }else
                		escreveBuffer(RotuloFalso + ":");

            
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
            
            int stringEnd = memoria.novoTemp();
            
            if(exp_Tipo.equals("tipo_string")){
				escreveBuffer("mov dx, " + exp_end);
				
				escreveBuffer("mov ah, 09h");
				
				escreveBuffer("int 21h");
				
				
			}else{
				escreveBuffer("mov ax, DS:[" + exp_end + "] ; endereco da variavel que vai ser impressa");
				escreveBuffer("mov di, " + stringEnd + " ;end. string temp.");
				
				escreveBuffer("mov cx, 0 ;contador");
				
				escreveBuffer("cmp ax,0 ;verifica sinal");
				
				String rot = rotulo.novoRotulo();
				escreveBuffer("jge " + rot + " ;salta se numero positivo");
				
				escreveBuffer("mov bl, 2Dh ;senao, escreve sinal ");
				
				escreveBuffer("mov ds:[di], bl");
				
				escreveBuffer("add di, 1 ;incrementa indice");
				
				escreveBuffer("neg ax ;toma modulo do numero");
				
				escreveBuffer(rot + ":");
				
				escreveBuffer("mov bx, 10 ;divisor");
				
				String rot1 = rotulo.novoRotulo();
				escreveBuffer(rot1 + ":");
				
				escreveBuffer("add cx, 1 ;incrementa contador");
				
				escreveBuffer("mov dx, 0 ;estende 32bits p/ div.");
				
				escreveBuffer("idiv bx ;divide DXAX por BX");
				
				escreveBuffer("push dx ;empilha valor do resto");
				
				escreveBuffer("cmp ax, 0 ;verifica se quoc.  0");
				
				escreveBuffer("jne " + rot1 + " ;se nao  0, continua");
								
				String rot2 = rotulo.novoRotulo();
				escreveBuffer(rot2 + ":");
				
				escreveBuffer("pop dx ;desempilha valor");
				
				escreveBuffer("add dx, 30h ;transforma em caractere");
				
				escreveBuffer("mov ds:[di],dl ;escreve caractere");
				
				escreveBuffer("add di, 1 ;incrementa base");
				
				escreveBuffer("add cx, -1 ;decrementa contador");
				
				escreveBuffer("cmp cx, 0 ;verifica pilha vazia");
				
				escreveBuffer("jne " + rot2 + " ;se nao pilha vazia, loop");
				
				escreveBuffer("mov dl, 024h ;fim de string");
				escreveBuffer("mov ds:[di], dl ;grava '$'");
				
				escreveBuffer("mov dx, " + stringEnd);
				
				escreveBuffer("mov ah, 09h");
				
				escreveBuffer("int 21h");
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