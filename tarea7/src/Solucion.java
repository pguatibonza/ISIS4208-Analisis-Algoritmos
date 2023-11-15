import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Set;

public class Solucion {
    public static void main(String[] args) throws IOException{
        InputStreamReader in = new InputStreamReader(System.in);
        BufferedReader br = new BufferedReader(in);

        String line = br.readLine();
        Graph graph = new Graph();
        while(line.length()>1){
            String numbers[] = line.split("\\s+"); // \\s+ es para que tome cualquier cantidad de espacios. No se usa \t porque no funcionó 
            System.out.println(numbers[0]+" "+numbers[1]);
            int a = Integer.parseInt(numbers[0]);
            int b = Integer.parseInt(numbers[1]);
            graph.addEdge(a, b);
            line=br.readLine();
        }
        int algoritmo = Integer.parseInt(line);

        switch(algoritmo){
            case 1:
                System.out.println("1");
                printSolution(graph.firstAlgorithm());
                break;
            case 2:
                System.out.println("2");
               
                break;
            case 3:
                System.out.println("3");
                
                break;
            case 4:
                System.out.println("4");
                
                break;
            default:
                System.out.println("Algoritmo no válido");
                break;
        }
        
    }

    private static void printSolution(Set<Integer> algorithm) {
        for (Integer integer : algorithm) {
            System.out.print(integer+" ");
        }
        System.out.println();
    }
}
