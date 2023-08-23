import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class App {
    public static void main(String[] args) throws Exception {
        File file = new File("./input/input.txt");
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
        ArrayList<Integer> noRepetir = new ArrayList<Integer>();

        String line = reader.readLine();
        int n;

        n = Integer.parseInt(line.split("=")[1]);
        Grafo grafo = new Grafo(n);
        line = reader.readLine();
        // Lee el archivo de entrada y lo agrega al grafo
        while (line != null) {
    
            String[] data = line.split("-");
            int origen = Integer.parseInt(data[0]);
            int destino = Integer.parseInt(data[1]);
            int capacidad = Integer.parseInt(data[2]);

            for(int i=0; i<noRepetir.size(); i+=2) {
            	if(noRepetir.get(i) == origen && noRepetir.get(i+1) == destino) {
            		System.out.println("Se encontraron tubos repetidos o en sentido contrario a uno existente");
            		System.exit(0);
            	}
            	else if(noRepetir.get(i) == destino && noRepetir.get(i+1) == origen) {
            		System.out.println("Se encontraron tubos repetidos o en sentido contrario a uno existente");
            		System.exit(0);
            	}
            }
            
            noRepetir.add(origen);
            noRepetir.add(destino);
            
            grafo.addArco(origen, destino, capacidad);
            System.out.println("Origen: " + origen + " Destino: " + destino + " Capacidad: " + capacidad);
            grafo.addArco(destino, origen);
            System.out.println("Origen: " + destino + " Destino: " + origen);
            line = reader.readLine();
            
        }
        reader.close();
        System.out.println();
        grafo.flujoMaximo(0, n-1);
        int total = 0;
        BufferedWriter writer = new BufferedWriter(new FileWriter("./output/output.txt"));
        for(ArrayList<Arco> arcoList:grafo.getAdyacentes()) {
        	for(Arco arco: arcoList) {
        		if(arco.flujo > 0) {
        			writer.write("Desde " + arco.fuente + " hasta " + arco.destino + " con flujo " + arco.flujo + " de capacidad " + arco.capacidad+"\n");
            		System.out.println("Desde " + arco.fuente + " hasta " + arco.destino + " con flujo " + arco.flujo + " de capacidad " + arco.capacidad);
        		}
        		if(arco.destino == n-1) {
        			total += arco.flujo;
        		}
        	}
        }
		writer.write("Flujo total: " + total);
        System.out.println("Flujo total: " + total);
        writer.close();
    }
}
