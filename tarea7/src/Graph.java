import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
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
            
            int random_vertex = (int) (Math.random() * (adjacencyList.size()) + 1);
            while (!adjacencyList.containsKey(random_vertex)) {
                random_vertex = (int) (Math.random() * (adjacencyList.size()) + 1);
            }

            List<Integer> list = adjacencyList.get(random_vertex);
            int random_index = (int) (Math.random() * (list.size() - 1));
            int random_vertex2 = list.get(random_index);

            // Incluir vertices conectados
            coveredVertices.add(random_vertex);
            coveredVertices.add(random_vertex2);

            // Descartar ejes de los vertices escogidos
            adjacencyList.remove(random_vertex);
            adjacencyList.remove(random_vertex2);
        }
        return coveredVertices;

    }

}
