package main;


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
    
    public Nodo getNodo(int id) {
    	for(Nodo nodo : this.nodos) {
    		if(nodo.darId() == id) {
    			return nodo;
    		}
    	}
    	return null;
    }
}
