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
            List keys = new ArrayList<>(adjacencyList.keySet());
            Random random = new Random();
            int randomIndex = random.nextInt(keys.size());
            int randomVertex = (int) keys.get(randomIndex);
            List<Integer> list = adjacencyList.get(randomVertex);

            if (list.size() > 0) {
                int randomIndex2 = (int) (Math.random() * (list.size() - 1));
                int randomVertex2 = list.get(randomIndex2);
                // Incluir vertices conectados
                coveredVertices.add(randomVertex);
                coveredVertices.add(randomVertex2);

                // Descartar ejes de los vertices escogidos
                adjacencyList.remove(randomVertex);
                adjacencyList.remove(randomVertex2);

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

            List<Integer> empty=new ArrayList<>();
            // descartar ejes que llegan al vertice escogido
            for (Integer vertex : adjacencyList.keySet()) {
                List<Integer> list = adjacencyList.get(vertex);
                for (int i = 0; i < list.size(); i++) {
                    if (list.get(i) == maxDegreeVertex) {
                        list.remove(i);
                    }
                    
                }
                if (list.isEmpty()) {
                  empty.add(vertex);
                }
                adjacencyList.put(vertex, list);
            }
            //eliminar vertices sin ejes
            for (Integer integer : empty) {
                adjacencyList.remove(integer);
            }
            //añadir vertice escogido
            coveredVertices.add(maxDegreeVertex);
        }
        return coveredVertices;
    }

    public Set<Integer> thirdAlgorithm() {
        Set<Integer> coveredVertices = new HashSet<Integer>();
        while (!adjacencyList.isEmpty()) {
            // Seleccionar eje aleatorio
            List keys = new ArrayList<>(adjacencyList.keySet());
            Random random = new Random();
            int randomIndex = random.nextInt(keys.size());
            int randomVertex = (int) keys.get(randomIndex);
            List<Integer> list = adjacencyList.get(randomVertex);
            if (list.size() > 0) {
                int randomIndex2 = (int) (Math.random() * (list.size() - 1));
                int randomVertex2 = list.get(randomIndex2);
                // Vertice de mayor grado entre los seleccionados
                int maxDegreeVertex = randomVertex;
                int size2=0;
                if (adjacencyList.get(randomVertex2) != null) {
                    size2 = adjacencyList.get(randomVertex2).size();
                }
                if (size2> list.size()) {
                    maxDegreeVertex = randomVertex2;
                }

                // añadir vertice
                coveredVertices.add(maxDegreeVertex);

                // Descartar ejes del vertice escogido
                adjacencyList.remove(maxDegreeVertex);
            }

        }

        return coveredVertices;
    }

    public Set<Integer> fourthAlgorithm() {
        Set<Integer> coveredVertices = new HashSet<Integer>();

        while(!adjacencyList.isEmpty()){
            // Seleccionar eje aleatorio
            List keys = new ArrayList<>(adjacencyList.keySet());
            Random random = new Random();
            int randomIndex = random.nextInt(keys.size());
            int randomVertex = (int) keys.get(randomIndex);
            List<Integer> list = adjacencyList.get(randomVertex);
            if (list.size() > 0) {
                int randomIndex2 = (int) (Math.random() * (list.size() - 1));
                int randomVertex2 = list.get(randomIndex2);

                //vertice aleatorio entre los seleccionados
                int selectedVertex=randomVertex;
                if(random.nextBoolean()){
                    selectedVertex=randomVertex2;
                }
                //Añadir vertice
                coveredVertices.add(selectedVertex);

                //Descartar ejes conectados por el vertice escogido
                adjacencyList.remove(selectedVertex);
            }

        }
        return coveredVertices;
    }

}
