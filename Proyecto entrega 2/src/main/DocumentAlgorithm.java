package main;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * DocumentAlgorithm
 */
public class DocumentAlgorithm {




    
    public static ArrayList<ArrayList<Integer>> mainAlgorithm(Grafo graph, int[][] demand, int numberRoutes, int minNode, int maxNode, int targetScore){
    	
    	int A = 10; //Definir bien esta constante
    	int B = 10; //Definir bien esta constante
    	
    	ArrayList<ArrayList<Integer>> css = init(graph, demand, numberRoutes, minNode, maxNode);

		System.out.println(css);
		for(ArrayList<Integer> route : css) {
			for(int i=0 ; i<(route.size()-1); i++) {
				for(int j=i+1 ; j<route.size() ; j++) {
					if(route.get(i) == route.get(j)) {
						route.remove(j);
					}
				}
			}
		}
		System.out.println(css);
    	int sc = evaluate(A, B, css, graph, demand);
    	int sn = 0;
    	
    	while(sc <= targetScore) {
    		ArrayList<ArrayList<Integer>> possibleCSS = modifyRoute(css, graph, minNode, maxNode, numberRoutes);
    		if(isProper(possibleCSS, numberRoutes,minNode,maxNode)) {
    			sn = evaluate(A, B, possibleCSS, graph, demand);
    		}
    		css = HC(A, B, css, possibleCSS, graph, demand, sc, minNode, maxNode);
    		sc = evaluate(A, B, css, graph, demand);
    	}
    	return css;
    	
    }
	public static ArrayList<ArrayList<Integer>> init(Grafo graph, int[][] demand, int numberRoutes,int minNode,int maxNode){
        int vertices=graph.getNodos().size();
		//Create a list with all shortest paths
		//Calculate total demand

        ArrayList<ArrayList<ArrayList<Nodo>>> SPList=graph.floydWarshallWithPath();
		int totalDemand = 0;
        
		for(int i = minNode; i < maxNode; i++) {
			for (int j = 0; j < maxNode; j++) {
				totalDemand += demand[i][j];
			}
		}
		//Calculate edge usage
		int[][] edgeUsage = new int[maxNode][maxNode];
		int totalEdge = 0;
		for(int i = minNode; i < maxNode; i++) {
			for (int j = minNode; j < maxNode; j++) {
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
		for (int i = minNode; i < maxNode; i++) {
			for (int j = minNode; j < maxNode; j++) {
				System.out.print(edgeUsage[i][j]+",");
			}
			System.out.println();
		}
		
		//Calculate usage probability
		float[][] usageProbability = new float[maxNode][maxNode];
		
		List<MatrixElement> matrixList=new ArrayList<>();
		
		for(int i = minNode; i < maxNode; i++) {
			for (int j = minNode; j < maxNode; j++) {
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
		for (int i = minNode; i < maxNode; i++) {
			for (int j = minNode; j < maxNode; j++) {
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
		
		while(end != numberRoutes  ) {
			//selected nodes
			boolean[] selectedNodes = new boolean[maxNode];
			//selected edges
			boolean[][] selectedEdges = new boolean[maxNode][maxNode];

			//random edge
			float prob=0;
			double rand = Math.random();
			boolean found = false;
			int[] randomEdge = new int[2];
			for (int i = minNode; i < maxNode && !found; i++) {
				for (int j = minNode; j < maxNode && !found; j++) {
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
			int randomNode = (int)(Math.random()*maxNode);
			System.out.println("node selected: " + randomNode);
			int end2 = 0;

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

			int counter=0;
			while(end2 != randomNode  )   {
                
				int[] highestUsageEdge= new int[2];
				//The edge with the highest usage statistics among the candidate edges is selected as the new edge
				for (MatrixElement usageEdge : matrixList) {

					if (!selectedEdges[usageEdge.i][usageEdge.j]){
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
					tempRoute.add(highestUsageEdge[0]);
					selectedNodes[highestUsageEdge[0]]=true;
					
					for (int i = 0; i < shortestPath.size(); i++) {
						int id= shortestPath.get(i).darId();
						tempRoute.add(id );
						selectedNodes[id]=true;

						selectedEdges[id][tempRoute.get(tempRoute.size()-2)]=true;
						selectedEdges[tempRoute.get(tempRoute.size()-2)][id]=true;
						counter+=2;
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
						int id= shortestPath.get(i).darId();
						route.add(id );
						selectedNodes[id]=true;


						selectedEdges[id][route.get(route.size()-2)]=true;
						selectedEdges[route.get(route.size()-2)][id]=true;
						counter+=2;
						
					}
					route.add(highestUsageEdge[1]);
                }
                end2++;
			}
			solutionList.add(route);

			end ++;
		}

		return solutionList;
		
	}
    
    public static ArrayList<ArrayList<Integer>> HC(int A, int B, ArrayList<ArrayList<Integer>> css, ArrayList<ArrayList<Integer>> possibleCSS, Grafo graph, int[][] demand, int O2, int minNode, int maxNode){
    	int O2css = evaluate(A, B, possibleCSS, graph, demand);
    	int better = O2css;
    	ArrayList<ArrayList<Integer>> copy = css;
    	while(better > O2css) {
    		copy = css;
    		for(int i=0 ; i<copy.size() ; i++) {
    			for (int j = 0; j < copy.get(i).size(); j++) {
					double rand = Math.random();
        			if(rand > 0.8) { //Define a better limit for HC modification
        				ArrayList<Integer> copyRoute = copy.get(i);
        				copyRoute.set(j, possibleCSS.get(i).get(j));
        				copy.set(i, copyRoute);
        			}
				}
    		}
        	better = evaluate(A, B, copy, graph, demand);
    	}
    	return copy;
    }
    
    public static int evaluate(int A, int B, ArrayList<ArrayList<Integer>> css, Grafo graph, int[][] demand ) {
    	int scoreA = 0;
    	int scoreB = 0;
    	
    	for(ArrayList<Integer> route: css) {
    		for(int i=0 ; i<(route.size()- 1) ; i++) {
    			scoreA += demand[route.get(i)][route.get(i+1)]*graph.getNodo(route.get(i)).darCostoRuta(graph.getNodo(route.get(i+1)));
    		}
    	}
    	
    	int[][] transbordos = new int[demand[0].length][demand[0].length];
    	
    	for(int i=0 ; i<demand[0].length ; i++) {
    		for(int j=0 ; j<demand[0].length ; j++) {
    			transbordos[i][j] = demand[i][j];
        	}
    	}
    	
    	for(ArrayList<Integer> ruta : css) {
    		for(int i=0 ; i<ruta.size() ; i++) {
    			for(int j=i ; j<ruta.size() ; j++) {
    				transbordos[ruta.get(i)][ruta.get(j)] = 0;
    			}
    		}
    	}
    	
    	for(ArrayList<Integer> route: css) {
    		for(int i=0 ; i<route.size()-1 ; i++) {
    			scoreB += demand[route.get(i)][route.get(i+1)]*transbordos[route.get(i)][route.get(i+1)];
    		}
    	}

    	scoreA = A*scoreA;
    	scoreB = B*scoreB;
    	
    	return (scoreA + scoreB);
    }
    
    public static boolean isProper(ArrayList<ArrayList<Integer>> css, int numberRoutes,int minNode,int maxNode) {
    	//No backtracking check
    	for(ArrayList<Integer> route : css) {
    		ArrayList<Integer> alreadyVisited = new ArrayList<Integer>();
			for (int i = 0; i < route.size(); i++) {
				int station = route.get(i);
				for(int visited : alreadyVisited) {
					if(station == visited) {
						return false;
					}
				}
				alreadyVisited.add(station);
			}
		}
    	
    	//No exceed max number of stations in a route
    	for(ArrayList<Integer> route : css) {
    		int count = route.size();
			if(count < minNode || count > maxNode) {
				return false;
			}
		}
    	
    	return true;
    }
    
    public static ArrayList<ArrayList<Integer>> modifyRoute(ArrayList<ArrayList<Integer>> css, Grafo graph, int minNode, int maxNode, int numberRoutes){
    	ArrayList<ArrayList<Integer>> modifiedRoute = css;
    	int cost = 0;
    	for(ArrayList<Integer> route: css) {
    		for(int i=0 ; i<route.size()-1 ; i++) {
    			cost += graph.getNodo(route.get(i)).darCostoRuta(graph.getNodo(route.get(i+1)));
    		}
    	}
		//Select node not in route
    	int actualRoute = 0;
    	for(ArrayList<Integer> route : modifiedRoute) {
    		int[] selected = new int[maxNode];
    		for(int station : route) {
    			selected[station] = 1;
    		}
    		int cant = 0;
    		for(int val : selected) {
    			if(val == 0) {
    				cant ++;
    			}
    		}
    		if(cant != 0) {
        		double proportion = 1/cant;
    			double rand = Math.random();
        		int selectedNode = 0;
        		boolean flag = true;
        		for(int i=0 ; i<selected.length && flag ; i++) {
        			if(selected[i] == 0 && proportion > rand) {
        				selectedNode=i;
        				flag = false;
        			}
        			else {
        				proportion += proportion;
        			}
        		}
        		/*
        		for(int i=0 ; i<route.size() ; i++) {
        			ArrayList<Integer> newRoute = route;
        			newRoute.set(i, selectedNode);
        			System.out.println(route);
        			System.out.println(newRoute+"a");
        			int newCost = 0;
        	    	for(int j=0 ; j<(newRoute.size()-1) ; j++) {
        	    		System.out.println(j);
        	    		newCost += graph.getNodo(newRoute.get(j)).darCostoRuta(graph.getNodo(newRoute.get(j+1)));
        	    	}
        	    	if(newCost < cost) {
        	    		modifiedRoute.set(actualRoute, newRoute);
        	    	}
        		}
        		*/
        		actualRoute ++;
    		}
    		
    	}
    	actualRoute = 0;
    	/*
    	for(ArrayList<Integer> route : modifiedRoute) {
        	int count = route.size();
    		while(count > numberRoutes) {
    			int remove = (int)(Math.random()*route.size());
    			route.remove(remove);
    		}
    		modifiedRoute.set(actualRoute, route);
        	actualRoute ++;
    	}
		*/
    	return modifiedRoute;
    }
}
