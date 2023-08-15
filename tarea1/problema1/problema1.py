def six_degrees(graph):
    initial_node=next(iter(graph))

    visited=set()
    queue=[]
    queue.append(initial_node)

    contador=6

    while queue and contador > 0:
        node=queue.pop()
        for i in graph[node]:
            if i not in visited:
                visited.add(i)
                queue.append(i)
        contador-=1
    return len(visited)==len(graph)

#lee la entrada a partir de un archivo y crea el grafo
def read_graph(file):
    graph = {}
    with open(file, 'r') as f:
        for line in f:
            line=line.replace(':',',' ).replace('\n','').replace(" ","")
            lista=line.split(',')
            key=lista[0]
            val=lista[1:]
            graph[key]=val
    return graph

def main():
    print("Ingrese el nombre del archivo que contiene el grafo")
    file=input()
    graph =read_graph(file)
    print("Para el grafo : ",graph)
    if six_degrees(graph):
        print('La teoria de los 6 grados de separacion es cierta')
    else:
        print('La teoria de los 6 grados de separacion no es cierta')
main()
