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
import java.util.Collections;
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
		File file = new File("tarea5/input/10.txt");
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
		File file = new File("tarea5/input/10.txt");//cambiar
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
        String line = reader.readLine();

		ArrayList<Double> pol1 = new ArrayList<Double>();
        ArrayList<Double> pol2 = new ArrayList<Double>();
        
        while (line != null) {
        	String[] nums = line.split(" ");
        	pol1.add(Double.parseDouble(nums[0]));
        	pol2.add(Double.parseDouble(nums[1]));
            line = reader.readLine();
        }
	
		//Aplicar fft a polinomios
		List<Complex> pol1FFT=aplicarFFT(pol1);
		List<Complex> pol2FFT=aplicarFFT(pol2);

		  // Multiplicación en el dominio de la frecuencia
		List<Complex> pol3FFT = new ArrayList<Complex>();
		for (int i = 0; i < pol1FFT.size(); i++) {
			pol3FFT.add(pol1FFT.get(i).multiply(pol2FFT.get(i)));
		}

		// Aplicar la transformada inversa de Fourier
		List<Complex> pol3 = new ArrayList<Complex>();
		pol3=ifft(pol3FFT);

		//imprimir la transformada
		for (int i = 0; i < pol3.size(); i++) {
			System.out.println(pol3.get(i));
		}



    
        
		long timeEnd = System.currentTimeMillis();
		System.out.println("El tiempo tomado en milisegundos fue de: " + ((timeEnd - timeStart)));
	}
	public static List<Complex> aplicarFFT(ArrayList<Double> coefficients){
		int n = coefficients.size();
		int paddedSize = 1 << (int) Math.ceil(Math.log(n) / Math.log(2));//Tamaño de la FFT debe ser potencia de 2

		// Rellenar con ceros
		for (int i = 0; i < paddedSize - n; i++) {
			coefficients.add(0.0);
		}

        List<Complex> complexCoefficients = new ArrayList<>();
        for (Double coefficient : coefficients) {
            complexCoefficients.add(new Complex(coefficient, 0));
        }

        // Aplicar la FFT
        complexCoefficients=fft(complexCoefficients);

        return complexCoefficients;
	} 

	public  static List<Complex> fft(List<Complex> a) {
        int n = a.size();

        if (n == 1) {
			List<Complex> result=new ArrayList<Complex>(1);
			result.add(a.get(0));
            return result;
        }

        // Dividir en pares e impares
        List<Complex> even = new ArrayList<Complex>(n/2);
        for (int i = 0; i < n / 2; i++) {
            even.add(i,a.get(2 * i));  
        }
		List<Complex> evenFFT=fft(even);

		List<Complex> odd=new ArrayList<Complex>(n/2);
		for(int j=0;j<n/2;j++){
			odd.add(j,a.get(2*j +1));
		}
		List<Complex> oddFFT=fft(odd);

        List<Complex> result=new ArrayList<Complex>(n);
		for(int k=0;k<n;k++){
			result.add(k,new Complex(0,0));
		}
		for(int k=0;k<n/2;k++){
			double kth=-2*k*Math.PI/n;
			Complex wk=new Complex(Math.cos(kth),Math.sin(kth));
			result.set(k,evenFFT.get(k).add(wk.multiply(oddFFT.get(k))));
			result.set(k+ n/2,evenFFT.get(k).subtract(wk.multiply(oddFFT.get(k))));
		}
		return result;
	}
	public static List<Complex> ifft(List<Complex> x) {
        int n = x.size();

		for (int i = 0; i < n; i++) {
			x.set(i, x.get(i).conjugate());
		}
        List<Complex> y = fft(x);
		
		for(int i=0;i<n;i++){
			y.set(i, y.get(i).conjugate());
		}
		for (int i = 0; i < n; i++) {
			y.set(i, y.get(i).divide(n));
		}
        return y;
    }

}
