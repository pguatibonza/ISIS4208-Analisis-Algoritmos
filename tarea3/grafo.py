import random
import networkx as nx
import csv
lista1=[]
lista2=[]

def generar_grafo_aleatorio(num_vertices):
    
    if num_vertices <= 1:
        raise ValueError("El número de vértices debe ser al menos 2.")

    grafo = nx.Graph()

    # Agregar vértices al grafo
    for i in range(num_vertices):
        grafo.add_node(i)
        

    # Conectar nodos aleatoriamente para garantizar que el grafo sea conexo
    for i in range(num_vertices - 1):
        grafo.add_edge(i, i + 1)
        lista1.append(i)
        lista2.append(i+1)

    # Agregar aristas adicionales de manera aleatoria
    num_aristas_adicionales = random.randint(0, 10)
    while num_aristas_adicionales > 0:
        u = random.randint(0, num_vertices - 1)
        v = random.randint(0, num_vertices - 1)
        if u != v and not grafo.has_edge(u, v):
            grafo.add_edge(u, v)
            lista1.append(u)
            lista2.append(v)
            num_aristas_adicionales -= 1

    return grafo

def guardar_grafo_en_csv(grafo, nombre_archivo):
    with open(nombre_archivo, 'w', newline='') as archivo_csv:
        writer = csv.writer(archivo_csv)
        writer.writerow(['Nodo1\tNodo2'])

        for i in range(len(lista1)):
            string=str(lista1[i]) + '\t'+ str(lista2[i])
            writer.writerow([string])

if __name__ == "__main__":
    num_vertices = 20
    grafo_aleatorio = generar_grafo_aleatorio(num_vertices)

    nombre_archivo_csv = "grafo_conexiones.csv"
    guardar_grafo_en_csv(grafo_aleatorio, nombre_archivo_csv)
    print(f"Archivo CSV '{nombre_archivo_csv}' creado.")
