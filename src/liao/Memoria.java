/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package liao;

/**
 *
 * @author mateus
 */

public class Memoria {
	public static int contador;
	public static int contTemp;
	
	public Memoria(){
		contador = 0;
		contTemp = 0;
	}
	
	public void restetTemp(){
		contTemp = 0;
	}
	
	public int alocarTemp(){
		int tmp = contador;
		contador += 16384;
		return tmp;
	}
	
	public int alocarByte(){
		int tmp = contador;
		contador++;
		return tmp;
	}
	
	public int alocarLogico(){
		int tmp = contador;
		contador++;
		return tmp;
	}
	
	public int alocarInteiro(){
		int tmp = contador;
		contador += 2;
		return tmp;
	}
	
	public int alocarString(){
		int tmp = contador;
		contador += 256;
		return tmp;
	}
	
	public int alocarString(int tam){
		int tmp = contador;
		contador += tam;
		return tmp;
	}
	
	public int novoTemp(){
		return contTemp;
	}
	
	public int alocarTempByte(){
		int tmp = contTemp;
		contTemp++;
		return tmp;
	}
	
	public int alocarTempLogico(){
		int tmp = contTemp;
		contTemp++;
		return tmp;
	}
	
	public int alocarTempInteiro(){
		int tmp = contTemp;
		contTemp += 2;
		return tmp;
	}
	
	public int alocarTempString(){
		int tmp = contTemp;
		contTemp += 256;
		return tmp;
	}
        
        public int alocarTemp(String tipo){
            int tmp=contTemp;
            switch(tipo){
                case "tipo_byte":
                        tmp=alocarTempByte();
                        break;
                case "tipo_inteiro":
                        tmp=alocarTempInteiro();
                        break;
                case "tipo_logico":
                        tmp=alocarTempByte();
                        break;
                case "tipo_string":
                        tmp=alocarTempString();
                        break;
                default:
                    System.out.println("Erro ao alocar memoria temp");
                    break;
                        
            }
            return tmp;
        }
}