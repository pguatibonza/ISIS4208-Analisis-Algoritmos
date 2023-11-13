package main;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Grafo {

    private Set<Nodo> nodos = new HashSet<>();
    private int[][] distancias;
    
    public void agregarNodo(Nodo pNodo) {
        nodos.add(pNodo);
    }
    
    public Set<Nodo> getNodos(){
    	return nodos;
    }
    
    public Nodo getNodo(int id) {
    	for(Nodo nodo : this.nodos) {
    		if(nodo.darId() == id) {
    			return nodo;
    		}
    	}
    	return null;
    }
    public void setDistancias(int[][] pDistancias) {
    	distancias = pDistancias;
    }
    public int[][] getDistancias(){
    	return distancias;
    }

    public ArrayList<ArrayList<ArrayList<Nodo>>> floydWarshallWithPath() {
        int n = nodos.size();
        int[][] dist = new int[n][n];
        int[][] next = new int[n][n];

        // Inicializa las matrices de distancias y nodos intermedios
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i == j) {
                    dist[i][j] = 0;
                } else {
                    dist[i][j] = Integer.MAX_VALUE;
                }
                next[i][j] = -1; // -1 indica que no hay camino directo
            }
        }

        // Llena las matrices con los valores conocidos
        for (Nodo nodo : nodos) {
            int src = nodo.darId();
            for (Map.Entry<Nodo, Integer> entry : nodo.nodosAdyacentes.entrySet()) {
                int dest = entry.getKey().darId();
                int weight = entry.getValue();
                dist[src][dest] = weight;
                next[src][dest] = dest; // El nodo destino es el siguiente en el camino
            }
        }

        // Algoritmo de Floyd-Warshall
        for (int k = 0; k < n; k++) {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (dist[i][k] != Integer.MAX_VALUE && dist[k][j] != Integer.MAX_VALUE
                            && dist[i][k] + dist[k][j] < dist[i][j]) {
                        dist[i][j] = dist[i][k] + dist[k][j];
                        next[i][j] = next[i][k]; // Actualiza el nodo intermedio
                    }
                }
            }
        }
        setDistancias(dist);

        // Reconstruye los caminos más cortos
        ArrayList<ArrayList<ArrayList<Nodo>>> shortestPaths = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            ArrayList<ArrayList<Nodo>> row = new ArrayList<>();
            for (int j = 0; j < n; j++) {
                row.add(reconstructPath(i, j, next));
            }
            shortestPaths.add(row);
        }

        return shortestPaths;
    }

    private ArrayList<Nodo> reconstructPath(int i, int j, int[][] next) {
        ArrayList<Nodo> path = new ArrayList<>();
        if (next[i][j] == -1) {
            return path; // No hay camino directo
        }

        int current = i;
        while (current != j) {
            path.add(getNodo(current));
            current = next[current][j];
        }
        path.add(getNodo(j));

        return path;
    }

    
}
