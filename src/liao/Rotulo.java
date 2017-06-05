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
public class Rotulo {
	static int contador;
	
	public Rotulo(){
		contador = 0;
	}
	
	public void resetRotulo(){
		contador = 0;
	}
	
	public String novoRotulo(){
		return "R" + contador++;
	}
}