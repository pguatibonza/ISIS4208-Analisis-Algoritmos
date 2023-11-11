package main;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * DocumentAlgorithm
 */
public class DocumentAlgorithm {

    public static ArrayList<ArrayList<Integer>> init(Grafo graph, int[][] demand, int numberRoutes){
        int vertices=graph.getNodos().size();
		//Create a list with all shortest paths
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
		
		List<MatrixElement> matrixList=new ArrayList<>();
		
		for(int i = 0; i < vertices; i++) {
			for (int j = 0; j < vertices; j++) {
				Nodo nodo1 = graph.getNodo(i);
				Nodo nodo2 = graph.getNodo(j);
				if(nodo1.isConnected(nodo2))
				{
					usageProbability[i][j] += ((float)(edgeUsage[i][j])/totalEdge);
					matrixList.add(new MatrixElement(usageProbability[i][j], i, j));
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
		//Sort usage probability highest to lowest
		Collections.sort(matrixList,Collections.reverseOrder());
		System.out.println("edge probability sorted");
		for (int i = 0; i < matrixList.size(); i++) {
			System.out.println(matrixList.get(i).usageProbability + " " + matrixList.get(i).i+ " " + matrixList.get(i).j);
		}
		//Create solution list
		ArrayList<ArrayList<Integer>> solutionList = new ArrayList<ArrayList<Integer>>();
		//Final
		int end=0;
		while(end != numberRoutes) {
			
			
			//selected nodes
			boolean[] selectedNodes = new boolean[vertices];
			//selected edges
			boolean[][] selectedEdges = new boolean[vertices][vertices];

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

			//first element route
            ArrayList<Integer> route=new ArrayList<>();
            route.add(randomEdge[0]);
			route.add(randomEdge[1]);

			//mark selected nodes
			selectedNodes[randomEdge[0]] = true;
			selectedNodes[randomEdge[1]] = true;
			//mark selected edges
			selectedEdges[randomEdge[0]][randomEdge[1]] = true;
			selectedEdges[randomEdge[1]][randomEdge[0]] = true;


			while(end2 != randomNode) {
                
				int[] highestUsageEdge= new int[2];
				//The edge with the highest usage statistics among the candidate edges is selected as the new edge
				for (MatrixElement usageEdge : matrixList) {

					if (!selectedEdges[usageEdge.i][usageEdge.j]){
						randomEdge[0]=usageEdge.i;
						randomEdge[1]=usageEdge.j;
						highestUsageEdge[0]=usageEdge.i;
						highestUsageEdge[1]=usageEdge.j;
						break;
					}
				}
                 rand=Math.random();
                //add route to the beginning
                if (rand>0.5){
					ArrayList<Integer> tempRoute=new ArrayList<>();

					ArrayList<Nodo> shortestPath=SPList.get(highestUsageEdge[1]).get(route.get(0));
					for (int i = 0; i < shortestPath.size(); i++) {
						tempRoute.add(shortestPath.get(i).darId() );
					}
					for (int i = 1; i < route.size(); i++) {
						tempRoute.add(route.get(i));
					}
					route=tempRoute;
                }
                //add route to the end	
                else{
					
					ArrayList<Nodo> shortestPath=SPList.get(route.get(route.size()-1)).get(highestUsageEdge[0]);
					for (int i = 1; i < shortestPath.size(); i++) {
						route.add(shortestPath.get(i).darId() );
					}
					route.add(highestUsageEdge[1]);
                }
                end2++;
                

			}
			solutionList.add(route);

			//FALTA NEW ROUTE
			end ++;
		}

		return solutionList;
		
	}
}
