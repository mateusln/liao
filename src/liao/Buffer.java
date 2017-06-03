/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package liao;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author mateus
 */
public class Buffer {
    
    public static List<String> buffer;
	public BufferedWriter arquivo;
	
	public Buffer() throws Exception{
		buffer = new ArrayList<>();
		arquivo = new BufferedWriter(new FileWriter("codigo.asm"));
	}
        
        public void criarArquivo() throws IOException{
		for(String s : buffer){
			arquivo.write(s);
			arquivo.newLine();
		}
		arquivo.close();
	}
    
}
