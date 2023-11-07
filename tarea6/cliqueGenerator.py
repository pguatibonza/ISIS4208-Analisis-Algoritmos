import random

def check_if_edge_changes_max_clique(graph, u, v,clique):
    # Copia el grafo para no modificar el original
    temp_graph = {node: set(neighbors) for node, neighbors in graph.items()}

    # Agrega la nueva arista temporalmente
    temp_graph[u].add(v)
    temp_graph[v].add(u)

    # Encuentra el máximo clique que incluye a u, v y sus nodos adyacentes
    max_clique = set([u, v])
    neighbors_u = set(graph[u])
    neighbors_v = set(graph[v])
    common_neighbors = neighbors_u & neighbors_v

    for node in common_neighbors:
        max_clique.add(node)
        for neighbor in set(temp_graph[node]) - max_clique:
            if all(neigh in max_clique for neigh in temp_graph[neighbor]):
                max_clique.add(neighbor)
    return len(max_clique)<len(clique)


def generate_graph(n, k):
    if k > n:
        raise ValueError("El tamaño del clique máximo (k) debe ser menor que el número de nodos (n)")

    # Crear un conjunto de nodos
    nodes = set(range(0, n ))

    # Crear un conjunto para el clique máximo
    clique = set(random.sample(nodes, k))

    # Inicializar un conjunto para las aristas
    edges = set()

    # Conectar todos los nodos en el clique máximo
    for u in clique:
        for v in clique:
            if u != v:
                edges.add((u, v))
    print(clique)
    
    graph = {}
    for i in nodes:
        graph[i] = set()

    for u, v in edges:
        graph[u].add(v)
        graph[v].add(u)

     # Generar aristas aleatorias entre nodos que no están en el clique máximo
    for u in nodes:
        for v in nodes:
            if u != v and k<n and  check_if_edge_changes_max_clique(graph, u, v,clique):
                if random.random() < 0.2:
                    edges.add((u, v))
                    graph[u].add(v)
                    graph[v].add(u)
                    
    
    return graph


# Ejemplo de uso
n = 11 # Número de nodos
k = 3   # Tamaño del clique máximo (debe ser menor que n)
graph = generate_graph(n, k)

# Imprimir el grafo
print(graph)