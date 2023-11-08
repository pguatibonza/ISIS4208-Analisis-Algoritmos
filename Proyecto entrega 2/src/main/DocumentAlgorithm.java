package main;

import java.util.ArrayList;

/**
 * DocumentAlgorithm
 */
public class DocumentAlgorithm {

    public static void init(Grafo graph, int[][] demand, int numberRoutes){
        int vertices=graph.getNodos().size();
		//Create a list with all shortest paths
		// PENDING
		//Calculate total demand

        ArrayList<ArrayList<ArrayList<Nodo>>> SPList=graph.floydWarshallWithPath();
		int totalDemand = 0;
        
		for(int i = 0; i < vertices; i++) {
			for (int j = 0; j < vertices; j++) {
				totalDemand += demand[i][j];
			}
		}
		//Calculate edge usage
		int[][] edgeUsage = new int[vertices][vertices];
		int totalEdge = 0;
		for(int i = 0; i < vertices; i++) {
			for (int j = 0; j < vertices; j++) {
				Nodo nodo1 = graph.getNodo(i);
				Nodo nodo2 = graph.getNodo(j);
				if(nodo1.isConnected(nodo2))
				{
					edgeUsage[i][j] += demand[i][j];
					edgeUsage[j][i] += demand[i][j];
					totalEdge += demand[i][j]*2;
				}
			}
		}
		
		System.out.println("edge usage");
		for (int i = 0; i < vertices; i++) {
			for (int j = 0; j < vertices; j++) {
				System.out.print(edgeUsage[i][j]+",");
			}
			System.out.println();
		}
		
		//Calculate usage probability
		float[][] usageProbability = new float[vertices][vertices];
		for(int i = 0; i < vertices; i++) {
			for (int j = 0; j < vertices; j++) {
				Nodo nodo1 = graph.getNodo(i);
				Nodo nodo2 = graph.getNodo(j);
				if(nodo1.isConnected(nodo2))
				{
					usageProbability[i][j] += ((float)(edgeUsage[i][j])/totalEdge);
				}
			}
		}
		
		System.out.println("edge probability");
		for (int i = 0; i < vertices; i++) {
			for (int j = 0; j < vertices; j++) {
				System.out.print(usageProbability[i][j]+",");
			}
			System.out.println();
		}
		
		//Final
		int end=0;
		while(end != numberRoutes+100) {
			//random edge
			float prob=0;
			double rand = Math.random();
			boolean found = false;
			int[] randomEdge = new int[2];
			for (int i = 0; i < vertices && !found; i++) {
				for (int j = 0; j < vertices && !found; j++) {
					if(usageProbability[i][j] != 0) {
						prob += usageProbability[i][j];
						if(prob >= rand) {
							found = true;
							randomEdge[0] = i;
                            randomEdge[1]=j;
						}
					}
				}
			}
			System.out.println("edge selected: " + randomEdge);
			//random node
			int randomNode = (int)(Math.random()*vertices);
			System.out.println("node selected: " + randomNode);
			int end2 = -1;
			while(end2 != randomNode) {
				// FALTA EXTEND ROUTE

			}
			//FALTA NEW ROUTE
			end ++;
		}
		
	}
}