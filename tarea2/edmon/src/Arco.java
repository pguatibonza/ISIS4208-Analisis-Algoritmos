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
    
    public int getFlujo() {
    	return this.flujo;
    }
    
    public int getFuente() {
    	return this.fuente;
    }
    
    public int getCapacidad() {
    	return this.capacidad;
    }
    
    public int getDestino() {
    	return this.fuente;
    }
}
