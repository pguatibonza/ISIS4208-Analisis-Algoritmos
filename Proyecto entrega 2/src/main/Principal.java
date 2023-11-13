package main;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class Principal {

	private final static int RANDOM_TRIES = 10;
	private final static int VERTICES = 5;
	private final static Grafo GRAFO = new Grafo();
	private static int[][] DEMANDA = new int[VERTICES][VERTICES];

	public static void main(String[] args) {
		Nodo nodo0 = new Nodo(0);
		Nodo nodo1 = new Nodo(1);
		Nodo nodo2 = new Nodo(2);
		Nodo nodo3 = new Nodo(3);
		Nodo nodo4 = new Nodo(4);
		
		nodo0.agregarDestino(nodo1, 15);
		nodo1.agregarDestino(nodo2, 20);
		nodo1.agregarDestino(nodo3, 25);
		nodo1.agregarDestino(nodo4, 30);
		nodo2.agregarDestino(nodo3, 10);
		nodo3.agregarDestino(nodo4, 15);
		nodo4.agregarDestino(nodo0, 12);
		
//		For undirected graph
		nodo1.agregarDestino(nodo0, 15);
		nodo2.agregarDestino(nodo1, 20);
		nodo3.agregarDestino(nodo1, 25);
		nodo4.agregarDestino(nodo1, 30);
		nodo3.agregarDestino(nodo2, 10);
		nodo4.agregarDestino(nodo3, 15);
		nodo0.agregarDestino(nodo4, 12);
		
		GRAFO.agregarNodo(nodo0);
		GRAFO.agregarNodo(nodo1);
		GRAFO.agregarNodo(nodo2);
		GRAFO.agregarNodo(nodo3);
		GRAFO.agregarNodo(nodo4);
		
		DEMANDA[0][1] = 20;
		DEMANDA[0][2] = 30;
		DEMANDA[0][4] = 20;
		DEMANDA[1][0] = 10;
		DEMANDA[1][3] = 20;
		DEMANDA[2][1] = 5;
		DEMANDA[2][4] = 40;
		DEMANDA[3][0] = 15;
		DEMANDA[3][2] = 50;

		//Imprimir floyd warshall
		// System.out.println("Warshall");
		// ArrayList<ArrayList<ArrayList<Nodo>>>  paths=GRAFO.floydWarshallWithPath();
		// for (int i=0; i<paths.size(); i++){
		// 	for (int j=0; j<paths.get(i).size(); j++){
		// 		System.out.print("["+i+","+j+"]");
		// 		for (int k=0; k<paths.get(i).get(j).size(); k++){
		// 			System.out.print("->"+paths.get(i).get(j).get(k).darId());
		// 		}
		// 		System.out.println();
		// 	}
		// 	System.out.println();
		// }

		
		System.out.println("DEMANDA INICIAL");
		for (int i = 0; i < VERTICES; i++) {
			for (int j = 0; j < VERTICES; j++) {
				System.out.print(DEMANDA[i][j]+",");
			}
			System.out.println();
		}
		//ourAlgorithm(RANDOM_TRIES, VERTICES, GRAFO, DEMANDA);
		ArrayList<ArrayList<Integer>> initialSolution=DocumentAlgorithm.init(GRAFO, DEMANDA, 4,14);
		System.out.println("SOLUCION INICIAL");
		for (int i = 0; i < initialSolution.size(); i++) {
			for (int j = 0; j < initialSolution.get(i).size(); j++) {
				System.out.print(initialSolution.get(i).get(j)+",");
			}
			System.out.println();
		}
	}

	public static void ourAlgorithm(int RandomTries, int vertices, Grafo grafo, int[][] demanda) {
		boolean notEnd = true;
		ArrayList<ArrayList<Integer>> rutas = new ArrayList<>();
		//HashMap<Integer, String> rutaCompleta = new HashMap<Integer, String>();
		while (notEnd) {
			/*
			int columnNum = 0;
			int cant = 0;
			for (int i = 0; i < VERTICES; i++) {
				int val=0;
				for (int j = 0; j < VERTICES; j++) {
					val += DEMANDA[j][i];
				}
				if(val == 0) {
					rutaCompleta.put(columnNum, "true");
					cant++;
					if(cant == VERTICES-1) {
						rutaCompleta.put(columnNum, null);
					}
				}
				System.out.println(cant);
				columnNum++;
			}
			*/
			ArrayList<Integer> rutaMax = new ArrayList<>();
			for (int i = 0; i < RandomTries; i++) {
				int maxPoints = 0;
				int points = 0;
				int distancia = 0;
				int size = 0;
				ArrayList<Integer> rutaActual = new ArrayList<>();
				for (int j = 0; j < vertices; j++) {
					double rand = Math.random();
					if (rand > 0.5){
						size ++;
						rutaActual.add(j);
						if (rutaActual.size() >= 2) {
							int anterior = rutaActual.get(rutaActual.size() - 2);
							if (j - anterior != 1) {
								Nodo nodo1 = grafo.getNodo(anterior);
								Nodo nodo2 = grafo.getNodo(j);
								if (nodo1.isConnected(nodo2)) {
									distancia += nodo1.darCostoRuta(nodo2);
								} else {
									for (int k = anterior; k < j; k++) {
										nodo1 = grafo.getNodo(k);
										nodo2 = grafo.getNodo(k + 1);
										distancia += nodo1.darCostoRuta(nodo2);
									}
								}
							}
						}
					}
				}
				if (rutaActual.size() > 1) {
					int ultimo = rutaActual.get(rutaActual.size()-1);
					int primero = rutaActual.get(0);
					Nodo nodo1 = grafo.getNodo(ultimo);
					Nodo nodo2 = grafo.getNodo(primero);
					if (nodo1.isConnected(nodo2)) {
						distancia += nodo1.darCostoRuta(nodo2);
					} else {
						while (ultimo % vertices != primero) {
							nodo1 = grafo.getNodo(ultimo % vertices);
							nodo2 = grafo.getNodo((ultimo + 1) % vertices);
							distancia += nodo1.darCostoRuta(nodo2);
							ultimo++;
						}
					}
					for (int act : rutaActual) {
						for (int act2 : rutaActual) {
							points += demanda[act][act2];
							points += demanda[act2][rutaActual.get(0)];
						}
					}
					points = points - distancia;
					if (points > maxPoints) {
						maxPoints = points;
						rutaMax = rutaActual;
					}
				}
				else {
					i--;
				}
			}
			for (int i = 0; i < vertices; i++) {
				for (int j = 0; j < vertices; j++) {
					System.out.print(demanda[i][j]+",");
				}
				System.out.println();
			}
			rutas.add(rutaMax);
			for (int act : rutaMax) {
				for (int act2 : rutaMax) {
					demanda[act][act2] = 0;
					demanda[act2][rutaMax.get(0)] = 0;
				}
			}
			notEnd=false;
			for (int i = 0; i < vertices; i++) {
				for (int j = 0; j < vertices; j++) {
					if (demanda[i][j] != 0) {
						notEnd = true;
					}
				}
			}
		}
		System.out.println("###################################################");
		System.out.println("###################ALGORITHM#######################");
		System.out.println("###################################################");
		System.out.println("DEMANDA FINAL");
		for (int i = 0; i < VERTICES; i++) {
			for (int j = 0; j < VERTICES; j++) {
				System.out.print(DEMANDA[i][j]+",");
			}
			System.out.println();
		}
		System.out.println("RUTAS");
		for (ArrayList<Integer> ruta : rutas) {
			for (int estacion : ruta) {
				System.out.print(estacion+",");
			}
			System.out.println();
		}
	}
}

/*
 * rutaActual.add(rand); while(notEnd) { int[] actual = DEMANDA[rand]; int
 * cantDemGlob = 0; for(int[] demGlobb : DEMANDA) { for(int glob : demGlobb) {
 * cantDemGlob += glob; } } if(cantDemGlob == 0) { System.exit(0); } int cantDem
 * = 0; while(cantDem == 0) { for(int demLoc : actual) { cantDem += demLoc; }
 * if(cantDem == 0) { rand = (int) Math.random()*VERTICES; actual =
 * DEMANDA[rand]; } } boolean cond = true; int next=0; while(cond) { int tryNext
 * = (int) Math.random()*(VERTICES-rand); if(actual[rand+tryNext] != 0) { cond =
 * false; next = rand+tryNext; rutaActual.add(next); } } points += actual[next];
 * if(next == VERTICES-1) { points += DEMANDA[next][rutaActual.get(0)]; notEnd =
 * false; } }
 */
