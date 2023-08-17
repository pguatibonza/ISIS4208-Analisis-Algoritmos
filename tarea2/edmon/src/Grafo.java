import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Grafo {
    private int n; //numero de vertices
    private ArrayList<Arco>[] adyacentes;

    public Grafo(int n){
        this.n = n;
        adyacentes=new ArrayList[n];
        for(int i=1; i<=n; i++){
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
    public List<Integer> bfs(int origen){
        List<Integer> visitados=new ArrayList<Integer>();
        Set<Integer> pendientes=new HashSet<Integer>();
        pendientes.add(origen);
        while(!pendientes.isEmpty()){
            int actual=pendientes.iterator().next();
            pendientes.remove(actual);
            visitados.add(actual);
            for(Arco arco: adyacentes[actual]){
                if(!visitados.contains(arco.destino)){
                    pendientes.add(arco.destino);
                }
            }
        }
        return visitados;
    }

}