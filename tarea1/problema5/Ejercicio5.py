from pathlib import Path

class UnionFind:
    def __init__(self, n):
        self.parent = list(range(n))
        self.rank = [0] * n

    def find(self, x):
        if self.parent[x] != x:
            self.parent[x] = self.find(self.parent[x])
        return self.parent[x]

    def union(self, x, y):
        root_x = self.find(x)
        root_y = self.find(y)
        if root_x != root_y:
            if self.rank[root_x] > self.rank[root_y]:
                self.parent[root_y] = root_x
            else:
                self.parent[root_x] = root_y
                if self.rank[root_x] == self.rank[root_y]:
                    self.rank[root_y] += 1

def kruskal_mst(graph):
    n = len(graph)
    edges = []
    for u in range(n):
        for v, cost in graph[u]:
            edges.append((u, v, cost))
    edges.sort(key=lambda x: x[2])

    mst = []
    union_find = UnionFind(n)

    for u, v, cost in edges:
        if union_find.find(u) != union_find.find(v):
            union_find.union(u, v)
            mst.append((u, v, cost))

    return mst

# Lector de txt en la misma carpeta
def poblarGrafo():
    p = Path(__file__).with_name('input.txt')
    with p.open('r') as file:
        for line in file:
            if line.startswith("#"):
                continue 
            elif line is not "":
                content = line.split("-")
                graph[int(content[0])].append((int(content[1]),int(content[2])))
                graph[int(content[1])].append((int(content[0]),int(content[2])))


# Ejemplo de grafo de la ciudad (formato: {origen: [(destino, costo)]})
graph = {
    0: [(1, 3), (2, 5)],
    1: [(2, 1), (3, 6)],
    2: [(3, 4)],
    3: [(4, 2)],
    4: []
}

# Obtener el Bosque de Expansión Mínima (Minimum Spanning Forest)
mst = kruskal_mst(graph)

# Identificar las aristas a convertir en doble vía
edges_to_convert = [(u, v, cost) for u, v, cost in mst]

# Calcular el costo total de conversión
total_cost = sum(cost for _, _, cost in edges_to_convert)

# Resultados
print("Aristas a convertir en doble vía:")
for u, v, cost in edges_to_convert:
    print(f"{u} -> {v}, Costo: {cost}")
print("Costo total de conversión:", total_cost)