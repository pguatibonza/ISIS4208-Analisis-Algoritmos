package main;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Nodo {
	private int id;
    
    private List<Nodo> caminoCorto = new LinkedList<>();
    
    private Integer costo = Integer.MAX_VALUE;
    
    Map<Nodo, Integer> nodosAdyacentes = new HashMap<>();
    
    public Nodo(int id) {
        this.id = id;
    }

    public void agregarDestino(Nodo pDestino, int pCosto) {
    	nodosAdyacentes.put(pDestino, pCosto);
    }
    
    public boolean isConnected(Nodo nodo){
    	if(nodosAdyacentes.containsKey(nodo)) {
    		return true;
    	}
    	return false;
    }
    
    public int darCostoRuta(Nodo nodo) {
        if (nodosAdyacentes.get(nodo) == null) {
            return 0;
        }
    	return nodosAdyacentes.get(nodo);
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
    
    public int darId() {
    	return this.id;
    }
}