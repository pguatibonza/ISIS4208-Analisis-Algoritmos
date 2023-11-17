import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class Graph {
    // La numeración de nodos comienza desde 1
    private Map<Integer, List<Integer>> adjacencyList;

    public Graph() {
        adjacencyList = new HashMap<Integer, List<Integer>>();
    }

    public void addEdge(int vertex1, int vertex2) {
        if (adjacencyList.containsKey(vertex1)) {
            List<Integer> list = adjacencyList.get(vertex1);
            list.add(vertex2);
            adjacencyList.put(vertex1, list);
        } else {

            List<Integer> list = new ArrayList<Integer>();
            list.add(vertex2);
            adjacencyList.put(vertex1, list);
        }
    }

    public Set<Integer> firstAlgorithm() {
        
        Set<Integer> coveredVertices = new HashSet<>();

        while (!adjacencyList.isEmpty()) {
            // Seleccionar eje aleatorio
            List<Integer> keys = new ArrayList<>(adjacencyList.keySet());
            Random random = new Random();
            int randomIndex = random.nextInt(keys.size());
            int randomVertex = keys.get(randomIndex);

            List<Integer> list = adjacencyList.get(randomVertex);
            if (list.size() > 0) {
                int randomIndex2 = random.nextInt(list.size());
                int randomVertex2 = list.get(randomIndex2);

                // Incluir vertices conectados
                coveredVertices.add(randomVertex);
                coveredVertices.add(randomVertex2);

                // Descartar ejes de los vertices escogidos
                list.remove(Integer.valueOf(randomVertex)); // Eliminar conexión desde randomVertex2
                if (list.isEmpty()) {
                    adjacencyList.remove(randomVertex);
                }

                // Actualizar la conexión inversa (no dirigida)
                for(Integer vertex: list){
                    List<Integer> list2 = adjacencyList.get(vertex);
                    list2.remove(Integer.valueOf(randomVertex));
                    
                    if (list2.isEmpty()) {
                        adjacencyList.remove(vertex);
                    }
                    else{
                        adjacencyList.put(vertex, list2);
                    }
                }
            }
        }

        return coveredVertices;

    }

    public Set<Integer> secondAlgorithm() {
        Set<Integer> coveredVertices = new HashSet<>();
        while (!adjacencyList.isEmpty()) {

            // vertice de mayor grado
            int maxDegreeVertex = adjacencyList.keySet().iterator().next();
            int maxDegree = 0;
            for (Integer vertex : adjacencyList.keySet()) {
                if (adjacencyList.get(vertex).size() > maxDegree && !coveredVertices.contains(vertex)) {
                    maxDegree = adjacencyList.get(vertex).size();
                    maxDegreeVertex = vertex;
                }
            }

            // descartar ejes que llegan al vertice escogido
            adjacencyList.remove(maxDegreeVertex);
            
            List<Integer> list = adjacencyList.get(maxDegreeVertex);
            // Eliminar conexión inversa (no dirigido)
            for(Integer vertex: list){
                List<Integer> list2 = adjacencyList.get(vertex);
                list2.remove(Integer.valueOf(maxDegreeVertex));
                
                if (list2.isEmpty()) {
                    adjacencyList.remove(vertex);
                }
                else{
                    adjacencyList.put(vertex, list2);
                }
            }

            //añadir vertice escogido
            coveredVertices.add(maxDegreeVertex);
        }
        return coveredVertices;
    }

    public Set<Integer> thirdAlgorithm() {
        Set<Integer> coveredVertices = new HashSet<>();

        while (!adjacencyList.isEmpty()) {
            // Seleccionar eje aleatorio
            List<Integer> keys = new ArrayList<>(adjacencyList.keySet());
            Random random = new Random();
            int randomIndex = random.nextInt(keys.size());
            int randomVertex = keys.get(randomIndex);

            List<Integer> list = adjacencyList.get(randomVertex);
            if (list.size() > 0) {
                int randomIndex2 = random.nextInt(list.size());
                int randomVertex2 = list.get(randomIndex2);

                int degree=list.size();
                int degree2=0;

                if (adjacencyList.containsKey(randomVertex2) && adjacencyList.get(randomVertex2)!=null) {
                    degree2 = adjacencyList.get(randomVertex2).size();
                }
                int selectedVertex = degree2>degree?randomVertex2:randomVertex;
                list=adjacencyList.get(selectedVertex);

                // Añadir vertice al conjunto coveredVertices
                coveredVertices.add(selectedVertex);

                // Descartar ejes del vértice escogido
                adjacencyList.remove(selectedVertex);

                // Eliminar conexión inversa (no dirigido)
                for(Integer vertex: list){
                    List<Integer> list2 = adjacencyList.get(vertex);
                    list2.remove(Integer.valueOf(randomVertex));
                    
                    if (list2.isEmpty()) {
                        adjacencyList.remove(vertex);
                    }
                    else{
                        adjacencyList.put(vertex, list2);
                    }
                }
            }
        }

        return coveredVertices;
    }

    public Set<Integer> fourthAlgorithm() {
        Set<Integer> coveredVertices = new HashSet<>();

        while (!adjacencyList.isEmpty()) {
            // Seleccionar eje aleatorio
            List<Integer> keys = new ArrayList<>(adjacencyList.keySet());
            Random random = new Random();
            int randomIndex = random.nextInt(keys.size());
            int randomVertex = keys.get(randomIndex);

            List<Integer> list = adjacencyList.get(randomVertex);
            if (list.size() > 0) {
                int randomIndex2 = random.nextInt(list.size());
                int randomVertex2 = list.get(randomIndex2);

                // Seleccionar aleatoriamente uno de los dos vértices
                int selectedVertex = random.nextBoolean() ? randomVertex : randomVertex2;
                list=adjacencyList.get(selectedVertex);

                // Añadir vértice
                coveredVertices.add(selectedVertex);

                // Descartar ejes conectados por el vértice escogido
                adjacencyList.remove(selectedVertex);

                // Eliminar conexión inversa (no dirigida)
                for(Integer vertex: list){
                    List<Integer> list2 = adjacencyList.get(vertex);
                    list2.remove(Integer.valueOf(randomVertex));
                    adjacencyList.put(vertex, list2);
                    if (list2.isEmpty()) {
                        adjacencyList.remove(vertex);
                    }
                    else{
                        adjacencyList.put(vertex, list2);
                    }
                }
            }
        }

        return coveredVertices;
    }

}
