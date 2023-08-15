package Tarea1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class ejecutor {

	public static Grafo calcularCaminoCortoDesdeOrigen(Grafo grafo, String nombreOrigen) {

	    Set<Nodo> settledNodes = new HashSet<>();
	    Set<Nodo> unsettledNodes = new HashSet<>();
	    Nodo origen = grafo.buscarNodo(nombreOrigen);
		origen.definirCosto(0);

	    unsettledNodes.add(origen);

	    while (unsettledNodes.size() != 0) {
	        Nodo currentNode = darNodoMenorDistancia(unsettledNodes);
	        unsettledNodes.remove(currentNode);
	        for (Entry<Nodo, Integer> adjacencyPair: 
	          currentNode.darNodosAdyacentes().entrySet()) {
	        	Nodo adjacentNode = adjacencyPair.getKey();
	            Integer edgeWeight = adjacencyPair.getValue();
	            if (!settledNodes.contains(adjacentNode)) {
	                calcularDistanciaMinima(adjacentNode, edgeWeight, currentNode);
	                unsettledNodes.add(adjacentNode);
	            }
	        }
	        settledNodes.add(currentNode);
	    }
	    return grafo;
	}
	
	private static Nodo darNodoMenorDistancia(Set<Nodo> nodosNoDefinidos) {
		Nodo nodoMenorCosto = null;
	    int menorCosto = Integer.MAX_VALUE;
	    for (Nodo nodo: nodosNoDefinidos) {
	        int nodoCosto = nodo.darCosto();
	        if (nodoCosto < menorCosto) {
	        	menorCosto = nodoCosto;
	        	nodoMenorCosto = nodo;
	        }
	    }
	    return nodoMenorCosto;
	}
	
	private static void calcularDistanciaMinima(Nodo nodo,
	  Integer costo, Nodo origen) {
	    Integer costoOrigen = origen.darCosto();
	    if (costoOrigen + costo < nodo.darCosto()) {
	    	nodo.definirCosto(costoOrigen + costo);
	        LinkedList<Nodo> caminoCorto = new LinkedList<>(origen.darCaminoCorto());
	        caminoCorto.add(origen);
	        nodo.definirCaminoCorto(caminoCorto);
	    }
	}
	public static void main(String[] args) throws FileNotFoundException, IOException {

		String filename = "./src/input/vuelos.txt";
		Map<String, Nodo> nodos = new HashMap<String, Nodo>();
		int flag=0;
		String origen = "";
		String destino = "";
		Grafo grafo;
		
		try (FileReader reader = new FileReader(filename);
			BufferedReader in = new BufferedReader(reader)) {
			String line = in.readLine();
			while (line != null) {
				if(line.equals("")) {
					flag=1;
				}
				if(flag==0) {
					String[] vuelo = line.split("-");
					if(nodos.get(vuelo[0]) == null) {
						Nodo nodo = new Nodo(vuelo[0]);
						Map<String, Nodo> nodes = exists(nodo, vuelo[1], Integer.parseInt(vuelo[2]), nodos);
						nodos = nodes;
					}
					else {
						Nodo act = nodos.get(vuelo[0]);
						Map<String, Nodo> nodes = exists(act, vuelo[1], Integer.parseInt(vuelo[2]), nodos);
						nodos = nodes;
					}
				}
				else if(flag==1) {
					if(line.startsWith("origen")) {
						String[] inicio = line.split(":");
						origen = inicio[1];
						flag=2;
					}
				}
				else if(flag==2){
					if(line.startsWith("destino")) {
						String[] fin = line.split(":");
						destino = fin[1];
					}
				}
				line = in.readLine();
			}
			grafo = poblarGrafo(nodos, origen);
			Nodo caminoCorto = null;
			for ( Nodo nodo : grafo.getNodos() ) {
				if(nodo.darNombre().equals(destino)) {
					caminoCorto = nodo;
				}
			}
			System.out.println("El camino corto desde " + origen + " hasta " + destino + " es:");
			for(Nodo nodo: caminoCorto.darCaminoCorto()) {
				if(nodo.darNombre().equals(origen)) {
					System.out.print("Desde ");
				}
				else {
					System.out.print("A ");
				}
				System.out.println(nodo.darNombre() + " con un costo acumulado de: " + nodo.darCosto());
			}
			System.out.println("A " + caminoCorto.darNombre() + " por un costo total de: " + caminoCorto.darCosto());
		}
	}
	
	public static Grafo poblarGrafo(Map<String, Nodo> nodos, String origen) {
		Grafo grafo = new Grafo();
		
		for ( Nodo nodo : nodos.values() ) {
			grafo.agregarNodo(nodo);
		}
		grafo = calcularCaminoCortoDesdeOrigen(grafo, origen);
		return grafo;
	}
	
	public static Map<String, Nodo> exists(Nodo vuelo0, String vuelo1, int val, Map<String, Nodo> nodos) {
		Nodo act2 = nodos.get(vuelo1);
		if(act2 == null) {
			Nodo nodo2 = new Nodo(vuelo1);
			vuelo0.agregarDestino(nodo2, val);
			nodos.put(vuelo0.darNombre(), vuelo0);
			nodos.put(vuelo1, nodo2);
		}
		else {
			vuelo0.agregarDestino(act2, val);
			nodos.put(vuelo0.darNombre(), vuelo0);
		}
		return nodos;
	}
	
	/*
	public static void main(String[] args) {
		Nodo nodeA = new Nodo("A");
		Nodo nodeB = new Nodo("B");
		Nodo nodeC = new Nodo("C");
		Nodo nodeD = new Nodo("D"); 
		Nodo nodeE = new Nodo("E");
		Nodo nodeF = new Nodo("F");

		nodeA.agregarDestino(nodeB, 10);
		nodeA.agregarDestino(nodeC, 15);

		nodeB.agregarDestino(nodeD, 12);
		nodeB.agregarDestino(nodeF, 15);

		nodeC.agregarDestino(nodeE, 10);

		nodeD.agregarDestino(nodeE, 2);
		nodeD.agregarDestino(nodeF, 1);

		nodeF.agregarDestino(nodeE, 5);

		Grafo graph = new Grafo();

		graph.agregarNodo(nodeA);
		graph.agregarNodo(nodeB);
		graph.agregarNodo(nodeC);
		graph.agregarNodo(nodeD);
		graph.agregarNodo(nodeE);
		graph.agregarNodo(nodeF);

		graph = calcularCaminoCortoDesdeOrigen(graph, nodeA.darNombre());
		for( Nodo nodo : graph.getNodos() ) {
			System.out.println(nodo.darNombre());
			System.out.println("Camino corto[");
			for( Nodo nodo2 : nodo.darCaminoCorto() ) {
				System.out.println(nodo2.darNombre()+"-"+nodo2.darCosto());
			}
			System.out.println("]");
			System.out.println("Adyacentes{");
			for( Nodo nodo3 : nodo.darNodosAdyacentes().keySet() ) {
				System.out.println(nodo3.darNombre()+"-"+nodo3.darCosto());
			}
			System.out.println("}");
		}
	}
	*/
}
