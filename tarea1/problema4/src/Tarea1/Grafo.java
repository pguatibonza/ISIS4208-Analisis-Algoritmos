package Tarea1;

import java.util.HashSet;
import java.util.Set;

public class Grafo {

    private Set<Nodo> nodos = new HashSet<>();
    
    public void agregarNodo(Nodo pNodo) {
        nodos.add(pNodo);
    }
    
    public Set<Nodo> getNodos(){
    	return nodos;
    }
    
    public Nodo buscarNodo(String nombre) {
    	for(Nodo nodo: nodos) {
    		if(nodo.darNombre().equals(nombre)) {
    			return nodo;
    		}
    	}
    	return null;
    }
}
