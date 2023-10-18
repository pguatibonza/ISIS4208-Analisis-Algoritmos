package tarea5;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class generator {
	public static void main(String[] args) throws IOException {

		Scanner sc = new Scanner(System.in);
		boolean no = true;
		int cant = 0;
		while(no) {
			System.out.println("Ingrese el grado del polinomio a generar:");
			cant = sc.nextInt();
			if(cant > -1) {
				no=false;
			}
			else {
				System.out.println("El grado debe ser mayor o igual 0");
			}
		}
		BufferedWriter writer = new BufferedWriter(new FileWriter("./input/input.txt"));
		for(int i=0; i <= cant; i++) {
			int num1 = (int)(Math.random()*30);
			int num2 = (int)(Math.random()*30);
			if(Math.random() < 0.5) {
				num1 = -num1;
			}
			if(Math.random() < 0.5) {
				num2 = -num2;
			}
			writer.write(num1 + " " + num2 + "\n");
		}
		writer.close();
	}
}
