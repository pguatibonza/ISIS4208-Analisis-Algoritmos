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
                List<Integer> list2 = adjacencyList.get(randomVertex2);
                list2.remove(Integer.valueOf(randomVertex2)); // Eliminar conexión desde randomVertex
                if (list2.isEmpty()) {
                    adjacencyList.remove(randomVertex2);
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

                // Añadir vértices al conjunto coveredVertices
                coveredVertices.add(randomVertex);
                coveredVertices.add(randomVertex2);

                // Descartar ejes del vértice escogido
                adjacencyList.remove(randomVertex);

                // Eliminar conexión inversa (no dirigido)
                list.remove(Integer.valueOf(randomVertex2));
                if (list.isEmpty()) {
                    adjacencyList.remove(randomVertex);
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

                // Añadir vértice
                coveredVertices.add(selectedVertex);

                // Descartar ejes conectados por el vértice escogido
                adjacencyList.remove(selectedVertex);

                // Eliminar conexión inversa (no dirigida)
                list.remove(Integer.valueOf(selectedVertex));
                if (list.isEmpty()) {
                    adjacencyList.remove(randomVertex);
                }
            }
        }

        return coveredVertices;
    }

}
