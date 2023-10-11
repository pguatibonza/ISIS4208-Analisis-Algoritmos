package tarea5;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class App {
	public static void main(String[] args) throws IOException {
		Scanner sc = new Scanner(System.in);
		int cant = 0;
		while(true) {
			System.out.println("Ingrese 1 para método directo, 2 para usar la transformada de Fourier");
			cant = sc.nextInt();
			if(cant == 1) {
				metodoDirecto();
				System.exit(0);
			}
			else if(cant == 2) {
				metodoFFT();
				System.exit(0);
			}
			System.out.println("Valor incorrecto");
		}
		
	}
	
	public static void metodoDirecto() throws IOException {
		long timeStart = System.currentTimeMillis();
		File file = new File("./input/input.txt");
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
        String line = reader.readLine();
        
        ArrayList<Integer> pol1 = new ArrayList<Integer>();
        ArrayList<Integer> pol2 = new ArrayList<Integer>();
        
        while (line != null) {
        	String[] nums = line.split(" ");
        	pol1.add(Integer.parseInt(nums[0]));
        	pol2.add(Integer.parseInt(nums[1]));
            line = reader.readLine();
        }
        
        List<Integer> result = Arrays.asList(new Integer[pol1.size()*2]);
        
        int desfase = 0;
        for(int i=0 ; i < pol1.size() ; i++) {
        	for(int j=0 ; j < pol2.size() ; j++) {
            	if(result.get(j+desfase) != null) {
            		int temp = result.get(j+desfase);
            		result.set(j+desfase, temp+(pol1.get(i)*pol2.get(j)));
            	}
            	else {
            		result.set(j+desfase, pol1.get(i)*pol2.get(j));
            	}
            }
        	desfase ++;
        }
        
        System.out.println("El polinomio resultante es:");
        System.out.println();
        for(int i=0 ; i < result.size() ; i++) {
        	if(result.get(i) != null) {
            	System.out.print(result.get(i)+" ");
        	}
        }
        System.out.println();
        System.out.println();
        
		long timeEnd = System.currentTimeMillis();
		System.out.println("El tiempo tomado en milisegundos fue de: " + ((timeEnd - timeStart)));
	}
	
	public static void metodoFFT() throws IOException {

		long timeStart = System.currentTimeMillis();
		File file = new File("./input/input.txt");
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
        String line = reader.readLine();
        
        //Implementar
        
		long timeEnd = System.currentTimeMillis();
		System.out.println("El tiempo tomado en segundos fue de: " + ((timeEnd - timeStart)/1000));
	}
}
