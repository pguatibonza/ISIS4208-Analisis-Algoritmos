package Tarea1;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Nodo {
	private String nombre;
    
    private List<Nodo> caminoCorto = new LinkedList<>();
    
    private Integer costo = Integer.MAX_VALUE;
    
    Map<Nodo, Integer> nodosAdyacentes = new HashMap<>();
    
    public Nodo(String nombre) {
        this.nombre = nombre;
    }

    public void agregarDestino(Nodo pDestino, int pCosto) {
    	nodosAdyacentes.put(pDestino, pCosto);
    }
 
    public void definirCosto(int pCosto) {
    	this.costo = pCosto;
    }
    
    public int darCosto() {
    	return this.costo;
    }
    
    public Map<Nodo, Integer> darNodosAdyacentes(){
    	return this.nodosAdyacentes;
    }
    
    public List<Nodo> darCaminoCorto() {
    	return this.caminoCorto;
    }
    
    public void definirCaminoCorto(List<Nodo> pCamino) {
    	this.caminoCorto = pCamino;
    }
    
    public String darNombre() {
    	return this.nombre;
    }
}
