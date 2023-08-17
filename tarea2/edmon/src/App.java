import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class App {
    public static void main(String[] args) throws Exception {
        File file = new File("input/input.txt");
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));

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

            grafo.addArco(origen, destino, capacidad);
            System.out.println("Origen: " + origen + " Destino: " + destino + " Capacidad: " + capacidad);
            grafo.addArco(destino, origen);
            System.out.println("Origen: " + destino + " Destino: " + origen);
            line = reader.readLine();
            
        }
        reader.close();
    }
}
