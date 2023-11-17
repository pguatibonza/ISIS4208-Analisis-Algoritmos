import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class generator {
	public static void main(String[] args) throws IOException {

		Scanner sc = new Scanner(System.in);
		int cant = 0;
		System.out.println("Ingrese el grado del polinomio a generar:");
		cant = sc.nextInt();
		BufferedWriter writer = new BufferedWriter(new FileWriter("./output.txt"));
		for(int i=0; i < cant; i++) {
			double ran = Math.random();
			int num1 = i;
			int num2 = (int)(Math.random()*cant);
			writer.write(num1 + "	" + num2 + "\n");
			while(ran < 0.8) {
				num2 = (int)(Math.random()*cant);
				ArrayList<Integer> selected = new ArrayList<Integer>();
				while(selected.contains(num2)) {
					num2 = (int)(Math.random()*cant);
				}
				writer.write(num1 + "	" + num2 + "\n");
				ran = Math.random();
			}
		}
		writer.close();
		sc.close();
	}
}
