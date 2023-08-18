import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

public class Grafo {
    private int n; //numero de vertices
    private ArrayList<Arco>[] adyacentes;

    public Grafo(int n){
        this.n = n;
        adyacentes=new ArrayList[n];
        for(int i=0; i<n; i++){
            adyacentes[i]=new ArrayList<Arco>();
        }  

    }

    void addArco(int fuente, int destino,int capacidad){
        Arco arco=new Arco( fuente, destino,capacidad);
        adyacentes[fuente].add(arco);
    }
    void addArco(int fuente,int destino){
        Arco arco=new Arco(fuente, destino);
        adyacentes[fuente].add(arco);
    }

    //implementar bfs
    public Boolean bfs(int[]ruta,int origen,int destino ){
        Boolean visitados[]=new Boolean[n];
        for(int i=0; i<n; i++){
            visitados[i]=false;
        }
        Queue<Integer> pendientes=new LinkedList<>();

        pendientes.add(origen);
        while(!pendientes.isEmpty()){
            int actual=pendientes.poll();
            visitados[actual]=true;

            for(Arco arco: adyacentes[actual]){
                if(!visitados[arco.destino] && arco.capacidad>arco.flujo){
                    pendientes.add(arco.destino);  
                    ruta[arco.destino]=actual;   
                }
            }
        }
        return visitados[destino];
    }
    public int flujoMaximo(int origen, int destino){
        int[] ruta = new int[n];
        int flujoMaximo = 0;

        while(bfs(ruta,origen,destino)){
            int flujoActual=Integer.MAX_VALUE;

            for(int v=destino;v!=origen;v=ruta[v]){
                int u=ruta[v];

                for(Arco arco: adyacentes[u]){
                    if(arco.destino==v){
                        flujoActual=Math.min(flujoActual,arco.capacidad-arco.flujo);
                        break;
                    }
                }
            }
            for(int v=destino;v!=origen;v=ruta[v]){
                int u=ruta[v];

                for(Arco arco: adyacentes[v]){
                    if(arco.destino==u){
                        arco.flujo-=flujoActual;
                    }
                }

                for(Arco arco: adyacentes[u]){
                    if(arco.destino==v){
                        arco.flujo+=flujoActual;
                    }
                }

                flujoMaximo+=flujoActual;

            }

        }   
        return flujoMaximo;

    }

}