import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Set;

public class Solucion {
    public static void main(String[] args) throws IOException{
    	//File file = new File("./src/test.in");
        //BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
        InputStreamReader in = new InputStreamReader(System.in);
        BufferedReader br = new BufferedReader(in);

        String line = br.readLine();
        Graph graph = new Graph();
        while(line.length()>1){
            String numbers[] = line.split("\\s+"); // \\s+ es para que tome cualquier cantidad de espacios. No se usa \t porque no funcionó 
            int a = Integer.parseInt(numbers[0]);
            int b = Integer.parseInt(numbers[1]);
            graph.addEdge(a, b);
            graph.addEdge(b, a);
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
                printSolution(graph.secondAlgorithm());
                break;
            case 3:
                System.out.println("3");
                printSolution(graph.thirdAlgorithm());
                break;
            case 4:
                System.out.println("4");
                printSolution(graph.fourthAlgorithm());
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
