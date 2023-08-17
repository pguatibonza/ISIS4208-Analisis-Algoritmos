public class Arco {
    int capacidad;
    int fuente;
    int destino;
    int flujo;

    public Arco( int fuente, int destino,int capacidad) {
        this.capacidad = capacidad;
        this.fuente = fuente;
        this.destino = destino;
    }
    public Arco(int fuente,int destino){
        this.fuente=fuente;
        this.destino=destino;
    }
}
